package com.hxp.util;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * Created by Administrator on 2015/7/23.
 */

public class FtpUtil {

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(FtpUtil.class);

//    public String downFileRoot= getClass().getClassLoader().getResource("").getPath();
//    public String downFileRoot= getClass().getResource("/").toString();


    /**
     * 获得连接-FTP方式
     * @param hostName
     * @param port
     * @param userName
     * @param passWord
     * @return
     */
    public FTPClient getConnectionFTP(String hostName, int port, String userName, String passWord) {
        //创建FTPClient对象
        FTPClient ftp = new FTPClient();
        try {
            //连接FTP服务器
            ftp.connect(hostName, port);
            //下面三行代码必须要，而且不能改变编码格式，否则不能正确下载中文文件
            ftp.setControlEncoding("GBK");
            FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_NT);
            conf.setServerLanguageCode("zh");
            //登录ftp
            ftp.login(userName, passWord);
            if (!FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
                ftp.disconnect();
                logger.error("连接服务器失败");
            }else {
                logger.info("登陆服务器成功");
            }
        } catch (IOException e) {
            logger.error("异常信息=====>"+e.getMessage());
            logger.error("连接服务器失败");
        }
        return ftp;
    }

    /**
     * 关闭连接-FTP方式
     * @param ftp FTPClient对象
     * @return boolean
     */
    public boolean closeFTP(FTPClient ftp) {
        if (ftp.isConnected()) {
            try {
                ftp.disconnect();
                logger.info("ftp已经关闭");
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 下载文件-FTP方式
     * @param ftp FTPClient对象
     * @param path FTP服务器文件地址
     * @param fileName 文件名
     * @param localPath 本里存储路径
     * @return boolean
     */
    public boolean downFile(FTPClient ftp, String path, String fileName, String localPath) {
        boolean success = false;
        try {
            boolean b =  ftp.changeWorkingDirectory(path);//转移到FTP服务器目录
            FTPFile[] fs = ftp.listFiles(); //得到目录的相应文件列表
            for (FTPFile ff : fs) {
                if (ff.getName().equals(fileName)) {
                    File localFile = new File(localPath);
                    if(!localFile.exists()){
                        localFile.mkdirs();
                    }
                    localFile = new File(localFile + "/" + ff.getName());
                    localFile.createNewFile();
                    OutputStream outputStream = new FileOutputStream(localFile);
                    //将文件保存到输出流outputStream中
                    ftp.retrieveFile(new String(ff.getName().getBytes("GBK"), "ISO-8859-1"), outputStream);
                    outputStream.flush();
                    outputStream.close();
                    logger.info("下载成功");
                }
            }
            ftp.logout();
            success = true;
        } catch (Exception e) {
            logger.info("下载失败");
            e.printStackTrace();
        }
        return success;
    }

    /**
     * 判断是否有重名文件
     * @param fileName
     * @param fs
     * @return
     */
    public static boolean isFileExist(String fileName, FTPFile[] fs) {
        for (int i = 0; i < fs.length; i++) {
            FTPFile ff = fs[i];
            if (ff.getName().equals(fileName)) {
                return true; //如果存在返回 正确信号
            }
        }
        return false; //如果不存在返回错误信号
    }

    /**
     * 根据重名判断的结果 生成新的文件的名称
     * @param fileName
     * @param fs
     * @return
     */
    public static String changeName(String fileName, FTPFile[] fs) {
        int n = 0;
//		fileName = fileName.append(fileName);
        while (isFileExist(fileName.toString(), fs)) {
            n++;
            String a = "[" + n + "]";
            int b = fileName.lastIndexOf(".");//最后一出现小数点的位置
            int c = fileName.lastIndexOf("[");//最后一次"["出现的位置
            if (c < 0) {
                c = b;
            }
            StringBuffer name = new StringBuffer(fileName.substring(0, c));//文件的名字
            StringBuffer suffix = new StringBuffer(fileName.substring(b + 1));//后缀的名称
            fileName = name.append(a) + "." + suffix;
        }
        return fileName.toString();
    }

    /**
     *
     * @param args
     *
     * @throws FileNotFoundException
     *
     * 测试程序
     *
     */
    public static void main(String[] args) throws FileNotFoundException {

       /* String path = "/home1/ftproot/textftp/test/";
        FtpUtil a = new FtpUtil();
        FTPClient ftp = a.getConnectionFTP("111.222.333.444", 21, "testU", "testP");
        a.downFile(ftp, "!#hwyhftp", path, "欢[2].txt");
        a.closeFTP(ftp);*/

//        System.out.println(new FtpUtil().downFileRoot);
    }


}