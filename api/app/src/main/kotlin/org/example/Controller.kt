package org.example

import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.util.*

class Controller {

    private suspend fun saveImage(part: PartData.FileItem): File {
        val uploadsDir = File("uploads")

        if (!uploadsDir.exists()) {
            uploadsDir.mkdirs()
        }

        val fileName = "${UUID.randomUUID()}_${part.originalFileName}"
        val file = File("uploads/$fileName")

        withContext(Dispatchers.IO) {
            part.streamProvider().use { inputStream ->
                file.outputStream().buffered().use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
            }
        }
        return file
    }

    private suspend fun deleteImage(filePath: String) {
        val file = File(filePath)
        if (file.exists()) {
            withContext(Dispatchers.IO) {
                file.delete()
            }
        }
    }

    suspend fun getContacts(call: ApplicationCall) {
        val contacts = Service.getContacts()
        call.respond(contacts)
    }

    suspend fun createContact(call: ApplicationCall) {
        val multipart = call.receiveMultipart()
        var contactData: Contact? = null
        var profileImageFile: File? = null

        multipart.forEachPart { part ->
            when (part) {
                is PartData.FormItem -> {
                    when (part.name) {
                        "name" -> contactData = contactData?.copy(name = part.value) ?: Contact(0, part.value, "", "", null)
                        "phone" -> contactData = contactData?.copy(phone = part.value) ?: Contact(0, "", part.value, "", null)
                        "email" -> contactData = contactData?.copy(email = part.value) ?: Contact(0, "", "", part.value, null)
                    }
                }
                is PartData.FileItem -> {
                    if (part.name == "image") {
                        profileImageFile = saveImage(part)
                    }
                }
                else -> {}
            }
            part.dispose()
        }

        if (contactData != null) {
            val finalContactData = if (profileImageFile != null) {
                contactData!!.copy(image = profileImageFile!!.path)
            } else {
                contactData!!
            }

            Service.createContact(finalContactData)

            call.respond(HttpStatusCode.OK, "Contact created successfully")
        } else {
            call.respond(HttpStatusCode.BadRequest, "Missing contact data")
        }
    }

    suspend fun updateContact(call: ApplicationCall, contactId: Int) {
        val multipart = call.receiveMultipart()
        var contactData: Contact? = null
        var newProfileImageFile: File? = null

        val existingContact = Service.getContactById(contactId)

        multipart.forEachPart { part ->
            val partName = part.name

            when (part) {
                is PartData.FormItem -> {
                    if (partName != null) {
                        when (partName) {
                            "name" -> contactData = contactData?.copy(name = part.value) ?: Contact(contactId, part.value, "", "", null)
                            "phone" -> contactData = contactData?.copy(phone = part.value) ?: Contact(contactId, "", part.value, "", null)
                            "email" -> contactData = contactData?.copy(email = part.value) ?: Contact(contactId, "", "", part.value, null)
                            "image" -> {
                                if (part.value.isEmpty()) {
                                    newProfileImageFile = null
                                    contactData = contactData?.copy(image = null) ?: Contact(contactId, "", "", "", null)
                                } else if(existingContact?.image != null) {
                                    contactData = contactData?.copy(image = existingContact.image) ?: Contact(contactId, "", "", "", existingContact.image)
                                }
                            }
                        }
                    }
                }
                is PartData.FileItem -> {
                    if (partName == "image") {
                        newProfileImageFile = saveImage(part)
                    }
                }
                else -> {}
            }
            part.dispose()
        }

        if (contactData != null || newProfileImageFile != null) {
            existingContact?.image?.let { oldImagePath ->
                if (newProfileImageFile != null || contactData?.image == null) {
                    deleteImage(oldImagePath)
                }
            }

            val finalContactData = contactData?.copy(
                image = newProfileImageFile?.path ?: contactData?.image
            ) ?: existingContact!!

            val updated = Service.updateContact(contactId, finalContactData)
            if (updated) {
                call.respond(HttpStatusCode.OK, "Contact updated successfully")
            } else {
                call.respond(HttpStatusCode.NotFound, "Contact not found")
            }
        } else {
            call.respond(HttpStatusCode.BadRequest, "No valid data to update")
        }
    }

    suspend fun deleteContact(call: ApplicationCall, contactId: Int) {
        val existingContact = Service.getContactById(contactId)

        if (existingContact != null) {
            existingContact.image?.let { imagePath ->
                deleteImage(imagePath)
            }

            val deleted = Service.deleteContact(contactId)
            if (deleted) {
                call.respond(HttpStatusCode.OK, "Contact deleted successfully")
            } else {
                call.respond(HttpStatusCode.NotFound, "Contact not found")
            }
        } else {
            call.respond(HttpStatusCode.NotFound, "Contact not found")
        }
    }
}