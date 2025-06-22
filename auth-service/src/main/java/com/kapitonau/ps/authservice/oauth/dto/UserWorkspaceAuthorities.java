package com.kapitonau.ps.authservice.oauth.dto;


import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserWorkspaceAuthorities {

    private Long workspaceId;
    private String roleName;
    private List<String> authorities;

}
