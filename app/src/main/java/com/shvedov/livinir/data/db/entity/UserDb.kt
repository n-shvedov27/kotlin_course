package com.shvedov.livinir.data.db.entity

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.UUID

open class UserDb(

    @PrimaryKey
    var id: String = UUID.randomUUID().toString(),
    var username: String? = null,
    var email: String? = null,
    var password: String? = null

) : RealmObject()