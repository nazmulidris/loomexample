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

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.Socket

const val THREAD_COUNT = 100_000

fun main() {
  val elapsedTimeSec = measureTimeSec {
    (1..THREAD_COUNT).forEach {
      val thread = Thread { doWork() }
      thread.start()
      thread.join()
    }
  }
  println("⏰ Total Elapsed time: ${elapsedTimeSec / 60f} min")
}

fun doWork() {
  val elapsedTimeSec = measureTimeSec {
    val socket = Socket("localhost", PORT)
    val inputStream = socket.getInputStream()
    val reader = BufferedReader(InputStreamReader(inputStream))
    val line = reader.readLine()
    println("Thread ${Thread.currentThread().name} - got: $line")
  }
  println("⏰ Elapsed time: $elapsedTimeSec sec")
}

fun measureTimeSec(block: () -> Unit): Float {
  val startTime = System.currentTimeMillis()
  block()
  val endTime = System.currentTimeMillis()
  return (endTime - startTime) / 1_000f
}