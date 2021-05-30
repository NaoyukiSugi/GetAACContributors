package com.example.getaaccontributors.api.config

class Environment(var serverConfig: ServerConfig) {

    fun getBaseUrl(): String {
        return serverConfig.apiBaseUrl()
    }
}
