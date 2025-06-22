package com.kapitonau.ps.refrenceservice.repository.projections;

import com.kapitonau.ps.refrenceservice.model.ReferenceItemModel;
import com.kapitonau.ps.refrenceservice.model.ReferenceMetadataModel;

public interface ReferenceItemProjections {

    ReferenceItemModel getReferenceItem();
    ReferenceMetadataModel getReferenceMetadata();
}
