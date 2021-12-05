package uz.abduvali.football.models.season

data class Standing(
    val note: Note,
    val stats: List<Stat>,
    val team: Team
)