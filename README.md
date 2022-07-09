# Distributed Auctioning System

This project implements a distributed auctioning system using Java Remote Method Invocation (RMI). Clients can both place items on auction as well as buy items with a user registration process in place for authentication. Fault tolerance is also present in this project with active replication used to ensure the system operates with only one server present.

## Getting Started

1. In the server directory type: javac Server.java (Ensure that IAuctionSystem.java exists in the server directory).

2. In the client directory type: javac Client.java

3. Now we can run the client and the server. First, however, we need to run the RMI registry. You will need three command prompt windows to run this example. Two should be in the server directory; one in the client directory.

4. In the first command prompt issue the command: rmiregistry

5. Now, in the second command prompt issue the command: java Server

6. Finally, in the client command prompt issue the command: java Client 17

    Note that the line LocateRegistry.getRegistry("localhost") in the client code indicates the address of the host machine on which the server code is running; if this is a different machine to that of the client then you'll need to change this parameter to be the IP address of the remote host.



