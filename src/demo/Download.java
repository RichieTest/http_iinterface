/**
 * 
 */
package demo;

import java.util.Map;

import org.junit.Test;

import util.HttpRequest;

/**
 * @author DungPat
 *
 */
public class Download {
	@Test
	public void dltest01(){
		System.out.println("\nDownload-Test:https-post");
		String url = "https://appcrash.uxip.meizu.com/appcrash/applyupload";
		String params = "{\"export_type\":\"full\",\"fileIndex\":\"4\",\"db_table\":\"bdl_fdt_ov_label_mapping\",\"appKey\":\"toCsvAppKey\",\"checkInfo\":\"19e6ef9150137469d04b763c856fe6ee\",\"time\":\"20151121\",\"nonce\":\"123\"}";
		Map<String, String> map = HttpRequest.post(url, params);
		for (String key : map.keySet()) {
			System.out.println(key + ":" + map.get(key));
		}
		System.out.println("ResponseCode:" + map.get("Code"));
		System.out.println("ResponseMessage:" + map.get("Message"));
		System.out.println("Response:" + map.get("Response"));
	}
}
