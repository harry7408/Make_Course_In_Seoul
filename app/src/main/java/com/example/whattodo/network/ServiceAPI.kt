package com.example.whattodo.network

import retrofit2.http.GET

interface ServiceAPI {
     @GET("")
     fun Join()
}