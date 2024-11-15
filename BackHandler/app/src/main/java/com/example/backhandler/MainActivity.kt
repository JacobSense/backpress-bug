package com.example.backhandler

import android.os.Bundle
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.navigation.fragment.NavHostFragment
import androidx.appcompat.app.AppCompatActivity
import com.example.backhandler.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val navHostFragment: NavHostFragment by lazy {
        supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
    }

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            Log.d("BackHandler", "MainActivity onBackPressedCallback called")

            /**
             * Desired behavior is to let the fragment NavController pop it's back stack until
             * there is nothing left, then close the app. However, any custom behavior here is not
             * intended to be executed when a lower level OnBackPressedCallback exists e.g. BackHandler
             */
            if (!navHostFragment.navController.popBackStack()) {
                finish()
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enableEdgeToEdge()

        val graph = navHostFragment.navController.navInflater.inflate(R.navigation.main_nav_graph)
        navHostFragment.navController.setGraph(graph, startDestinationArgs = null)
    }

}