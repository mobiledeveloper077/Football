package uz.abduvali.football.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import uz.abduvali.football.database.dao.LeagueDao
import uz.abduvali.football.database.dao.TeamDao
import uz.abduvali.football.database.entity.LeagueEntity
import uz.abduvali.football.database.entity.TeamEntity

@Database(entities = [LeagueEntity::class, TeamEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun teamDao(): TeamDao
    abstract fun leagueDao(): LeagueDao

    companion object {
        private var appDatabase: AppDatabase? = null

        @Synchronized
        fun getInstance(context: Context): AppDatabase {
            if (appDatabase == null) {
                appDatabase = Room.databaseBuilder(context, AppDatabase::class.java, "my_db")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            }
            return appDatabase!!
        }
    }
}