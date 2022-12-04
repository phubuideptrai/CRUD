package com.example.studentlist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.component_button_insert.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        b_insert_student.setOnClickListener{
            val intent = Intent(this,InsertActivity::class.java)
            startActivity(intent)
        }

        val dataHelper = DataHelper(this)
        val studentList = dataHelper.getAllStudent()
        val studentAdapter = StudentAdapter(this@MainActivity,studentList)
        rv_student.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = studentAdapter
        }

        textView.setOnClickListener{
            val intent = Intent(this,FacultyActivity::class.java)
            startActivity(intent)
        }

        refresh_swipe.setOnRefreshListener {
            refresh_swipe.isRefreshing=false
            studentAdapter.getUpdate()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.about -> {
                val intent = Intent(this,AboutActivity::class.java)
                startActivity(intent)
                finish()
                true
            }
            R.id.exit -> {
                val intent = Intent(this,LoginActivity::class.java)
                startActivity(intent)
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
