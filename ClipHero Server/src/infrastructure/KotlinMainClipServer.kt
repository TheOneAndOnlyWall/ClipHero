package infrastructure

import server.ServiceReplayKotlin

fun main(){
    val serviceReply = ServiceReplayKotlin()
    serviceReply.start()
}