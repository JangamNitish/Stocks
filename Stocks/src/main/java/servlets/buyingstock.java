package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.google.gson.Gson;

import StocksConnection.StocksConnector;
import database.StockData;
import pojo.StocksPojo;




/**
 * Servlet implementation class buyingstock
 */
public class buyingstock extends HttpServlet {
	private static final long serialVersionUID = 1L;
	JSONObject obj = new JSONObject();
	Gson gson = new Gson();
	StockData obj1=new StockData();
    /**
     * Default constructor. 
     */
    public buyingstock() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext context = request.getServletContext();
	
		StocksConnector  ob = (StocksConnector) context.getAttribute("db");
		try {
			Connection conn = ob.getConnection();
			StocksPojo json = gson.fromJson(request.getReader(), StocksPojo.class);
			PrintWriter out = response.getWriter();
			String st = obj1.buying(json, conn);
			response.setContentType("application/JSON");
			obj.put("message", st);
			out.println(obj);
		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	}


