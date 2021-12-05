package uz.abduvali.football.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import uz.abduvali.football.database.dao.TeamDao
import uz.abduvali.football.database.entity.TeamEntity
import uz.abduvali.football.models.season.Season
import uz.abduvali.football.retrofit.ApiClient

class TeamViewModel : ViewModel() {

    fun getTeams(leagueId: String, teamDao: TeamDao): LiveData<ArrayList<TeamEntity>> {
        val leagues = MutableLiveData<ArrayList<TeamEntity>>()
        ApiClient.apiService.getTeamsByLeague(leagueId)
            .enqueue(object : Callback<Season> {
                override fun onResponse(
                    call: Call<Season>,
                    response: retrofit2.Response<Season>
                ) {
                    if (response.isSuccessful) {
                        val list = ArrayList<TeamEntity>()
                        response.body()?.data?.standings?.forEach { standing ->
                            val team = standing.team
                            val item = TeamEntity(
                                team.id,
                                leagueId,
                                standing.stats[8].value,
                                team.logos?.get(0)?.href ?: "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.dreamstime.com%2Froyalty-free-stock-image-transparent-designer-must-have-fake-background-image39672616&psig=AOvVaw3ZGVHABaHPANlI03YjTyYv&ust=1637737451226000&source=images&cd=vfe&ved=0CAsQjRxqFwoTCLj_uJH1rfQCFQAAAAAdAAAAABAN",
                                team.name,
                                standing.stats[2].value,
                                standing.stats[1].value,
                                standing.stats[5].value,
                                standing.stats[4].value,
                                standing.stats[6].value
                            )
                            teamDao.insert(item)
                            list.add(item)
                        }
                    }
                }

                override fun onFailure(call: Call<Season>, t: Throwable) {
                }
            })
        return leagues
    }
}