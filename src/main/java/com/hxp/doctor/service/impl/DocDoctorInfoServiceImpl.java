package com.hxp.doctor.service.impl;

import java.util.List;

import com.hxp.common.redis.JedisManager;
import com.hxp.commonality.dao.SysMessageDao;
import com.hxp.commonality.po.SysMessage;
import com.hxp.doctor.dao.DocDoctorInfoDao;
import com.hxp.doctor.dto.DocDoctorInfoDto;
import com.hxp.doctor.dto.DoctorDto;
import com.hxp.doctor.po.DocDoctorInfo;
import com.hxp.doctor.service.IDocDoctorInfoService;
import com.hxp.doctor.vo.DoctorVo;
import com.hxp.patient.po.PatientCustomer;
import com.hxp.util.ConstantsStatus;
import com.hxp.util.DateUtil;
import com.hxp.util.EncryptionUtil;
import com.hxp.util.constant.Constant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by qinjingyu on 2016/7/12.
 */
@Service
public class DocDoctorInfoServiceImpl implements IDocDoctorInfoService {

    @Autowired
    private DocDoctorInfoDao doctorInfoDao;
    
	@Autowired
	private DocDoctorInfoDao docDoctorInfoDao;

    @Autowired
    private SysMessageDao sysMessageDao;

    @Override
    public int deleteDocDoctorInfoKey(Integer id) throws Exception {
        return doctorInfoDao.delete(id);
    }

    @Override
    public int insert(DocDoctorInfo doctorInfo) throws Exception {
        doctorInfo.setStatus(0);//认证状态  -1删除 0:未认证,1审核通过/已认证,2认证未通过3认证中,4重新认证
        doctorInfo.setCreateTime(DateUtil.getDate());//创建时间
        return doctorInfoDao.insert(doctorInfo);
    }

    @Override
    public DocDoctorInfo selectDocDoctorInfoKey(Integer id) throws Exception {
        return doctorInfoDao.get(id);
    }

    @Override
    public DocDoctorInfo getByDocDoctorInfo(DocDoctorInfo docDoctorInfo) throws Exception {
        return doctorInfoDao.get("getByDocDoctorInfo",docDoctorInfo);
    }

    @Override
    public DocDoctorInfo getDocDoctorInfoByMobile(String mobile) throws Exception {
        return doctorInfoDao.get("getDocDoctorInfoByMobile",mobile);
    }

    @Override
    public int updateByResetPassword(DocDoctorInfo docDoctorInfo) throws Exception {
        return doctorInfoDao.update("updateByResetPassword",docDoctorInfo);
    }

    @Override
    public int updateDocDoctorInfoByAttestation(DocDoctorInfo docDoctorInfo) throws Exception {
        return doctorInfoDao.update("updateByResetPassword",docDoctorInfo);
    }

    //doctorType不传值,查名医风采时,传doctorType=1
	@Override
	public List<DocDoctorInfoDto> getDoctorInfoList(DocDoctorInfoDto ddid) {
		// TODO Auto-generated method stub
		return doctorInfoDao.find(ddid);
	}
	
  
	@Override
	public List<DocDoctorInfoDto> getDoctorInfoByPatientList(DocDoctorInfoDto ddid){
		
		return doctorInfoDao.find("findDoctorInfoByPatientId",ddid);
	}

	@Override
	public int updateDoctor(DocDoctorInfo docDoctorInfo){
        SysMessage sysMessage = new SysMessage();
        if (docDoctorInfo.getStatus() == 2) { // 认证不通过
            //系统消息表记录
            sysMessage.setType(1); //1 医生认证消息
            sysMessage.setObjId(docDoctorInfo.getId()); //医生id
            sysMessage.setContent(Constant.DOCTORAUTH_FAILED_MESSAGE_BACKGROUND + docDoctorInfo.getMark()); //内容
            sysMessage.setStatus(0); //0 未读  1已读
            sysMessage.setCreateTime(DateUtil.getDate());//创建时间
            sysMessageDao.insert(sysMessage);
            doctorInfoDao.update("updateByPrimaryKeySelective",docDoctorInfo);
            
            String token = EncryptionUtil.MD5(ConstantsStatus.DOCT_TOKEN_KEY+docDoctorInfo.getMobile());//md5加密的token
			DoctorDto dd = (DoctorDto) JedisManager.getObject(token);
			
			if(dd!=null){
				docDoctorInfo.setMobile(dd.getMobile());
				DoctorDto doctorDto = doctorInfoDao.get("getDoctorByMobileAndPass",docDoctorInfo);
				JedisManager.setObject(token, 0, doctorDto);
			}
            return 1;
        }else {
            //系统消息表记录
            sysMessage.setType(1); //1 医生认证消息
            sysMessage.setObjId(docDoctorInfo.getId()); //医生id
            sysMessage.setContent(Constant.DOCTORAUTH_SUCCESS_MESSAGE_BACKGROUND); //内容
            sysMessage.setStatus(0); //0 未读  1已读
            sysMessage.setCreateTime(DateUtil.getDate());//创建时间
            sysMessageDao.insert(sysMessage);
            
            doctorInfoDao.update("updateByPrimaryKeySelective",docDoctorInfo);
            String token = EncryptionUtil.MD5(ConstantsStatus.DOCT_TOKEN_KEY+docDoctorInfo.getMobile());//md5加密的token
			DoctorDto dd = (DoctorDto) JedisManager.getObject(token);
			if(dd!=null){
				docDoctorInfo.setMobile(dd.getMobile());
				DoctorDto doctorDto = docDoctorInfoDao.get("getDoctorByMobileAndPass",docDoctorInfo);
				JedisManager.setObject(token, 0, doctorDto);
			}
            return 1;
        }
	}
	/**
	 * 查询数据库
	 */
	@Override
	public DocDoctorInfo getByDocMobile(String mobile) {
		
		return doctorInfoDao.get("getByDocMobile",mobile);
	}

