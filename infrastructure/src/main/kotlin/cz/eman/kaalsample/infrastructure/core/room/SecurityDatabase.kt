package cz.eman.kaalsample.infrastructure.core.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import cz.eman.kaalsample.domain.feature.usermanagement.repository.SecurityRepository
import cz.eman.kaalsample.infrastructure.feature.movies.favorite.db.dao.FavoriteMovieDao
import cz.eman.kaalsample.infrastructure.feature.movies.favorite.db.entity.FavoriteMovieEntity
import cz.eman.kaalsample.infrastructure.feature.usermanagement.db.dao.SecurityRequirementsDao
import cz.eman.kaalsample.infrastructure.feature.usermanagement.db.entity.PasswordForbiddenCharsEntity
import cz.eman.kaalsample.infrastructure.feature.usermanagement.db.entity.UserEntity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Database(entities = [PasswordForbiddenCharsEntity::class], version = 1)
abstract class SecurityDatabase : RoomDatabase() {

    abstract val securityRequirementsDao: SecurityRequirementsDao

    companion object {
        @Volatile
        private var INSTANCE: SecurityDatabase? = null
        private const val DB_NAME = "kaal-security.db"
        const val DB_KEY_NAME = "SECURE_DB_KEY"

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
        ): SecurityDatabase =
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
        ): SecurityDatabase {

            return Room.databaseBuilder(
                context.applicationContext,
                SecurityDatabase::class.java, DB_NAME
            )
                .addCallback(object : Callback() {

                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        GlobalScope.launch {
                            val appDb = getInstance(context, openHelperFactory)
                            appDb.securityRequirementsDao.insert(populateData())
                        }
                    }
                })
                .openHelperFactory(openHelperFactory)
                .build()
        }

        private fun populateData() = PasswordForbiddenCharsEntity(0, ",@;$&")

    }

}