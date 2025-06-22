package com.kapitonau.ps.refrenceservice.repository;

import com.kapitonau.ps.refrenceservice.model.ReferenceItemModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ReferenceItemRepository extends JpaRepository<ReferenceItemModel, Long> {

    @Query("""
            select ri from ReferenceItemModel ri 
                        where ri.isActive and ri.referenceType = :referenceType 
                                    and (:itemCode is null or ri.itemCode = :itemCode)
            """)
    List<ReferenceItemModel> findAllByReferenceTypeAndItemCode(String referenceType, String itemCode);


    @Query("""
        select ri from ReferenceItemModel ri where ri.isActive and ri.referenceItemId = :referenceItemId
        """)
    Optional<ReferenceItemModel> findByReferenceItemId(Long referenceItemId);

}
