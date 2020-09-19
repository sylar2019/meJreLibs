package me.java.library.db.mongo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * @author :  sylar
 * @FileName :  BaseMongoRepository
 * @CreateDate :  2017/11/08
 * @Description :
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) xxx.com All Rights Reserved
 * *******************************************************************************************
 */
@NoRepositoryBean
public interface BaseMongoRepository<T, ID extends Serializable> extends MongoRepository<T, ID> {
    /**
     * 查询列表
     *
     * @param criteria
     * @return
     */
    Page<T> find(Criteria criteria);

    /**
     * 查询列表
     *
     * @param criteria
     * @param pageable
     * @return
     */
    Page<T> find(Criteria criteria, Pageable pageable);

    /**
     * 查询列表
     *
     * @param query
     * @return
     */
    Page<T> find(Query query);

    /**
     * 查询列表
     *
     * @param query
     * @param pageable
     * @return
     */
    Page<T> find(Query query, Pageable pageable);

    /**
     * 查询
     *
     * @param criteria
     * @return
     */
    T findOne(Criteria criteria);

    /**
     * 查询
     *
     * @param query
     * @return
     */
    T findOne(Query query);
}
