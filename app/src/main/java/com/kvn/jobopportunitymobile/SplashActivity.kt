package com.kvn.jobopportunitymobile

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : ComponentActivity() {
    
    companion object {
        // Duración del splash en milisegundos (3 segundos)
        private const val SPLASH_DELAY_MS = 3000L
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        
        // Deshabilitar botón atrás durante splash usando la API moderna
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // No hacer nada - deshabilita el botón atrás durante el splash
            }
        })
        
        // Usar coroutines para el delay, que respeta el ciclo de vida
        lifecycleScope.launch {
            delay(SPLASH_DELAY_MS)
            navigateToMain()
        }
    }
    
    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish() // Cerrar SplashActivity
        
        // Animación de transición usando la API moderna (API 34+)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            overrideActivityTransition(OVERRIDE_TRANSITION_OPEN, android.R.anim.fade_in, android.R.anim.fade_out)
        } else {
            // Fallback para versiones anteriores
            @Suppress("DEPRECATION")
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
    }
}