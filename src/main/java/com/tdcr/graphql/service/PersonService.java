package com.tdcr.graphql.service;

import com.tdcr.graphql.entity.Address;
import com.tdcr.graphql.entity.Person;
import com.tdcr.graphql.repo.AddressRepository;
import com.tdcr.graphql.repo.BaseDao;
import com.tdcr.graphql.repo.PersonRepository;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLContext;
import io.leangen.graphql.annotations.GraphQLQuery;
import org.dataloader.BatchLoader;
import org.dataloader.DataLoader;
import org.dataloader.DataLoaderOptions;
import org.dataloader.DataLoaderRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@Service
public class PersonService  {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    BaseDao baseDao;

    @Autowired
    DataLoaderRegistry dataLoaderRegistry;

    DataLoader<Long, Address> addressDataLoader;

    @PostConstruct
    void init(){
        initAddressDataLoader();
    }

    private void initAddressDataLoader() {
        BatchLoader<Long,Address> batchAddressLoader  = new BatchLoader<Long, Address>(){
            @Override
            public CompletionStage<List<Address>> load(List<Long> addressIdList) {
                return CompletableFuture.supplyAsync(() -> {
                    return address(addressIdList);
                });
            }

            private List<Address> address(List<Long> addressIdList) {
                return baseDao.getAddress(addressIdList);
            }
        };
        //Cache
        DataLoaderOptions options = DataLoaderOptions.newOptions().setCachingEnabled(false);
        addressDataLoader = DataLoader.newDataLoader(batchAddressLoader,options);

        dataLoaderRegistry.register("address",addressDataLoader);
    }


    @GraphQLQuery(name = "person")
    public Person person(@GraphQLArgument(name = "uid") Long uid){
        return personRepository.findById(uid).get();
    }

    @GraphQLQuery(name = "persons")
    public List<Person> persons(){
        return personRepository.findAll();
    }

    @GraphQLQuery(name = "address")
    public CompletableFuture<Address> address(@GraphQLContext  Person person){
        return  addressDataLoader.load(person.getAddressId());
    }


}