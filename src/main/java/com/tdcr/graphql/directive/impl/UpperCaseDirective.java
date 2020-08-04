package com.tdcr.graphql.directive.impl;


//import com.tdcr.graphql.directive.Upper;
import io.leangen.graphql.execution.InvocationContext;
import io.leangen.graphql.execution.ResolverInterceptor;

public class UpperCaseDirective { //implements ResolverInterceptor {


//    @Override
//    public Object aroundInvoke(InvocationContext context, Continuation continuation) throws Exception {
//        Upper upper = context.getResolver().getExecutable().getDelegate().getAnnotation(Upper.class);
////
////        Object obj = context.getResolutionEnvironment().getDirectives().find(Introspection.DirectiveLocation.FIELD, "upper");
////
//        if (upper != null) {
////            boolean isActive = (Boolean)context.getResolutionEnvironment().getDirectives().find(Introspection.DirectiveLocation.FIELD, "upper")
////                    .get(0).get("isActive");
////            ((Person)context.getResolutionEnvironment().context).getName().toUpperCase()
//            System.out.println(upper.isActive());
//        }
//        return continuation.proceed(context);
//
//    }
}