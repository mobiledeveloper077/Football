package uz.abduvali.football.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = LeagueEntity::class,
            parentColumns = ["id"],
            childColumns = ["leagueId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class TeamEntity(
    @PrimaryKey
    val id: String,
    val leagueId: String,
    val rank: Int,
    val logo: String,
    val name: String,
    val draws: Int,
    val losses: Int,
    val goalAgainst: Int,
    val goalFor: Int,
    val points: Int,
)