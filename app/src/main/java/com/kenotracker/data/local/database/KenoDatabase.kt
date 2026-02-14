package com.kenotracker.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kenotracker.data.local.dao.DrawDao
import com.kenotracker.data.local.entity.DrawEntity

@Database(
    entities = [DrawEntity::class],
    version = 1,
    exportSchema = false
)
abstract class KenoDatabase : RoomDatabase() {
    abstract fun drawDao(): DrawDao
}
