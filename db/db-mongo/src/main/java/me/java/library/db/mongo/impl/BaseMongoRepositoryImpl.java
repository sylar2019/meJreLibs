package me.java.library.db.mongo.impl;

import me.java.library.db.mongo.BaseMongoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository;

import java.io.Serializable;
import java.util.List;

/**
 * @author :  sylar
 * @FileName :
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
public class BaseMongoRepositoryImpl<T, ID extends Serializable> extends SimpleMongoRepository<T, ID> implements
        BaseMongoRepository<T, ID> {
    protected final MongoOperations mongoTemplate;

    protected final MongoEntityInformation<T, ID> entityInformation;

    public BaseMongoRepositoryImpl(MongoEntityInformation<T, ID> metadata, MongoOperations mongoOperations) {
        super(metadata, mongoOperations);

        this.mongoTemplate = mongoOperations;
        this.entityInformation = metadata;
    }

    protected Class<T> getEntityClass() {
        return entityInformation.getJavaType();
    }


    @Override
    public Page<T> find(Criteria criteria) {
        return find(new Query(criteria));
    }

    @Override
    public Page<T> find(Criteria criteria, Pageable pageable) {
        return find(new Query(criteria), pageable);
    }

    @Override
    public Page<T> find(Query query) {
        return find(query, PageRequest.of(0, Integer.MAX_VALUE));
    }

    @Override
    public Page<T> find(Query query, Pageable pageable) {
        long total = mongoTemplate.count(query, getEntityClass());
        List<T> list = mongoTemplate.find(query.with(pageable), getEntityClass());

        return new PageImpl<>(list, pageable, total);
    }

    @Override
    public T findOne(Criteria criteria) {
        return findOne(new Query(criteria));
    }

    @Override
    public T findOne(Query query) {
        query = query.limit(1);
        List<T> list = mongoTemplate.find(query, getEntityClass());
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }
}