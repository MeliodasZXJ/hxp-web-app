package com.hxp.common.framework.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.hxp.common.framework.dao.ISimpleDao;


/**
 * SimpleDao<T>
 * DAO层的公共父类，所有的DAO类都要继承此类
 *
 * @author anpushang
 * @date 2016-03-12
 */
public abstract class SimpleDaoImpl<T> implements ISimpleDao<T> {
    protected Logger logger = Logger.getLogger(SimpleDaoImpl.class);
    protected static final String GET = "get";
    protected static final String FIND = "find";
    protected static final String PAGE = "page";
    protected static final String BATCH_INSERT = "batchInsert";
    protected static final String INSERT = "insert";
    protected static final String UPDATE = "update";
    protected static final String DELETE = "delete";
    protected static final String BATCH_DELETE = "batch_delete";

    @Resource(name = "dynamicSqlSessionTemplate")
    private SqlSession sqlSessionTemplate;
    protected Class<T> entityClass;
    protected String className;

  /* (non-Javadoc)
 * @
 */

    public SqlSession getSqlSessionTemplate() {
        return this.sqlSessionTemplate;
    }

  /* (non-Javadoc)
 * @see com.kld.basedao.ISimpleDao#getClassName()
 */

    public String getClassName() {
        return this.className;
    }

    /***
     * 拼接 mybatisMappId
     * @param methodName
     * @return
     */
    public String getMapperMethod(String methodName) {
        return new StringBuilder(className).append(".").append(methodName).toString();
    }

    /***
     * 构造函数 初始化数据
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public SimpleDaoImpl() {
        Type genType = getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        this.entityClass = ((Class) params[0]);
        this.className = entityClass.getName();
    }

    /***
     * 新增实体
     * @param entity
     * @return
     */
    public int insert(T entity) {
        return insert(INSERT, entity);
    }

    /***
     * 保存对象，参数不受泛型限制，此处的泛型表明参数可以是任意
     * @param mapperMethod
     * @param entity
     * @param <I>
     * @return
     */
    public <I> int insertObj(String mapperMethod,I entity){
        return getSqlSessionTemplate().insert(getMapperMethod(mapperMethod), entity);
    }

    public <I> int insertObj(I entity){
        return insertObj(INSERT,entity);
    }

    /***
     * 批量插入集合对象
     * @param entityList
     * @return
     */
    public int insert(List<T>  entityList) {
        return insert(BATCH_INSERT, entityList);
    }

    /***
     * 插入单个对象
     * @param mapperMethod mappId
     * @param entity
     * @return
     */
    public int insert(String mapperMethod, T entity) {
        return getSqlSessionTemplate().insert(getMapperMethod(mapperMethod), entity);
    }

    /***
     *  插入对象集合
     * @param mapperMethod
     * @param entityList
     * @return
     */
    public int insert(String mapperMethod, List<T> entityList) {
        return getSqlSessionTemplate().insert(getMapperMethod(mapperMethod), entityList);
    }


    /***
     * 修改单个对象
     * @param entity
     * @return
     */
    public int update(T entity) {
        return update(UPDATE, entity);
    }

    /***
     * 修改单个对象
     * @param mapperMethod
     * params
     * @return
     */
    public <I> int updateByObject(String mapperMethod,I params) {
        return getSqlSessionTemplate().update(mapperMethod,params);
    }


    /***
     * 修改单个对象，参数是 map集合，参数可以封装到map中
     * @param objectMap
     * @return
     */
    public int update(Map<String,Object> objectMap){
        return update(UPDATE,objectMap);
    }

    /***
     * 修改单个对象，自定义mappid
     * @param mapperMethod
     * @param objectMap
     * @return
     */
    public int update(String mapperMethod, Map<String,Object> objectMap){
        return getSqlSessionTemplate().update(getMapperMethod(mapperMethod), objectMap);
    }




    /****
     *  修改单个实体对象，自定义mappid
     * @param mapperMethod
     * @param entity
     * @return
     */
    public int update(String mapperMethod, T entity) {
        return getSqlSessionTemplate().update(getMapperMethod(mapperMethod), entity);
    }


    public <I> int updateObj(String mapperMethod, I params) {
        return getSqlSessionTemplate().update(getMapperMethod(mapperMethod), params);
    }


    /***
     * 泛型文档
     * http://blog.csdn.net/jinuxwu/article/details/6771121
     * @param obj
     * @param <I>
     * @return
     */
    public <I> int bathUpdate(I obj) {
        return delete(DELETE, obj);
    }


