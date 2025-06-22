package com.kapitonau.ps.refrenceservice.controller;

import com.kapitonau.ps.apirequestlib.reference.api.ReferenceItemApi;
import com.kapitonau.ps.apirequestlib.reference.dto.ReferenceItemResponse;
import com.kapitonau.ps.refrenceservice.service.ReferenceItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReferenceItemController implements ReferenceItemApi {

    private final ReferenceItemService referenceItemService;

    @Override
    public List<ReferenceItemResponse> getAllReferenceItem(Long spaceId, String lang, String referenceType, String itemCode) {
        return referenceItemService.getAllReferenceItem(spaceId, lang, referenceType, itemCode);
    }

    @Override
    public ReferenceItemResponse getReferenceItem(Long spaceId, String lang, Long referenceItemId) {
        return referenceItemService.getReferenceItem(spaceId, lang, referenceItemId);
    }
}
