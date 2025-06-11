package com.kvn.jobopportunitymobile

import android.app.Application
import com.kvn.jobopportunitymobile.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class JobOpportunityApp : Application() {
    override fun onCreate() {
        super.onCreate()
        
        startKoin {
            androidLogger()
            androidContext(this@JobOpportunityApp)
            modules(appModule)
        }
    }
}