    /****
     *
     * @param mapperMethod mappid
     * @param params 参数， 任何类型都可以
     * @param <I>
     * @return
     */
    public <I> int bathUpdate(String mapperMethod, I params) {
        return getSqlSessionTemplate().delete(getMapperMethod(mapperMethod), params);
    }


    /***
     * 泛型文档
     * http://blog.csdn.net/jinuxwu/article/details/6771121
     * @param obj
     * @param <I>
     * @return
     */
    public <I> int delete(I obj) {
        return delete(DELETE, obj);
    }

    /****
     *
     * @param mapperMethod mappid
     * @param params 参数， 任何类型都可以
     * @param <I>
     * @return
     */
    public <I> int delete(String mapperMethod, I params) {
        return getSqlSessionTemplate().delete(getMapperMethod(mapperMethod), params);
    }



    /***
     * 删除对象
     * @param obj
     * @param <I> 参数为任何对象，I 是继承了Object
     * @return
     *//*
    public <I> int delete(I obj) {
        return delete(DELETE, obj);
    }

    /***
     * 删除对象，I 是继承了Object ，自定义mappId
     * @param mapperMethod
     * @param params
     * @param <I>
     * @return
     *//*
    public <I> int delete(String mapperMethod, I params) {
        deleteMulti(true, params);
        return getSqlSessionTemplate().delete(getMapperMethod(mapperMethod), params);
    }

  *//* (non-Javadoc)
 * @see com.kld.basedao.ISimpleDao#deleteMulti(boolean, I)
 *//*

    public <I> int deleteMulti(boolean haveFK, final I... ids) {
        Assert.notEmpty(ids, "ids不能为空");
        int count = 0;
        if (haveFK) {
            for (I id : ids)
                try {
                    count += delete(id);
                } catch (Exception e) {
                    this.logger.error("删除出现异常!", e);
                    throw new RuntimeException("删除出现异常!");
                }
        } else {
            count = getSqlSessionTemplate().update(getMapperMethod(BATCH_DELETE), ids);
        }
        logger.debug("delete entity {}, batch: {}, ids is {}", className, haveFK, ids);
        return count;
    }
*/
  /* (non-Javadoc)
 * @see com.kld.basedao.ISimpleDao#get(I)
 */

    public <I> I get(Object obj) {
        return get(GET, obj);
    }

    /* (non-Javadoc)
   * @see com.kld.basedao.ISimpleDao#get(java.lang.String, I)
   * 如果想传递map或者list，直接传递就可以，
   * 类 Object 是类层次结构的根类。每个类都使用 Object 作为超类。所有对象（包括数组）都实现这个类的方法。
   */
    public <I> I get(String mapperMethod, Object obj) {
        return getSqlSessionTemplate().selectOne(getMapperMethod(mapperMethod), obj);
    }

  /* (non-Javadoc)
 * @see com.kld.basedao.ISimpleDao#find(I)
 */

    public <I> List<I> find(Object params) {
        return find(FIND, params);
    }

  /* (non-Javadoc)
 * @see com.kld.basedao.ISimpleDao#find(java.lang.String, I)
 */

    public <I> List<I> find(String mapperMethod, Object params) {
        return getSqlSessionTemplate().selectList(getMapperMethod(mapperMethod), params);
    }

  /* (non-Javadoc)
 * @see com.kld.basedao.ISimpleDao#find()
 */

    public <I> List<I> find() {
        return getSqlSessionTemplate().selectList(getMapperMethod(FIND));
    }




/**
 public GridResult<T> getPageList(String sqlId,GridQueryPara page,QueryParaDto query){
 QueryPageDto qpd = new QueryPageDto();
 if(null != page.getCurrentPage()){
 qpd.setCurrentPage(Integer.parseInt(page.getCurrentPage()));
 qpd.setMaxRows(Integer.parseInt(page.getMaxRows()));
 }
 Map<String,Object> m = new HashMap<String,Object>();
 m.put("page", qpd);
 if(null != query) m.putAll(query.getMap());
 List<T> list = find(sqlId, m);
 GridResult<T> gr = new GridResult<T>();
 gr.setRows(list);
 gr.setPage(qpd.getCurrentPage());
 gr.setRecords(qpd.getRecords());
 gr.setTotal(qpd.getTotal());
 return gr;
 }

 public GridResult<T> getPageList(String sqlId,GridQueryPara page){
 return getPageList(sqlId, page,null);
 }*/






}
