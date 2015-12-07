package demo;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import util.HttpRequest;

public class HttpRequestTest {
	//@Test
	public void httpGetTest01(){
		System.out.println("\nTest:http-post01");
		String url = "http://servicecut.meizu.com/interface/locate?name=y.meizu.com";
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("accept", "*/test");
		headers.put("content-type", "html");
		//headers.put("user-agent","test");
		Map<String, String> map = HttpRequest.get(url, headers);
		
		for (String key : map.keySet()) {
			System.out.println(key + ":" + map.get(key));
		}
		System.out.println("Response:" + map.get("Response"));
	}
	
	
	
	//@Test
	public void httpPostTest01(){
		System.out.println("\nTest:http-post01");
		String URL = "http://kiev.orion.meizu.com:8080/recommendByContent";
		String params = "{\"biz\": {\"openid\": \"custom\", \"uid\": \"865863023397922\", \"source_id_type\": \"None\", \"token\": \"b99186dbfbf9197daeec5b1f4c198e42\", \"ts\": \"12323\", \"reqid\": \"864793029443608\", \"context\": \"{\"channel\":\" \",\"title\":\" kk \", \"keyword\":\" \", \"category\":\" \"}\", \"content_id\": \"1155\"}, \"com\": {\"openid\": \"custom\", \"uid\": \"865863023397922\", \"token\": \"b99186dbfbf9197daeec5b1f4c198e42\", \"ts\": \"12323\", \"reqid\": \"864793029443608\", \"os\": \"Flyme4.5\"}}";
		Map<String, String> map = HttpRequest.post(URL, params);
		for (String key : map.keySet()) {
			System.out.println(key + ":" + map.get(key));
		}
		System.out.println("ResponseCode:" + map.get("Code"));
		System.out.println("ResponseMessage:" + map.get("Message"));
		System.out.println("Response:" + map.get("Response"));
		System.out.println("test:"+map.get(null));
	}
	
	//@Test
	public void testHttpPost02() {
		System.out.println("\nTest:http-post02");
		String URL = "http://datarsyn.orion.meizu.com/download";
		String params = "{\"export_type\":\"full\",\"fileIndex\":\"4\",\"db_table\":\"bdl_fdt_ov_label_mapping\",\"appKey\":\"toCsvAppKey\",\"checkInfo\":\"19e6ef9150137469d04b763c856fe6ee\",\"time\":\"20151121\",\"nonce\":\"123\"}";
		Map<String, String> map = HttpRequest.post(URL, params);
		for (String key : map.keySet()) {
			System.out.println(key + ":" + map.get(key));
		}
		System.out.println("ResponseCode:" + map.get("Code"));
		System.out.println("ResponseMessage:" + map.get("Message"));
		System.out.println("Response:" + map.get("Response"));
		System.out.println("test:"+map.get(null));
	}
	
	@Test
	public void testHttpPost03() {
		System.out.println("\nTest:http-post03");
		//String url = "http://api.orion.meizu.com/userprofile/getTags?reqid=%221232%22&openid=userprofile&token=b99186dbfbf9197daeec5b1f4c198e42&ts=12336&operator=maohui";
		//String params = "{\"operator\": \"maohui\", \"openid\": \"userprofile\", \"reqid\": 864793029443608, \"token\": \"b99186dbfbf9197daeec5b1f4c198e42\", \"ts\": 12323}";
		String url = "http://datarsyn.orion.meizu.com/download";
		String params = "{\"export_type\":\"full\",\"fileIndex\":\"4\",\"db_table\":\"bdl_fdt_ov_label_mapping\",\"appKey\":\"toCsvAppKey\",\"checkInfo\":\"19e6ef9150137469d04b763c856fe6ee\",\"time\":\"20151121\",\"nonce\":\"123\"}";
		
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("test", "test");
		headers.put("user-agent", "meizu");
		Map<String, String> map = HttpRequest.post(url, params, headers);
		for (String key : map.keySet()) {
			System.out.println(key + ":" + map.get(key));
		}
		System.out.println("ResponseCode:" + map.get("Code"));
		System.out.println("ResponseMessage:" + map.get("Message"));
		System.out.println("Response:" + map.get("Response"));
	}
	
	
	
}
	
