package uz.abduvali.football.database.dao

import androidx.room.Dao
import androidx.room.Query
import uz.abduvali.football.database.entity.TeamEntity

@Dao
interface TeamDao : BaseDao<TeamEntity> {

    @Query("select * from teamentity where leagueId = :id")
    fun getTeamsByLeagueId(id: String): List<TeamEntity>
}