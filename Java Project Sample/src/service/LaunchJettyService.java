package edu.gatech.saad.p3.service;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.Configuration;
import org.eclipse.jetty.webapp.WebAppContext;

public class LaunchJettyService {

	public static void main(String[] args) throws Exception {
		System.out.println(System.getProperty("user.dir"));
//		
		Server server = new Server(8080);
		WebAppContext webapp = new WebAppContext(System.getProperty("user.dir")+"/web","/");
		
		// Setup JSP
        Configuration.ClassList classlist = Configuration.ClassList.setServerDefault(server);
        classlist.addBefore(
                "org.eclipse.jetty.webapp.JettyWebXmlConfiguration",
                "org.eclipse.jetty.annotations.AnnotationConfiguration" );
		
        server.setHandler(webapp);
		server.start();
		server.join();

	}

}
