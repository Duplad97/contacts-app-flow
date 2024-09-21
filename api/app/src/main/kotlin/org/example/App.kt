package org.example

import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.gson.gson
import io.ktor.server.response.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import io.ktor.server.plugins.cors.routing.CORS
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.File

fun main() {
    embeddedServer(Netty, port = 8080, module = Application::module).start(wait = true)
}

fun Application.module() {
    initDatabase()

    install(CORS) {
        anyHost()

        allowMethod(HttpMethod.Get)
        allowMethod(HttpMethod.Post)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Delete)

        allowHeader(HttpHeaders.Authorization)
        allowHeader(HttpHeaders.ContentType)

        allowCredentials = true

        exposeHeader(HttpHeaders.Authorization)

        maxAgeInSeconds = 24 * 60 * 60  // 1 day
    }

    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
        }
    }

    routing {
        route("/") {
            // GET all contacts
            get {
                val contacts = transaction {
                    Contacts.selectAll().map {
                        Contact(
                            it[Contacts.id],
                            it[Contacts.name],
                            it[Contacts.email],
                            it[Contacts.phone],
                            it[Contacts.image]
                        )
                    }
                }
                call.respond(contacts)
            }

            // POST (create a new contact)
            post {
                val newContact = call.receive<Contact>()
                transaction {
                    Contacts.insert {
                        it[name] = newContact.name
                        it[email] = newContact.email
                        it[phone] = newContact.phone
                    }
                }
                call.respond("Contact created successfully")
            }
        }
        post("/contacts/{id}/image") {
            val id = call.parameters["id"]?.toIntOrNull() ?: return@post call.respond(HttpStatusCode.BadRequest, "Invalid contact ID")

            // Handle file upload
            val multipart = call.receiveMultipart()
            var imageFile: File? = null

            multipart.forEachPart { part ->
                if (part is PartData.FileItem) {
                    // Save the file to the server
                    val fileBytes = part.streamProvider().readBytes()
                    val fileName = "profile_$id.jpg"
                    val file = File("uploads/$fileName")
                    file.writeBytes(fileBytes)
                    imageFile = file

                    // Update the contact's profile picture path in the database
                    transaction {
                        Contacts.update({ Contacts.id eq id }) {
                            it[image] = file.absolutePath
                        }
                    }
                }
                part.dispose()
            }

            if (imageFile != null) {
                call.respond(HttpStatusCode.OK, "Profile picture uploaded successfully.")
            } else {
                call.respond(HttpStatusCode.BadRequest, "No file uploaded.")
            }
        }
    }
}

fun initDatabase() {
    Database.connect("jdbc:sqlite:./contacts.db", driver = "org.sqlite.JDBC")
    transaction {
        SchemaUtils.create(Contacts)
    }
}

data class Contact(val id: Int? = null, val name: String, val email: String, val phone: String, val image: String?)