package fr.runn.catalina.valves;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.ServletException;

import org.apache.catalina.connector.CoyotePrincipal;
import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.catalina.valves.ValveBase;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

public class RemoteUserValve extends ValveBase {
	
	private static final Log log = LogFactory.getLog(RemoteUserValve.class);
	
	public void invoke(Request request, Response response) throws IOException,
			ServletException {
		String remoteUser = request.getHeader("REMOTE_USER");
		Principal userPrincipal = request.getUserPrincipal();
		if (userPrincipal == null && remoteUser != null && !"".equals(remoteUser)) {
			request.setUserPrincipal(new CoyotePrincipal(remoteUser));
			log.debug(remoteUser + " authenticated with REMOTE_USER Request Header");
		}
		getNext().invoke(request, response);
	}
	
}