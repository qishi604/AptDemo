package com.lanayru.apt.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zhengqi
 * @since 2020/3/8
 **/
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface Adapter {
  String source();
}
