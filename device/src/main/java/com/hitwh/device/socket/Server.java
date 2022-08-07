package com.hitwh.device.socket;

import org.java_websocket.WebSocket;
import org.java_websocket.drafts.Draft;
import org.java_websocket.exceptions.InvalidDataException;
import org.java_websocket.framing.CloseFrame;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.handshake.ServerHandshakeBuilder;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;

/**
 * @author wangrong
 * @date 2022/8/7 22:01
 */
public class Server extends WebSocketServer {
    private final String privateKey;
    private Boolean connected = false;

    public Boolean getConnected() {
        return connected;
    }

    public Server(int port, String privateKey) {
        super(new InetSocketAddress(port));
        this.privateKey = privateKey;
    }

    @Override
    public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {
        connected = true;
    }

    @Override
    public void onClose(WebSocket webSocket, int i, String s, boolean b) {
        connected = false;
    }

    @Override
    public void onMessage(WebSocket webSocket, String s) {
        System.out.println(s);
        webSocket.send("hello");
    }

    @Override
    public void onError(WebSocket webSocket, Exception e) {

    }

    @Override
    public void onStart() {
        System.out.println("Server started at " + getPort());
    }

    @Override
    public ServerHandshakeBuilder onWebsocketHandshakeReceivedAsServer(WebSocket conn, Draft draft, ClientHandshake request) throws InvalidDataException {
        ServerHandshakeBuilder builder = super.onWebsocketHandshakeReceivedAsServer(conn, draft, request);
        if (!request.hasFieldValue("privateKey")) {
            throw new InvalidDataException(CloseFrame.REFUSE, "authentication failed");
        }
        String privateKey = request.getFieldValue("privateKey");
        if (!this.privateKey.equals(privateKey)) {
            throw new InvalidDataException(CloseFrame.REFUSE, "authentication failed");
        }
        return builder;
    }
}
