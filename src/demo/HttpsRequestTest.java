/**
 * 
 */
package demo;

import java.util.Map;

import org.junit.Test;

import util.HttpsRequest;

/**
 * @author DungPat
 *
 */
public class HttpsRequestTest {
	//@Test
	public void HttpsGetTest01() {
		System.out.println("\nTest:https-get01");
		Map<String, String> map = HttpsRequest.get("https://servicecut.meizu.com/interface/locate?name=y.meizu.com");
		for (String key : map.keySet()) {
			System.out.println(key + ":" + map.get(key));
		}
		System.out.println("ResponseCode:" + map.get("Code"));
		System.out.println("ResponseMessage:" + map.get("Message"));
		System.out.println("Response:" + map.get("Response"));
	}

	//@Test
	public void HttpsGetTest02() {
		System.out.println("\nTest:https-get02");
		Map<String, String> map = HttpsRequest.get(
				"https://uxinphone.meizu.com/youxin/phone?appv=1&uid=7679983&op=4&osv=1&imei=865863020037300&pv=1");
		for (String key : map.keySet()) {
			System.out.println(key + ":" + map.get(key));
		}
		System.out.println("ResponseCode:" + map.get("Code"));
		System.out.println("ResponseMessage:" + map.get("Message"));
		System.out.println("Response:" + map.get("Response"));
	}

	//@Test
	public void HttpsPostTest01() {
		System.out.println("\nTest:https-post01");
		String URL = "https://appcrash.uxip.meizu.com/appcrash/applyupload";
		String params = "{\"imei\":\"865863023007862\",\"sn\":\"760BCKL222B7\",\"version\":\"1.0\",\"fileinfos\":[{\"crashuuid\":\"12345678-1111-0a0a-12345678af9bcf00\",\"crashtime\":137882318,\"crashfile_md5\":\"3b359449f395c0c5\",\"dumpfile_md5\":\"d19ab882ef930843\",\"ext_md5\":\"\",\"package_name\":\"com.meizu.media.camera\",\"crash_cause_type\":\"unknow\"}]}";
		Map<String, String> map = HttpsRequest.post(URL, params);
		for (String key : map.keySet()) {
			System.out.println(key + ":" + map.get(key));
		}
		System.out.println("ResponseCode:" + map.get("Code"));
		System.out.println("ResponseMessage:" + map.get("Message"));
		System.out.println("Response:" + map.get("Response"));
	}
	
	//@Test
	public void HttpsPostTest02() {
		System.out.println("\nTest:https-post02");
		String URL = "https://eouc.meizu.com/appcrash/applyupload";
		String params = "imei=865863023007862&sn=760BCKL222B7&version=1.0&fileinfos=[{\"crashuuid\":\"12345678-1111-0a0a-12345678af9bcf00\",\"crashtime\":137882318,\"crashfile_md5\":\"3b359449f395c0c5\",\"dumpfile_md5\":\"d19ab882ef930843\",\"ext_md5\":\"\",\"package_name\":\"com.meizu.media.camera\", \"crash_cause_type\":\"unknow\"}]";
		Map<String, String> map = HttpsRequest.post(URL, params);
		for (String key : map.keySet()) {
			System.out.println(key + ":" + map.get(key));
		}
		System.out.println("ResponseCode:" + map.get("Code"));
		System.out.println("ResponseMessage:" + map.get("Message"));
		System.out.println("Response:" + map.get("Response"));
	}
	
	@Test
	public void HttpsPostTest03() {
		System.out.println("\nTest:https-post03");
		String URL = "https://eouc.meizu.com/appcrash/upload";
		String params = "crash_data=@java.crash.gz --form dump_data=@1.modem.gz  --form ext_data= --form-string imei=865863023007862 --form-string flyme_uid= --form-string sn=760BCKL222B7 --form-string package_name=com.meizu.media.camera --form-string package_ver=4.0.1 --form-string device=M462 --form-string \"flyme_ver=espresso5430-user 4.4.4 KTU84P builder.20141112120333 test-keys\" --form-string \"os_version=4.4.4-1415765013_I\" --form-string root=false --form-string operator= --form-string country=CN --form-string network=wifi --form-string ip= --form-string dns= --form-string cell_id= --form-string cpu_info= --form-string mem_info= --form-string location= --form-string nonce=e7aaf2282060ccd80b17d61350cfa02f --form-string crashuuid=12345678 --form-string crashtype=2 --form-string crashtime=137882318 --form-string crash_cause_type= --form-string crash_cause= --form-string crash_md5=d6eea74842c8f1e29a3988b1109c0ae7 --form-string dump_md5=e7f61374f04534058595b640b5f73acb --form-string ext_md5=";
		Map<String, String> map = HttpsRequest.post(URL, params, "multipart/form-data; boundary=---011000010111000001101001");
		for (String key : map.keySet()) {
			System.out.println(key + ":" + map.get(key));
		}
		System.out.println("ResponseCode:" + map.get("Code"));
		System.out.println("ResponseMessage:" + map.get("Message"));
		System.out.println("Response:" + map.get("Response"));
	}

}
