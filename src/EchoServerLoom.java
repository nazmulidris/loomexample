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

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServerLoom {
public static float maxWaitTimeToAcceptMs   = 0f;
public static float totalWaitTimeToAcceptMs = 0f;
public static int   connectionCount         = 0;

public static void log(int id, String msg) {
  if (id == 1 || id % 1000 == 0) {
    System.out.println(msg);
  }
}

public static void main(String[] args) {
  try {
    ServerSocket server = new ServerSocket(10_000);
    while (true) {

      long startTimeMs = System.currentTimeMillis();

      Socket client = server.accept();
      connectionCount++;
      EchoHandlerLoom handler = new EchoHandlerLoom(client);
      FiberScope.background().schedule(handler);

      long endTimeMs = System.currentTimeMillis();
      long waitTimeMs = endTimeMs - startTimeMs;

      if (connectionCount > 1) {
        totalWaitTimeToAcceptMs += waitTimeMs;
        if (waitTimeMs > maxWaitTimeToAcceptMs) {
          maxWaitTimeToAcceptMs = waitTimeMs;
        }
      }

      log(connectionCount, "maxTime now: " + maxWaitTimeToAcceptMs + " ms");
      log(connectionCount, "avgTime now: " +
                           totalWaitTimeToAcceptMs / (connectionCount - 1) +
                           " ms");

    }
  } catch (Exception e) {
  }
}
}

class EchoHandlerLoom implements Runnable {
Socket client;

EchoHandlerLoom(Socket client) {
  this.client = client;
}

@Override
public void run() {
  try (PrintWriter writer = new PrintWriter(client.getOutputStream(), true)) {
    writer.println("[echo] Hello world");
  } catch (Exception e) {
  }
}
}
