package me.java.library.db.jpa.mysql;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
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
    FooJpaRepository fooJpaRepository;

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
            foo.setName("sylar_" + i).setCount(random.nextInt(10000));
            long id = fooJpaRepository.save(foo).getId();
            System.out.println("create success. id:" + id);
        }
    }

    @Test
    public void clear() {
        System.out.println("Records count:" + fooJpaRepository.count());

        System.out.println("begin clear all");
        fooJpaRepository.deleteAll();

        System.out.println("Records count:" + fooJpaRepository.count());
    }

    @Test
    public void findAll() {
        List<Foo> list = fooJpaRepository.findAll();
        list.forEach(foo -> {
            System.out.println(foo == null ? "nothing" : foo);
        });
    }

    @Test
    public void findById() {
        Foo foo = fooJpaRepository.findOne(101L);
        System.out.println(foo == null ? "nothing" : foo);
    }

    @Test
    public void findByName() {
        Foo foo = fooJpaRepository.getByName("sylar_0");
        System.out.println(foo == null ? "nothing" : foo);
    }

    @Test
    public void findLikeName() {
        List<Foo> list = fooJpaRepository.findAll(new Specification<Foo>() {
            @Override
            public Predicate toPredicate(Root<Foo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.like(root.get("name").as(String.class), "%sylar%");
            }
        }, new Sort(new Sort.Order(Sort.Direction.DESC, "name")));

        list.forEach(foo ->
                System.out.println(foo == null ? "nothing" : foo)
        );

    }

}