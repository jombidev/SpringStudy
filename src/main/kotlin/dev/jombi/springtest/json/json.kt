package dev.jombi.springtest.json

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper

private val mapper = ObjectMapper()

fun writeJson(vararg pair: Pair<String, Any?>): String = mapper.writeValueAsString(hashMapOf(*pair))
fun readJson(json: String): JsonNode = mapper.readTree(json)