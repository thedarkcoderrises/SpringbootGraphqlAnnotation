package com.tdcr.graphql.repo.impl;


import com.tdcr.graphql.entity.Address;
import com.tdcr.graphql.entity.Person;
import com.tdcr.graphql.repo.BaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Repository
public class BaseDaoImpl implements BaseDao {

    @Autowired
    MongoTemplate template;

    @Autowired
    MongoOperations mo;


    @Override
    public List<Person> getFriends(List<Long> friends) {
        return mo.find(new Query(where("uid").in(friends)), Person.class);
    }

    @Override
    public List<Address> getAddress(List<Long> addressIdList) {
        return mo.find(new Query(where("addressId").in(addressIdList)), Address.class);
    }

}
