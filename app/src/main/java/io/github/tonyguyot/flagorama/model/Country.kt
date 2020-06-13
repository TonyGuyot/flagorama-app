package io.github.tonyguyot.flagorama.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Country(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "region_id") val regionId: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "flag_url") val flagUrl: String?)