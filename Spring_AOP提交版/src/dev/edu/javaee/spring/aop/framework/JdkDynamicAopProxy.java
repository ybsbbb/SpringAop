package dev.edu.javaee.spring.aop.framework;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import dev.edu.javaee.spring.aop.AopProxy;
import dev.edu.javaee.spring.aop.support.AdvisedSupport;

public class JdkDynamicAopProxy implements AopProxy, InvocationHandler{

	private AdvisedSupport advised;
	
	public JdkDynamicAopProxy(AdvisedSupport advised)
	{
		this.advised = advised;
	}
	
	public Object getProxy()
	{
//		System.out.println(object.getClass());
//		System.out.println(objectClass);
		return Proxy.newProxyInstance(
				this.getClass().getClassLoader(),
				new Class[]{advised.getInterfaces()}, 
				this);
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		if(advised.getMethodMatcher().matches(method, advised.getTargetSource().getTarget().getClass()))
			return advised.getMethodInterceptor().invoke(advised.getTargetSource().getTarget(),method,args);
		return method.invoke(advised.getTargetSource().getTarget(), args);
	}

}
