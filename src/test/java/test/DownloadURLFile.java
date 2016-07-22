package test;

import java.io.File;
import java.net.URL;

import org.apache.commons.io.FileUtils;

/** 
* @author  作者 E-mail: liuyang
* @date 创建时间：2016年7月21日 下午8:33:30 
* @version 2.0 
* @parameter  
* @since  
* @return  
*/
public class DownloadURLFile {
    /** 
     * @param args 
     */  
    public static void main(String[] args) {  
  
        String res = downloadFromUrl("http://172.168.1.12:7500/dev1/0/000/001/0000001534.fid","F:/");  
        System.out.println(res);  
    }  
  
  
    public static String downloadFromUrl(String url,String dir) {  
  
        try {  
            URL httpurl = new URL(url);  
            String fileName = getFileNameFromUrl(url);  
            System.out.println(fileName);  
            File f = new File(dir + fileName);  
            FileUtils.copyURLToFile(httpurl, f);  
        } catch (Exception e) {  
            e.printStackTrace();  
            return "Fault!";  
        }   
        return "Successful!";  
    }  
      
    public static String getFileNameFromUrl(String url){  
        String name = new Long(System.currentTimeMillis()).toString() + ".X";  
        int index = url.lastIndexOf("/");  
        if(index > 0){  
            name = url.substring(index + 1);  
            if(name.trim().length()>0){  
                return name;  
            }  
        }  
        return name;  
    }  
}  