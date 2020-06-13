package io.github.tonyguyot.flagorama.data.db

import androidx.room.Dao
import androidx.room.Query
import io.github.tonyguyot.flagorama.model.Country

/**
 * The Data Access Object for the Country class.
 */
@Dao
interface CountriesDao {

    @Query("SELECT * FROM country WHERE region_id = :regionId ORDER BY name ASC")
    fun getCountriesByRegion(regionId: String): List<Country>

}