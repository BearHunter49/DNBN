package com.swma.dnbn.model.restApiData

import java.time.LocalDateTime

data class OrderData (val id: Int, val userId: Int, val totalPrice: Int,
                      val time: LocalDateTime, val city: String, val gu: String,
                      val dong: String, val detail: String)

// product01, 02에 대해서 확인 필요