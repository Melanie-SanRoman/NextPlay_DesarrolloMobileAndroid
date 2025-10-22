package ar.edu.unicen.nextplay.ddl.data.dto

import com.google.gson.annotations.SerializedName

class PlatformResponseDto(
    @SerializedName("count")
    val count: Int,
    @SerializedName("next")
    val next: String?,
    @SerializedName("previous")
    val previous: String?,
    @SerializedName("results")
    val results: List<PlatformDto>
) {

}