package com.example.core.data.remote

import android.content.Context
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.charset.Charset

class MockInterceptor (private val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        var path = request.url.encodedPath
        if (path.startsWith("/paga-pos-app/mock")) { // User for mocking data
            path = path.replace("/paga-pos-app/mock", "")
            path = path.replace("/", "_") + ".json"
            val json = loadJSONFromAsset(context, path)

            return Response.Builder()
                .body(json.toResponseBody(MEDIA_JSON))
                .request(chain.request())
                .protocol(Protocol.HTTP_2)
                .code(200)
                .message("Success")
                .build()
        }

        val newRequest = request.newBuilder()
        newRequest.addHeader("Accept", "application/json")
        newRequest.addHeader("Content-Type", "application/json")
        return chain.proceed(newRequest.build())
    }

    @Throws(IOException::class)
    fun loadJSONFromAsset(context: Context, jsonFileName: String): String {
        (context.assets).open(jsonFileName).let {
            val buffer = ByteArray(it.available())
            it.read(buffer)
            it.close()
            return String(buffer, Charset.forName("UTF-8"))
        }
    }

    private fun parseStream(stream: InputStream): ByteArray {
        val builder = StringBuilder()
        val buffer = BufferedReader(InputStreamReader(stream, "UTF-8"))
        buffer.use { reader ->
            var line = reader.readLine()
            while (line != null) {
                builder.append(line)
                line = reader.readLine()
            }
        }
        buffer.close()
        return builder.toString().toByteArray(Charsets.UTF_8)
    }

    companion object {
        private val MEDIA_JSON = "application/json".toMediaTypeOrNull()
    }
}
