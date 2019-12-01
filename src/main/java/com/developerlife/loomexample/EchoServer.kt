/*
 * Copyright 2019 Nazmul Idris All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.developerlife.loomexample

import com.importre.crayon.blue
import com.importre.crayon.red
import java.io.PrintWriter
import java.net.ServerSocket
import java.net.Socket

val PORT = 10_000
val CLASS_NAME = SocketHandlerThread::class.java.simpleName.blue()

fun main() {
  println("Starting ServerSocket on port: $PORT")
  val server = ServerSocket(PORT)

  var maxTime = 0f
  var avgTime = 0f

  while (true) {
    val timeInterval = measureTime {
      val socket = server.accept()
      val socketHandlerThread = SocketHandlerThread(socket)
      socketHandlerThread.start()
    }
    if (timeInterval > maxTime) {
      maxTime = timeInterval
      println("🚨 maxTime now: $maxTime sec")
    }
  }
}

/**
 * More info on closing nested streams: https://stackoverflow.com/a/21723193/2085356
 */
class SocketHandlerThread(var clientSocket: Socket) : Thread() {
  override fun run() {
    val CURRENT_THREAD_NAME = currentThread().name.red()
    println("$CURRENT_THREAD_NAME")
    println("  🚀 Starting $CLASS_NAME")
    clientSocket.use { socket ->
      PrintWriter(socket.getOutputStream(), true).use { writer ->
        writer.println("[echo] Hello World")
        println("  ⚙️ Writing data to socket (to client)")
      }
    }
    println("  🏁 Ending $CLASS_NAME")
  }

}