    @Override
    public DoctorDto getDoctorByMobileAndPass(DocDoctorInfo docDoctorInfo) {
        return doctorInfoDao.get("getDoctorByMobileAndPass",docDoctorInfo);
    }

    /***
     * 查找doctorDto对象
     * @param dbDocDoctor
     * @return
     */
    @Override
    public  DoctorDto getDoctorByDoctorId(DoctorDto dbDocDoctor){
        return doctorInfoDao.get("getDoctorByDoctorId",dbDocDoctor);
    }

    @Override
    public Integer updateDoctorInfo(DoctorVo doctorVo) {
        int count = 0;
        Integer status = doctorVo.getStatus();
        if (StringUtils.isNotEmpty(doctorVo.getHeadPath()) || StringUtils.isNotEmpty(doctorVo.getTerritory())||
                StringUtils.isNotEmpty(doctorVo.getIntro())) {
            DoctorVo vo = new DoctorVo();
            vo.setDoctorId(doctorVo.getDoctorId());
            vo.setHeadPath(doctorVo.getHeadPath());
            vo.setTerritory(doctorVo.getTerritory());
            vo.setIntro(doctorVo.getIntro());
            count = doctorInfoDao.updateObj("updateDoctorInfo", vo);

            doctorVo.setHeadPath(null);
            doctorVo.setTerritory(null);
            doctorVo.setIntro(null);
        }
        if (status != ConstantsStatus.CERTIFIED && status != ConstantsStatus.APPROVE){ //不是已认证或者重新认证

            doctorVo.setStatus(ConstantsStatus.CERTIFICATION);
            count = doctorInfoDao.updateObj("updateDoctorInfo", doctorVo);
        }else{
            DoctorDto doctorDtoTemp = doctorInfoDao.get("getDoctorByDoctorId",doctorVo.getDoctorId());
            if (!doctorVo.getPidPath().equals(doctorDtoTemp.getPidPath()) ||
                    !doctorVo.getDoctorName().equals(doctorDtoTemp.getDoctorName())
                    || !doctorVo.getDeptId().equals(doctorDtoTemp.getDeptId().toString())||
                     !doctorVo.getHospitalId().equals(doctorDtoTemp.getHospitalId().toString())||
                     doctorVo.getSex() != doctorDtoTemp.getSex()||
                    !doctorVo.getProfessional().equals(doctorDtoTemp.getProfId().toString())||
                   !doctorVo.getDeptTel().equals(doctorDtoTemp.getDeptTel())){
                doctorVo.setStatus(ConstantsStatus.APPROVE);
            }
            count = doctorInfoDao.updateByObject("upDoctorBasicInfo", doctorVo);
            Integer doctorNum = doctorInfoDao.get("getDoctorTempNum", doctorVo.getDoctorId());
            if (doctorNum > 0){ //已经提交过信息修改，直接更新一下修改表的数据
                doctorInfoDao.updateByObject("updateDoctorTempById", doctorVo);
            }else{
                // 修改需要审核的信息直接入医生修改库
                count = doctorInfoDao.insertObject("insertDoctorTempInfo", doctorVo);
            }
        }
        return count;
    }

    @Override
    public List<PatientCustomer> selectCollectionPatient(Long doctorId) {
        return doctorInfoDao.find("selectCollectionPatient",doctorId);
    }
}
