package com.hxp.doctor.service;

import com.hxp.doctor.po.Doctor;

/**
 * Created by anpushang on 2016/7/9.
 */
public interface IDoctorService {

    /***
     *  根据ID删除
     * @param id
     * @return
     * @throws Exception
     */
    int deleteByPrimaryKey(Integer id) throws Exception;

    /***
     * 新增
     * @param doctor
     * @return
     */
    int insert(Doctor doctor)throws Exception;

    /***
     * 根据ID查询
     * @param id
     * @return
     */
    Doctor selectByPrimaryKey(Integer id)throws Exception;
}
