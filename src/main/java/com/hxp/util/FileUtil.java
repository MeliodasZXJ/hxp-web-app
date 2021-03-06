package com.hxp.util;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;

public class FileUtil {

	public static void main(String[] args) {
		String dirName = "d:/FH/topic/";// 创建目录
		FileUtil.createDir(dirName);
	}

	/**
	 * 创建目录
	 * 
	 * @param destDirName
	 *            目标目录名
	 * @return 目录创建成功返回true，否则返回false
	 */
	public static boolean createDir(String destDirName) {
		File dir = new File(destDirName);
		if (dir.exists()) {
			return false;
		}
		if (!destDirName.endsWith(File.separator)) {
			destDirName = destDirName + File.separator;
		}
		// 创建单个目录
		if (dir.mkdirs()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 删除文件
	 * 
	 * @param filePathAndName
	 *            String 文件路径及名称 如c:/fqf.txt
	 *            String
	 * @return boolean
	 */
	public static void delFile(String filePathAndName) {
		try {
			String filePath = filePathAndName;
			filePath = filePath.toString();
			File myDelFile = new File(filePath);
			myDelFile.delete();

		} catch (Exception e) {
			System.out.println("删除文件操作出错");
			e.printStackTrace();

		}

	}

	/**
	 * 读取到字节数组0
	 *
	 * @param filePath //路径
	 * @throws java.io.IOException
	 */
	public static byte[] getContent(String filePath) throws IOException {
		File file = new File(filePath);
		long fileSize = file.length();
		if (fileSize > Integer.MAX_VALUE) {
			System.out.println("file too big...");
			return null;
		}
		FileInputStream fi = new FileInputStream(file);
		byte[] buffer = new byte[(int) fileSize];
		int offset = 0;
		int numRead = 0;
		while (offset < buffer.length
				&& (numRead = fi.read(buffer, offset, buffer.length - offset)) >= 0) {
			offset += numRead;
		}
		// 确保所有数据均被读取
		if (offset != buffer.length) {
			throw new IOException("Could not completely read file "
					+ file.getName());
		}
		fi.close();
		return buffer;
	}

	/**
	 * 读取到字节数组1
	 *
	 * @param filePath
	 * @return
	 * @throws java.io.IOException
	 */
	public static byte[] toByteArray(String filePath) throws IOException {

		File f = new File(filePath);
		if (!f.exists()) {
			throw new FileNotFoundException(filePath);
		}
		ByteArrayOutputStream bos = new ByteArrayOutputStream((int) f.length());
		BufferedInputStream in = null;
		try {
			in = new BufferedInputStream(new FileInputStream(f));
			int buf_size = 1024;
			byte[] buffer = new byte[buf_size];
			int len = 0;
			while (-1 != (len = in.read(buffer, 0, buf_size))) {
				bos.write(buffer, 0, len);
			}
			return bos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			bos.close();
		}
	}

	/**
	 * 读取到字节数组2
	 *
	 * @param filePath
	 * @return
	 * @throws java.io.IOException
	 */
	public static byte[] toByteArray2(String filePath) throws IOException {

		File f = new File(filePath);
		if (!f.exists()) {
			throw new FileNotFoundException(filePath);
		}

		FileChannel channel = null;
		FileInputStream fs = null;
		try {
			fs = new FileInputStream(f);
			channel = fs.getChannel();
			ByteBuffer byteBuffer = ByteBuffer.allocate((int) channel.size());
			while ((channel.read(byteBuffer)) > 0) {
				// do nothing
				// System.out.println("reading");
			}
			return byteBuffer.array();
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				channel.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				fs.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Mapped File way MappedByteBuffer 可以在处理大文件时，提升性能
	 *
	 * @param filePath
	 * @return
	 * @throws java.io.IOException
	 */
	public static byte[] toByteArray3(String filePath) throws IOException {

		FileChannel fc = null;
		RandomAccessFile rf = null;
		try {
			rf = new RandomAccessFile(filePath, "r");
			fc = rf.getChannel();
			MappedByteBuffer byteBuffer = fc.map(MapMode.READ_ONLY, 0,
					fc.size()).load();
			//System.out.println(byteBuffer.isLoaded());
			byte[] result = new byte[(int) fc.size()];
			if (byteBuffer.remaining() > 0) {
				// System.out.println("remain");
				byteBuffer.get(result, 0, byteBuffer.remaining());
			}
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				rf.close();
				fc.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	/**
	 * 取得文件名得前缀
	 * 
	 * @param fPath
	 *            String
	 * @return String
	 */
	public static String getFilePrefixName(String fPath) {
		String fileName = getFileName(fPath);
		int pos = fileName.lastIndexOf(".");

		if (pos < 0) {
			return fileName;
		} else {
			return fileName.substring(0, pos);
		}
	}
	
	
	/**
	 * Get file name from absolute path
	 * 
	 * @param fPath
	 *            absolute path
	 * @return file name
	 */
	public static String getFileName(String fPath) {
		int pos1 = fPath.lastIndexOf(File.separator);
		int pos2 = fPath.lastIndexOf("/");
		if (pos1 < pos2) {
			pos1 = pos2;
		}

		if (pos1 < 0) {
			return fPath;
		}

		if (pos1 == fPath.length() - 1) {
			return "";
		}

		return fPath.substring(pos1 + 1, fPath.length());
	}
	
	
	/**
	 * Copy file from source to destination
	 * 
	 * @param source
	 *            source directory
	 * @param dest
	 *            destination directory
	 * @return true if copy sucessfully; return false when error
	 */
	public static boolean copyFile(String source, String dest) {

		// check if they are the same file
		File fSource = new File(source);
		File fDest = new File(dest);
		String path = dest.substring(0, dest.lastIndexOf("/"));
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}

		if (fSource.equals(fDest)) {
			return true;
		}
		FileInputStream fis = null;
		FileOutputStream fos = null;

		try {
			fis = new FileInputStream(source);
			fos = new FileOutputStream(dest);

			byte[] buf = new byte[10 * 1024];
			int size = 0;

			while ((size = fis.read(buf)) != -1) {
				fos.write(buf, 0, size);
			}

			fos.flush();

			return true;
		} catch (Exception ex) {
			return false;
		} finally {
			try {
				if (fis != null) {
					fis.close();
				}
			} catch (Exception ex) {
			}
			try {
				if (fos != null) {
					fos.close();
				}
			} catch (Exception ex) {
			}
		}
	}

	/**
	 * Get file extended name from absolute path
	 * 
	 * @param fPath
	 *            file absolute name
	 * @return file extended name
	 */
	public static String getFileExtName(String fPath) {
		String fileName = getFileName(fPath);
		int pos = fileName.lastIndexOf(".");

		if (pos == -1 && fileName.lastIndexOf("jpg") != -1) {
			pos = fileName.lastIndexOf("jpg") - 1;
		}

		if (pos == -1 && fileName.lastIndexOf("jpeg") != -1) {
			pos = fileName.lastIndexOf("jpeg") - 1;
		}

		if (pos == -1 && fileName.lastIndexOf("png") != -1) {
			pos = fileName.lastIndexOf("png") - 1;
		}

		if (pos == -1 && fileName.lastIndexOf("png") != -1) {
			pos = fileName.lastIndexOf("png") - 1;
		}

		if (pos < 0 || pos == fileName.length() - 1) {
			return "";
		}
		return fileName.substring(pos + 1);
	}
	
	
	/**
	 * 取得文件名得前缀
	 * 
	 * @param fPath
	 *            String
	 * @return String
	 */
	public static String getFilePath(String filePath) {
		int pos = filePath.lastIndexOf(".");

		if (pos < 0) {
			return filePath;
		} else {
			return filePath.substring(0, pos);
		}
	}

}