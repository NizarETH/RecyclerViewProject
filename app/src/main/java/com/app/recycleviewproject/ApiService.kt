package com.app.recycleviewproject

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("photos")
     fun getData(): Call<List<DataModel>>
}
