package com.tdcr.graphql.repo;


import com.tdcr.graphql.entity.Address;
import com.tdcr.graphql.entity.Person;

import java.util.List;

public interface BaseDao {


    public List<Person> getFriends(List<Long> friends);

    public List<Address> getAddress(List<Long> addressIdList);

}
