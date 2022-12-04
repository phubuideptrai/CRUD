package com.example.studentlist

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_faculty.view.*
import kotlinx.android.synthetic.main.item_student.view.*
import kotlinx.android.synthetic.main.item_student.view.tv_faculty

class FacultyAdapter (private val context: Context, private var facultyList: ArrayList<Faculty>) : RecyclerView.Adapter<FacultyAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_faculty,parent,false))
    }

    override fun getItemCount(): Int {
        return facultyList.size
    }

    override fun onBindViewHolder(holder: FacultyAdapter.ViewHolder, position: Int) {
        holder.onBind(facultyList[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        @SuppressLint("NotifyDataSetChanged")
        fun onBind(faculty: Faculty){
            itemView.tv_faculty.text = faculty.name
            itemView.setOnLongClickListener{
                val alertDialogBuilder = AlertDialog.Builder(itemView.context)
                alertDialogBuilder.setTitle("Thông báo")
                    .setMessage("Bạn có chắc muốn xoá "+faculty.name+"?")
                    .setCancelable(true)
                    .setPositiveButton("Không"){dialog,which->
                        Toast.makeText(itemView.context, "cancel delete ", Toast.LENGTH_SHORT).show()
                    }
                    .setNegativeButton("Có"){dialog,which->
                        val db = DataHelper(itemView.context)
                        db.deleteFaculty(faculty)
                        facultyList.remove(faculty)
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
        facultyList = db.getAllFaculty()
        notifyDataSetChanged()
    }
}