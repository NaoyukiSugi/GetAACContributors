package com.example.getaaccontributors.api.github

import com.example.getaaccontributors.api.config.ServerConfig

class GitHubServerConfig : ServerConfig() {

    override fun domain(): String {
        return DOMAIN
    }

    override fun scheme(): String {
        return API_SCHEME
    }

    override fun apiHost(): String {
        return API_HOST
    }

    private companion object {
        private const val API_HOST = "api"
        private const val DOMAIN = "github.com"
        private const val API_SCHEME = "https"
    }
}
