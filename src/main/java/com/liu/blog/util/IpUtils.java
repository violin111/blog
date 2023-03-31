package com.liu.blog.util;

import com.alibaba.fastjson.JSON;
import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.web.HttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.util.List;
import java.util.Map;


public class IpUtils {
    public static String getIdAddress(HttpServletRequest request){
       String ipAddress=null;
       try{
           ipAddress = request.getHeader("x-forwarded-for");
           if(ipAddress==null || ipAddress.length()==0 || "unknown".equals(ipAddress)){
               ipAddress = request.getHeader("Proxy-Client-IP");
           }
           if(ipAddress==null || ipAddress.length()==0 || "unknown".equals(ipAddress)){
               ipAddress = request.getHeader("WL-Proxy-Client-IP");
           }
           if(ipAddress==null || ipAddress.length()==0 || "unknown".equals(ipAddress)){
               ipAddress = request.getRemoteAddr();
               // 根据网卡取本机配置的IP
               if("127.0.0.1".equals(ipAddress)){
                   InetAddress inet=null;
                   try{
                       inet=InetAddress.getLocalHost();
                   }catch (Exception e){
                        e.printStackTrace();
                   }
                   ipAddress=inet.getHostAddress();
               }
           }
           // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
           if (ipAddress != null && ipAddress.length() > 15) {
               // = 15
               if (ipAddress.indexOf(",") > 0) {
                   ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
               }
           }
       }catch (Exception e){
            ipAddress="";
       }

        return ipAddress;
    }

    public static String getIpSource(String ipAddress){
        try {
            URL url = new URL("http://opendata.baidu.com/api.php?query=" + ipAddress + "&co=&resource_id=6006&oe=utf8");
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream(), "utf-8"));
            String line = null;
            StringBuffer result = new StringBuffer();
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            reader.close();
            Map map = JSON.parseObject(result.toString(), Map.class);
            List<Map<String, String>> data = (List) map.get("data");
            return data.get(0).get("location");
        } catch (Exception e) {
            return "";
        }
    }

    public static UserAgent getUserAgent(HttpServletRequest request){
        return UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
    }

}
