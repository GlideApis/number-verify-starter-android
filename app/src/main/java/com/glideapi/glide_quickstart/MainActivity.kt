package com.glideapi.glide_quickstart

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.glideapi.glide_quickstart.ui.theme.GlideQuickstartTheme
import com.glideapi.glide_sdk_android.GlideClient
import com.glideapi.glide_sdk_android.NumberVerifyAuthUrlInput
import com.glideapi.glide_sdk_android.TelcoRequestDelegate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject


class MainActivity : ComponentActivity(), TelcoRequestDelegate {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GlideQuickstartTheme {
                Scaffold(modifier = Modifier.fillMaxSize(), containerColor = Color(0x00F0F0F0)) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding),
                        ctx = this
                    )
                }
            }
        }
    }

    override fun onError(error: String, requestId: String) {
        TODO("Not yet implemented")
    }

    override fun onResponse(response: String, requestId: String) {
        TODO("Not yet implemented")
    }


}


val JSON: MediaType = "application/json".toMediaType()

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier, ctx: Context) {
    val coroutineScope = rememberCoroutineScope()
    var result by remember { mutableStateOf("Click Here") }
    var numberInput by remember { mutableStateOf("+555123456789") }
    var finalBody by remember { mutableStateOf("") }

    val glide = GlideClient(
        ctx = ctx,
        clientId = BuildConfig.GLIDE_CLIENT_ID
    )
    val telcoRequestDelegate = object : TelcoRequestDelegate {
        override fun onError(error: String, requestId: String) {
            result = error
        }
        override fun onResponse(response: String, requestId: String) {
            coroutineScope.launch {
                withContext(Dispatchers.Main) { result = "Verifying Number" }
                val phoneNumber = numberInput
                val jsonObject: JSONObject = JSONObject()
                jsonObject.put("code", response)
                jsonObject.put("phoneNumber", phoneNumber)
                val json = jsonObject.toString()
                withContext(Dispatchers.IO) {
                    // http request to verify number
                    val body: RequestBody = json.toRequestBody(JSON)
                    val req = Request.Builder().url(BuildConfig.BACKEND_SERVER_URL).post(body).build()
                    val client: OkHttpClient = OkHttpClient.Builder().build()
                    val res = client.newCall(req).execute()
                    val resBody = res.body?.string()
                    withContext(Dispatchers.Main) {
                        result = "Verify User"
                        val json = JSONObject(resBody.toString())
                        finalBody = json.toString(4)
                    }
                }
            }
        }
    }

    @Composable
    fun ConditionalCodeBox(text: String) {
        if (text.isNotEmpty()) {
            Box(
                Modifier.background(Color(0xFF000000)).fillMaxWidth()
            ) {
                Text(
                    text = finalBody,
                    modifier = Modifier.padding(10.dp),
                    fontFamily = FontFamily.Monospace,
                    fontSize = 12.sp,
                    fontWeight = FontWeight(800),
                    color = Color.White
                    )
            }
        }
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(10.dp), contentAlignment = Alignment.Center) {
        Box(
            Modifier
                .padding(20.dp)) {
            Column {
                    Row {

                       Text(
                           "Number Verification Quickstart",
                           fontSize = 20.sp,
                           fontWeight = FontWeight(800)
                       )

                    }


                Text(
                    "Enter a phone number below to start the verification process, you can use the test number +555123456789 or your own",
                    modifier = Modifier.padding(top = 10.dp)
                )

                TextField(value = numberInput, onValueChange = {
                    numberInput = it
                },
                    maxLines = 1, modifier = Modifier
                        .padding(top = 30.dp)
                        .fillMaxWidth(), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone, imeAction = ImeAction.Done))
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 30.dp, bottom = 30.dp),
                    colors = ButtonColors(
                        containerColor = Color(0xFF304ffe),
                        contentColor = Color.White,
                        disabledContainerColor = Color(0xFFCCCCCC),
                        disabledContentColor = Color.White
                    ),
                    onClick = {
                    coroutineScope.launch {
                        glide.numberVerify.startVerification(
                            NumberVerifyAuthUrlInput(
                                useDevNumber = "+555123456789",
                                state = "demo",
                            ), telcoRequestDelegate
                        )
                        withContext(Dispatchers.Main) { result = "Identifying User" }
                    }

                }) {
                    Text(
                        text = result,
                    )
                }
                ConditionalCodeBox(text = finalBody)
            }
        }
    }

}
