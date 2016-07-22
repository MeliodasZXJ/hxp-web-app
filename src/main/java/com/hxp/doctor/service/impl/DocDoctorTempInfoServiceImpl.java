package com.hxp.doctor.service.impl;

import java.util.List;

import com.hxp.commonality.dao.SysMessageDao;
import com.hxp.commonality.po.SysMessage;
import com.hxp.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hxp.common.redis.JedisManager;
import com.hxp.doctor.dao.DocDoctorInfoDao;
import com.hxp.doctor.dao.DocDoctorInfoTempDao;
import com.hxp.doctor.dto.DocDoctorInfoTempDto;
import com.hxp.doctor.dto.DoctorDto;
import com.hxp.doctor.po.DocDoctorInfo;
import com.hxp.doctor.po.DocDoctorInfoTemp;
import com.hxp.doctor.service.IDocDoctorTempInfoService;
import com.hxp.util.AliSmsService;
import com.hxp.util.ConstantsStatus;
import com.hxp.util.EncryptionUtil;
import com.hxp.util.constant.Constant;

@Service
public class DocDoctorTempInfoServiceImpl implements IDocDoctorTempInfoService {
	@Autowired
	private DocDoctorInfoTempDao docDoctorInfoTempDao;

	@Autowired
	private DocDoctorInfoDao docDoctorInfoDao;

	@Autowired
	private SysMessageDao sysMessageDao;
	

	@Override
	public List<DocDoctorInfoTempDto> getAllDocTempInfo(DocDoctorInfoTempDto ddintd) {
		// TODO Auto-generated method stub
		List<DocDoctorInfoTempDto> list = docDoctorInfoTempDao.findAll(ddintd);
		return list;
	}

	@Override
	public void delete(Integer tempId) {
		// TODO Auto-generated method stub
		docDoctorInfoTempDao.delete(tempId);
	}

	@Override
	public void updateAuth(DocDoctorInfoTempDto docDoctorInfoTempDto) throws Exception {
		// TODO Auto-generated method stub
		DocDoctorInfo doctor = docDoctorInfoDao.get("selectByPrimaryKey", docDoctorInfoTempDto.getDocId());
		DocDoctorInfoTemp docDoctorInfoTemp = new DocDoctorInfoTemp();
		SysMessage sysMessage = new SysMessage();
		if (docDoctorInfoTempDto.getStatus() ==2) { // 认证不通过
			docDoctorInfoTemp.setId(docDoctorInfoTempDto.getId()); //患者id
			docDoctorInfoTemp.setStatus(docDoctorInfoTempDto.getStatus()); //状态

			doctor.setMark(docDoctorInfoTempDto.getMark()); //备注
			doctor.setStatus(3); //-1删除 0:未认证,1审核通过/已认证,2认证未通过3认证中,4重新认证       @产品闫佳佳 重新认证不通过改为认证中
			docDoctorInfoDao.update("updateByPrimaryKeySelective", doctor);
			docDoctorInfoTempDao.update("updateByPrimaryKeySelective", docDoctorInfoTemp);

			String token = EncryptionUtil.MD5(ConstantsStatus.DOCT_TOKEN_KEY+doctor.getMobile());//md5加密的token
			DoctorDto dd = (DoctorDto) JedisManager.getObject(token);
			if(dd!=null){
				DoctorDto doctorDto = docDoctorInfoDao.get("getDoctorByMobileAndPass",doctor);
				JedisManager.setObject(token, 0, doctorDto);
			}
			//系统消息表记录
			sysMessage.setType(1); //1 医生认证消息
			sysMessage.setObjId(doctor.getId()); //医生id
			sysMessage.setContent(Constant.DOCTORAUTH_FAILED_MESSAGE_BACKGROUND + docDoctorInfoTempDto.getMark()); //内容
			sysMessage.setStatus(0); //0 未读  1已读
			sysMessage.setCreateTime(DateUtil.getDate());//创建时间
			sysMessageDao.insert(sysMessage);

			AliSmsService.sendSmsMessage(doctor.getMobile(), Constant.DOCTORAUTH_FAILED_MESSAGE);
		} else {

			DocDoctorInfoTemp doctorInfoTemp = docDoctorInfoTempDao.get("selectByPrimaryKey", docDoctorInfoTempDto.getId());

			doctor.setName(doctorInfoTemp.getName());
			doctor.setSex(doctorInfoTemp.getSex());
			doctor.setHospitalId(doctorInfoTemp.getHospitalId());
			doctor.setDeptId(doctorInfoTemp.getDeptId());
			doctor.setPidPath(doctorInfoTemp.getPidPath());
			doctor.setType(doctorInfoTemp.getType());
			doctor.setStatus(1); //-1删除 0:未认证,1审核通过/已认证,2认证未通过3认证中,4重新认证
			doctor.setMark(docDoctorInfoTempDto.getMark()); //备注
			docDoctorInfoDao.update("updateByPrimaryKeySelective", doctor);
			Long id = docDoctorInfoTempDto.getId(); //患者id
			docDoctorInfoTempDao.delete("deleteByPrimaryKey", id);

			//系统消息表记录
			sysMessage.setType(1); //1 医生认证消息
			sysMessage.setObjId(doctor.getId()); //医生id
			sysMessage.setContent(Constant.DOCTORAUTH_SUCCESS_MESSAGE_BACKGROUND); //内容
			sysMessage.setStatus(0); //0 未读  1已读
			sysMessage.setCreateTime(DateUtil.getDate());//创建时间
			sysMessageDao.insert(sysMessage);

			String token = EncryptionUtil.MD5(ConstantsStatus.DOCT_TOKEN_KEY+doctor.getMobile());//md5加密的token
			DoctorDto dd = (DoctorDto) JedisManager.getObject(token);
			if(dd!=null){
				DoctorDto doctorDto = docDoctorInfoDao.get("getDoctorByMobileAndPass",doctor);
				JedisManager.setObject(token, 0, doctorDto);
			}
			AliSmsService.sendSmsMessage(doctor.getMobile(), Constant.DOCTORAUTH_SUCCESS_MESSAGE);
		}
	}

	/**
	 * 插入一条医生修改临时数据
	 * 
	 * @param docDoctorInfoTemp
	 */
	@Override
	public void insert(DocDoctorInfoTemp docDoctorInfoTemp) {
		docDoctorInfoTempDao.insert(docDoctorInfoTemp);
	}

}
