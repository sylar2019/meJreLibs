package me.java.library.db.mongo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * File Name             :  ApplicationTest
 * Author                :  sylar
 * Create Date           :  2018/4/30
 * Description           :
 * Reviewed By           :
 * Reviewed On           :
 * Version History       :
 * Modified By           :
 * Modified Date         :
 * Comments              :
 * CopyRight             : COPYRIGHT(c) xxx.com   All Rights Reserved
 * *******************************************************************************************
 */

public class ApplicationTest {

    @Autowired
    FooRepository fooRepository;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void create() {
        Foo foo;
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            foo = new Foo();
            foo.setName("sylar_" + i);
            foo.setCount(random.nextInt(10000));
            String id = fooRepository.save(foo).getId();
            System.out.println("create success. id:" + id);
        }
    }

    @Test
    public void clear() {
        System.out.println("Records count:" + fooRepository.count());

        System.out.println("begin clear all");
        fooRepository.deleteAll();

        System.out.println("Records count:" + fooRepository.count());
    }

    @Test
    public void findAll() {
        List<Foo> list = fooRepository.findAll();
        list.forEach(foo -> {
            System.out.println(foo == null ? "nothing" : foo);
        });
    }

    @Test
    public void findById() {
        String id = "5ae6cc3507efe62dd4f87276";
        Optional<Foo> foo = Optional.ofNullable(fooRepository.findOne(id));
        System.out.println(foo.isPresent() ? foo : "NULL");
    }

    @Test
    public void findByName() {
        Foo foo = fooRepository.getByName("sylar_0");
        System.out.println(foo == null ? "nothing" : foo);
    }

    @Test
    public void findLikeName() {
        Query query = new Query();

        //模糊查询
        Criteria criteria = Criteria.where("name").regex(".*?" + "sylar" + ".*");
        query.addCriteria(criteria);
        query.with(new Sort(new Sort.Order("name")));

        Page<Foo> page;
        page = fooRepository.find(query);
        System.out.println("getTotalPages:" + page.getTotalPages());
        System.out.println("getTotalElements:" + page.getTotalElements());

        page.forEach(foo ->
                System.out.println(foo == null ? "nothing" : foo)
        );


        System.out.println("find with pageRequest");
        page = fooRepository.find(query, new PageRequest(0, 10));
        System.out.println("getTotalPages:" + page.getTotalPages());
        System.out.println("getTotalElements:" + page.getTotalElements());

        page.forEach(foo ->
                System.out.println(foo == null ? "nothing" : foo)
        );

    }

}