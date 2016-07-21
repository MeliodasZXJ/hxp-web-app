package com.hxp.common.fileUpload;

import com.hxp.util.StringUtil;
import org.apache.log4j.Logger;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;


/**
 * 文件操作工具类.
 * @version 1.0.0
 */
public class FileUtils {
	protected Logger log = Logger.getLogger(FileUtils.class);
	private static final int DEFAULT_BUFFER_SIZE = 1024 * 1024 * 5;//5M

	/**
	 * 拷贝文件
	 * @param src 原文件
	 * @param dest 目的文件
	 * @return 是否成功
	 */
	public static boolean copyFile(File src, File dest) {
		if (src == null || !src.isFile()) return false;
		if (!dest.getParentFile().exists() && !dest.getParentFile().mkdirs()) return false;
		if (dest.isFile() && dest.exists()) dest.delete();

		FileChannel fcIn = null, fcOut = null;
		RandomAccessFile rafIn = null, rafOut = null;
		byte[] buff = new byte[DEFAULT_BUFFER_SIZE];

		try {
			rafIn = new RandomAccessFile(src, "r");
			rafOut = new RandomAccessFile(src, "rw");
			fcIn = rafIn.getChannel();
			fcOut = rafOut.getChannel();

			long fileSize = fcIn.size();
			MappedByteBuffer mbbIn = fcIn.map(MapMode.READ_ONLY, 0, fileSize);
			MappedByteBuffer mbbOut = fcOut.map(MapMode.READ_WRITE, 0, fileSize);

			if (fileSize <= DEFAULT_BUFFER_SIZE) {// 如果文件不大,可以选择一次性读取到内存
				mbbIn.get(buff, 0, (int) fileSize);
				mbbOut.put(buff, 0, (int) fileSize);

			} else {// 如果文件内容很大,可以循环读取,计算应该读取多少次
		        long cycle = fileSize / DEFAULT_BUFFER_SIZE;
		        int mode = (int)(fileSize % DEFAULT_BUFFER_SIZE);
		        for (int i = 0; i < cycle; i++) {// 每次读取DEFAULT_BUFFER_SIZE个字节
		        	mbbIn.get(buff, 0, DEFAULT_BUFFER_SIZE);
		        	mbbOut.put(buff, 0, DEFAULT_BUFFER_SIZE);
		        }
		        if (mode > 0) {
		        	buff = new byte[mode];
		        	mbbOut.get(buff, 0, mode);
		        	mbbOut.put(buff, 0, mode);
		        }
			}

//			原有方式，未与上述方式比较哪种效率更高
//			long num = 0;
//			while (fcIn.position() < fcIn.size()) {
//				num = ((fcIn.size() - fcIn.position()) > DEFAULT_BUFFER_SIZE)
//						? DEFAULT_BUFFER_SIZE : (fcIn.size() - fcIn.position());
//	            fcIn.transferTo(fcIn.position(), num, fcOut);
//	            fcIn.position(fcIn.position() + num);
//	        }

			return true;
		} catch (IOException e) {
		//	log.error("", e.fillInStackTrace());
		} finally {
			try {
				if (fcOut != null) fcOut.close();
			} catch (IOException e) {}
			try {
				if (fcIn != null) fcIn.close();
			} catch (IOException e) {}
			try {
				if (rafIn != null) rafIn.close();
			} catch (IOException e) {}
			try {
				if (rafOut != null) rafOut.close();
			} catch (IOException e) {}
		}
		return false;
	}

	/**
	 * 拷贝目录
	 * @param src 原目录
	 * @param dest 目的目录
	 * @return 是否成功
	 */
	public static boolean copyDirectory(File src, File dest) {
		try {
			if (!src.isDirectory()) return false;

			File[] arr = src.listFiles();
			if (arr == null || arr.length < 1) return true;

			File f = null;
			String relativePath = null;
			for (int i = 0; i < arr.length; i++) {
				relativePath = arr[i].getAbsolutePath().substring(src.getAbsolutePath().length());
				f = new File(dest.getAbsolutePath() + relativePath);
				if (arr[i].isDirectory()) {
					if (f.isFile() && !f.delete()) { return false; }

					if (!f.exists() && !f.mkdirs())	{ return false; }

					if (!copyDirectory(arr[i], f)) { return false; }
				} else {
					if (f.isDirectory() && !delDirectory(f)) { return false; }
					if (!copyFile(arr[i], f)) {	return false; }
				}
			}
			return true;
		} catch(Exception e) {}
		return false;
	}

