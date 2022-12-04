package com.example.studentlist

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import java.lang.Exception
import kotlin.coroutines.coroutineContext

class DataHelper(private val context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null,DATABASE_VERSION){
    companion object{
        private val DATABASE_NAME = "studentList.db"
        private val DATABASE_VERSION = 3
        val TABLE_NAME = "STUDENT"
        val TABLE_NAME_FACULTY = "FACULTY"
        val KEY_STUDENT_ID = "student_id"
        val KEY_NAME = "name"
        val KEY_GENDER = "gender"
        val KEY_FAC = "faculty"
        val KEY_ID = "id"
        val KEY_NAME_FAC = "name_fac"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_STUDENTLIST_TABLE = ("CREATE TABLE " +
                TABLE_NAME + "("
                + "STUDENT_ID" + " INTEGER PRIMARY KEY," +
                "NAME" + " TEXT," +
                "GENDER" + " TEXT,"+
                "FACULTY" + " INTEGER)")
        db.execSQL(CREATE_STUDENTLIST_TABLE)

        val CREATE_FACULTY_TABLE = ("CREATE TABLE " +
                TABLE_NAME_FACULTY + "("
                + "ID" + " INTEGER PRIMARY KEY," +
                "NAME_FAC" + " TEXT)")
        db.execSQL(CREATE_FACULTY_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addStudent(student: Student):Boolean{
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KEY_STUDENT_ID,student.id)
        values.put(KEY_NAME,student.name)
        values.put(KEY_GENDER,student.gender)
        values.put(KEY_FAC,student.faculty)
        val success = db.insert(TABLE_NAME,null,values)
        db.close()
        if (success.toInt() == -1){
            Toast.makeText(context,"Thêm sinh viên thất bại",Toast.LENGTH_SHORT).show()
            return false
        }
        else{
            Toast.makeText(context,"Thêm sinh viên thành công",Toast.LENGTH_SHORT).show()
            return true
        }
    }

    fun deleteStudent(student: Student){
        val db = this.writableDatabase
        val selectionArgs = arrayOf(student.id.toString())
        db.delete(TABLE_NAME, "$KEY_STUDENT_ID = ? ",selectionArgs)
    }

    @SuppressLint("Recycle")
    fun getAllStudent():ArrayList<Student>{
        val db = this.writableDatabase
        val studentList : ArrayList<Student> = ArrayList()
        val selectAll = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(selectAll,null)
        if (cursor.moveToFirst()){
            do{
                val student = Student()
                student.id = cursor.getInt(0)
                student.name = cursor.getString(1)
                student.gender = cursor.getString(2)
                student.faculty = cursor.getInt(3)
                studentList.add(student)
            } while (cursor.moveToNext())
        }
        return studentList
    }

    fun addFaculty(faculty: Faculty):Boolean{
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KEY_NAME_FAC,faculty.name)
        values.put(KEY_ID,faculty.id)
        val success = db.insert(TABLE_NAME_FACULTY,null,values)
        db.close()
        if (success.toInt() == -1){
            Toast.makeText(context,"Thêm khoa thất bại",Toast.LENGTH_SHORT).show()
            return false
        }
        else{
            Toast.makeText(context,"Thêm khoa thành công",Toast.LENGTH_SHORT).show()
            return true
        }
    }

    fun deleteFaculty(faculty: Faculty){
        val db = this.writableDatabase
        val selectionArgs = arrayOf(faculty.id.toString())
        db.delete(TABLE_NAME_FACULTY, "$KEY_ID = ? ",selectionArgs)
    }

    @SuppressLint("Recycle")
    fun getAllFaculty():ArrayList<Faculty>{
        val db = this.writableDatabase
        val facultyList : ArrayList<Faculty> = ArrayList()
        val selectAll = "SELECT * FROM $TABLE_NAME_FACULTY"
        val cursor = db.rawQuery(selectAll,null)
        if (cursor.moveToFirst()){
            do{
                val faculty = Faculty()
                faculty.id = cursor.getInt(0)
                faculty.name = cursor.getString(1)

                facultyList.add(faculty)
            } while (cursor.moveToNext())
        }
        return facultyList
    }

}