package servlet;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import pojo.UserObject;

public class Callback extends HttpServlet{
	
	private final String USER_AGENT = "Mozilla/5.0";
	private final String client_id  = "695980982576-s9f3f7cuoq9ik5qr80ctqvet1d40gnpi.apps.googleusercontent.com";
	private final String clientSecret = "A9A-WoYpaKf-xuAXGqi8t9WV";
	private static UserObject userobj = null;
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException,IOException{
		String code = req.getParameter("code");
		System.out.println("the code is " + code);
		String url = "https://accounts.google.com/o/oauth2/token?";
		URL obj = new URL(url);
		HttpsURLConnection con = (HttpsURLConnection)obj.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		
		String urlParameters = "code="+code+"&client_id="+ client_id + "&client_secret=" + clientSecret + "&redirect_uri=http://localhost:8080/googleAuthenticator/callback&grant_type=authorization_code";
		con.setDoInput(true);
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();
		
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + urlParameters);
		System.out.println("Response Code : " + responseCode);
		
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		
		//print result
		System.out.println(response.toString());
		JSONObject jsonObject = null;
		try {
			jsonObject = (JSONObject) new JSONParser().parse(response.toString());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String accessToken = (String)jsonObject.get("access_token");
		System.out.println("the access token is " + accessToken);
		String json = get("https://www.googleapis.com/oauth2/v1/userinfo?access_token="+accessToken);
		System.out.println("the json is " +  json);
		try {
			JSONObject temp = (JSONObject)new JSONParser().parse(json);
			System.out.println("name is " + temp.get("name"));
			System.out.println("name is " + temp.get("email"));
			userobj = new UserObject();
			userobj.setName(temp.get("name").toString());
			userobj.setEmail(temp.get("email").toString());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		res.sendRedirect("/googleAuthenticator/test.html");
	}
	
	
	public String execute(HttpRequestBase request ) throws ClientProtocolException, IOException{
		HttpClient httpClient = new DefaultHttpClient();
		HttpResponse response = httpClient.execute(request);
		HttpEntity entity = response.getEntity();
		String body = EntityUtils.toString(entity);
		return body;
	}
	
	public String get(String url) throws ClientProtocolException, IOException{
		return execute(new HttpGet(url));
	}


	public static UserObject getUserobj() {
		return userobj;
	}		
	
}
