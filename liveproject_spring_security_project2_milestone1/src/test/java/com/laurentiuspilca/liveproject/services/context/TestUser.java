package com.laurentiuspilca.liveproject.services.context;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@WithSecurityContext(factory = TestSecurityContextFactory.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface TestUser {

  String username();
  String [] authorities() default {"read"};
}
