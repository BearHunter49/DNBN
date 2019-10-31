package com.swma.dnbn.restApiData

data class ChannelData (val id: Int, val userId: Int, val description: String,
                        val followerNumber: Int, val categoryId: Int,
                        val rating: Float, val cost: Int, val engagementRating: Float,
                        val city: String, val gu: String, val dong: String)