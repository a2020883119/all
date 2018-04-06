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
import java.util.Date;
import java.util.HashMap;  
import java.util.Iterator;  
import java.util.Map;
  
/** 
 * @author Post Method 
 */  
public class Bus {  
  
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
        //线路号
        mapParam.put("LINE", "7");
        //行驶方向
        mapParam.put("TYPE","1");
        //站牌序号
        mapParam.put("LABELNO","16");  
        //接口地址
        String pathUrl = "http://218.29.211.2:9020/gjapp/appBus/findByStationName";
        //格式化日期
        SimpleDateFormat df =new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat df2 =new SimpleDateFormat("mm:ss");
        //返回结果
        String result;
        //当前时间
        String date;
        //下一班车距离 int型
        int  indexInt;
        //下一班车距离 char型
        char indexChar;
        //上一班车路过此站台时间
        String lastOne = null;
        Date dateMS = null;
        Date lastOneMS = null; 
        do {
//        	发送请求
	        result = sendPost(pathUrl, mapParam);
//	                          得到当前时间，并格式化
	        date = df.format(dateMS = new java.util.Date());
	        //判断是否超时
	        if (result != null && !"".equals(result)) {
	        	//判断是否有下趟车
	        	if (result.length() > 48) {
	        		indexChar = result.charAt(47);
	        		indexInt = Integer.valueOf(indexChar);
	        		indexInt = indexInt - '0';
	        		//预警
	        		if (indexInt == 1) {
	        			
	        			lastOneMS = dateMS;
	        			lastOne = date;
	        		}
	        		if(indexInt <= 3) {
	        			System.out.print("[");
	        			for(; indexInt <= 3; indexInt++) {
	        				System.out.print("!");
	        			}
	        			System.out.print("] ");
	        		}
	        		System.out.print("[" + date +"] [" + result.charAt(47) + "]");
	        		//确定是否有下下趟车
		        	if (result.length() > 91) {
		        		if(result.charAt(89) == '1') {
		        			if (result.charAt(90) != '"') {
		        				System.out.print("[" + result.charAt(89) + "" + result.charAt(90) +"]");
		        			}
		        		} else {
		        			System.out.print("[" + result.charAt(89) + "]");
		        		}
		        	}
		        	if (lastOne != null) {
		        		System.out.print(" [" + lastOne + "] ");
		        	}
		        	if (dateMS != null && lastOneMS != null) {
		        		System.out.print(" [" + df2.format(dateMS.getTime() - lastOneMS.getTime()) + "]");
		        	}
	        	} else {
	        		System.out.print("time out !");
	        	}
	        	System.out.println();
	        }
	        try {
	        	Thread.sleep(1000 * 10);
	        } catch (InterruptedException e) {
	        	// TODO Auto-generated catch block
	        	e.printStackTrace();
	        }
		} while (1 == 1);
    }  
  
}  