package co.lucaspinazzola.example.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import co.lucaspinazzola.example.NamesSupplier
import co.lucaspinazzola.example.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupNavigation()
    }

    private fun setupNavigation() {
        val navController = findNavController(R.id.mainNavHostFragment)
        setupActionBarWithNavController(navController)
    }

    override fun onSupportNavigateUp() =
            findNavController(R.id.mainNavHostFragment).navigateUp()
}
