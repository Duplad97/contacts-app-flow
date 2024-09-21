package org.example

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

fun initDatabase() {
    Database.connect("jdbc:sqlite: contacts.db", driver = "org.sqlite.JDBC")
    transaction {
        SchemaUtils.create(Contacts)
    }
}

object Contacts : IntIdTable() {
    val name = text("name")
    val email = text("email")
    val phone = text("phone")
    val image = text("image").nullable()
}

data class Contact(val id: Int, val name: String, val email: String, val phone: String, val image: String?)
