package com.hxp.sys.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.hxp.sys.dao.CommonCollectionDao;
import com.hxp.sys.dto.DoctorCollecDto;
import com.hxp.sys.po.CommonCollection;
import com.hxp.sys.service.ICommonCollectionService;

/**
 * Created by anpushang on 2016/7/13.
 */
@Service
public class CommonCollectionServiceImpl implements ICommonCollectionService {


    @Autowired
    private CommonCollectionDao collectionDao;


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
    public List<DoctorCollecDto> selectByMyCollecDoctor(CommonCollection collection) {
        //查询到收藏的医生了
        List<DoctorCollecDto> doctorCollecDtoList = collectionDao.find("selectByMyCollecDoctor", collection);
        //根据医生ID去查询该医生的粉丝量
        for (DoctorCollecDto doctorCollecDto : doctorCollecDtoList) {
            int collecNum = collectionDao.get("getCollecNumByDocId", doctorCollecDto.getDocId());
            doctorCollecDto.setClinicalReception(collecNum);
            //缺少咨询量的计算
        }
        return doctorCollecDtoList;
    }
}
