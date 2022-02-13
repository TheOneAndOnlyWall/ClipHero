package infrastructure

import client.Client

fun main(){

    val client = Client()
    client.start()
    client.broadcastMessage("Test")

}