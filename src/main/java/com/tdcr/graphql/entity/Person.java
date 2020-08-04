package com.tdcr.graphql.entity;

import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode
@Document(collection = "person")
public class Person {

    @Id
    @GraphQLQuery(name = "person", description = "Person UID")
    long uid;
    String name;
    int age;
    Date dob;
    String sex;
    long addressId;
    long vehicleId;
    List<Long> friends;

    public String getName() {
        return name;
    }


}
