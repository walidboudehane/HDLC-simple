HDLC Protocol

An implementation of a simplified version of the HDLC protocol using multiple Java classes to facilitate data exchange between a Sender and a receiver .

Features

    Reliable file transfer with error handling.
    Connection management between sender and receiver.
    Automatic acknowledgment and retransmission.

Prerequisites

    Java SDK installed
    Compatible operating system (e.g., Windows, Linux)

Installation

git clone https://github.com/walidboudehane/HDLC-SIMPLE.git

Navigate to the project directory:


Compile the Java files:

    javac Sender.java
    javac Receiver.java

Usage :

java Receiver <port_number>

java Sender <receiver_host> <receiver_port> <input_file> <0> 


