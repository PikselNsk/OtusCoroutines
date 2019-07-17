package info.javaway.otuscoroutines.model

import android.util.Log
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import info.javaway.otuscoroutines.retrofit.RickAndMortyService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.SocketTimeoutException

const val baseUrl = "https://rickandmortyapi.com/api/"

class CharacterRepository
    : BaseRepository<CharacterRepository.OnLoadCharactersListener, CharacterRepository.Result>() {

    private val apiRMService = createApiRMService()

    override suspend fun getCharacters(
        listener: OnLoadCharactersListener,
        page:String):Result? {
        val result: Response<CharacterContainer>
        try {
           result = apiRMService
                .getCharacters(page)
                .await()
            listener.onLoadSuccess(result.body()!!)
            return null
        } catch (e: SocketTimeoutException) {
            e.printStackTrace()
        }
        return null
    }

    suspend fun getCharacter(onLoadCharacterListener: OnLoadCharacterListener, id: kotlin.String): Character? {
        val result: Response<Character>
        try {
            result = apiRMService
                .getCharacter(id)
                .await()
            onLoadCharacterListener.onLoadChar(result.body())
            return result.body()
        } catch (e: SocketTimeoutException) {
            e.printStackTrace()
            Log.d("retrofit", "No internet connection")
        }
        return null
    }



    fun createApiRMService(): RickAndMortyService {
        val gson = GsonBuilder()
            .setLenient()
            .create()

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(httpClient)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        return retrofit.create(RickAndMortyService::class.java)
    }

    companion object {
        val instance = CharacterRepository()
    }

    data class Result(val characters: List<Character>?)

    interface OnLoadCharactersListener {
        fun onLoadSuccess(container: CharacterContainer)
        fun onLoadError(errorMessage: kotlin.String)
    }

    interface OnLoadCharacterListener{
        fun onLoadChar(character: Character?)
    }
}
