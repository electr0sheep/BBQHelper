package com.hackdfw;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * Servlet implementation class DataServelet
 */
public class DataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Gson gson = new Gson();
    /**
     * Default constructor. 
     */
    public DataServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append("Hello people!");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
            StringBuilder sb = new StringBuilder();
            String s;
            while ((s = request.getReader().readLine()) != null) {
                sb.append(s);
            }
            //TempRequest temp = gson.fromJson(sb.toString(),TempRequest.class);
            BBQInboundMessage.sendTempToAll(sb.toString());
		}
		catch(Exception e){
			
		}
		Message mess = new Message("Data received!",false);
		response.getWriter().append(gson.toJson(mess));
		response.flushBuffer();
	}

}
