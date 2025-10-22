package ar.edu.unicen.nextplay.ddl.data.dto

import ar.edu.unicen.nextplay.ddl.models.Requirements

data class RequirementsDto(
    val minimum: String?,
    val recommended: String?
) {
    fun toRequirements(): Requirements {
        return Requirements(
            minimum = minimum,
            recommended = recommended
        )
    }
}