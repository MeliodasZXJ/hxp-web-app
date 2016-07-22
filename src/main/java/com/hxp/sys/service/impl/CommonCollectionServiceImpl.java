package com.hxp.sys.service.impl;

import com.github.pagehelper.PageHelper;
import com.hxp.patient.service.IRcloudChatLogService;
import com.hxp.sys.dao.CommonCollectionDao;
import com.hxp.sys.dto.DoctorCollecDto;
import com.hxp.sys.po.CommonCollection;
import com.hxp.sys.po.RcloudChatLog;
import com.hxp.sys.service.ICommonCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by anpushang on 2016/7/13.
 */
@Service
public class CommonCollectionServiceImpl implements ICommonCollectionService {


    @Autowired
    private CommonCollectionDao collectionDao;
    @Autowired
    private IRcloudChatLogService rcloudChatLogService;

    @Override
    public int insertCommonCollection(CommonCollection commonCollection) throws Exception {

        commonCollection.setCreateTime(new Date());
        return collectionDao.insert(commonCollection);
    }

    @Override
    public int updateCommonCollection(CommonCollection commonCollection) throws Exception {
        return collectionDao.update(commonCollection);
    }

    @Override
    public List<CommonCollection> selectCollectionByPageList(int pageNum, int pageSize, CommonCollection commonCollection) throws Exception {
        PageHelper.startPage(pageNum, pageSize);
        List<CommonCollection> collectionList = collectionDao.find(commonCollection);
        return collectionList;
    }

    @Override
    public int deleteCollection(CommonCollection commonCollection) throws Exception {
        return collectionDao.delete(commonCollection);
    }

    @Override
    public List<DoctorCollecDto> selectByMyCollecDoctor(int pageNum, int pageSize,CommonCollection collection) {
        PageHelper.startPage(pageNum, pageSize);
        //查询到收藏的医生了
        List<DoctorCollecDto> doctorCollecDtoList = collectionDao.find("selectByMyCollecDoctor", collection.getUserId());
        //根据医生ID去查询该医生的粉丝量
        for (DoctorCollecDto doctorCollecDto : doctorCollecDtoList) {
            int collecNum = collectionDao.get("getCollecNumByDocId", Long.valueOf(doctorCollecDto.getDocId()));
            doctorCollecDto.setClinicalReception(collecNum);
            //缺少咨询量的计算
            RcloudChatLog rcloudChatLog = new RcloudChatLog();
            rcloudChatLog.setTouserid(String.valueOf(doctorCollecDto.getDocId()));
            int consult = rcloudChatLogService.getRcloudChatLogCount(rcloudChatLog);
            doctorCollecDto.setConsult(consult);
        }
        return doctorCollecDtoList;
    }

    @Override
    public int getCollecNumByDocId(Long docId) throws Exception {
        return collectionDao.get("getCollecNumByDocId", docId);
    }

    @Override
    public boolean selectDoctorIsAttention(Long patientId, Long doctorId) throws Exception {
        Map<String,Object> map = new ConcurrentHashMap<String,Object>();
        map.put("patientId",patientId);
        map.put("doctorId",doctorId);
        int count = collectionDao.get("selectDoctorIsAttention",map);
        return count > 0;
    }

}
