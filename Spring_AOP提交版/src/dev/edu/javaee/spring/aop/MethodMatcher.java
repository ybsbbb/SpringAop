package dev.edu.javaee.spring.aop;

import java.lang.reflect.Method;

public interface MethodMatcher {
	boolean matches(Method method, Class<?> targetClass);
	boolean mathcer(Method method, Class<?> targetClass, Object... args);
	boolean isRuntime();
}
