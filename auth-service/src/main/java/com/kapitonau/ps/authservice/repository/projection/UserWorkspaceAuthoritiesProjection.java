package com.kapitonau.ps.authservice.repository.projection;

public interface UserWorkspaceAuthoritiesProjection {

    Long getWorkspaceId();
    String getRoleName();
    String getAuthorityName();
}
