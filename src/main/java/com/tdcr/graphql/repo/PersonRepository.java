package com.tdcr.graphql.repo;


import com.tdcr.graphql.entity.Person;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PersonRepository extends MongoRepository<Person, Long> {

}
