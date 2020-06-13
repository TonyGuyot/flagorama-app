package io.github.tonyguyot.flagorama.data.api.model

import com.google.gson.annotations.SerializedName
import io.github.tonyguyot.flagorama.model.Country

data class RestCountry(
    @field:SerializedName("alpha2Code")
    val id: String,

    @field:SerializedName("name")
    val name: String) {

    fun toCountry(region: String) = Country(id, region, name, "https://www.countryflags.io/$id/shiny/64.png")
}
