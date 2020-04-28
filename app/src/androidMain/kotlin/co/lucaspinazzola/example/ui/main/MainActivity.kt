package co.lucaspinazzola.example.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import co.lucaspinazzola.example.R
import co.lucaspinazzola.example.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        setupNavigation()
    }


    private fun setupNavigation() {
        val navController = findNavController(R.id.mainNavHostFragment)
        setupActionBarWithNavController(navController)
    }

    override fun onSupportNavigateUp() =
            findNavController(R.id.mainNavHostFragment).navigateUp()
}
