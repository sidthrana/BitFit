package com.example.bitfit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.bitfit.FoodEntry
import com.example.bitfit.R

class FoodEntryAdapter : ListAdapter<FoodEntry, FoodEntryAdapter.FoodEntryViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FoodEntry>() {
            override fun areItemsTheSame(oldItem: FoodEntry, newItem: FoodEntry): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: FoodEntry, newItem: FoodEntry): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodEntryViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.food_item, parent, false)
        return FoodEntryViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FoodEntryViewHolder, position: Int) {
        val currentEntry = getItem(position)
        holder.foodNameTextView.text = currentEntry.foodName
        holder.caloriesTextView.text = currentEntry.calorieCount.toString()
    }

    inner class FoodEntryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val foodNameTextView: TextView = itemView.findViewById(R.id.foodNameTextView)
        val caloriesTextView: TextView = itemView.findViewById(R.id.caloriesTextView)

    }
}