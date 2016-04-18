package com.hackdfw;


import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WebSocketServlet;

public class EchoServlet extends WebSocketServlet {

	private static final long serialVersionUID = 1L;
	

	@Override
	protected StreamInbound createWebSocketInbound(String arg0, HttpServletRequest arg1) {
		return new BBQInboundMessage();
	}

}
