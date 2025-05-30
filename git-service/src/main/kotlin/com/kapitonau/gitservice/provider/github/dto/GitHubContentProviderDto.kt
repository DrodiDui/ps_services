package com.kapitonau.gitservice.provider.github.dto

data class GitHubContentProviderDto(
    var name: String? = null,
    var path: String? = null,
    var sha: String? = null,
    var size: Int = 0,
    var url: String? = null,
    var html_url: String? = null,
    var git_url: String? = null,
    var download_url: String? = null,
    var type: String? = null,
    var content: String? = null,
    var encoding: String? = null,
    var links: Links? = null
) {
}

data class Links(
    var self: String? = null,
    var git: String? = null,
    var html: String? = null
) {

}
