import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Arrays;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import java.io.IOException;

public class JsonParse
{
	public static void main(String[] args)
	{
		//json();
		int sum = 0, arr[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
		for(int i : arr) {
			sum += i;
		}
		System.out.println("Sum of array of size " + arr.length + " is " + sum);
		
		int a = 60, b = 13;
		System.out.println(a + " is " + Integer.toBinaryString(a) + ", " + b + " is " + Integer.toBinaryString(b));
		System.out.println(a - b + " is " + Integer.toBinaryString(a-b));
		System.out.println("~b is " + ~b + " " + Integer.toBinaryString(~b));
		System.out.println("~b + 1 is " + (~b + 1) + " " + Integer.toBinaryString(~b+1));
		System.out.println("a + ~b is " + a + ~b + "=" + (a + ~b) + " " + Integer.toBinaryString(a + ~b));
		System.out.println("a + (~b + 1) is " + a + (~b + 1) + "=" + (a + (~b+1)) + " " + Integer.toBinaryString(a + (~b + 1)));
	}
	
	private static void json()
	{
		String custJson =
		"{ \"LGICUS01OperationResponse\": " + 
			"{ \"ca\": " +
				"{ " +
					"\"ca_request_id\": \"\", " +
					"\"ca_return_code\": 0, " +
					"\"ca_customer_num\": 1, " +
					"\"ca_first_name\": \"ANDREW\", " +
					"\"ca_last_name\": \"PANDY\", " +
					"\"ca_dob\": \"1950-07-11\", " +
					"\"ca_house_name\": \"\", " +
					"\"ca_house_num\": \"34\", " +
					"\"ca_postcode\": \"PI101O\", " +
					"\"ca_num_policies\": 0, " +
					"\"ca_phone_mobile\": \"01962 811234\", " +
					"\"ca_phone_home\": \"07799 123456\", " +
					"\"ca_email_address\": \"A.PANDY@BEEBHOUSE.COM\", " +
					"\"ca_policy_data\": \"\" " +
				"} " +
			"} " +
		"}";
		
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			//JsonFactory factory = objectMapper.getFactory();
			//JsonParser parser = factory.createParser(custJson);
			//JsonNode topNode = objectMapper.readTree(parser);
			
			JsonNode topNode = objectMapper.readValue(custJson, JsonNode.class);
			String top = topNode.asText();
			System.out.println("top = " + topNode.toString());

			JsonNode respNode = topNode.path("LGICUS01OperationResponse");
			String resp = respNode.asText();
			System.out.println("resp = " + respNode.toString());

			JsonNode caNode = respNode.path("ca");
			String ca = caNode.asText();
			System.out.println("ca = " + caNode.toString());

			int custNum = caNode.get("ca_customer_num").asInt();
			System.out.println("custNum = " + custNum);

			/*
			JsonNode array = node.get("owners");
			JsonNode jsonNode = array.get(0);
			String john = jsonNode.asText();
			System.out.println("john  = " + john);

			JsonNode child = node.get("nestedObject");
			JsonNode childField = child.get("field");
			String field = childField.asText();
			System.out.println("field = " + field);
			*/

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
