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
  1. In a terminal, make sure to use the Loom JVM to compile and run `src/EchoServerLoom.java`
- Client
  - Open `EchoClient.kt`, and then click on "Run" to launch a lot of threads

# Runtime performance stats

## Normal JVM version

Here are some performance numbers for this simple project on hardware (w/out using Loom JVM and using BIO (Java blocking
IO). With a new Apple 16" MacBook Pro laptop w/ 64GB RAM and 8 core 5GHz Intel CPU:

1. With about 10_000 client threads, the entire program can run in about 17 sec, and the server may wait a few ms at
   most to accept a connection.
2. 100_000 client threads connecting to a simple socket server, the JVM experiences some serious performance issues, w/
   threads causing the socket server to wait around for a very long time to be able to accept a new socket connection,
   max of about 22 sec 😱, and not a few ms! The average time to accept a socket connection increased to about 3 ms. And
   the entire program takes about 5 minutes to run 😨. The server process only consumed about 4.9 % of CPU. BTW, "Grep
   Console" is a great plugin to monitor output from the client and server println statements.

## Project Loom JVM version

With a similar machine above, the Project Loom JVM version of the Server using Fibers get the following stats.

1. With 100_000 clients, the total time it took for the program to complete is 3.9 min (22% less than the BIO JVM
   version). On the server, the average time to accept a socket dropped to 2.3 ms (23% less). The max time wait time to
   accept a socket increased to 19 sec (15% less). The server process only consumed about 2.5 % of CPU.

# Project Loom articles

- [Project loom performance for transaction use case (vs generators)](https://inside.java/2020/08/07/loom-performance/)

# References

- [JetBrains reference .gitignore](https://github.com/github/gitignore/blob/master/Global/JetBrains.gitignore)
- [More info on JetBrains and .gitignore](https://intellij-support.jetbrains.com/hc/en-us/articles/206544839)

# Change master to main

The
[Internet Engineering Task Force (IETF) points out](https://tools.ietf.org/id/draft-knodel-terminology-00.html#rfc.section.1.1.1)
that "Master-slave is an oppressive metaphor that will and should never become fully detached from history" as well as
"In addition to being inappropriate and arcane, the
[master-slave metaphor](https://github.com/bitkeeper-scm/bitkeeper/blob/master/doc/HOWTO.ask?WT.mc_id=-blog-scottha#L231-L232)
is both technically and historically inaccurate." There's lots of more accurate options depending on context and it
costs me nothing to change my vocabulary, especially if it is one less little speed bump to getting a new person excited
about tech.

You might say, "I'm all for not using master in master-slave technical relationships, but this is clearly an instance of
master-copy, not master-slave"
[but that may not be the case](https://mail.gnome.org/archives/desktop-devel-list/2019-May/msg00066.html). Turns out the
original usage of master in Git very likely came from another version control system (BitKeeper) that explicitly had a
notion of slave branches.

- https://dev.to/lukeocodes/change-git-s-default-branch-from-master-19le
- https://www.hanselman.com/blog/EasilyRenameYourGitDefaultBranchFromMasterToMain.aspx

[#blacklivesmatter](https://blacklivesmatter.com/)
