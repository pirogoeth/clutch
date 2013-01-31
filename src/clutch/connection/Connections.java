package clutch.connnection;

import java.io.*;
import java.util.*;

public class Connections {

    private static Map<Integer, SocketConnection> socketMap = new HashMap<Integer, SocketConnection>();
    private static Map<String, Integer> idMap = new HashMap<String, Integer>();
    private static serverCount = 0;

    private static int registerConnection(String serverUri, SocketConnection sock) {
        socketMap.put(serverCount, sock);
        idMap.put(serverUri, serverCount);

        return serverCount++;
    }

    private static void deregisterConnection(int serverId) {
        String uri = "";
        for (Map.Entry<String, Integer> entry : idMap.entrySet()) {
            if (entry.getValue() == serverId) {
                uri = entry.getKey();
                break;
            }
        }
        idMap.remove(uri);
        socketMap.remove(serverId);
    }

    private static void deregisterConnection(String serverUri) {
        int id = idMap.get(serverUri);
        idMap.remove(serverUri);
        socketMap.remove(id);
    }

    public static SocketConnection getConnection(int serverId) {
        return socketMap.get(serverId);
    }

    public static SocketConnection getConnection(String serverUri) {
        return socketMap.get(idMap.get(serverUri));
    }
}