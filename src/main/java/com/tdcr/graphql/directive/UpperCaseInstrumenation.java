package com.tdcr.graphql.directive;

import graphql.execution.instrumentation.SimpleInstrumentation;
import graphql.execution.instrumentation.parameters.InstrumentationFieldFetchParameters;
import graphql.language.BooleanValue;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetcherFactories;
import graphql.schema.GraphQLFieldDefinition;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UpperCaseInstrumenation extends SimpleInstrumentation {

    @Override
    public DataFetcher<?> instrumentDataFetcher(DataFetcher<?> originalFetcher, InstrumentationFieldFetchParameters parameters) {

        GraphQLFieldDefinition field = parameters.getField();



        DataFetcher dataFetcher = DataFetcherFactories.wrapDataFetcher(originalFetcher,
                ((dataFetchingEnvironment, value) -> {
                    BooleanValue isActive;
                    if(dataFetchingEnvironment.getField()
                            .getDirective("upper") == null){
                        isActive = BooleanValue.newBooleanValue().build();
                    }else{
                        isActive = (BooleanValue) dataFetchingEnvironment.getField()
                                .getDirective("upper")
                                .getArgument("isActive").getValue();
                    }

                    return (isActive.isValue())? toUpper(value) : value;
                }));
         field.transform( builder -> builder.dataFetcher(dataFetcher) );
        return super.instrumentDataFetcher(dataFetcher, parameters);
    }

    private Object toUpper(Object value) {
        if(value instanceof ArrayList){
            ArrayList<String> li = (ArrayList<String>) value;
            List<String> li_uc= li.stream().map(String::toUpperCase).collect(Collectors.toList());
            return li_uc;
        }
        return value;
    }

}
