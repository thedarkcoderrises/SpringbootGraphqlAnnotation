package com.tdcr.graphql.config;

import com.mongodb.MongoClient;
import com.tdcr.graphql.directive.CustomDirectiveDef;
import com.tdcr.graphql.service.PersonService;
import graphql.GraphQL;
import graphql.analysis.MaxQueryComplexityInstrumentation;
import graphql.analysis.MaxQueryDepthInstrumentation;
import graphql.execution.batched.BatchedExecutionStrategy;
import graphql.execution.instrumentation.ChainedInstrumentation;
import graphql.execution.instrumentation.tracing.TracingInstrumentation;
import graphql.schema.GraphQLSchema;
import io.leangen.graphql.GraphQLSchemaGenerator;
import io.leangen.graphql.metadata.strategy.query.AnnotatedResolverBuilder;
import io.leangen.graphql.metadata.strategy.query.PublicResolverBuilder;
import io.leangen.graphql.metadata.strategy.value.jackson.JacksonValueMapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Arrays;

@Configuration
@EnableMongoRepositories(basePackages = "com.tdcr.graphql")
public class AppConfig {

    @Autowired
    PersonService personService;

    @Bean
    public GraphQL graphQL() {

        GraphQLSchema schema = new GraphQLSchemaGenerator()
                .withResolverBuilders(
                        new AnnotatedResolverBuilder(),
                        new PublicResolverBuilder("com.tdcr.graphql"))
                .withOperationsFromSingleton(personService)
                .withValueMapperFactory(new JacksonValueMapperFactory())
                .withAdditionalDirectives(CustomDirectiveDef.UpperDirective)
                .generate();
        GraphQL graphQL = GraphQL.newGraphQL(schema)
                .queryExecutionStrategy(new BatchedExecutionStrategy())
                .instrumentation(new ChainedInstrumentation(Arrays.asList(
                        new MaxQueryComplexityInstrumentation(200),
                        new MaxQueryDepthInstrumentation(20),//This instrumentation controll how much depth we have have in graphql query.
//                        TracingInstrumentation.Options.newOptions().includeTrivialDataFetchers()
                        new TracingInstrumentation()
                )))
                .build();

        return graphQL;
    }

    @Bean
    public MongoClient mongo() {
        return new MongoClient("localhost",27017);
    }

    @Bean
    public MongoTemplate mongoTemplate()  throws Exception {
        return new MongoTemplate(mongo(), "grpahql");
    }

}
