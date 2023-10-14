package com.example.bitfit

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodEntryDao {
    @Query("SELECT * FROM food_entries")
    fun getAllEntries(): Flow<List<FoodEntry>>

    @Insert
    fun insertEntry(entry: FoodEntry)

    @Query("SELECT AVG(calorieCount) FROM food_entries")
    fun getCalAvg(): Float
}