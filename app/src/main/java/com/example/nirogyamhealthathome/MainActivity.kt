package com.example.nirogyamhealthathome

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import java.nio.channels.AsynchronousFileChannel.open

class MainActivity : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            // Create an Intent to start SecondActivity
            val intent = Intent(this@MainActivity, RemedyApiActivity::class.java)
            startActivity(intent)
        }

        button2.setOnClickListener {
            // Create an Intent to start SecondActivity
            val intent = Intent(this@MainActivity, ExerciseApiActivity::class.java)
            startActivity(intent)
        }

        button3.setOnClickListener {
            // Create an Intent to start AnotherActivity
            val intent = Intent(this@MainActivity, RecipeeApiActivity::class.java)
            startActivity(intent)
        }

        toggle= ActionBarDrawerToggle(this,drawer_layout,R.string.open,R.string.close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navigationView: NavigationView = findViewById(R.id.nav_view)

        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_about -> {
                    // Start HomeActivity when Home item is clicked
                    startActivity(Intent(this, ContactActivity::class.java))
                }
                R.id.nav_contact -> {
                    // Start ProfileActivity when Profile item is clicked
                    startActivity(Intent(this, AboutActivity::class.java))
                }
                // Add more cases for other items as needed
            }

            // Close the drawer after handling the item click
            drawerLayout.closeDrawer(GravityCompat.START)

            true
        }



    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return false
    }
}