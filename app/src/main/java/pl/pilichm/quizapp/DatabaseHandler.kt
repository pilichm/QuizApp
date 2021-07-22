package pl.pilichm.quizapp

import android.content.ContentValues
import android.content.Context
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseHandler(val context: Context, val dbName: String, dbVersion: Int):
    SQLiteOpenHelper(context, dbName, null, dbVersion) {

        companion object {
            private const val DATABASE_NAME: String = "topScoresDB"
            private const val DATABASE_VERSION: Int = 4
            private const val TABLE_TOP_SCORES_NAME: String = "TOP_SCORES"
            private const val KEY_TOP_SCORES_ID: String = "ID_"
            private const val KEY_USER_NAME: String = "USERNAME"
            private const val KEY_RESULT: String = "RESULT"
            private const val SQL_CREATE_TABLE_TOP_SCORES = "CREATE TABLE $TABLE_TOP_SCORES_NAME " +
                    "( $KEY_TOP_SCORES_ID INTEGER PRIMARY KEY, $KEY_USER_NAME TEXT, $KEY_RESULT " +
                    "TEXT )"

            fun getDatabaseHandler(context: Context): DatabaseHandler {
                return DatabaseHandler(context, DATABASE_NAME, DATABASE_VERSION)
            }

            fun getMockData(): ArrayList<UserScore> {
                val userScores = ArrayList<UserScore>()

                for (i in 1..10){
                    userScores.add(UserScore.getMockUserScore())
                }

                return userScores
            }
        }

    override fun onConfigure(db: SQLiteDatabase?) {
        super.onConfigure(db)
        db?.setForeignKeyConstraintsEnabled(true)
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_TABLE_TOP_SCORES)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_TOP_SCORES_NAME")
        onCreate(db)
    }

    fun saveResult(userScore: UserScore){
        val db = writableDatabase
        db.beginTransaction()

        try {
            val values = ContentValues()
            values.put(KEY_USER_NAME, userScore.userName)
            values.put(KEY_RESULT, userScore.score)

            val result = db.insertOrThrow(TABLE_TOP_SCORES_NAME, null, values)
            Log.i("INSERT RESULT", "Number is $result")
            db.setTransactionSuccessful()
        } catch (e: SQLException) {
            e.printStackTrace()
            Log.d("DatabaseHandler", "Error while trying to add result to database")
        } finally {
            db.endTransaction()
        }
    }

    fun getTopScores(): ArrayList<UserScore>{
        val userScores = ArrayList<UserScore>()
        val db = readableDatabase

        val query = "SELECT * FROM $TABLE_TOP_SCORES_NAME"
        val cursor = db.rawQuery(query, null)

        Log.i("COUNT", " = ${cursor.count}")

        try {
            if (cursor.moveToFirst()){
                do {
                    val resultId = cursor.getInt(cursor.getColumnIndex(KEY_TOP_SCORES_ID))
                    val userName = cursor.getString(cursor.getColumnIndex(KEY_USER_NAME))
                    val userResult = cursor.getInt(cursor.getColumnIndex(KEY_RESULT))

                    val userScore = UserScore(resultId, userName, userResult)
                    userScores.add(userScore)
                } while (cursor.moveToNext())
            }
        } catch (e: Exception){
            Log.d("DatabaseHandler", "Error while trying to get posts from database");
        } finally {
            if (cursor!=null&&!cursor.isClosed){
                cursor.close()
            }
        }

        while (userScores.size<10){
            userScores.add(UserScore.getMockUserScore())
        }

        userScores.sortByDescending { item -> item.score }

        while (userScores.size>10){
            userScores.removeLast()
        }

        for (i in 1..10){
            userScores[i-1].scoreNumber = i
        }

        return userScores
    }

}