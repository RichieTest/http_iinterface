package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.junit.Test;

public class HttpsRequest {
	static int contentLengthAllow = 2000;

	private static class TrustAnyTrustManager implements X509TrustManager {

		public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		}

		public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		}

		public X509Certificate[] getAcceptedIssuers() {
			return new X509Certificate[] {};
		}
	}

	private static class TrustAnyHostnameVerifier implements HostnameVerifier {
		public boolean verify(String hostname, SSLSession session) {
			return true;
		}
	}

	/**
	 * 向指定URL发送GET方法的请求
	 * 
	 * @param url 发送请求的URL
	 *            
	 * 
	 * @return Result 所代表远程资源的响应,头信息
	 */
	public static Map<String, String> get(String url) {

		Map<String, String> result = new HashMap<String, String>();
		BufferedReader in = null;

		try {
			// 创建SSLContext对象，并使用信任管理器初始化
			SSLContext MySSlContext = SSLContext.getInstance("SSL");
			MySSlContext.init(null, new TrustManager[] { new TrustAnyTrustManager() },
					new java.security.SecureRandom());

			// 打开和URL之间的连接
			URLConnection connection = new URL(url).openConnection();
			// 此处的URLConnection对象实际上是根据URL的请求协议(此处是http)生成的URLConnection类的子类HttpURLConnection
			// 故此处最好将其转化为HttpURLConnection类型的对象,以便用到HttpURLConnection更多的API.
			HttpsURLConnection httpsURLConnection = (HttpsURLConnection) connection;

			// 从上述SSLContext对象中得到SSLSocketFactory对象,并设置其SSLSocketFactory对象
			httpsURLConnection.setSSLSocketFactory(MySSlContext.getSocketFactory());
			httpsURLConnection.setHostnameVerifier(new TrustAnyHostnameVerifier());

			// 设置通用的请求属性
			httpsURLConnection.setRequestProperty("accept", "*/*");
			httpsURLConnection.setRequestProperty("connection", "Keep-Alive");
			httpsURLConnection.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");

			// Fidder监听请求
			System.setProperty("https.proxyHost", "172.16.152.56");
			System.setProperty("https.proxyPort", "8888");

			// 建立连接
			httpsURLConnection.connect();
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(httpsURLConnection.getInputStream(), "UTF-8"));
			String line;

			// 获取所有响应头字段
			Map<String, List<String>> Hearder = httpsURLConnection.getHeaderFields();
			for (String key : Hearder.keySet()) {
				result.put(key, Hearder.get(key).toString());
			}

			// responseList.clear();
			String responseStr = "";
			while ((line = in.readLine()) != null) {
				responseStr += line;
			}
			
			// Content长度限制
			if (responseStr.length() > contentLengthAllow ){
				responseStr = responseStr.substring(0, contentLengthAllow);
			}
			
			result.put("Message", httpsURLConnection.getResponseMessage());
			result.put("Code", String.valueOf(httpsURLConnection.getResponseCode()));
			result.put("Response", responseStr);

		} catch (Exception requestException) {
			System.err.println("发送GET请求出现异常!" + requestException);
			requestException.printStackTrace();
		}
		// 关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception closeException) {
				closeException.printStackTrace();
			}
		}
		return result;
	}

	public static Map<String, String> post(String url, String Params) {

		Map<String, String> result = new HashMap<String, String>();
		PrintWriter out = null;
		BufferedReader in = null;

		try {
			// 创建SSLContext对象，并使用信任管理器初始化
			SSLContext MySSlContext = SSLContext.getInstance("SSL");
			MySSlContext.init(null, new TrustManager[] { new TrustAnyTrustManager() },
					new java.security.SecureRandom());

			// 打开和URL之间的连接
			URLConnection connection = new URL(url).openConnection();
			// 此处的URLConnection对象实际上是根据URL的请求协议(此处是http)生成的URLConnection类的子类HttpURLConnection
			// 故此处最好将其转化为HttpURLConnection类型的对象,以便用到HttpURLConnection更多的API.
			HttpsURLConnection httpsURLConnection = (HttpsURLConnection) connection;
			

			// 从上述SSLContext对象中得到SSLSocketFactory对象,并设置其SSLSocketFactory对象
			httpsURLConnection.setSSLSocketFactory(MySSlContext.getSocketFactory());
			httpsURLConnection.setHostnameVerifier(new TrustAnyHostnameVerifier());
			
			// 设置通用的请求属性
			httpsURLConnection.setRequestProperty("accept", "*/*");
			httpsURLConnection.setRequestProperty("connection", "Keep-Alive");
			httpsURLConnection.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");

			// 发送POST请求必须设置
			// 设置是否向httpUrlConnection输出，因为这个是post请求，参数要放在http正文内，
			// 需要设为true, 默认情况下是false;
			httpsURLConnection.setDoOutput(true);
			// 设置是否从httpUrlConnection读入，默认情况下是true;
			httpsURLConnection.setDoInput(true);
			// Post 请求不能使用缓存
			httpsURLConnection.setUseCaches(false);

			// 设定传送的内容类型
			httpsURLConnection.setRequestProperty("Content-type", "application/json");
			// 设定请求的方法为"POST"，默认是GET
			httpsURLConnection.setRequestMethod("POST");
			// Fidder监听请求
			System.setProperty("https.proxyHost", "127.0.0.1");
			System.setProperty("https.proxyPort", "8080");

			httpsURLConnection.connect();
			// 获取URLConnection对象对应的输出流
			// 此处getOutputStream会隐含的进行connect(即：如同调用上面的connect()方法，
			// 所以在开发中不调用上述的connect()也可以)。
			out = new PrintWriter(httpsURLConnection.getOutputStream());
			// 发送请求参数
			out.print(Params);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应(把准备好的http请求正式发送到服务器)
			in = new BufferedReader(new InputStreamReader(httpsURLConnection.getInputStream()));
			String line;
			String responseStr = "";
			while ((line = in.readLine()) != null) {
				responseStr += line;
			}

			// 获取所有响应头字段
			Map<String, List<String>> Hearder = httpsURLConnection.getHeaderFields();
			for (String key : Hearder.keySet()) {
				result.put(key, Hearder.get(key).toString());
			}

			// Content长度限制
			if (responseStr.length() > contentLengthAllow ){
				responseStr = responseStr.substring(0, contentLengthAllow);
			}
			result.put("Response", responseStr);
			result.put("Message", httpsURLConnection.getResponseMessage());
			result.put("Code", String.valueOf(httpsURLConnection.getResponseCode()));

		} catch (Exception requestException) {
			System.err.print(requestException);
		}

		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}
	
	public static Map<String, String> post(String url, String params, String content_type) {

		Map<String, String> result = new HashMap<String, String>();
		PrintWriter out = null;
		BufferedReader in = null;

		try {
			// 创建SSLContext对象，并使用信任管理器初始化
			SSLContext MySSlContext = SSLContext.getInstance("SSL");
			MySSlContext.init(null, new TrustManager[] { new TrustAnyTrustManager() },
					new java.security.SecureRandom());

			// 打开和URL之间的连接
			URLConnection connection = new URL(url).openConnection();
			// 此处的URLConnection对象实际上是根据URL的请求协议(此处是http)生成的URLConnection类的子类HttpURLConnection
			// 故此处最好将其转化为HttpURLConnection类型的对象,以便用到HttpURLConnection更多的API.
			HttpsURLConnection httpsURLConnection = (HttpsURLConnection) connection;
			

			// 从上述SSLContext对象中得到SSLSocketFactory对象,并设置其SSLSocketFactory对象
			httpsURLConnection.setSSLSocketFactory(MySSlContext.getSocketFactory());
			httpsURLConnection.setHostnameVerifier(new TrustAnyHostnameVerifier());
			
			// 设置通用的请求属性
			httpsURLConnection.setRequestProperty("accept", "*/*");
			httpsURLConnection.setRequestProperty("connection", "Keep-Alive");
			httpsURLConnection.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");

			// 发送POST请求必须设置
			// 设置是否向httpUrlConnection输出，因为这个是post请求，参数要放在http正文内，
			// 需要设为true, 默认情况下是false;
			httpsURLConnection.setDoOutput(true);
			// 设置是否从httpUrlConnection读入，默认情况下是true;
			httpsURLConnection.setDoInput(true);
			// Post 请求不能使用缓存
			httpsURLConnection.setUseCaches(false);

			// 设定传送的内容类型
			httpsURLConnection.setRequestProperty("Content-type", content_type);
			// 设定请求的方法为"POST"，默认是GET
			httpsURLConnection.setRequestMethod("POST");
			// Fidder监听请求
			System.setProperty("https.proxyHost", "172.16.152.56");
			System.setProperty("https.proxyPort", "8080");

			httpsURLConnection.connect();
			// 获取URLConnection对象对应的输出流
			// 此处getOutputStream会隐含的进行connect(即：如同调用上面的connect()方法，
			// 所以在开发中不调用上述的connect()也可以)。
			out = new PrintWriter(httpsURLConnection.getOutputStream());
			// 发送请求参数
			out.print(params);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应(把准备好的http请求正式发送到服务器)
			in = new BufferedReader(new InputStreamReader(httpsURLConnection.getInputStream()));
			String line;
			String responseStr = "";
			while ((line = in.readLine()) != null) {
				responseStr += line;
			}

			// 获取所有响应头字段
			Map<String, List<String>> Hearder = httpsURLConnection.getHeaderFields();
			for (String key : Hearder.keySet()) {
				result.put(key, Hearder.get(key).toString());
			}

			// Content长度限制
			if (responseStr.length() > contentLengthAllow ){
				responseStr = responseStr.substring(0, contentLengthAllow);
			}
			result.put("Response", responseStr);
			result.put("Message", httpsURLConnection.getResponseMessage());
			result.put("Code", String.valueOf(httpsURLConnection.getResponseCode()));

		} catch (Exception requestException) {
			System.err.print(requestException);
		}

		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}
	
}