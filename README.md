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

TODO

# Runtime performance stats

## Normal JVM version

Here are some performance numbers for this simple project on hardware. With a
new Apple 16" MacBook Pro laptop w/ 64GB RAM and 8 core 5GHz Intel CPU:

1. With about 10_000 client threads, the entire program can run in about 17 sec,
   and the server may wait a few ms at most to accept a connection.
2. 100_000 client threads connecting to a simple socket server, the JVM
   experiences some serious performance issues, w/ threads causing the socket
   server to wait around for a very long time to be able to accept a new socket
   connection, max of about 7 seconds 😱, and not a few ms! And the entire
   program takes about 5 minutes to run 😨. BTW, "Grep Console" is a great
   plugin to monitor output from the client and server println statements.

## Project Loom JVM version

TODO

# References

- [JetBrains reference .gitignore](https://github.com/github/gitignore/blob/master/Global/JetBrains.gitignore)
- [More info on JetBrains and .gitignore](https://intellij-support.jetbrains.com/hc/en-us/articles/206544839)
