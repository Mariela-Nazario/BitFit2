package com.mnazari2.bitfit2

import android.app.Application

class FoodApplication : Application() {
    val db by lazy { DataBase.getInstance(this) }
}