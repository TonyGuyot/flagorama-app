package io.github.tonyguyot.flagorama.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import io.github.tonyguyot.flagorama.model.Country

@Database(entities = [Country::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun countriesDao(): CountriesDao
}
