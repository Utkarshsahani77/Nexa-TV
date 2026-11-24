package com.nexatv.app.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class StalkerRepository(
    private val service: StalkerService
) {

    suspend fun login(
        portalUrl: String,
        mac: String
    ): StalkerResult<StalkerAccount> = withContext(Dispatchers.IO) {
        try {
            val response = service.handshake(
                mac = mac
            )

            val json = JSONObject(response.string())

            val token = json.optString("token")
            val expiry = json.optString("expires")
            val status = json.optString("status")

            if (token.isEmpty()) {
                StalkerResult.Error("Login failed")
            } else {
                StalkerResult.Success(
                    StalkerAccount(
                        username = "",
                        password = "",
                        portalUrl = portalUrl,
                        mac = mac,
                        expiry = expiry,
                        status = status
                    )
                )
            }
        } catch (e: Exception) {
            StalkerResult.Error(e.message ?: "Login failed")
        }
    }

    suspend fun getCategories(
        portalUrl: String,
        mac: String,
        token: String
    ): StalkerResult<List<StalkerCategory>> = withContext(Dispatchers.IO) {
        try {
            val response = service.getCategories(
                mac = mac,
                token = token
            )

            val json = JSONObject(response.string())
            val data = json.optJSONObject("js") ?: JSONObject()
            val categories = data.optJSONArray("categories") ?: org.json.JSONArray()

            val list = mutableListOf<StalkerCategory>()

            for (i in 0 until categories.length()) {
                val obj = categories.optJSONObject(i) ?: continue

                val name = obj.optString("name")
                val id = obj.optString("id")

                val channelsResult = getChannels(
                    portalUrl = portalUrl,
                    mac = mac,
                    token = token,
                    categoryId = id
                )

                val channels = when (channelsResult) {
                    is StalkerResult.Success -> channelsResult.data
                    else -> emptyList()
                }

                list.add(
                    StalkerCategory(
                        name = name,
                        channels = channels
                    )
                )
            }

            StalkerResult.Success(list)
        } catch (e: Exception) {
            StalkerResult.Error(e.message ?: "Failed to load categories")
        }
    }

    suspend fun getChannels(
        portalUrl: String,
        mac: String,
        token: String,
        categoryId: String
    ): StalkerResult<List<StalkerChannel>> = withContext(Dispatchers.IO) {
        try {
            val response = service.getChannels(
                mac = mac,
                token = token,
                categoryId = categoryId
            )

            val json = JSONObject(response.string())
            val data = json.optJSONObject("js") ?: JSONObject()
            val channels = data.optJSONArray("data") ?: org.json.JSONArray()

            val list = mutableListOf<StalkerChannel>()

            for (i in 0 until channels.length()) {
                val obj = channels.optJSONObject(i) ?: continue

                val id = obj.optString("id")
                val name = obj.optString("name")
                val logo = obj.optString("logo")
                val url = obj.optString("url")

                list.add(
                    StalkerChannel(
                        id = id,
                        name = name,
                        logo = logo,
                        url = url,
                        category = categoryId
                    )
                )
            }

            StalkerResult.Success(list)
        } catch (e: Exception) {
            StalkerResult.Error(e.message ?: "Failed to load channels")
        }
    }
}
