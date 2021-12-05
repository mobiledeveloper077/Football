package uz.abduvali.football.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import uz.abduvali.football.R
import uz.abduvali.football.adapters.LeagueAdapter
import uz.abduvali.football.database.AppDatabase
import uz.abduvali.football.database.entity.LeagueEntity
import uz.abduvali.football.database.entity.TeamEntity
import uz.abduvali.football.databinding.FragmentHomeBinding
import uz.abduvali.football.models.League
import uz.abduvali.football.utils.show
import uz.abduvali.football.viewmodels.LeagueViewModel
import uz.abduvali.football.viewmodels.TeamViewModel

class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding by viewBinding(FragmentHomeBinding::bind)

    private lateinit var leagueAdapter: LeagueAdapter

    private lateinit var database: AppDatabase

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        database = AppDatabase.getInstance(requireContext())
        LeagueViewModel().getLeagues(database.leagueDao()).observe(viewLifecycleOwner) { leagues ->
            leagues.forEach { league ->
                TeamViewModel().getTeams(league.id, database.teamDao())
                    .observe(viewLifecycleOwner) {

                    }
            }
        }
        leagueAdapter =
            LeagueAdapter(object : LeagueAdapter.OnItemClickListener {
                override fun onNextClick(leagueEntity: LeagueEntity) {
                    findNavController().navigate(R.id.action_homeFragment_to_adMobFragment,
                        Bundle().apply {
                            putString("league", leagueEntity.id)
                        })
                }
            })
        binding.rv.adapter = leagueAdapter

        loadUi()

        binding.swipe.setOnRefreshListener {
            loadUi()
        }
    }

    private fun loadUi() {
        val list = ArrayList<League>()
        database.leagueDao().getLeagues().forEach { league ->
            val teams = ArrayList<TeamEntity>()
            val teamList = database.teamDao().getTeamsByLeagueId(league.id)
            if (teamList.size > 3) {
                for (i in 0..3) {
                    teams.add(teamList[i])
                }
            }
            list.add(League(league, teams))
        }
        leagueAdapter.submitList(list)
        binding.apply {
            swipe.isRefreshing = false
            rv.show()
        }
    }
}