package uz.abduvali.football.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.abduvali.football.database.entity.TeamEntity
import uz.abduvali.football.databinding.ItemTeamBinding
import uz.abduvali.football.utils.setImage

class TeamAdapter(private val isFirst: Boolean) : ListAdapter<TeamEntity, TeamAdapter.Vh>(MyDiffUtill()) {

    inner class Vh(var itemTeamBinding: ItemTeamBinding) :
        RecyclerView.ViewHolder(itemTeamBinding.root)

    class MyDiffUtill : DiffUtil.ItemCallback<TeamEntity>() {
        override fun areItemsTheSame(oldItem: TeamEntity, newItem: TeamEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TeamEntity, newItem: TeamEntity): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamAdapter.Vh {
        return Vh(
            ItemTeamBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        val teamEntity = getItem(position)
        holder.itemTeamBinding.apply {
            if (isFirst && position == 0) {
                root.setBackgroundColor(Color.parseColor("#F44336"))
            }
            rank.text = teamEntity.rank.toString()
            logo.setImage(teamEntity.logo)
            name.text = teamEntity.name
            draw.text = teamEntity.draws.toString()
            lose.text = teamEntity.losses.toString()
            goalAgainst.text = teamEntity.goalAgainst.toString()
            goal.text = teamEntity.goalFor.toString()
            point.text = teamEntity.points.toString()
        }
    }
}