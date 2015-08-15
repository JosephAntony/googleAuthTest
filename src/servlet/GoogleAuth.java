package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.GoogleApi;
import org.scribe.oauth.OAuthService;

public class GoogleAuth extends HttpServlet{

	private final String clinetId = "695980982576-s9f3f7cuoq9ik5qr80ctqvet1d40gnpi.apps.googleusercontent.com";
	private final String clientSecret = "A9A-WoYpaKf-xuAXGqi8t9WV";
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException,IOException{
		StringBuilder oauthUrl = new StringBuilder().append("https://accounts.google.com/o/oauth2/auth")
				.append("?client_id=").append(clinetId)
				.append("&response_type=code")
				.append("&scope=openid%20email")
				.append("&redirect_uri=http://localhost:8080/googleAuthenticator/callback")
				.append("&access_type=offline")
				.append("&approval_prompt=force");
				
				res.sendRedirect(oauthUrl.toString());
		
//		ServiceBuilder builder= new ServiceBuilder(); 	
//	      OAuthService service = builder.provider(GoogleApi.class) 
//	         .apiKey(clinetId) 
//	         .apiSecret(clientSecret) 
//	         .callback("http://localhost:8080/googleAuthenticator/test.html") 
//	         .scope("https://www.googleapis.com/auth/plus.login " + 
//	               "https://www.googleapis.com/auth/plus.me")  
//	         .debug() 
//	         .build(); //Now build the call
//	      HttpSession sess = req.getSession(); 
//	      sess.setAttribute("oauth2Service", service);
//	      res.sendRedirect(service.getAuthorizationUrl(null)); 
		
		
	}
	
}
