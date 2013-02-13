package clutch.connection;

import clutch.protocol.*;

import java.io.*;
import java.net.*;
import java.util.*;

public class SocketConnection {

    private int nullDataCount = 0;
    private final int id;
    private final DataQueue queue = new DataQueue();
    private final PrintWriter writer;
    private final IProtocol protocol;
    private final Scanner reader;
    private final Socket socket;

    public SocketConnection(String address, int port) {
        this.socket = new Socket(address, port);
        this.setSockParameters();
        this.id = Connections.registerConnection(address, this);
        this.scanner = new Scanner(this.socket.getInputStream());
        this.writer = new PrintWriter(this.socket.getOutputStream(), true);
    }

    private void setSockParameters() {
        this.socket.setTcpNoDelay(true);
        this.socket.setReuseAddress(true);
    }

    public void setProtocol(Class<? extends IProtocol> proto) {
        if (this.protocol != null) {
            throw new UnsupportedOperationException("Cannot reset the protocol of a SocketConnection after it has been set.");
        }

        this.protocol = proto.newInstance();
    }

    public SocketData readNextData() {
        if (this.scanner.hasNext()) {
            this.nullDataCount = 0;
            return new SocketData(this, this.scanner.nextLine());
        }
        this.nullDataCount++;
        return null;
    }

    public void close() {
        this.scanner.close();
        this.socket.shutdownInput();
        this.writer.close();
        this.socket.shutdownOutput();
        this.socket.close();
    }

    public void writeNextData() {
        if (this.queue.hasNext()) {
            this.writer.print(this.queue.next());
        }
    }
}