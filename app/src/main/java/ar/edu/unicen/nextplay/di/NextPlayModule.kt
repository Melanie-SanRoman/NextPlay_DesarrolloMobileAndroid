package ar.edu.unicen.nextplay.di

import android.content.Context
import androidx.room.Room
import ar.edu.unicen.nextplay.BuildConfig
import ar.edu.unicen.nextplay.ddl.data.NextPlayApi
import ar.edu.unicen.nextplay.ddl.data.dao.GameFavItemDao
import ar.edu.unicen.nextplay.ddl.data.local.GameFavDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class NextPlayModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }
    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://api.rawg.io/api/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideNextPlayApi(
        retrofit: Retrofit
    ): NextPlayApi {
        return retrofit.create(NextPlayApi::class.java)
    }

    @Provides
    @Singleton
    @Named("apiKey")
    fun provideApiKey(): String {
        return BuildConfig.RAWG_API_KEY
    }

    @Provides
    @Singleton
    fun provideGameFavDatabase(
        @ApplicationContext
        context: Context
    ): GameFavDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = GameFavDatabase::class.java,
            name = "game_fav_db"
        ).build(
        )
    }

    @Provides
    fun provideGameFavDao(
        database: GameFavDatabase
    ): GameFavItemDao{
        return database.gameFavDao()
    }
}