package com.hackdfw;


import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.ArrayList;

import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.WsOutbound;

import com.google.gson.Gson;


public class BBQInboundMessage  extends MessageInbound{
    WsOutbound myoutbound;
    Gson gson = new Gson();
    private static ArrayList<BBQInboundMessage> mmiList = new ArrayList<BBQInboundMessage>();
    
    @Override
    public void onOpen(WsOutbound outbound){
        try {
            System.out.println("Open Client.");
            this.myoutbound = outbound;
            mmiList.add(this);
            Message mess = new Message("Connected",false);
            outbound.writeTextMessage(CharBuffer.wrap(gson.toJson(mess)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClose(int status){
        System.out.println("Close Client.");
        mmiList.remove(this);
    }

    @Override
    public void onTextMessage(CharBuffer cb) throws IOException{
        System.out.println("Accept Message : "+ cb);
        
        /*for(BBQInboundMessage mmib: mmiList){
            CharBuffer buffer = CharBuffer.wrap(cb);
            mmib.myoutbound.writeTextMessage(buffer);
            mmib.myoutbound.flush();
        }*/
    }

    public static void sendTempToAll(String temp) throws IOException{
    	for(BBQInboundMessage mmib: mmiList){
            CharBuffer buffer = CharBuffer.wrap(temp);
            mmib.myoutbound.writeTextMessage(buffer);
            mmib.myoutbound.flush();
        }
    }
    
    @Override
    public void onBinaryMessage(ByteBuffer bb) throws IOException{
    }
}
