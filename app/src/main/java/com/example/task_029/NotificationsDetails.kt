package com.example.task_029

import java.io.Serializable

class NotificationDetails (
    val id: Int?,
    var content: String,
    var data: String
): Serializable {}