package com.example.lesson24.models

import android.os.Parcel
import android.os.Parcelable

class Post @JvmOverloads constructor(
    id: Long = 0,
    title: String? = null,
    email: String? = null,
    body: String? = null,
    fullName: String? = null
) : Parcelable {

    var safeId: Long = id

    var safeTitle: String? = title

    var safeEmail: String? = email

    var safeBody: String? = body

    var safeFullName: String? = fullName

    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(safeId)
        parcel.writeString(safeTitle.orEmpty())
        parcel.writeString(safeEmail.orEmpty())
        parcel.writeString(safeBody.orEmpty())
        parcel.writeString(safeFullName.orEmpty())
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Post> {
        override fun createFromParcel(parcel: Parcel): Post {
            return Post(parcel)
        }

        override fun newArray(size: Int): Array<Post?> {
            return arrayOfNulls(size)
        }
    }
}