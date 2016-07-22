package com.hxp.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;

import javax.imageio.ImageIO;

import org.im4java.core.ConvertCmd;
import org.im4java.core.IMOperation;

import com.hxp.common.fileUpload.config.MogolifsConfig;
import com.hxp.common.fileUpload.config.MogolifsConstant;

public class IM4Java {

	public static void resize(String srcPath, String desPath, int dw, int dh) throws Exception {

		if (dw <= 0 || dh <= 0)
			return;

		IMOperation op = new IMOperation();
		op.addImage();

		op.addRawArgs("-strip"); //
		op.addRawArgs("-quality", "90"); // 设置图片质量

		op.resize(dw, dh);
		op.addImage();

		String imagePath = MogolifsConfig.getString(MogolifsConstant.IM4JAVA_IMAGEPATH);
		String useGM = MogolifsConfig.getString(MogolifsConstant.IM4JAVA_USEGM);
		System.out.println("=====imagePath==========" + imagePath);
		System.out.println("=====useGM==========" + useGM);
		ConvertCmd convert = new ConvertCmd(Boolean.parseBoolean(useGM));
		System.out.println("=====convert1==========");
		// convert.setSearchPath("F:/temp/ImageMagick-6.3.5-Q16");
		ConvertCmd.setGlobalSearchPath(imagePath);
		System.out.println("=====convert2==========");
		convert.run(op, srcPath, desPath);
		System.out.println("=====convert3==========");
	}

	public static void main(String[] args) {
		try {

			URL url = new URL("http://172.168.1.12:7500/dev1/0/000/001/0000001534.fid");
			
//			File picture = new File(url.toURI());
	
			BufferedImage sourceImg = ImageIO.read(new FileInputStream("http://172.168.1.12:7500/dev1/0/000/001/0000001534.fid"));
	//		System.out.println(String.format("%.1f", picture.length() / 1024.0));
			System.out.println("sourceImg width :" + sourceImg.getWidth());
			System.out.println("sourceImg height :" + sourceImg.getHeight());

			// resize("http://172.168.1.12:7500/dev1/0/000/001/0000001534.fid",
			// "F:/temp/0000001534.jpg", 200, 200);
			// System.out.println("转换成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}