	/**
	 * 拷贝文件或目录
	 * @param src 原文件或目录
	 * @param dest 目的文件或目录
	 * @return 是否成功
	 */
	public static boolean copy(File src, File dest) {
		if (src.isFile()) {
			return copyFile(src, dest);
		} else if (src.isDirectory()) {
			return copyDirectory(src, dest);
		} else
			return false;
	}

	/**
	 * 移动文件
	 * @param src 原文件
	 * @param dest 目的文件
	 * @return 是否成功
	 */
	public static boolean moveFile(File src, File dest) {
		if (!copyFile(src, dest)) return false;
		return src.delete();
	}

	/**
	 * 移动目录
	 * @param src 原目录
	 * @param dest 目的目录
	 * @return 是否成功
	 */
	public static boolean moveDirectory(File src, File dest) {
		if (!copyDirectory(src, dest)) return false;
		return delDirectory(src);
	}

	/**
	 * 移动文件或目录
	 * @param src 原文件或目录
	 * @param dest 目的文件或目录
	 * @return 是否成功
	 */
	public static boolean move(File src, File dest) {
		if (src.isFile()) {
			return moveFile(src, dest);
		} else if (src.isDirectory()) {
			return moveDirectory(src, dest);
		} else
			return false;
	}

	/**
	 * 删除文件或目录
	 * @param file 待删除的文件或目录
	 * @return 是否删除成功
	 */
	public static boolean delete(File file) {
		try {
			if (file.isFile())
				return file.delete();
			else if (file.isDirectory())
				return delDirectory(file);
			else
				return false;
		} catch (Exception e) {
//			log.error("", e.fillInStackTrace());
		}
		return false;
	}

	/**
	 * 删除目录
	 * @param file 待删除的目录
	 * @return 是否删除成功
	 */
	public static boolean delDirectory(File file) {
		if (!file.isDirectory())
			return false;
		File[] arr = file.listFiles();
		if (arr != null && arr.length > 0) {
			for (int i = 0; i < arr.length; i++) {
				if (arr[i].isDirectory()) {
					if (!delDirectory(arr[i]))
						return false;
				} else if (arr[i].isFile())
					if (!arr[i].delete())
						return false;
			}
		}
		return file.delete();
	}

	/**
	 * 新建目录
	 * @param path 待新建的目录
	 */
	public static void createDirectory(String path) {
		File directory = new File(path);
		if (!directory.exists())
			directory.mkdirs();
	}

	/**
	 * 从文件读取字节内容
	 * @param file 指定文件
	 * @return 文件字节内容
	 */
	public static byte[] readBytes(File file) {
		if (file.exists() == false) return null;

		FileChannel fc = null;
		RandomAccessFile raf = null;
        byte[] buff = new byte[DEFAULT_BUFFER_SIZE];
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		try {
			raf = new RandomAccessFile(file, "r");
			fc = raf.getChannel();
			long fileSize = fc.size();
			//使用内存文件映射，速度会快很多
			MappedByteBuffer mbb = fc.map(MapMode.READ_ONLY, 0, fileSize);

			if (fileSize <= DEFAULT_BUFFER_SIZE) {// 如果文件不大,可以选择一次性读取到数组
				mbb.get(buff, 0, (int)fileSize);

			} else {// 如果文件内容很大,可以循环读取,计算应该读取多少次
		        long cycle = fileSize / DEFAULT_BUFFER_SIZE;
		        int mode = (int)(fileSize % DEFAULT_BUFFER_SIZE);
		        for (int i = 0; i < cycle; i++) {
		        	mbb.get(buff);// 每次读取DEFAULT_BUFFER_SIZE个字节
		            baos.write(buff);
		        }
		        if(mode > 0) {
		        	buff = new byte[mode];
		        	mbb.get(buff);
		        	baos.write(buff);
		        }
			}
			return baos.toByteArray();
		} catch (Exception e) {
//			log.error("", e);
		} finally {
			try {
				if (fc != null) fc.close();
			} catch (Exception e) {}
			try {
				if (raf != null) raf.close();
			} catch (Exception f) {}
			try {
				if (baos != null) baos.close();
			} catch (IOException g) {}
		}
		return null;
	}

