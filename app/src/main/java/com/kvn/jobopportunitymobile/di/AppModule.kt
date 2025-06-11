package com.kvn.jobopportunitymobile.di

import com.kvn.jobopportunitymobile.data.network.JobApi
import com.kvn.jobopportunitymobile.data.repository.JobRepository
import com.kvn.jobopportunitymobile.presentation.viewmodel.HomeViewModel
import com.kvn.jobopportunitymobile.presentation.viewmodel.JobDetailViewModel
import com.kvn.jobopportunitymobile.presentation.viewmodel.SearchViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val appModule = module {
    // Network
    single {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()    }
    
    single {
        Retrofit.Builder()
            .baseUrl("https://job-opportunity-api.herokuapp.com/api/") // Update with actual API URL
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single {
        get<Retrofit>().create(JobApi::class.java)
    }

    // Repository
    single {
        JobRepository(get())
    }

    // ViewModels
    viewModel {
        HomeViewModel(get())
    }

    viewModel {
        SearchViewModel(get())
    }

    viewModel { parameters ->
        JobDetailViewModel(get(), parameters.get())
    }
}
