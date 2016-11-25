package dev.edu.javaee.spring.aop.support;

import dev.edu.javaee.spring.aop.MethodInterceptor;
import dev.edu.javaee.spring.aop.MethodMatcher;

public class AdvisedSupport {
	private TargetSource targetSource;
	
	private Class<?> interfaces;
	
	private MethodInterceptor methodInterceptor;
	
	private MethodMatcher methodMatcher;
	
	public TargetSource getTargetSource() {
		return targetSource;
	}
	public void setTargetSource(TargetSource targetSource) {
		this.targetSource = targetSource;
	}
	public MethodInterceptor getMethodInterceptor() {
		return methodInterceptor;
	}
	public void setMethodInterceptor(MethodInterceptor methodInterceptor) {
		this.methodInterceptor = methodInterceptor;
	}
	public MethodMatcher getMethodMatcher() {
		return methodMatcher;
	}
	public void setMethodMatcher(MethodMatcher methodMatcher) {
		this.methodMatcher = methodMatcher;
	}
	public Class<?> getInterfaces() {
		return interfaces;
	}
	public void setInterfaces(Class<?> interfaces) {
		this.interfaces = interfaces;
	}
}
