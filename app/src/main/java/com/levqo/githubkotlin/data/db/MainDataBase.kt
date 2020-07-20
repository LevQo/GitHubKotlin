package com.levqo.githubkotlin.data.db

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.levqo.githubkotlin.data.db.repositories.RepositoriesDao
import com.levqo.githubkotlin.data.db.repositories.RepositoryEntity

@Database(
    entities = [RepositoryEntity::class], version = 1
)
abstract class MainDataBase : RoomDatabase() {
    abstract val repositoriesDao: RepositoriesDao

    companion object {
        fun create(app: Application): MainDataBase {
            return Room.databaseBuilder(app, MainDataBase::class.java, "weather_data_base")
                .build()
        }
    }
}