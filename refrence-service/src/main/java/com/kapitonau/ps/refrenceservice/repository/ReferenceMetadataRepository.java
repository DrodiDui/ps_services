package com.kapitonau.ps.refrenceservice.repository;

import com.kapitonau.ps.refrenceservice.model.ReferenceMetadataModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReferenceMetadataRepository extends JpaRepository<ReferenceMetadataModel, Long> {

    @Query("""
        select rm from ReferenceMetadataModel rm where
                rm.referenceItemId = :referenceItemId
        """)
    List<ReferenceMetadataModel> findAllByReferenceItemIdAndWorkspaceId(Long referenceItemId, Long workspaceId);
}
