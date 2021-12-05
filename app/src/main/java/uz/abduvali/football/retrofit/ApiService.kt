package uz.abduvali.football.retrofit

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import uz.abduvali.football.models.leagues.Leagues
import uz.abduvali.football.models.season.Season

interface ApiService {

    @GET("leagues")
    fun getLeagues(): Call<Leagues>

    @GET("leagues/{id}/standings?season=2021&sort=asc")
    fun getTeamsByLeague(@Path("id") id: String): Call<Season>
}