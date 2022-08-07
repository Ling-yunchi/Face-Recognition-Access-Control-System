package com.hitwh.face.socket;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.Map;

/**
 * @author wangrong
 * @date 2022/8/7 22:15
 */
public class Client extends WebSocketClient {


    public Client(URI serverUri, Map<String, String> httpHeaders) {
        super(serverUri, httpHeaders);
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        System.out.println("connected to " + serverHandshake.getHttpStatusMessage());
        send("hello");
    }

    @Override
    public void onMessage(String s) {
        System.out.println(s);
    }

    @Override
    public void onClose(int i, String s, boolean b) {

    }

    @Override
    public void onError(Exception e) {

    }
}
