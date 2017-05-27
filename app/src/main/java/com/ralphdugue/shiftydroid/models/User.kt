package com.ralphdugue.shiftydroid.models

import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.ralphdugue.shiftydroid.storage.LocalDB

/**
 * Created by Ralph on 5/26/2017.
 */

@Table(database = LocalDB::class)
data class User(
        @PrimaryKey var userId: String,
        @Column var company: String,
        @Column var password: String,
        @Column var firstName: String? = null,
        @Column var lastName: String? = null,
        @Column var email: String? = null,
        @Column var position: String? = null
)