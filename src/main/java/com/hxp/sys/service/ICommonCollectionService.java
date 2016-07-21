package com.hxp.sys.service;

import com.hxp.sys.dto.DoctorCollecDto;
import com.hxp.sys.po.CommonCollection;

import java.util.List;

/**
 * Created by anpushang on 2016/7/13.
 */
public interface ICommonCollectionService {

    /***
     * 收藏新增
     * @param commonCollection
     * @return
     * @throws Exception
     */
    int insertCommonCollection(CommonCollection commonCollection)throws Exception;

    /***
     * 收藏修改
     * @param commonCollection
     * @return
     * @throws Exception
     */
    int updateCommonCollection(CommonCollection commonCollection)throws Exception;

    /***
     * 查询收藏
     * @return
     * @throws Exception
     */
    List<CommonCollection> selectCollectionByPageList(int pageNum, int pageSize,
                                                      CommonCollection commonCollection)throws Exception;

    /***
     * 删除收藏
     * @param commonCollection
     * @return
     * @throws Exception
     */
    int deleteCollection(CommonCollection commonCollection)throws Exception;

    /***
     * 查询我收藏的医生
     * @param collection
     * @return
     */
    List<DoctorCollecDto> selectByMyCollecDoctor(CommonCollection collection);
}
