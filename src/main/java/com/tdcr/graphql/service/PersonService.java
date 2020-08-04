package com.tdcr.graphql.service;

import com.tdcr.graphql.entity.Person;
import com.tdcr.graphql.repo.PersonRepository;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    @Autowired
    PersonRepository personRepository;


    @GraphQLQuery
    public Person person(@GraphQLArgument(name = "uid") Long uid){
        return personRepository.findById(uid).get();
    }

    @GraphQLQuery(name = "persons")
    public List<Person> persons(){
        return personRepository.findAll();
    }
}