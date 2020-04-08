package com.example.core.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = false) @ColumnInfo(name = "userId") var userId: String,
    @ColumnInfo(name = "firstName") var firstName: String? = null,
    @ColumnInfo(name = "lastName") var lastName: String? = null,
    @ColumnInfo(name = "userName") var userName: String? = null,
    @ColumnInfo(name = "email") var email: String? = null,
    @ColumnInfo(name = "phoneNumber") var phoneNumber: String? = null,
    @ColumnInfo(name = "profilePictureUrl") var profilePictureUrl: String? = null,
    @ColumnInfo(name = "registrationDate") var registrationDate: Long,
    @ColumnInfo(name = "accountStatus") var accountStatus: String? = null,
    @ColumnInfo(name = "accountKYC") var accountKYC: String? = null,
    @ColumnInfo(name = "userBVN") var userBVN: String? = null,
    @ColumnInfo(name = "pendingActivityCount") var pendingActivityCount: Int
)