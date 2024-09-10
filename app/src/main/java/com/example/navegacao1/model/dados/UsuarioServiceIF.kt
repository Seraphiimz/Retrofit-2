package com.example.navegacao1.model.dados

import retrofit2.http.GET
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.Path

interface UsuarioServiceIF {

    @GET("usuarios")
    suspend fun listar(): List<Usuario>

    @GET("usuarios/{id}")
    suspend fun buscarPorId(@Path("id") id: String): Usuario

    @POST("usuarios")
    suspend fun adicionar(@Body usuario: Usuario): Usuario

    @DELETE("usuarios/{id}")
    suspend fun remover(@Path("id") id: String)
}