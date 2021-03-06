package gj;

  
import java.io.BufferedReader;  
import java.io.IOException;  
import java.io.InputStreamReader;  
import java.io.OutputStreamWriter;  
import java.io.PrintWriter;  
import java.net.MalformedURLException;  
import java.net.URL;  
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.HashMap;  
import java.util.Iterator;  
import java.util.Map;
  
/** 
 * @author Post Method 
 */  
public class Test {  
  
    /** 
     * 向指定URL发送POST请求 
     * @param url 
     * @param paramMap 
     * @return 响应结果 
     */  
    public static String sendPost(String url, Map<String, String> paramMap) {  
        PrintWriter out = null;  
        BufferedReader in = null;  
        String result = "";  
        try {  
            URL realUrl = new URL(url);  
            // 打开和URL之间的连接  
            URLConnection conn = realUrl.openConnection();  
            // 设置通用的请求属性  
            conn.setRequestProperty("accept", "*/*");  
            conn.setRequestProperty("connection", "Keep-Alive");  
            conn.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");  
            // conn.setRequestProperty("Charset", "UTF-8");  
            // 发送POST请求必须设置如下两行  
            conn.setDoOutput(true);  
            conn.setDoInput(true);  
            // 获取URLConnection对象对应的输出流  
            out = new PrintWriter(conn.getOutputStream());  
  
            // 设置请求属性  
            String param = "";  
            if (paramMap != null && paramMap.size() > 0) {  
                Iterator<String> ite = paramMap.keySet().iterator();  
                while (ite.hasNext()) {  
                    String key = ite.next();// key  
                    String value = paramMap.get(key);  
                    param += key + "=" + value + "&";  
                }  
                param = param.substring(0, param.length() - 1);  
            }  
  
            // 发送请求参数  
            out.print(param);  
            // flush输出流的缓冲  
            out.flush();  
            // 定义BufferedReader输入流来读取URL的响应  
            in = new BufferedReader(  
                    new InputStreamReader(conn.getInputStream()));  
            String line;  
            while ((line = in.readLine()) != null) {  
                result += line;  
            }  
        } catch (Exception e) {  
            System.err.println("发送 POST 请求出现异常！" + e);  
            e.printStackTrace();  
        }  
        // 使用finally块来关闭输出流、输入流  
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
      
    /**  
     * 数据流post请求  
     * @param urlStr  
     * @param xmlInfo  
     */  
    public static String doPost(String urlStr, String strInfo) {  
        String reStr="";  
        try {  
            URL url = new URL(urlStr);  
            URLConnection con = url.openConnection();  
            con.setDoOutput(true);  
            con.setRequestProperty("Pragma:", "no-cache");  
            con.setRequestProperty("Cache-Control", "no-cache");  
            con.setRequestProperty("Content-Type", "text/xml");  
            OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream());  
            out.write(new String(strInfo.getBytes("utf-8")));  
            out.flush();  
            out.close();  
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));  
            String line = "";  
            for (line = br.readLine(); line != null; line = br.readLine()) {  
                reStr += line;  
            }  
        } catch (MalformedURLException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return reStr;  
    }  
      
  
    /** 
     * 测试主方法 
     * @param args 
     * @throws InterruptedException 
     */  
    public static void main(String[] args) {  
        Map<String, String> mapParam = new HashMap<String, String>();  
        mapParam.put("LINE", "7");  
        mapParam.put("TYPE","1");  
        mapParam.put("LABELNO","16");  
        String pathUrl = "http://218.29.211.2:9020/gjapp/appBus/findByStationName";  
        SimpleDateFormat df =new SimpleDateFormat("HH:mm:ss");
        String result;
        String date;
        int  indexInt;
        char indexChar;
        do {
	        result = sendPost(pathUrl, mapParam);
	        try {
		        if (result != null && !"".equals(result) && result.length() > 90) {
		        	date = df.format(new java.util.Date());
		        	indexChar = result.charAt(47);
		        	indexInt = Integer.valueOf(indexChar);
		        	indexInt = indexInt - '0';
		        	if(indexInt <= 3) {
		        		System.out.println("[");
		        		for(; indexInt <= 3; indexInt++) {
		        			System.out.print("!");
		        		}
		        		System.out.println("]");
		        	}
		        	System.out.println(" [" + date +"] [" + result.charAt(47) + "][" + result.charAt(89) + "]");
		        } else {
		        	System.out.println("timeout!");
		        }
		        Thread.sleep(1000 * 10);
	        } catch (InterruptedException e) {
	        	// TODO Auto-generated catch block
	        	e.printStackTrace();
	        }
		} while (true);
    }  
  
}  