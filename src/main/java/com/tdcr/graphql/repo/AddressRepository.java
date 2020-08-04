package com.tdcr.graphql.repo;

import com.tdcr.graphql.entity.Address;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AddressRepository extends MongoRepository<Address, Long> {

}
