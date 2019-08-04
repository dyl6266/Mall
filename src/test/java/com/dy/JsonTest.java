package com.dy;

import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class JsonTest {

	public static void main(String[] args) {
		Gson gson = new GsonBuilder().create();
		String json = "{\"블루\":{\"200\":1,\"210\":1},\"레드\":{\"220\":1,\"230\":1},\"화이트\":{\"250\":1,\"260\":1}}";
		JsonObject jsonObj = gson.fromJson(json, JsonObject.class);
		
		JsonObject testObj = new JsonObject(); 
		HashMap<String, Object> hash = new HashMap<>();
		
		for (String key : jsonObj.keySet()) {
			JsonElement value = jsonObj.get(key);
			HashMap<String, Object> test = gson.fromJson(value, HashMap.class);
			
			for (String size : test.keySet()) {
				System.out.println("size : " + size);
				System.out.println("value : " + test.get(size));
			}
//			System.out.println("key : " + key);
//			System.out.println("value : " + jsonObj.get(key));
			
//			HashMap test = gson.fromJson(value, HashMap.class);
			System.out.println(test);
				
//			hash = gson.fromJson(value, HashMap.class);
		}
		
		System.out.println(hash.get("250"));
		System.out.println(hash.get("260"));
		System.out.println("");
	}

}
