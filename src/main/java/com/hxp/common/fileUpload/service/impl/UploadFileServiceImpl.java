package com.hxp.common.fileUpload.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.druid.util.StringUtils;
import com.hxp.common.fileUpload.po.UploadImg;
import com.hxp.common.fileUpload.po.UploadImgResponse;
import com.hxp.common.fileUpload.service.IUploadFileService;
import com.hxp.common.fileUpload.service.IUploadImgService;
import com.hxp.util.DownloadURLFile;
import com.hxp.util.MojiImageUtils;
import com.hxp.util.StringUtil;
import com.hxp.util.UploadFileUtils;

import fm.last.moji.spring.SpringMojiBean;

/**
 * @author 作者 E-mail: liuyang
 * @date 创建时间：2016年7月15日 下午3:14:14
 * @version 2.0
 * @parameter
 * @since
 * @return
 */
@Service
public class UploadFileServiceImpl implements IUploadFileService {

	@Autowired
	private SpringMojiBean moji;

	@Autowired
	public void setMoji(SpringMojiBean moji) {
		this.moji = moji;
	}

	@Autowired
	private IUploadImgService uploadImgService;

	// 图片地址
	private static String im4java_tempPath = null;
	// 缩略图宽
	private int thumbnail_width = 200;
	// 缩略图高
	private int thumbnail_height = 200;

	static {
		im4java_tempPath = "";// 图片压缩后的存储路径
		if (StringUtils.isEmpty(im4java_tempPath)) {
			im4java_tempPath = System.getProperty("java.io.tmpdir");
		}
		if (im4java_tempPath.endsWith(File.separator) != true) {
			im4java_tempPath = (new StringBuffer(im4java_tempPath)).append(File.separator).toString();
		}
	}

	

	public UploadImgResponse updateForUpload(boolean thumbnail, MultipartFile[] files) throws Exception {
		if (files == null || files.length < 1){
			return null;
		}

		String tmp = null;
		StringBuffer sbf = null;
		UploadImg uplpodImg = null;

		String[] uploadResult, uploadThumbnail = null;
		UploadImgResponse result = new UploadImgResponse();
		// 图片名称
		String imgName = "";

		for (MultipartFile current : files) {

			uplpodImg = new UploadImg();
			imgName = current.getOriginalFilename();

			if (current.isEmpty()) {
				result.add(imgName, "file is empty!");
				continue;
			}

			// 上传文件系统
			uploadResult = UploadFileUtils.uploadFileToServer(current, moji);

			// 图片url
			uplpodImg.setImgUrl(uploadResult[1]);
			// 图片后缀
			uplpodImg.setSuffix(uploadResult[2]);


			// 生成压缩图
			if (thumbnail == true) {

				tmp = MojiImageUtils.getCompressedImage(uploadResult[1], im4java_tempPath, "small", thumbnail_width,
						thumbnail_height, false, "", "");

				sbf = new StringBuffer(1000);
				sbf.append(im4java_tempPath).append(tmp);
				uploadThumbnail = UploadFileUtils.uploadFileToServer(sbf.toString(), moji);

				sbf.delete(0, sbf.length());
				sbf.append(uploadThumbnail[0]).append(".").append(uploadThumbnail[2]);

				// 压缩图片id
				uplpodImg.setThumbnaillId(sbf.toString());
				// 压缩图片url
				uplpodImg.setThumbnaillUrl(uploadThumbnail[1]);
				// 图片后缀
				uplpodImg.setSuffix(uploadResult[2]);

			}
			
			//删除状态
			uplpodImg.setFlag(0);

			uploadImgService.insertUploadImg(uplpodImg);

			// 生成返回信息
			result.add(uplpodImg.getId(), imgName, uploadResult[1], uploadThumbnail != null ? uploadThumbnail[1] : null);

		}

		return result;
	}
	
	
	public UploadImgResponse updateForUpload(boolean thumbnail,String url) throws Exception {
		if (StringUtil.isEmpty(url)){
			return null;
		}

		String tmp = null;
		StringBuffer sbf = null;
		UploadImg uplpodImg = null;

		String[] uploadResult, uploadThumbnail = null;
		UploadImgResponse result = new UploadImgResponse();
		// 图片名称
		String imgName = DownloadURLFile.getFileNameFromUrl(url);
		
		 
		FileInputStream fileInput =  new FileInputStream(url);
		
		
		// 上传文件系统
		uploadResult = UploadFileUtils.uploadFileToServer(fileInput,imgName,moji);

		// 图片url
		uplpodImg.setImgUrl(uploadResult[1]);
		// 图片后缀
		uplpodImg.setSuffix(uploadResult[2]);


		// 生成压缩图
		if (thumbnail == true) {

			tmp = MojiImageUtils.getCompressedImage(uploadResult[1], im4java_tempPath, "small", thumbnail_width,
					thumbnail_height, false, "", "");

			sbf = new StringBuffer(1000);
			sbf.append(im4java_tempPath).append(tmp);
			uploadThumbnail = UploadFileUtils.uploadFileToServer(sbf.toString(), moji);

			sbf.delete(0, sbf.length());
			sbf.append(uploadThumbnail[0]).append(".").append(uploadThumbnail[2]);

			// 压缩图片id
			uplpodImg.setThumbnaillId(sbf.toString());
			// 压缩图片url
			uplpodImg.setThumbnaillUrl(uploadThumbnail[1]);
			// 图片后缀
			uplpodImg.setSuffix(uploadResult[2]);

		}
		
		//删除状态
		uplpodImg.setFlag(0);

		uploadImgService.insertUploadImg(uplpodImg);
		
		
		// 生成返回信息
		result.add(uplpodImg.getId(), imgName, uploadResult[1], uploadThumbnail != null ? uploadThumbnail[1] : null);

		return result;
	}
	

}
