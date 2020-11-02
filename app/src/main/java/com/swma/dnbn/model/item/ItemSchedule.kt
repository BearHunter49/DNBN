package com.swma.dnbn.model.item

data class ItemSchedule(val scheduleBroadcastId: Int, val scheduleTitle: String, val scheduleUserId: Int,
                        val scheduleUserName: String, val scheduleProductId: Int, val scheduleDate: String,
                        val scheduleImageUrl: String)