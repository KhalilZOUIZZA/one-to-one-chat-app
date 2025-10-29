# One-to-One Chat Application

A one-to-one chat application built in **Java** using **Sockets**, **RMI**, and **Threads**, structured as a Maven multi-module project with separate `client` and `server` modules.

---

## 🚀 Table of Contents

- [Overview](#overview)  
- [Features](#features)  
- [Project Structure](#project-structure)  
- [Requirements](#requirements)  
- [Build & Run](#build--run)  
- [Usage](#usage)  
- [Technologies Used](#technologies-used)  

---

## Overview

This application enables two users to engage in real-time, one-on-one chat. The **server** handles incoming and outgoing connections, message routing, and concurrency via threads. The **client** connects to the server and allows the user to send and receive messages via a simple console interface.

---

## Features

- Real-time, one-to-one chat between two users  
- Multi-threaded server capable of handling simultaneous connections  
- Communication implemented via Java **Sockets** and optionally **RMI**  
- Clean separation of client and server modules  
- Console-based interface for simplicity  

---

## Project Structure

```
one-to-one-chat-app/
│
├── client/                      # Client-side Maven module
│   ├── src/main/java/org/chatapplicationjava/main/Main.java
│   └── pom.xml
│
├── server/                      # Server-side Maven module
│   ├── src/main/java/org/chatapplicationjava/main/Main.java
│   └── pom.xml
│
└── pom.xml                      # Parent Maven POM
```

---

## Requirements

- Java **17** or newer  
- Maven **3.9+**  
- (Optional) Network connectivity if running client and server across different machines  

---

## Build & Run

1. **Clone the repository**  
   ```bash
   git clone https://github.com/KhalilZOUIZZA/one-to-one-chat-app.git
   cd one-to-one-chat-app
   ```

2. **Build the whole project**  
   ```bash
   mvn clean install
   ```

3. **Run the Server**  
   ```bash
   cd server
   mvn exec:java -Dexec.mainClass="org.chatapplicationjava.main.Main"
   ```

4. **Run the Client** (in a different terminal)  
   ```bash
   cd client
   mvn exec:java -Dexec.mainClass="org.chatapplicationjava.main.Main"
   ```

5. **Follow the CLI prompts** to connect, send and receive messages.

---

## Usage

- Start the server first so it listens for incoming connections.  
- Then launch the client and follow the prompts (e.g., specify server address, port).  
- Once connected, type messages in the client console to send, and you’ll receive on the other end.

---

## Technologies Used

- Java (version 17+)  
- Java Sockets (TCP)  
- Java RMI (Remote Method Invocation)  
- Threads / Multithreading  
- Maven for build and module management  
- Console interface (CLI)  

---


*Happy coding ☕️*

