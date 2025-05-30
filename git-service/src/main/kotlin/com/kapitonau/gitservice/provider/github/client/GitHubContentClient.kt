package com.kapitonau.gitservice.provider.github.client

import com.kapitonau.gitservice.provider.github.dto.GitHubContentProviderDto
import jakarta.annotation.PostConstruct
import org.springframework.core.ParameterizedTypeReference
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

@Component
class GitHubContentClient {

    private lateinit var webClient: WebClient

    @PostConstruct
    fun init() {
        webClient = WebClient
            .builder()
            .baseUrl("https://api.github.com/repos/")
            .build();
    }


    fun getRepositoryContents(owner: String, name: String, path: String, accessToken: String) : List<GitHubContentProviderDto> {
        return webClient.get()
            .uri("/${owner}/${name}/contents/${path}")
            .header("Authorization", "Bearer " + accessToken)
            .header("Accept", "application/vnd.github+json")
            //.header("X-GitHub-Api-Version", " 2022-11-28")
            .retrieve()
            .bodyToMono<MutableList<GitHubContentProviderDto>>(object :
                ParameterizedTypeReference<MutableList<GitHubContentProviderDto>>() {})
            .block()!!;
    }

    fun getRepositoryContent(owner: String, name: String, path: String, accessToken: String) : GitHubContentProviderDto {
        return webClient.get()
            .uri("/${owner}/${name}/contents/${path}")
            .header("Authorization", "Bearer " + accessToken)
            .header("Accept", "application/vnd.github+json")
            //.header("X-GitHub-Api-Version", " 2022-11-28")
            .retrieve()
            .bodyToMono<GitHubContentProviderDto>(object :
                ParameterizedTypeReference<GitHubContentProviderDto>() {})
            .block()!!;
    }

}
