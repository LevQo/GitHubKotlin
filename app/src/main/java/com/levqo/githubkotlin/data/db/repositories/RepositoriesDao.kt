package com.levqo.githubkotlin.data.db.repositories

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RepositoriesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveRepositories(repositories: List<RepositoryEntity>)

    @Query("SELECT * FROM repositories")
    fun loadRepositories(): List<RepositoryEntity>?
}