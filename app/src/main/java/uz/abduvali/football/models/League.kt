package uz.abduvali.football.models

import uz.abduvali.football.database.entity.LeagueEntity
import uz.abduvali.football.database.entity.TeamEntity

data class League(
    var leagueEntity: LeagueEntity,
    var teamList: ArrayList<TeamEntity>
)