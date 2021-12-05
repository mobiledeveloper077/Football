package uz.abduvali.football.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LeagueEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val abbr: String,
    val logo: String
)