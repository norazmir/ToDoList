package com.norazmir.todolist.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.norazmir.todolist.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [Task::class], version = 1)
abstract class TaskDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao

    class Callback @Inject() constructor(
        private val database: Provider<TaskDatabase>,
        @ApplicationScope private val applicationScope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            // db operations
            val dao = database.get().taskDao()

            applicationScope.launch {
                dao.insert(Task("Wash the dishes"))
                dao.insert(Task("Do Coding", important = true))
                dao.insert(Task("Throw rubbish", completed = true))
                dao.insert(Task("Clean room"))
                dao.insert(Task("Study"))
                dao.insert(Task("Code review", important = true))
            }
        }
    }
}