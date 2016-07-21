package com.hxp.common.fileUpload;

import com.hxp.util.ObjectUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * IO工具类
 */
public class IOUtils {

	private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;
	public static final String LINE_SEPARATOR;
	static {
		StringWriter buf = new StringWriter(4);
		PrintWriter out = new PrintWriter(buf);
		out.println();
		LINE_SEPARATOR = buf.toString();
	}

	/**
	 * 读取输入流到字节数组
	 * @param is 输入流
	 * @param length 读取长度，小于1时一直读到流终止，大于0时读取固定长度
	 * @return 读出的输入流内容
	 */
	public static byte[] toBytes(InputStream is, int length) {

		int bytesRead = 0;
		int totalRead = 0;
		byte[] bReturn = null;
		byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
		BufferedInputStream reader = null;
		ByteArrayOutputStream outStream = null;

		if (length < 1) {
			outStream = new ByteArrayOutputStream();
			try {
				reader = new BufferedInputStream(is);
				while ((bytesRead = reader.read(buffer)) != -1) {
					outStream.write(buffer, 0, bytesRead);
				}
			} catch (Exception e) {}

			try {
				bReturn = outStream.toByteArray();
			} catch (Exception e) {
			} finally {
				try {
					if (outStream != null) outStream.close();
				} catch (IOException f) {}
				outStream = null;
			}
		} else {
			bReturn = new byte[length];
			try {
				for (; totalRead < length && bytesRead >= 0; totalRead += bytesRead) {
					bytesRead = is.read(bReturn, totalRead, length - totalRead);
				}
			} catch (Exception e) {}
		}
		return bReturn;
	}

	/**
	 * Copy bytes from a large (over 2GB) InputStream to an OutputStream. This method
	 *  buffers the input internally, so there is no need to use a BufferedInputStream.
	 * @param input 输入流
	 * @param output 输出流
	 * @return 读取的字节总数
	 * @throws IOException in case of I/O errors
	 */
	public static long copy(InputStream input, OutputStream output) throws IOException {
		int bytesRead = -1;
		long byteCount = 0;
		byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
		while ((bytesRead = input.read(buffer)) != -1) {
			output.write(buffer, 0, bytesRead);
			byteCount += bytesRead;
		}
		output.flush();
		return byteCount;
	}

	/**
	 * Copy chars from a large (over 2GB) Reader to a Writer. This method
	 *  buffers the input internally, so there is no need to use a BufferedReader.
	 * @param input 读取器
	 * @param output 写出器
	 * @return 读取的字节总数
	 * @throws IOException
	 */
	public static long copy(Reader input, Writer output) throws IOException {
		char[] buffer = new char[DEFAULT_BUFFER_SIZE];
		long count = 0;
		int n = 0;
		while (-1 != (n = input.read(buffer))) {
			output.write(buffer, 0, n);
			count += n;
		}
		output.flush();
		return count;
	}

