package com.hxp.patient.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.hxp.patient.dto.PatientRecordImgDto;
import com.hxp.util.StringUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.hxp.common.redis.JedisManager;
import com.hxp.patient.dao.PatientRecordDao;
import com.hxp.patient.dto.PatientRecordDto;
import com.hxp.patient.po.PatientDoctorRel;
import com.hxp.patient.po.PatientRecord;
import com.hxp.patient.po.PatientRecordImg;
import com.hxp.patient.service.IPatientDoctorRelService;
import com.hxp.patient.service.IPatientRecordImgService;
import com.hxp.patient.service.IPatientRecordService;

/**
 * Created by slyi on 2016/7/12.
 */
@Service
public class PatientRecordServiceImpl implements IPatientRecordService {
	protected final Logger logger = Logger.getLogger(PatientRecordServiceImpl.class);

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
		  if(imageIds!=null&&!imageIds.equals("")){
	          patientRecordImgService.insertPatientRecordImgList(patientRecord.getSessionId(),imageIds);
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
		return patientRecordDao.get("getPatientRecord", patientRecord);
	}


	@Override
	public PatientRecordDto getPatientRecord(PatientRecord patientRecord) {
		PatientRecordImg patientRecordImg=new PatientRecordImg();
		patientRecordImg.setSessionId(patientRecord.getSessionId());
		//根据sesionId查询图片url
		List<PatientRecordImgDto> patientRecordImgDtoList= patientRecordImgService.findPatientRecordImgList(patientRecordImg);
		PatientRecordDto patientRecordDto=patientRecordDao.get("getPatientRecordDto", patientRecord);
		if(patientRecordDto!=null) {
			patientRecordDto.setImgList(patientRecordImgDtoList);
		}
		return patientRecordDto;
	}

	@Override
	public List<PatientRecord> findPatientRecordList(PatientRecord patientRecord) {
		List<PatientRecord> patientRecordList = patientRecordDao.find(patientRecord);
		return patientRecordList;
	}
	
    /**
     * 查询提问
     * @param doctorId    医生id
     * @param type		   1:查询最新提问,2查询我的会诊	
     * @return
     */
	@Override
    public List<PatientRecordDto> findPatientRecordList(Long doctorId,int type){
		List<PatientRecordDto> patientRecordDtoList = null;
		if(type == 1){
			patientRecordDtoList = findNewPatientRecord(null);
		}else if(type ==2){
			patientRecordDtoList = findMyPatientRecord(doctorId);
		}
		return patientRecordDtoList;
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

		try {
			synchronized (sessionNumber) {
				//这个房间人数不足三个人时,往PatientDoctorRel表中插入一条数据
				if(sessionNumber < 3){

					PatientDoctorRel  patient = new PatientDoctorRel();
					patient.setPatientId(patientId);
					patient.setSessionId(sessionId);
					patient.setDocId(doctorId);
					//根据会话id,医生id,患者id,去数据库中查数据
					//不为空,医生已经在这个会话中,直接return true
					PatientDoctorRel patientDoctorRel = patientDoctorRelService.findPatientDoctorRel(patient);
					//为空,数据库中添加一条数据,redis缓存加1
					if(patientDoctorRel == null){

						patientDoctorRelService.insert(patient);
						sessionNumber ++;
						//将房间里的人数添加到redis中
						JedisManager.setObject(sessionId,-1,sessionNumber);
						
						//有一个人时,更新PatientRecord表中的状态为抢答开始
						if(sessionNumber == 1){
							PatientRecord patientReocrd = new PatientRecord();
							patientReocrd.setSessionId(sessionId);
							patientReocrd.setStatus(1);
							//更新 patientRecord的状态 ，改为抢答完成
							updateStatusByPrimaryKey(patientReocrd);
						}
						
						//人数到了三个时,更新PatientRecord表中的状态,改为抢答完成
						if(sessionNumber == 3){
							PatientRecord patientReocrd = new PatientRecord();
							patientReocrd.setSessionId(sessionId);
							patientReocrd.setStatus(2);
							//更新 patientRecord的状态 ，改为抢答完成
							updateStatusByPrimaryKey(patientReocrd);
						}

					}

				}else{
					flag = false;
				}
			}
		}catch (Exception e){
			logger.error("抢答问题出现异常",e.fillInStackTrace());
			flag = false;
		}
		return flag;
	}


	/**
	 * 最新问题
	 * @param patientRecord
     * @return
     */
	@Override
	public List<PatientRecordDto> findNewPatientRecord(PatientRecord patientRecord) {
	     List<PatientRecordDto> patientRecordDtoList = patientRecordDao.find("findNewPatientRecord",patientRecord);
		return patientRecordDtoList;
	}

	/**
	 * 我的会诊
	 * @param doctorId
     * @return
     */
	@Override
	public List<PatientRecordDto> findMyPatientRecord(long doctorId) {
	     List<PatientRecordDto> patientRecordDtoList = patientRecordDao.find("findMyPatientRecord",doctorId);
		return patientRecordDtoList;
	}

	/**
	 * 获取问题详情
	 * @param sessionId
	 * @return
	 */
	@Override
	public PatientRecordDto getPatientRecordById(String sessionId){
		PatientRecordDto patientRecordDto = (PatientRecordDto) patientRecordDao.find("getPatientRecordById",sessionId);
		return patientRecordDto;
	}
}
