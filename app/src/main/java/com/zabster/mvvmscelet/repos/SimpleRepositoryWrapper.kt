package com.zabster.mvvmscelet.repos

import com.zabster.mvvmscelet.net.RestApi
import io.reactivex.Single
import retrofit2.Response

interface SimpleRepository {
    fun syncSomeData(): Single<Response<Boolean>>
}

class SimpleRepositoryImpl(api: RestApi): SimpleRepository {
    override fun syncSomeData(): Single<Response<Boolean>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}