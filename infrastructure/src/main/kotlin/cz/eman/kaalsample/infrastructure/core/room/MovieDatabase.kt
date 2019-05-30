package cz.eman.kaalsample.infrastructure.core.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import cz.eman.kaalsample.infrastructure.feature.movies.favorite.db.dao.FavoriteMovieDao
import cz.eman.kaalsample.infrastructure.feature.movies.favorite.db.entity.FavoriteMovieEntity
import cz.eman.kaalsample.infrastructure.feature.usermanagement.db.dao.UserDao
import cz.eman.kaalsample.infrastructure.feature.usermanagement.db.entity.UserEntity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * Room database for the Movie Kaal sample app which providing tables and DAOs
 * @author vsouhrada (vaclav.souhrada@eman.cz)
 * @since 0.1.0
 */
@Database(entities = [FavoriteMovieEntity::class, UserEntity::class], version = 1)
abstract class MovieDatabase : RoomDatabase() {

    abstract val favoriteMovieDao: FavoriteMovieDao
    abstract val userDao: UserDao

    companion object {
        @Volatile
        private var INSTANCE: MovieDatabase? = null
        private const val DB_NAME = "kaal-movie.db"
        const val DB_KEY_NAME = "DB_KEY"

        /**
         * Returns Singleton instance of this AppDatabase.
         * AppDatabase is created if needed.
         *
         * @param context Context of application
         * @param factory SafeRoom
         * @return Singleton instance of AppDatabase
         */
        fun getInstance(
            context: Context,
            factory: SupportSQLiteOpenHelper.Factory?
        ): MovieDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context, factory).also {
                    INSTANCE = it
                }
            }

        /**
         * Creates Singleton instance of this AppDatabase if needed.
         *
         * @param context Context of application
         * @param openHelperFactory SupportOpenHelper factory
         * @return Singleton instance of this AppDatabase
         */
        private fun buildDatabase(
            context: Context,
            openHelperFactory: SupportSQLiteOpenHelper.Factory? = null
        ): MovieDatabase {

            return Room.databaseBuilder(
                context.applicationContext,
                MovieDatabase::class.java, DB_NAME
            )
                .addCallback(object : Callback() {

                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        GlobalScope.launch {
                            val appDb = getInstance(context, openHelperFactory)
                            appDb.userDao.insert(populateDefaultUserData())
                        }
                    }
                })
                .openHelperFactory(openHelperFactory)
                .build()
        }

        private fun populateDefaultUserData() = UserEntity("john", "travolta")

    }

}