package com.shvedov.livinir.data.db.entity

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.UUID

open class PostDb(

    @PrimaryKey
    var id: String = UUID.randomUUID().toString(),
    var text: String? = null,
    var title: String? = null,
    var author: UserDb? = null
) : RealmObject()