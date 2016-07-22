package com.hxp.util;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.ImageOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import fm.last.moji.MojiFile;
import fm.last.moji.spring.SpringMojiBean;

@Component
public class MojiImageUtils {
private static Logger log = LoggerFactory.getLogger(MojiImageUtils.class);
	
	private static SpringMojiBean moji = null;
	
	public void setMoji(SpringMojiBean moji) {
		this.moji = moji;
	}

	public static boolean isJmagick = true;
	public static final int IMAGE_JPEG = 0;
	public static final int IMAGE_PNG = 1;

	/**
	 * 等比例缩放图像, 本方法目前支持三个图片格式jpg, gif, png, 所有生成都被转化为jpg
	 *
	 * @param fullname  图片路径 path
	 * @param path      目标路径（不带文件名）
	 * @param prefix    生成的文件名的前缀
	 * @param maxWidth  文件宽度范围
	 * @param maxHeight 文件高度范围
	 * @param isaddbg
	 * @param backcolor
	 * @param num
	 * @return
	 */
	public static String getCompressedImage(String fullname, String path,
											String prefix, int maxWidth, int maxHeight, boolean isaddbg,
											String backcolor, String num) {
		// 设置添加背景无效
		isaddbg = false;
		File filePath = null;
		BufferedInputStream in = null;
		try {
			in = new BufferedInputStream(new URL(fullname).openStream());
			if (in.available() != 0) {
				int index = fullname.lastIndexOf("/");
				String fileName = fullname.substring(index + 1, fullname.length());
				// 上传flash,判断是flash就不压缩
				if (fullname.endsWith("swf")) {
					String filePiexName = FileUtil.getFilePrefixName(fileName);
					filePiexName = filePiexName + num;
					String newPmgPath = path + prefix + "_" + filePiexName
							+ ".swf";
					FileUtil.copyFile(fullname, newPmgPath);
					File newFile = new File(newPmgPath);
					if (newFile.exists()) {
						return newFile.getName();
					} else {
						return null;
					}
				}

				try {
					if (MojiImageUtils.isJmagick) {
//						if (StringUtil.isEmpty(backcolor)) {
//							backcolor = "white";
//						}
						// 获取图片前缀
						String filePiexName = FileUtil.getFilePrefixName(fileName);
						// 获取图片后缀
						String fileExtName = FileUtil.getFileExtName(fileName);
						filePiexName = filePiexName + num;

						if (!"jpg".equals(fileExtName)
								&& !"jpeg".equals(fileExtName)) {
							fileExtName = ".jpg";
						}					

						if (!fileExtName.startsWith(".")) {
							fileExtName = "." + fileExtName;
						}

						// 图片全名
						String newPmgPath = path + prefix + "_"
								+ filePiexName + fileExtName;

						File newPmgPathDir = new File(FileUtil.getFilePath(path));
						if (!newPmgPathDir.exists() || !newPmgPathDir.isDirectory()) {
							newPmgPathDir.mkdirs();
						}
						IM4Java.resize(fullname, newPmgPath, maxWidth, maxHeight);

						String returnFileName = FileUtil.getFileName(newPmgPath);
						return returnFileName;
					}
				} catch (Exception ex1) {
					log.error(fullname, ex1);
				}
				// 如果不支持Jmagick压缩则用普通压缩
				int type = MojiImageUtils.IMAGE_JPEG;

				File newPmgPathDir = new File(path);
				if (!newPmgPathDir.exists() || !newPmgPathDir.isDirectory()) {
					newPmgPathDir.mkdirs();
				}

				StringBuffer sbf = new StringBuffer(1000);
    			sbf.append(path).append(path.endsWith(File.separator) ? "" : File.separator)
    			.append(prefix).append("_").append(FileUtil.getFilePrefixName(fileName)).append(".jpg");	
				String newPmgPath = sbf.toString();

				File newPmgPathDir1 = new File(FileUtil.getFilePath(path));
				if (!newPmgPathDir1.exists() || !newPmgPathDir1.isDirectory()) {
					newPmgPathDir1.mkdirs();
				}

				try {
					BufferedImage image = MojiImageUtils.resizeImage(in, type, maxWidth, maxHeight);
					MojiImageUtils.saveImage(image, newPmgPath, type);
					filePath = new File(newPmgPath); // 读入文件
				} catch (Exception ex) {
					log.error(fullname, ex);
				}

				if (filePath != null) {
					return filePath.getName();
				}
			}
		} catch (Exception e) {
			log.error(fullname, e);
		} finally {
			try {
				if (null != in)	in.close();
			} catch (Exception e) {}
		}
		return null;
	}

