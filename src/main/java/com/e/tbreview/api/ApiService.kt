package com.e.tbreview.api

import androidx.lifecycle.LiveData
import com.e.tbreview.api.response.ItemResponse
import com.e.tbreview.model.ItemModel
import com.e.tbreview.util.GenericApiResponse
import retrofit2.http.GET

interface ApiService {
    @GET("taboola-mobile-sdk/public/home_assignment/data.json")
    fun getListFromApi(): LiveData<GenericApiResponse<List<ItemResponse>>>
}