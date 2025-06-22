package com.kapitonau.ps.refrenceservice.service.base;

import com.kapitonau.ps.apirequestlib.reference.dto.ReferenceItemResponse;
import com.kapitonau.ps.apirequestlib.reference.dto.ReferenceMetadataResponse;
import com.kapitonau.ps.commonspringlib.exception.CommonServiceException;
import com.kapitonau.ps.refrenceservice.model.ReferenceMetadataModel;
import com.kapitonau.ps.refrenceservice.repository.ReferenceItemRepository;
import com.kapitonau.ps.refrenceservice.repository.ReferenceMetadataRepository;
import com.kapitonau.ps.refrenceservice.service.ReferenceItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BaseReferenceItemService implements ReferenceItemService {

    private final ReferenceItemRepository referenceItemRepository;
    private final ReferenceMetadataRepository referenceMetadataRepository;

    @Override
    public List<ReferenceItemResponse> getAllReferenceItem(Long spaceId, String lang, String referenceType, String itemCode) {

        List<ReferenceItemResponse> referenceItemResponses = new ArrayList<>();
        referenceItemRepository.findAllByReferenceTypeAndItemCode(referenceType, itemCode).forEach(referenceItem -> {
            List<ReferenceMetadataResponse> metamodel = referenceMetadataRepository.findAllByReferenceItemIdAndWorkspaceId(referenceItem.getReferenceItemId(), spaceId)
                    .stream()
                    .map(it -> new ReferenceMetadataResponse(
                            it.getId(),
                            it.getReferenceItemId(),
                            it.getWorkspaceId(),
                            it.getMetadataName(),
                            it.getMetadata()
                    ))
                    .toList();
            referenceItemResponses.add(new ReferenceItemResponse(
                    referenceItem.getReferenceItemId(),
                    referenceItem.getReferenceType(),
                    referenceItem.getItemCode(),
                    referenceItem.getDescription().getOrDefault(lang, referenceItem.getDescription().get("ru")).toString(),
                    metamodel
            ));
        });

        return referenceItemResponses;
    }

    @Override
    public ReferenceItemResponse getReferenceItem(Long spaceId, String lang, Long referenceItemId) {
        return referenceItemRepository.findByReferenceItemId(referenceItemId)
                .map(referenceItem -> {
                    List<ReferenceMetadataResponse> metamodel = referenceMetadataRepository.findAllByReferenceItemIdAndWorkspaceId(referenceItem.getReferenceItemId(), spaceId)
                            .stream()
                            .map(it -> new ReferenceMetadataResponse(
                                    it.getId(),
                                    it.getReferenceItemId(),
                                    it.getWorkspaceId(),
                                    it.getMetadataName(),
                                    it.getMetadata()
                            ))
                            .toList();
                    return new ReferenceItemResponse(
                            referenceItem.getReferenceItemId(),
                            referenceItem.getReferenceType(),
                            referenceItem.getItemCode(),
                            referenceItem.getDescription().getOrDefault(lang, referenceItem.getDescription().get("ru")).toString(),
                            metamodel
                    );
                })
                .orElseThrow(() -> new CommonServiceException("REFERENCE_SERVCIE", "Reference item not found"));
    }
}
