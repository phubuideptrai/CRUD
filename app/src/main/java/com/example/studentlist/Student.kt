package com.example.studentlist

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Student (
    var id : Int = 0,
    var name : String = "",
    var gender : String = "",
    var faculty : Int = 0
) : Parcelable

class Faculty (
    var id : Int = 0,
    var name : String = ""
)