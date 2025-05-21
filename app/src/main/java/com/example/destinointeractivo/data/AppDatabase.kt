package com.example.destinointeractivo.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Enemy::class, Player::class], version = 6)
abstract class AppDatabase : RoomDatabase() {

    abstract fun enemyDao(): EnemyDao
    abstract fun playerDao(): PlayerDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        // Nombre de las SharedPreferences
        private const val PREFS_NAME = "app_prefs"
        private const val KEY_APP_VERSION = "app_version"

        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                // Comprobamos si necesitamos borrar la base de datos
                val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                val currentAppVersionCode = getAppVersionCode(context)

                val shouldResetDatabase = prefs.getInt(KEY_APP_VERSION, -1) < currentAppVersionCode

                if (shouldResetDatabase) {
                    // Borramos la base de datos si es necesario
                    context.deleteDatabase("app_database")
                }

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            scope.launch(Dispatchers.IO) {
                                INSTANCE?.let { database ->
                                    database.populateDatabaseOnce(context, database.enemyDao(), database.playerDao())
                                }
                            }
                        }
                    })
                    .addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4, MIGRATION_4_5, MIGRATION_5_6)
                    .build()

                // Guardamos la nueva versión de la app
                prefs.edit().putInt(KEY_APP_VERSION, currentAppVersionCode).apply()

                INSTANCE = instance
                instance
            }
        }

        // Obtiene la versión de la app desde el manifiesto
        private fun getAppVersionCode(context: Context): Int {
            return try {
                val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
                packageInfo.versionCode
            } catch (e: Exception) {
                1 // Valor por defecto si falla
            }
        }

        // Migraciones aquí igual que antes...
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE enemies ADD COLUMN nivel TEXT NOT NULL DEFAULT ''")
            }
        }

        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE enemies ADD COLUMN critFreq INTEGER NOT NULL DEFAULT 2")
            }
        }

        val MIGRATION_3_4 = object : Migration(3, 4) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("""
                    CREATE TABLE player_new (
                        id INTEGER PRIMARY KEY NOT NULL DEFAULT 1,
                        currentLife INTEGER NOT NULL,
                        maxLife INTEGER NOT NULL,
                        damage INTEGER NOT NULL,
                        defense INTEGER NOT NULL,
                        potions INTEGER NOT NULL,
                        potionHealAmount INTEGER NOT NULL,
                        effectsVolume INTEGER NOT NULL,
                        musicVolume INTEGER NOT NULL,
                        vibrationEnabled INTEGER NOT NULL,
                        language TEXT NOT NULL DEFAULT 'es',
                        lastLevel TEXT NOT NULL DEFAULT ''
                    )
                """)

                database.execSQL("""
                    INSERT INTO player_new (
                        id, currentLife, maxLife, damage, defense, potions,
                        potionHealAmount, effectsVolume, musicVolume, vibrationEnabled
                    ) SELECT 
                        id, currentLife, maxLife, damage, defense, potions,
                        potionHealAmount, effectsVolume, musicVolume, vibrationEnabled
                    FROM player
                """)

                database.execSQL("DROP TABLE player")
                database.execSQL("ALTER TABLE player_new RENAME TO player")
            }
        }

        val MIGRATION_4_5 = object : Migration(4, 5) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("DROP TABLE IF EXISTS player")
                database.execSQL("DROP TABLE IF EXISTS enemies")
            }
        }

        val MIGRATION_5_6 = object : Migration(5, 6) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE player ADD COLUMN enemyTurnCount INTEGER NOT NULL DEFAULT 0")
            }
        }



        // Método para insertar datos solo una vez por instalación
        private suspend fun AppDatabase.populateDatabaseOnce(context: Context, enemyDao: EnemyDao, playerDao: PlayerDao) {
            populateDatabase(enemyDao, playerDao)
        }

        // Datos iniciales
        private suspend fun AppDatabase.populateDatabase(enemyDao: EnemyDao, playerDao: PlayerDao) {
            enemyDao.deleteAllEnemies()
            playerDao.deleteAllPlayers()

            // Insertamos desde InitialData
            for (enemy in InitialData.enemyList) {
                enemyDao.insert(enemy)
            }
            playerDao.insert(InitialData.defaultPlayer)
        }
    }
}

