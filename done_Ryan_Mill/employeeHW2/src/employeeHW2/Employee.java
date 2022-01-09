package employeeHW2;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import redis.clients.jedis.*;
import java.util.*;
import org.json.*;

@Path("/")
public class Employee {

	
	private final String serverIP = "35.197.12.226";
	private final String password = "Passw0rd!";
	private final String entity = "employee";
	private final String[] ids = {"1111", "2222", "3333"};
	
	private Jedis getConn() {
		Jedis db = new Jedis(serverIP);
		db.auth(password);
		System.out.println("Connection to server was successful");
		
		return db;
	}
	
	private String getJSONData(ArrayList<List<String>> arrayOfValues) {
		JSONWriter jstr = new JSONStringer();
		jstr.object();
		
		// key
		jstr.key("employee");
		
		// value (array of employee data)
		jstr.array();
		for (List<String> values: arrayOfValues) {
			// get employee information
			String first = values.get(0);
			String last = values.get(1);
			String role = values.get(2);
			String department = values.get(3);
			
			// create an object
			jstr.object();
			jstr.key("first").value(first);
			jstr.key("last").value(last);
			jstr.key("role").value(role);
			jstr.key("department").value(department);
			jstr.endObject();
		}
		jstr.endArray();
		
	jstr.endObject();
	
	return jstr.toString();
	
	}
	// get all employees
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public String getEmployee() {
		ArrayList<List<String>> arrayofValues = new ArrayList<List<String>>();
		Jedis db = getConn();
		for (int i=0; i < ids.length; i++) {
			String key = entity + ":" + ids[i];
			List<String> values = db.hvals(key);
			arrayofValues.add(values);
		}
		
		String jsonData= getJSONData(arrayofValues);
		System.out.println(jsonData);
		return jsonData;
	}
	// Get an employee with ID (any)
	// public String getEmployee(@PathParam("id") String id)
	// public String getEmployee(@QueryParam("id") Integer id)
	// public String getEmployeeWithHighestSalary()
	//Fill in these three methods

	public static void main(String[] args) {
		String serverIP = "35.197.12.226";
		String entity = "employee";
		String[] ids = {"1111", "2222", "3333",};
		
		Jedis db = new Jedis(serverIP);
		db.auth("Passw0rd!");
		System.out.println("Connection to server successfully");
		// Get three employee information
		for (int i=0; i < ids.length; i++) {
			String key = entity + ":" + ids[i];
			List<String> values = db.hvals(key);
			show(values);
		}
		System.out.println();
		
	}

	
}