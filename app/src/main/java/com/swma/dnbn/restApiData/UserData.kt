package com.swma.dnbn.restApiData

import java.time.LocalDateTime

data class UserData (val id: Int, val email: String?, val password: String?,
                     val name: String, val role: Int, val gender: Int,
                     val age: Int, val birth: String, val deleteAt: String?,
                     val balance: Int, val city: String, val gu: String, val dong: String,
                     val detail: String, val isDelete: Int, val profileImage: String?)
