package com.jcdesign.openweatherbottomnavigation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.jcdesign.openweatherbottomnavigation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        onItemSelect()

    }

    private fun onItemSelect() {
        binding.bNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.item1 -> {
                    Toast.makeText(this, "Item 1", Toast.LENGTH_SHORT).show()
                }

                R.id.item2 -> {
                    Toast.makeText(this, "Item 2", Toast.LENGTH_SHORT).show()
                }

                R.id.item3 -> {
                    Toast.makeText(this, "Item 3", Toast.LENGTH_SHORT).show()
                }

                R.id.item4 -> {
                    Toast.makeText(this, "Item 4", Toast.LENGTH_SHORT).show()
                }

                R.id.item5 -> {
                    Toast.makeText(this, "Item 5", Toast.LENGTH_SHORT).show()
                }
            }
            true
        }
    }
}