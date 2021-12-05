package uz.abduvali.football.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.abduvali.football.database.entity.LeagueEntity
import uz.abduvali.football.databinding.ItemLeagueBinding
import uz.abduvali.football.models.League
import uz.abduvali.football.utils.setImage

class LeagueAdapter(
    private val onItemClickListener: OnItemClickListener
) :
    ListAdapter<League, LeagueAdapter.Vh>(MyDiffUtill()) {

    inner class Vh(var itemLeagueBinding: ItemLeagueBinding) :
        RecyclerView.ViewHolder(itemLeagueBinding.root)

    class MyDiffUtill : DiffUtil.ItemCallback<League>() {
        override fun areItemsTheSame(oldItem: League, newItem: League): Boolean {
            return oldItem.leagueEntity.id == newItem.leagueEntity.id
        }

        override fun areContentsTheSame(oldItem: League, newItem: League): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemLeagueBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        val league = getItem(position)
        holder.itemLeagueBinding.apply {
            next.setOnClickListener {
                onItemClickListener.onNextClick(league.leagueEntity)
            }
            name.text = league.leagueEntity.abbr
            abbr.text = league.leagueEntity.name
            image.setImage(league.leagueEntity.logo)
            val adapter = TeamAdapter(false)
            adapter.submitList(league.teamList)
            rv.adapter = adapter
        }
    }

    interface OnItemClickListener {
        fun onNextClick(leagueEntity: LeagueEntity)
    }
}