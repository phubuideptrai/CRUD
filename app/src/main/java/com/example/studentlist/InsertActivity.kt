package com.example.studentlist

import android.app.AlertDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_insert.*
import kotlinx.android.synthetic.main.component_button_insert_conf.*

class InsertActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert)
        val dataHelper = DataHelper(this)
        val facultyList = dataHelper.getAllFaculty()
        val facultylistName = mutableListOf<String>()
        facultyList.forEach {
            facultylistName.add(it.name)
        }
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, facultylistName)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        et_faculty.adapter = adapter
        b_insert.setOnClickListener{
            val alertDialogBuilder = AlertDialog.Builder(this)
            alertDialogBuilder.setTitle("Thông báo?")
                .setMessage("Bạn có chắc muốn thêm sinh viên?")
                .setCancelable(true)
                .setPositiveButton("Không"){dialog,which->
                }
                .setNegativeButton("Có"){dialog,which->
                    val id = Integer.parseInt(et_id.text.toString())
                    val name = et_name.text.toString()
                    var faculty = 0
                    for (i in 0 until facultyList.size) {
                        if (et_faculty.selectedItem== facultyList[i].name) {
                            faculty = facultyList[i].id
                            break
                        }
                    }
                    val gender = findViewById<RadioButton>(rg_gender.checkedRadioButtonId)
                    dataHelper.addStudent(Student(id,name,gender.text.toString(),faculty))
                    et_name.setText("")
                    et_id.setText("")
                }
            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }
    }
}