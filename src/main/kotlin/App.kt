import io.ktor.client.*
import io.ktor.client.plugins.websocket.*
import io.ktor.http.*
import io.ktor.websocket.*
import kotlinx.coroutines.runBlocking


fun main() {
    val client = HttpClient {
        install(WebSockets)
    }
    runBlocking {
        client.webSocket(method = HttpMethod.Get, host = "0.0.0.0", port = 8080, path = "/chat/3") {
            while(true) {
                val othersMessage = incoming.receive() as? Frame.Text ?: continue
                println(othersMessage.readText())
                val myMessage = readLine()
                if(myMessage != null) {
                    send(myMessage)
                }
            }
        }
    }
    client.close()
    println("Connection closed. Goodbye!")
}
