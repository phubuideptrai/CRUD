package com.example.studentlist

import android.app.AlertDialog
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_insert.*
import kotlinx.android.synthetic.main.component_button_insert_conf.*

class InsertFacultyActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert_faculty)
        val dataHelper = DataHelper(this)
        b_insert.setOnClickListener{
            val alertDialogBuilder = AlertDialog.Builder(this)
            alertDialogBuilder.setTitle("Thông báo?")
                .setMessage("Bạn có chắc muốn thêm khoa?")
                .setCancelable(true)
                .setPositiveButton("Không"){dialog,which->
                }
                .setNegativeButton("Có"){dialog,which->
                    val id = Integer.parseInt(et_id.text.toString())
                    val name = et_name.text.toString()
                    dataHelper.addFaculty(Faculty(id,name))
                    et_name.setText("")
                    et_id.setText("")
                }
            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }
    }
}