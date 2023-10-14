package com.example.bitfit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Random

class MainActivity : AppCompatActivity() {

    private val foodEntries = mutableListOf<FoodEntry>()


    override fun onCreate(savedInstanceState: Bundle?) {

        val adapter = FoodEntryAdapter()
        lifecycleScope.launch(Dispatchers.IO) {
            val foodEntryDao = (application as FoodApplication).db.foodEntityDao()
            withContext(Dispatchers.Main) {
                foodEntryDao.getAllEntries().collect { entries ->
                    foodEntries.clear()
                    foodEntries.addAll(entries)
                    adapter.submitList(entries)
                    adapter.notifyDataSetChanged()
                    updateAverageCalories()
                }
            }
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val imageArray = arrayOf(
            R.drawable.bunny,
            R.drawable.fall,
            R.drawable.flower,
            R.drawable.france,
            R.drawable.taj,
            R.drawable.island,
            R.drawable.moon
        )

        // Get a reference to the ImageView in your layout
        val headerImageView = findViewById<ImageView>(R.id.headerImageView)

        // Generate a random index to select an image from the array
        val random = Random()
        val randomImageIndex = random.nextInt(imageArray.size)

        // Set the randomly selected image to the ImageView
        headerImageView.setImageResource(imageArray[randomImageIndex])

        val addNewFoodButton = findViewById<Button>(R.id.addNewFoodButton)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()

        // Initialize the FoodEntryDao

        // Load data from the database

        addNewFoodButton.setOnClickListener {
            val intent = Intent(this, EntryActivity::class.java)
            startActivity(intent)
        }
    }
    private fun updateAverageCalories() {
        val totalCalories = foodEntries.sumBy { it.calorieCount ?: 0 }
        val average = if (foodEntries.isNotEmpty()) totalCalories.toDouble() / foodEntries.size else 0.0
        val averageCaloriesTextView = findViewById<TextView>(R.id.averageCaloriesTextView)
        averageCaloriesTextView.text = "Average Calories: ${"%.2f".format(average)}"
    }
}