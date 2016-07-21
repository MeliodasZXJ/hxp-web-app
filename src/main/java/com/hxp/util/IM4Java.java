package com.hxp.util;

import org.im4java.core.ConvertCmd;
import org.im4java.core.IMOperation;



public class IM4Java {

	public static void  resize(String srcPath, String desPath, int dw, int dh) throws Exception {

		if (dw <= 0 || dh <= 0)
			return;

		IMOperation op = new IMOperation();
		op.addImage();

		op.addRawArgs("-strip"); //
		op.addRawArgs("-quality", "90"); // 设置图片质量

		op.resize(dw, dh);
		op.addImage();

		
/*		im4java.imagePath=F:/temp/ImageMagick-6.3.5-Q16/
				im4java.useGM=false
				im4java.tempPath=F:/temp/im4java/*/
		
		String imagePath = "F:/temp/ImageMagick-6.3.5-Q16/";
		String useGM ="false";
		System.out.println("=====imagePath=========="+imagePath);
		System.out.println("=====useGM=========="+useGM);
		ConvertCmd convert = new ConvertCmd(Boolean.parseBoolean(useGM));
		System.out.println("=====convert1==========");
		convert.setSearchPath("D\\:\\\\temp\\\\ImageMagick-6.3.5-Q16\\");
		ConvertCmd.setGlobalSearchPath(imagePath);
		System.out.println("=====convert2==========");
		convert.run(op, srcPath, desPath);
		System.out.println("=====convert3==========");
	}
	
	public static void main(String[] args) {
		try {
			resize("http://192.168.0.208:7500/dev1/0/000/003/0000003436.fid", "E:\\temp", 10, 10);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}