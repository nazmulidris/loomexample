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
var connectionCount = 0
var PRINT_EVERY_COUNT = 1_000

fun main() {
  println("Running program w/ $THREAD_COUNT threads")
  println("Starting ServerSocket on port: $PORT")
  val server = ServerSocket(PORT)

  var maxWaitTimeToAcceptSec = 0f
  var totalWaitTimeToAcceptSec = 0f

  while (connectionCount != THREAD_COUNT) {

    val waitTimeSec = measureTimeSec {
      // Blocking code.
      val socket = server.accept()
      connectionCount++

      // Create a new thread and start it (does not block).
      val socketHandlerThread = SocketHandlerThread(socket)
      socketHandlerThread.start()
    }

    when {
      connectionCount > 1 -> {
        totalWaitTimeToAcceptSec += waitTimeSec
        when {
          waitTimeSec > maxWaitTimeToAcceptSec ->
            maxWaitTimeToAcceptSec = waitTimeSec
        }
      }
    }

    printlnThrottled("🚨 maxTime now: $maxWaitTimeToAcceptSec sec")
    printlnThrottled("⌛️ avgTime now: ${1_000f * totalWaitTimeToAcceptSec / (connectionCount - 1)} ms")

  }

  Thread.sleep(1000)
  println("Program exiting ... ")
  println("🚨 maxTime now: $maxWaitTimeToAcceptSec sec")
  println("⌛️ avgTime now: ${1_000f * totalWaitTimeToAcceptSec / (connectionCount - 1)} ms")

}

fun printlnThrottled(msg: String) {
  if (connectionCount == 1) println(msg)
  else if (connectionCount % PRINT_EVERY_COUNT == 0) println(msg)
}

/**
 * More info on closing nested streams: https://stackoverflow.com/a/21723193/2085356
 */
class SocketHandlerThread(var clientSocket: Socket) : Thread() {
  override fun run() {
    val CURRENT_THREAD_NAME = currentThread().name.red()
    printlnThrottled("$CURRENT_THREAD_NAME")
    printlnThrottled("  🚀 Starting $CLASS_NAME")
    clientSocket.use { socket ->
      PrintWriter(socket.getOutputStream(), true).use { writer ->
        writer.println("[echo] Hello World")
        printlnThrottled("  ⚙️ Writing data to socket (to client)")
      }
    }
    printlnThrottled("  🏁 Ending $CLASS_NAME")
  }

}