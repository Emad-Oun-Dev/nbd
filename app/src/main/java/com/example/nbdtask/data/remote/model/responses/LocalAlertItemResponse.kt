package com.example.nbdtask.data.remote.model.responses

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Path
import org.simpleframework.xml.Root

/**
 * Created by Emad Mohamed Oun
 * Speedi
 * emad.3oon@gmail.com
 */

@Root(name = "notification", strict = false)
//@Element
data class LocalAlertItemResponse(
    @field:Element(name = "id")
    @param:Element(name = "id")
    val id: Int? = null,
    @field:Element(name = "title")
    @param:Element(name = "title")
    val title: String? = null,
    @field:Element(name = "timeInSeconds")
    @param:Element(name = "timeInSeconds")
    val timeInSeconds: Long? = null,
)

@Root(strict = false, name = "xml")
data class LocalAlertList @JvmOverloads constructor(
    @field:ElementList(name = "notification",  required = false, inline = true)
    @param:ElementList(name = "notification",  required = false, inline = true)
    @field:Path("notifications")
    @param:Path("notifications")
    var result: List<LocalAlertItemResponse>? = null,
)
