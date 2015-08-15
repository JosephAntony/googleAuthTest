package service;
 
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.json.JSONException;
import org.json.JSONObject;

import pojo.UserObject;
import servlet.Callback;

@Path("/loginDetails")
public class User {
	
	@GET
	@Produces("application/json")
	public Response userData() throws JSONException{
		UserObject userObj = Callback.getUserobj();
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("name", userObj.getName());
		jsonObject.put("email", userObj.getEmail());
		return Response.status(200).entity(jsonObject.toString()).build();
	}
	
}
