package com.hxp.patient.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.hxp.common.redis.JedisManager;
import com.hxp.common.rongCloud.client.RongCloudApi;
import com.hxp.common.rongCloud.result.RCloudSdkHttpResult;
import com.hxp.common.rongCloud.result.TokenResult;
import com.hxp.patient.dao.PatientRecordDao;
import com.hxp.patient.dto.PatientRecordDto;
import com.hxp.patient.po.PatientDoctorRel;
import com.hxp.patient.po.PatientRecord;
import com.hxp.patient.po.PatientRecordImg;
import com.hxp.patient.service.IPatientDoctorRelService;
import com.hxp.patient.service.IPatientRecordImgService;
import com.hxp.patient.service.IPatientRecordService;
import com.hxp.util.ConstantsStatus;
import com.hxp.util.StringUtil;

/**
 * Created by slyi on 2016/7/12.
 */
@Service
public class PatientRecordServiceImpl implements IPatientRecordService {
	@Autowired
	private PatientRecordDao patientRecordDao;
	
	//医生患者关联
	@Autowired
	private IPatientDoctorRelService patientDoctorRelService;
	
	//患者图片
	@Autowired
	private IPatientRecordImgService patientRecordImgService;

	@Override
	public int deleteByPrimaryKey(Long id) {
		return patientRecordDao.delete(id);
	}

	@Override
	public int insert(PatientRecord patientRecord,String imageIds) {
		  //图片列表
		  List<PatientRecordImg> patientRecordImgList=new ArrayList<PatientRecordImg>();
		  if(!imageIds.equals("")){
			  String[] imgIds = imageIds.split(",");
	          for(String imgId:imgIds){
	              PatientRecordImg patientRecordImg=new PatientRecordImg();
	              patientRecordImg.setImgId(Long.parseLong(imgId));
	              patientRecordImg.setSessionId(patientRecord.getSessionId());
	              patientRecordImg.setStatus(1);
	              patientRecordImg.setCreateTime(patientRecord.getCreateTime());
	              patientRecordImgList.add(patientRecordImg);
	          }
	          patientRecordImgService.insertPatientRecordImgList(patientRecordImgList);
		  }
		return patientRecordDao.insert("insertPatientRecord", patientRecord);
	}

	@Override
	public int updateByPrimaryKey(PatientRecord patientRecord) {
		return patientRecordDao.update(patientRecord);
	}
	
	@Override
    public int updateStatusByPrimaryKey(PatientRecord patientRecord){
		return patientRecordDao.update("updateStatusByPrimaryKey",patientRecord);
    }
	

	@Override
	public PatientRecord getByPatientRecord(PatientRecord patientRecord) {
		return patientRecordDao.get("getPatientRecordById", patientRecord);
	}

	@Override
	public List<PatientRecord> findPatientRecordList(int pageNum, int pageSize, PatientRecord patientRecord) {
		PageHelper.startPage(pageNum, pageSize);
		List<PatientRecord> patientRecordList = patientRecordDao.find(patientRecord);
		return patientRecordList;
	}
	
    /**
     * 查询提问
     * @param pageNum
     * @param pageSize
     * @param doctorId    医生id
     * @param type		   1:查询最新提问,2查询我的会诊	
     * @return
     */
	@Override
    public List<PatientRecord> findPatientRecordList(int pageNum,int pageSize,Long doctorId,int type){
		PageHelper.startPage(pageNum, pageSize);
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("doctorId", doctorId);
		map.put("type", type);
		List<PatientRecord> patientRecordList = patientRecordDao.find("findPatientRecord",map);
		return patientRecordList;
    }
	

	@Override
	public boolean patientRecordResponder(Long doctorId,Long patientId,String sessionId) {
		
		boolean flag = true;
		
		// 根据房间id,从redis中取出房间里的人数
		Object obj = JedisManager.getObject(sessionId);
		Integer sessionNumber =  0;  //默认是0个人
		if (obj != null) {
			sessionNumber = (Integer) obj;
		}
		
		synchronized (sessionNumber) {
			//这个房间人数不足三个人时,往PatientDoctorRel表中插入一条数据
			if(sessionNumber < 3){
				
				PatientDoctorRel  patient = new PatientDoctorRel();
				patient.setPatientId(patientId);
				patient.setSessionId(sessionId);
				patient.setDocId(doctorId);
				patientDoctorRelService.insert(patient);
				sessionNumber ++;
				//将房间里的人数添加到redis中
				JedisManager.setObject(sessionId,-1,sessionNumber);
				//人数到了三个时,更新PatientRecord表中的状态
				if(sessionNumber == 3){
					PatientRecord patientReocrd = new PatientRecord();
					patientReocrd.setSessionId(sessionId);
					patientReocrd.setStatus(1);
					//更新 patientRecord的状态 ，改为抢答完成
					updateStatusByPrimaryKey(patientReocrd);
				}
				
			}else{ 
				flag = false;
			}
		}
		return flag;
	}


	@Override
	public List<PatientRecordDto> findNewPatientRecord(int pageNum, int pageSize, PatientRecord patientRecord) {
		 PageHelper.startPage(pageNum, pageSize);
	     List<PatientRecordDto> patientRecordDtoList = patientRecordDao.find("findNewPatientRecord",patientRecord);
		return patientRecordDtoList;
	}

	@Override
	public List<PatientRecordDto> findMyPatientRecord(int pageNum, int pageSize,long doctorId) {
		 PageHelper.startPage(pageNum, pageSize);
	     List<PatientRecordDto> patientRecordDtoList = patientRecordDao.find("findMyPatientRecord",doctorId);
		return patientRecordDtoList;
	}
}