	/**
	 * 获取输入流里的字节数组
	 * @param is 输入流
	 * @return 字节数组
	 * @throws IOException
	 */
	public static byte[] toBytes(InputStream is) throws IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		copy(is, output);
		return output.toByteArray();
	}

	/**
	 * 获取读取器里的字节数组
	 * @param input 读取器
	 * @param encoding 字符编码
	 * @return 字节数组
	 * @throws IOException
	 */
	public static byte[] toBytes(Reader input, String encoding) throws IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		OutputStreamWriter out = ObjectUtils.isEmpty(encoding) ?
				new OutputStreamWriter(output) : new OutputStreamWriter(output, encoding);
		copy(input, out);
		out.flush();
		return output.toByteArray();
	}

	/**
	 * 获取输入流里的字符数组
	 * @param is 输入流
	 * @param encoding 字符编码
	 * @return 字符数组
	 * @throws IOException
	 */
	public static char[] toChars(InputStream is, String encoding) throws IOException {
		InputStreamReader in = ObjectUtils.isEmpty(encoding) ?
				new InputStreamReader(is) : new InputStreamReader(is, encoding);
		CharArrayWriter output = new CharArrayWriter();
		copy(in, output);
		return output.toCharArray();
	}

	/**
	 * 获取读取器里的字符数组
	 * @param input 读取器
	 * @return 字符数组
	 * @throws IOException
	 */
	public static char[] toChars(Reader input) throws IOException {
		CharArrayWriter sw = new CharArrayWriter();
		copy(input, sw);
		return sw.toCharArray();
	}

	/**
	 * 获取输入流里的字符串
	 * @param is 输入流
	 * @param encoding 字符编码
	 * @return 字符串
	 * @throws IOException
	 */
	public static String toString(InputStream is, String encoding) throws IOException {
		InputStreamReader reader = ObjectUtils.isEmpty(encoding) ?
				new InputStreamReader(is) : new InputStreamReader(is, encoding);
		StringWriter sw = new StringWriter();
		copy(reader, sw);
		return sw.toString();
	}

	/**
	 * 获取读取器里的字符串
	 * @param input 读取器
	 * @return 字符串
	 * @throws IOException
	 */
	public static String toString(Reader input) throws IOException {
		StringWriter sw = new StringWriter();
		copy(input, sw);
		return sw.toString();
	}

	/**
	 * 字符串转成输入流
	 * @param input 字符串
	 * @param encoding 字符编码
	 * @return 输入流
	 * @throws Exception
	 */
	public static InputStream toInputStream(String input, String encoding) throws Exception {
		byte[] bytes = ObjectUtils.isEmpty(encoding) ? input.getBytes() : input.getBytes(encoding);
		return new ByteArrayInputStream(bytes);
	}

	/**
	 * 获取输入流里的字符串行
	 * @param is 输入流
	 * @param encoding 字符编码
	 * @return 字符串行
	 * @throws Exception
	 */
	public static List<String> readLines(InputStream is, String encoding) throws IOException {
		BufferedReader reader = null;
		try {
			InputStreamReader in = ObjectUtils.isEmpty(encoding) ?
					new InputStreamReader(is) : new InputStreamReader(is, encoding);
			reader = new BufferedReader(in);
			List<String> list = new ArrayList<String>();
			String line = reader.readLine();
			while (line != null) {
				list.add(line);
				line = reader.readLine();
			}
			return list;
		} catch (IOException e) {
			throw e;
		} finally {
			try {
				if (reader != null) reader.close();
			} catch (IOException f) { }
		}
	}

	/**
	 * 字符串行写入输出流
	 * @param lines 字符串行
	 * @param lineEnding 行结束符
	 * @param output 输出流
	 * @param encoding 字符编码
	 * @throws IOException
	 */
	public static void writeLines(Collection<?> lines, String lineEnding, OutputStream output, String encoding) throws IOException {
		if (lines == null) return;
		if (lineEnding == null) {
			lineEnding = LINE_SEPARATOR;
		}
		if (ObjectUtils.isEmpty(encoding)) {
			encoding = Charset.defaultCharset().name();
		}
		Object line = null;
		for (Iterator<?> it = lines.iterator(); it.hasNext();) {
			line = it.next();
			if (line != null) {
				output.write(line.toString().getBytes(encoding));
			}
			output.write(lineEnding.getBytes(encoding));
		}
		output.flush();
	}

	/**
	 * 字符串行输出到写出对象
	 * @param lines 字符串行
	 * @param lineEnding 行结束符
	 * @param writer 写出器
	 * @throws IOException
	 */
	public static void writeLines(Collection<?> lines, String lineEnding, Writer writer) throws IOException {
		if (lines == null) return;
		if (lineEnding == null) {
			lineEnding = LINE_SEPARATOR;
		}
		Object line = null;
		for (Iterator<?> it = lines.iterator(); it.hasNext();) {
			line = it.next();
			if (line != null) {
				writer.write(line.toString());
			}
			writer.write(lineEnding);
		}
		writer.flush();
	}

	/**
	 * Object对象转换成字节数组
	 * @param obj 目标对象实例
	 * @return 转换后的字节数组
	 * @throws IOException
	 */
	public static byte[] objectToBytes(Object obj) throws IOException {
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		ObjectOutputStream out = new ObjectOutputStream(bout);
		out.writeObject(obj);
		out.flush();
		byte[] bytes = bout.toByteArray();
		bout.close();
		out.close();
		return bytes;
	}

	/**
	 * 字节数组转换成Object对象
	 * @param bytes 目标字节数组
	 * @return 转换后的对象实例
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static Object bytesToObject(byte[] bytes) throws IOException, ClassNotFoundException {
		ByteArrayInputStream bi = new ByteArrayInputStream(bytes);
		ObjectInputStream oi = new ObjectInputStream(bi);
		Object obj = oi.readObject();
		bi.close();
		oi.close();
		return obj;
	}
}
