package com.shvedov.livinir.rule

import android.content.Context
import android.net.Uri
import androidx.test.platform.app.InstrumentationRegistry
import com.shvedov.livinir.utlis.AssetReaderUtil.asset
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class WebServerMockRule: TestRule {

    private val server = MockWebServer()

    override fun apply(base: Statement, description: Description): Statement = object : Statement() {
        override fun evaluate() {

            server.start(8080)
            server.dispatcher = SuccessDispatcher()
            base.evaluate()
            server.shutdown()

        }
    }
}

class SuccessDispatcher(
    private val context: Context = InstrumentationRegistry.getInstrumentation().context
) : Dispatcher() {
    private val responseFilesByPath: Map<String, String> = mapOf(
        "/api/v1/registration" to "reg_response.json"
    )

    override fun dispatch(request: RecordedRequest): MockResponse {
        val errorResponse = MockResponse().setResponseCode(404)

        val pathWithoutQueryParams = Uri.parse(request.path).path ?: return errorResponse
        val responseFile = responseFilesByPath[pathWithoutQueryParams]

        return if (responseFile != null) {
            val responseBody = asset(context, responseFile)
            MockResponse().setResponseCode(200).setBody(responseBody)
        } else {
            errorResponse
        }
    }
}