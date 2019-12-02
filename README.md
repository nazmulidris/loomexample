# How to import this project into IDEA

1. Import project from existing sources
2. Make sure to select "Import Project From External Model" and select "Maven"
   - This will create all the necessary .iml files for IDEA modules

# Running this project

## Normal JDK version

- Server
  1. Open `EchoServer.kt`, and then click on "Run"
- Client
  - Either:
    - Run `curl localhost:10000` to open one socket connection to the server
    - Open `EchoClient.kt`, and then click on "Run" to launch a lot of threads

## Project Loom JDK version

- Server
  1. In a terminal, make sure to use the Loom JVM to compile and run
     `src/EchoServerLoom.java`
- Client
  - Open `EchoClient.kt`, and then click on "Run" to launch a lot of threads

# Runtime performance stats

## Normal JVM version

Here are some performance numbers for this simple project on hardware (w/out
using Loom JVM and using BIO (Java blocking IO). With a new Apple 16" MacBook
Pro laptop w/ 64GB RAM and 8 core 5GHz Intel CPU:

1. With about 10_000 client threads, the entire program can run in about 17 sec,
   and the server may wait a few ms at most to accept a connection.
2. 100_000 client threads connecting to a simple socket server, the JVM
   experiences some serious performance issues, w/ threads causing the socket
   server to wait around for a very long time to be able to accept a new socket
   connection, max of about 22 sec 😱, and not a few ms! The average time to
   accept a socket connection increased to about 3 ms. And the entire program
   takes about 5 minutes to run 😨. The server process only consumed about 4.9 %
   of CPU. BTW, "Grep Console" is a great plugin to monitor output from the
   client and server println statements.

## Project Loom JVM version

With a similar machine above, the Project Loom JVM version of the Server using
Fibers get the following stats.

1. With 100_000 clients, the total time it took for the program to complete is
   3.9 min (22% less than the BIO JVM version). On the server, the average time
   to accept a socket dropped to 2.3 ms (23% less). The max time wait time to
   accept a socket increased to 19 sec (15% less). The server process only
   consumed about 2.5 % of CPU.

# References

- [JetBrains reference .gitignore](https://github.com/github/gitignore/blob/master/Global/JetBrains.gitignore)
- [More info on JetBrains and .gitignore](https://intellij-support.jetbrains.com/hc/en-us/articles/206544839)
