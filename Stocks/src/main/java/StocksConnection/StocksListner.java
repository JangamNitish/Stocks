package StocksConnection;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Application Lifecycle Listener implementation class StocksListner
 *
 */
public class StocksListner implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public StocksListner() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent sce)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent sce)  { 
         // TODO Auto-generated method stub
    	StocksConnector ob = new StocksConnector();
		ServletContext context = sce.getServletContext();
		context.setAttribute("db", ob);
		System.out.println("server started");
    }
	
}