	/**
	 * Resizes an image
	 *
	 * @param maxWidth
	 *            The image's max width
	 * @param maxHeight
	 *            The image's max height
	 * @return A resized <code>BufferedImage</code>
	 * @throws IOException
	 *             If the file is not found
	 */
	public static BufferedImage resizeImage(BufferedInputStream buff, int type,
			int maxWidth, int maxHeight) throws IOException {
		// 读入文件
//		BufferedInputStream in = new BufferedInputStream(new URL(imgName).openStream());

			if (buff.available() != 0) {
			BufferedImage sourceImg = ImageIO.read(buff);
			if (maxWidth == 0) {
				maxWidth = sourceImg.getWidth(null);
			}
			if (maxHeight == 0) {
				maxHeight = sourceImg.getHeight(null);
			}

			return resizeImage(sourceImg, type, maxWidth, maxHeight);
		} else {
			return null;
		}
	}

	/**
	 * Resizes an image.
	 * 
	 * @param image
	 *            The image to resize
	 * @param maxWidth
	 *            The image's max width
	 * @param maxHeight
	 *            The image's max height
	 * @return A resized <code>BufferedImage</code>
	 */
	public static BufferedImage resizeImage(Image image, int type,
			int maxWidth, int maxHeight) {
		Dimension largestDimension = new Dimension(maxWidth, maxHeight);
		// Original size
		int imageWidth = image.getWidth(null);
		int imageHeight = image.getHeight(null);

		float aspectRation = (float) imageWidth / imageHeight;

		if (imageWidth > maxWidth || imageHeight > maxHeight) {
			if ((float) largestDimension.width / largestDimension.height > aspectRation) {
				largestDimension.width = (int) Math
						.ceil(largestDimension.height * aspectRation);
			} else {
				largestDimension.height = (int) Math
						.ceil(largestDimension.width / aspectRation);
			}

			imageWidth = largestDimension.width;
			imageHeight = largestDimension.height;
		}

		return createBufferedImage(image, type, imageWidth, imageHeight);
	}

	/**
	 * Creates a <code>BufferedImage</code> from an <code>Image</code>.
	 * 
	 * @param image
	 *            The image to convert
	 * @param w
	 *            The desired image width
	 * @param h
	 *            The desired image height
	 * @return The converted image
	 */
	public static BufferedImage createBufferedImage(Image image, int type,
			int w, int h) {
		if (type == MojiImageUtils.IMAGE_PNG && hasAlpha(image)) {
			type = BufferedImage.TYPE_INT_ARGB;
		} else {
			type = BufferedImage.TYPE_INT_RGB;
		}

		BufferedImage bi = new BufferedImage(w, h, type);
		Graphics g = bi.createGraphics();
		Graphics2D g2d = (Graphics2D) g;
		// 设置平滑抗锯齿
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		g.drawImage(image, 0, 0, w, h, null);
		g.dispose();
		g2d.dispose();
		return bi;
	}

	/**
	 * Determines if the image has transparent pixels.
	 * 
	 * @param image
	 *            The image to check for transparent pixel.s
	 * @return <code>true</code> of <code>false</code>, according to the result
	 * @throws InterruptedException
	 */
	public static boolean hasAlpha(Image image) {
		try {
			PixelGrabber pg = new PixelGrabber(image, 0, 0, 1, 1, false);
			pg.grabPixels();
			return pg.getColorModel().hasAlpha();
		} catch (InterruptedException e) {
			return false;
		}
	}

