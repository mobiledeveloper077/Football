package uz.abduvali.football.database.dao

import androidx.room.Dao
import androidx.room.Query
import uz.abduvali.football.database.entity.LeagueEntity

@Dao
interface LeagueDao : BaseDao<LeagueEntity> {

    @Query("select * from leagueentity")
    fun getLeagues(): List<LeagueEntity>

    @Query("select * from leagueentity where id=:id")
    fun getLeagueById(id: String): LeagueEntity
}