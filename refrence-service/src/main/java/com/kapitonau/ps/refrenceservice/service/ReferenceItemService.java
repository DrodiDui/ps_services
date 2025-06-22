package com.kapitonau.ps.refrenceservice.service;

import com.kapitonau.ps.apirequestlib.reference.dto.ReferenceItemResponse;

import java.util.List;

public interface ReferenceItemService {
    List<ReferenceItemResponse> getAllReferenceItem(Long spaceId, String lang, String referenceType, String itemCode);

    ReferenceItemResponse getReferenceItem(Long spaceId, String lang, Long referenceItemId);
}
