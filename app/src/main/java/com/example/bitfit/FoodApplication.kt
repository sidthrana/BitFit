package com.example.bitfit

import android.app.Application
import com.facebook.stetho.Stetho

class FoodApplication : Application() {
    val db by lazy { AppDatabase.getInstance(this) }
}
