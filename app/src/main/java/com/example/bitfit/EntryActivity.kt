package com.example.bitfit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EntryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.food_enter)
        val submitButton = findViewById<Button>(R.id.submitButton)
        val foodNameEditText = findViewById<EditText>(R.id.foodNameEditText)
        val caloriesEditText = findViewById<EditText>(R.id.caloriesEditText)
        val foodEntryDao = (application as FoodApplication).db.foodEntityDao()
        submitButton.setOnClickListener {
            // Launch the NewEntryActivity
            val foodName = foodNameEditText.text.toString()
            val calories = caloriesEditText.text.toString().toIntOrNull() // Parse to Integer
            val foodEntry = FoodEntry(foodName = foodName, calorieCount = calories)
            lifecycleScope.launch(Dispatchers.IO) {
                val foodEntryDao = (application as FoodApplication).db.foodEntityDao()
                foodEntryDao.insertEntry(foodEntry)
            }
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}