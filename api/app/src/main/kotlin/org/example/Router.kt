package org.example

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.routes() {
    val controller = Controller()

    route("/") {
        // GET all contacts
        get {
           controller.getContacts(call)
        }

        // POST create a new contact
        post {
            controller.createContact(call)
        }

        // PUT update existing contact
        put("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull() ?: return@put call.respond(HttpStatusCode.BadRequest, "Invalid contact ID")
            controller.updateContact(call, id)
        }

        delete("{id}") {
            val id = call.parameters["id"]?.toIntOrNull() ?: return@delete call.respond(HttpStatusCode.BadRequest, "Invalid contact ID")
            controller.deleteContact(call, id) // Call the method to handle the request
        }
    }
}
