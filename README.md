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

# References

- [JetBrains reference .gitignore](https://github.com/github/gitignore/blob/master/Global/JetBrains.gitignore)
- [More info on JetBrains and .gitignore](https://intellij-support.jetbrains.com/hc/en-us/articles/206544839)
