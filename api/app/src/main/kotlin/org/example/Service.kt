package org.example
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

object Service {

    fun getContacts(): List<Contact> {
        return transaction {
            Contacts.selectAll().map {
                Contact(
                    id = it[Contacts.id].value,
                    name = it[Contacts.name],
                    phone = it[Contacts.phone],
                    email = it[Contacts.email],
                    image = it[Contacts.image]
                )
            }
        }
    }

    fun getContactById(contactId: Int): Contact? {
        return transaction {
            Contacts.select { Contacts.id eq contactId }
                .map {
                    Contact(
                        id = it[Contacts.id].value,
                        name = it[Contacts.name],
                        phone = it[Contacts.phone],
                        email = it[Contacts.email],
                        image = it[Contacts.image]
                    )
                }.singleOrNull()
        }
    }

    fun createContact(contact: Contact): Int {
        return transaction {
            Contacts.insertAndGetId {
                it[name] = contact.name
                it[phone] = contact.phone
                it[email] = contact.email
                it[image] = contact.image
            }.value
        }
    }

    fun updateContact(contactId: Int, contact: Contact): Boolean {
        return transaction {
            Contacts.update({ Contacts.id eq contactId }) {
                it[name] = contact.name
                it[phone] = contact.phone
                it[email] = contact.email
                it[image] = contact.image
            } > 0
        }
    }

    fun deleteContact(contactId: Int): Boolean {
        return transaction {
            Contacts.deleteWhere { Contacts.id eq contactId } > 0
        }
    }
}