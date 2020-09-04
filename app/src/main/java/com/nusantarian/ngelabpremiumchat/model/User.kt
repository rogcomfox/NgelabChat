package com.nusantarian.ngelabpremiumchat.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val email : String
) : Parcelable