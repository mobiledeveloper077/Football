package uz.abduvali.football.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import uz.abduvali.football.database.dao.LeagueDao
import uz.abduvali.football.database.entity.LeagueEntity
import uz.abduvali.football.models.leagues.Leagues
import uz.abduvali.football.retrofit.ApiClient

class LeagueViewModel : ViewModel() {

    fun getLeagues(leagueDao: LeagueDao): LiveData<ArrayList<LeagueEntity>> {
        val leagues = MutableLiveData<ArrayList<LeagueEntity>>()
        ApiClient.apiService.getLeagues()
            .enqueue(object : Callback<Leagues> {
                override fun onResponse(
                    call: Call<Leagues>,
                    response: retrofit2.Response<Leagues>
                ) {
                    if (response.isSuccessful) {
                        val list = ArrayList<LeagueEntity>()
                        val body = response.body()
                        body?.data?.forEach { data ->
                            val league =
                                LeagueEntity(data.id, data.name, data.abbr, data.logos.dark)
                            leagueDao.insert(league)
                            list.add(league)
                        }
                        leagues.value = list
                    }
                }

                override fun onFailure(call: Call<Leagues>, t: Throwable) {
                }
            })
        return leagues
    }
}