package info.javaway.otuscoroutines.retrofit

import info.javaway.otuscoroutines.model.Character
import info.javaway.otuscoroutines.model.CharacterContainer
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface RickAndMortyService {

    @GET("character/")
    fun getCharacters(@Query("page") page: String):Deferred<Response<CharacterContainer>>

    @GET("character/{id}")
    fun getCharacter(@Path("id") id:String):Deferred<Response<Character>>

}