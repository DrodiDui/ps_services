package com.kapitonau.projectservice.service.base;

import com.kapitonau.commonspring.exception.CommonServiceException;
import com.kapitonau.commonspring.utils.ResourceServerUtil;
import com.kapitonau.projectservice.event.sender.GitRepositoryAddEventSender;
import com.kapitonau.projectservice.event.sender.ProjectAddEventSender;
import com.kapitonau.projectservice.model.ProjectModel;
import com.kapitonau.projectservice.repository.ProjectRepository;
import com.kapitonau.projectservice.service.ProjectService;
import com.kapitonau.projectservice.service.SpaceService;
import com.kapitonau.projectstudio.bean.cache.ReferenceCache;
import com.kapitonau.projectstudio.event.GtiRepositoryAddEventDto;
import com.kapitonau.projectstudio.event.ProjectAddEventDto;
import com.kapitonau.projectstudio.projectservice.dto.project.ProjectPostRequest;
import com.kapitonau.projectstudio.projectservice.dto.project.ProjectResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

import static org.springframework.context.i18n.LocaleContextHolder.getLocale;

@Service
@RequiredArgsConstructor
public class BaseProjectService implements ProjectService {


    private final MessageSource messageSource;
    private final ReferenceCache referenceCache;
    private final ModelMapper modelMapper;
    private final ProjectRepository projectRepository;
    private final SpaceService spaceService;
    private final ProjectAddEventSender projectAddEventSender;
    private final GitRepositoryAddEventSender gitRepositoryAddEventSender;

    @Override
    public List<ProjectResponse> getProjects() {
        return projectRepository.findAll()
                .stream()
                .map(src -> modelMapper.map(src, ProjectResponse.class))
                .toList();
    }

    @Override
    public ProjectResponse getProject(Long projectId) {
        return projectRepository.findById(projectId)
                .map(src -> modelMapper.map(src, ProjectResponse.class))
                .orElseThrow(() ->
                        new CommonServiceException("PROJECT_SERVICE", messageSource.getMessage("PROJECT_SERVICE_1", null, getLocale()))
                );
    }

    @Override
    @Transactional("projectPlatformTransactionManager")
    public ProjectResponse createProject(ProjectPostRequest body) {
        spaceService.checkSpaceExistingOrGet(body.getSpaceId());
        if (projectRepository.findByTitleAndSpaceId(body.getTitle(), body.getSpaceId()).isPresent()) {
            throw new CommonServiceException("PROJECT_SERVICE", messageSource.getMessage("PROJECT_SERVICE_2", new Object[]{body.getTitle()}, getLocale()));
        }
        ProjectModel model = modelMapper.map(body, ProjectModel.class);
        model.setProjectStatusId(referenceCache.getReferenceItemByTypeAndCode("PROJECT_STATUS", "ACTIVE").referenceItemId());
        model.setCreatedDate(LocalDate.now());
        model = projectRepository.save(model);

        projectAddEventSender.send(new ProjectAddEventDto(model.getProjectId()));
        if (body.getCreateGitRepository()) {
            gitRepositoryAddEventSender.send(new GtiRepositoryAddEventDto(
                    model.getProjectId(),
                    model.getTitle(),
                    model.getSpaceId(),
                    ResourceServerUtil.getUserId(),
                    ZonedDateTime.now(),
                    body.getGitProviderCode()
            ));
        }

        return modelMapper.map(model, ProjectResponse.class);
    }
    
}
