package hw2test;

import redis.clients.jedis.*;
import java.util.*;

public class HW2Test {

public static void show(List<String> values) {
	String first = values.get(0);
	String last = values.get(1);
	String role = values.get(2);
	String department = values.get(3);
	System.out.println(
			"first: " + first + ", " +
			"last: " + last + ", " + 
			"role: " + role + ", " +
			"department: " + department);
}
	public static void main(String[] args) {
		String serverIP = "35.197.12.226";
		String entity = "employee";
		String[] ids = {"1111", "2222", "3333"};
		
		Jedis db = new Jedis(serverIP);
		db.auth("Passw0rd!");
		System.out.println("Connection to server successfully");
		
		// get three employee information
		
		for (int i=0; i < ids.length; i++) {
			String key = entity + ":" + ids[i];
			List<String> values = db.hvals(key);
			show(values);
			
		}
		System.out.println();
		
		// get an employee with 1111 id
		String key = entity + ":" + "1111";
		List<String> values = db.hvals(key);
		show(values);
		System.out.println();
		
		// get an employee with the highest salary
		key = entity + ":" + "salaries";
		Set<String>empIds = db.zrevrange(key, 0, -1);
		String empId = empIds.iterator().next();
		 
		// get the employee information with the id.
		key = entity + ":" + empId;
		values = db.hvals(key);
		show(values);
	}
	
	
	
	

}