	/**
	 * Saves an image to the disk.
	 * 
	 * @param image
	 *            The image to save
	 * @param toFileName
	 *            The filename to use
	 * @param type
	 *            The image type. Use <code>ImageUtils.IMAGE_JPEG</code> to save
	 *            as JPEG images, or <code>ImageUtils.IMAGE_PNG</code> to save
	 *            as PNG.
	 * @return <code>false</code> if no appropriate writer is found
	 * @throws IOException
	 */
	public static boolean saveImage(BufferedImage image, String toFileName,
			int type) throws IOException {
		try {
			saveCompressedImage(image, toFileName, type);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	/**
	 * Compress and save an image to the disk. Currently this method only
	 * supports JPEG images.
	 * 
	 * @param image
	 *            The image to save
	 * @param toFileName
	 *            The filename to use
	 * @param type
	 *            The image type. Use <code>ImageUtils.IMAGE_JPEG</code> to save
	 *            as JPEG images, or <code>ImageUtils.IMAGE_PNG</code> to save
	 *            as PNG.
	 * @return <code>false</code> if no appropriate writer is found
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public static void saveCompressedImage(BufferedImage image,
			String toFileName, int type) throws IOException {
		if (type == IMAGE_PNG) {
			throw new UnsupportedOperationException(
					"PNG compression not implemented");
		}

		ImageWriter writer = null;

		Iterator iter = ImageIO.getImageWritersByFormatName("jpg");
		writer = (ImageWriter) iter.next();
		File file = new File(toFileName);

		ImageOutputStream ios = ImageIO.createImageOutputStream(file);
		writer.setOutput(ios);

		ImageWriteParam iwparam = new JPEGImageWriteParam(Locale.getDefault());

		iwparam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
		iwparam.setCompressionQuality(0.9F);
		IIOImage iIOImage = new IIOImage(image, null, null);
		writer.write(null, iIOImage, iwparam);
		ios.flush();
		writer.dispose();
		ios.close();
	}
	
	/**
	 * 删除图片
	 * @param filePath 图片路径
	 * @return
	 */
	
	public static boolean deleteFile(String filePath) {
		
		if(StringUtil.isEmpty(filePath)){
			return false;
		}
		//路径是否合法
		boolean hasErrorUrl = false;
		//文件路径中包含以下字符
		String[] errorUrl = {"./", ".\\"};
        if(errorUrl != null && errorUrl.length > 0){
	        for (int i = 0; i < errorUrl.length; i++) {
	            if (filePath.indexOf(errorUrl[i]) != -1) {
	            	hasErrorUrl = true;
	            	break;
	            }
	        }
        }
        if(hasErrorUrl){
        	return false;
        }
		        
		File file = new File(filePath);

		if (file.exists() && !file.isDirectory()) {
			file.delete();
			return true;
		}

		return false;
	}
	
	public static void main(String[] args) {
		getCompressedImage("d:\\qqq.jpg", "E:\\", "big", 50, 50, false, "", "");
	}
	
	

	 
	/**
	 * ==========================================================
	 * 公用方法----上传图片
	 * ==========================================================
	 * */
	private static List<URL> uploadToMogilefs(String uuid, InputStream in)
			throws Exception {
		MojiFile mf = moji.getFile("MyFileKey" + uuid);

		OutputStream out = null;
		try {
			out = mf.getOutputStream();

			byte[] bs = new byte['?'];
			int bytesRead = 0;
			while (-1 != (bytesRead = in.read(bs, 0, bs.length))) {
				out.write(bs, 0, bytesRead);
			}
			out.flush();
		} catch (Exception err) {
			throw new Exception(err.fillInStackTrace());
	//		throw new Exception("file.upload","uploadToMogilefs error,file==", err);
		} finally {
			try {
				in.close();
			} catch (Exception e) {
				log.error("uploadToMogilefs in.close error", e);
			}
			try {
				out.close();
			} catch (Exception e) {
				log.error("uploadToMogilefs out.close error", e);
			}
		}
		return mf.getPaths();
	}
	
	/**
	 * 上传图片到moglif
	 * @param pathName 图片路径包括名称
	 * @param name 图片
	 */
	public static void uploadImage(String pathName,String name){
		File file = new File(pathName);
        InputStream in = null;
        try {
            in = new FileInputStream(file);
            uploadFiles(in, name);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
	}
	
	/**
	 * ==========================================================
	 * 公用方法----上传图片
	 * ==========================================================
	 * @throws IOException 
	 * @throws Exception 
	 * */
	public static String uploadFiles(InputStream in, String fileName) throws IOException {
//		List<FileModel> retList = new ArrayList<FileModel>();
		String remotePaths = "";
		try {
			 
//				FileModel fm = fileService.getOneFileModel(fileName);

				List<URL> list = uploadToMogilefs(remotePaths, in);

//				fm.setRemotePaths(list.get(0).toString());
//				fileService.update(fm);
//				return fm;
		} catch (Exception e) {
			throw new IOException(e.fillInStackTrace());
		}
		return  remotePaths;
	}
	
	
	
}
