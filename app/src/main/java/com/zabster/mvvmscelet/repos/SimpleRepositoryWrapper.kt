package com.zabster.mvvmscelet.repos

import com.zabster.mvvmscelet.net.RestApi
import kotlinx.coroutines.delay

interface SimpleRepository {
    suspend fun syncSomeData(): Boolean
}

class SimpleRepositoryImpl(api: RestApi) : SimpleRepository {
    override suspend fun syncSomeData(): Boolean {
        delay(2000)
        return true
    }
}