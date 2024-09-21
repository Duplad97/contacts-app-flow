package org.example

import org.jetbrains.exposed.sql.Table

object Contacts : Table() {
    val id = integer("id").autoIncrement()
    val name = text("name")
    val email = text("email")
    val phone = text("phone")
    val image = text("image")

    override val primaryKey = PrimaryKey(id)
}
