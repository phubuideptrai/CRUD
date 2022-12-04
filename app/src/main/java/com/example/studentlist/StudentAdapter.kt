package com.example.studentlist

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_student.view.*

class StudentAdapter (private val context: Context, private var studentList: ArrayList<Student>) : RecyclerView.Adapter<StudentAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_student,parent,false))
    }

    override fun getItemCount(): Int {
        return studentList.size
    }

    override fun onBindViewHolder(holder: StudentAdapter.ViewHolder, position: Int) {
        holder.onBind(studentList[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        @SuppressLint("NotifyDataSetChanged")
        fun onBind(student: Student){
            itemView.tv_id.text = student.id.toString()
            itemView.tv_name.text = student.name

            val db = DataHelper(itemView.context)
          val faculty =   db.getAllFaculty()
            for (i in 0 until faculty.size){
                if (student.faculty == faculty[i].id){
                    itemView.tv_faculty.text = faculty[i].name
                }
            }

            if (student.gender=="Male"){
                itemView.iv_student.background = context.getDrawable(R.drawable.maleprofile)
            }
            else{
                itemView.iv_student.background = context.getDrawable(R.drawable.femaleprofile)
            }
            itemView.setOnLongClickListener{
                val alertDialogBuilder = AlertDialog.Builder(itemView.context)
                alertDialogBuilder.setTitle("Thông báo")
                    .setMessage("Bạn có chắc muốn xoá "+student.name+"?")
                    .setCancelable(true)
                    .setPositiveButton("Không"){dialog,which->
                        Toast.makeText(itemView.context, "cancel delete ", Toast.LENGTH_SHORT).show()
                    }
                    .setNegativeButton("Có"){dialog,which->
                        val db = DataHelper(itemView.context)
                        db.deleteStudent(student)
                        studentList.remove(student)
                        notifyDataSetChanged()
                        Toast.makeText(itemView.context,"Xoá thành công",Toast.LENGTH_SHORT).show()
                    }
                val alertDialog = alertDialogBuilder.create()
                alertDialog.show()
                true
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun getUpdate(){
        val db = DataHelper(context)
        studentList = db.getAllStudent()
        notifyDataSetChanged()
    }
}