package json.com.validation;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONArray;
import org.json.JSONObject;

public class JsonFileValidation {
	
	public static void main(String[] args) {
		String strJSON=GetContentFromTextFile("C:\\Users\\NILADRI\\Desktop\\JsonDemo.txt");
		JSONObject inputJSONObject=new JSONObject(strJSON);
		getKey(inputJSONObject, "included");
		//getKey(inputJSONObject, "age");

	}

	public static String GetContentFromTextFile(String filePath){
		String content=null;
		try {
			content=new String(Files.readAllBytes(Paths.get(filePath)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return content;
	}
	
	public static Object parseJSONObject(JSONObject json,String key){
		return json.get(key);
	}
	
	public static void getKey(JSONObject json,String key){
		boolean exists=json.has(key);
		java.util.Iterator<?> keys;
		String nextKeys;
		
		if(!exists){
			keys=json.keys();
			while(keys.hasNext()){
				nextKeys=(String)keys.next();
				try {
					if(json.get(nextKeys) instanceof JSONObject){
						if(exists==false){
							getKey(json.getJSONObject(nextKeys), key);
						}
					}else if(json.get(nextKeys) instanceof JSONArray){
						JSONArray jsonarray = json.getJSONArray(nextKeys);
						for(int i=0; i<jsonarray.length(); i++){
							String jsonarrayString =jsonarray.get(i).toString();
							JSONObject innerJSON = new JSONObject(jsonarrayString);
							if(exists == false){
								getKey(innerJSON, key);
							}
						}
					}
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
						
			}
			
			
		}else{
			String keyValue=parseJSONObject(json, key).toString();
			System.out.println(key+" : "+keyValue);
		}
	}

}
