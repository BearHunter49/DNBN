package com.swma.dnbn.model.restApiData

data class MediaData (val state: String, val channel_id: String, val source_url: String,
                      val destination_url: HashMap<String, String>)