	/**
	 * 从文件读取字节内容
	 * @param filePath 文件路径
	 * @return 文件字节内容
	 */
	public static byte[] readBytes(String filePath) {
		if (filePath==null || filePath.trim().length() < 1) return null;
		File file = new File(filePath.trim());
		if (file.exists() == false) return null;
		return readBytes(file);
	}

	/**
	 * 将字节数组写入文件
	 * @param fileBytes 文件字节内容
	 * @param file 目标文件
	 * @param append 是否追加
	 */
	public static void writeBytes(byte[] fileBytes, File file, boolean append) {
		FileChannel fc = null;
		RandomAccessFile raf = null;
		try {
			raf = new RandomAccessFile(file, "rw");
			fc = raf.getChannel();
		    fc.write(ByteBuffer.wrap(fileBytes), append ? fc.size() : 0);
		} catch (Exception e) {
//			log.error(file.getAbsolutePath(), e);
		} finally {
			try {
				if (fc != null) fc.close();
			} catch (Exception f) {}
			try {
				if (raf != null) raf.close();
			} catch (IOException g) {}
		}
	}

	/**
	 * 将字节数组写入文件
	 * @param fileBytes 文件字节内容
	 * @param filePath 目标文件路径
	 * @param append 是否追加
	 */
	public static void writeBytes(byte[] fileBytes, String filePath, boolean append) {
		if (filePath==null || filePath.trim().length() < 1) return;
		File file = new File(filePath.trim());
		if (false == append && file.exists()) file.delete();
		writeBytes(fileBytes, file, append);
	}

	/**
	 * 输入流写入文件
	 * @param is 输入流
	 * @param file 目标文件
	 * @param append 是否追加
	 */
	public static void writeBytes(InputStream is, File file, boolean append) {
		OutputStream os = null;
		try {
			os = new FileOutputStream(file, append);
			IOUtils.copy(is, os);
		} catch (Exception e) {
//			log.error(file.getAbsolutePath(), e);
		} finally {
			try {
				if (os != null) os.close();
			} catch (IOException f) {}
		}
	}

	/**
	 * 输入流写入文件
	 * @param is 输入流
	 * @param filePath 目标文件保存路径
	 * @param append 是否追加
	 */
	public static void writeBytes(InputStream is, String filePath, boolean append) {
		if (filePath==null || filePath.trim().length() < 1) return;
		File file = new File(filePath.trim());
		if (false == append && file.exists()) file.delete();
		writeBytes(is, file, append);
	}

	/**
	 * 获取文件后缀
	 * @param file 文件
	 * @return 后缀名
	 */
	public static String getExtension(File file) {
		if (file == null || file.exists() == false) { return null; }
		return getExtension(file.getName());
	}

	/**
	 * 获取文件后缀
	 * @param filePath 文件路径
	 * @return 文件后缀，不包含符号“.”
	 */
	public static String getExtension(String filePath) {
		if (filePath == null || StringUtil.trimToEmpty(filePath).length() < 1) { return null; }
		int intPos =  filePath.lastIndexOf(".");
		if (intPos > -1 && intPos < (filePath.length() - 1))
			return filePath.substring(intPos + 1);
		return "";
	}


	/**
	 * 修改文件后缀
	 * @param fileIn 待修改文件
	 * @param ext 目的后缀
	 * @return 修改后的文件
	 */
	public static File changeExtension(File fileIn, String ext) {
		if (fileIn == null || fileIn.exists() == false) { return null; }
		String strPath = fileIn.getAbsolutePath();
		int intPos =  strPath.lastIndexOf(".");
		if (intPos > -1 && intPos < (strPath.length() - 1))
			strPath = strPath.substring(0, intPos);
		StringBuffer sbf = new StringBuffer(1000);
		sbf.append(strPath).append(".").append(StringUtil.trimToEmpty(ext));
		File fileOut = new File(sbf.toString());
		if (fileOut.exists() == true) { return fileOut; }
		try {
			boolean b = fileIn.renameTo(fileOut);
			if (b == true) return fileOut;
		} catch(Exception e) {}
		return null;
	}

	/**
	 * 修改文件后缀
	 * @param fileIn 待修改文件路径
	 * @param ext 目的后缀
	 * @return 修改后的文件
	 */
	public static File changeExtension(String fileIn, String ext) {
		if (fileIn == null || StringUtil.trimToEmpty(fileIn).length() < 1) { return null; }
		return changeExtension(new File(fileIn), ext);
	}


}