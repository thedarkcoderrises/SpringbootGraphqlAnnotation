package com.tdcr.graphql.directive;


import io.leangen.graphql.annotations.types.GraphQLDirective;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@GraphQLDirective
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Upper {

    boolean isActive() default false;
}
