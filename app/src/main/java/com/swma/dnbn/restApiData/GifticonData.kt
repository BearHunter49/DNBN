package com.swma.dnbn.restApiData

import java.time.LocalDateTime

data class GifticonData (val id: Int, val productId: Int, val isUsing: Int,
                         val issueAt: LocalDateTime, val usedAt: LocalDateTime,
                         val userId: Int)

// Transient 부분