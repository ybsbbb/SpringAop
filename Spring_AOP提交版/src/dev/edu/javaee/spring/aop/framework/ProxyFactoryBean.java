package dev.edu.javaee.spring.aop.framework;

import dev.edu.javaee.spring.aop.support.AdvisedSupport;
import dev.edu.javaee.spring.aop.support.NameMatchMethodPointcut;
import dev.edu.javaee.spring.aop.support.TargetSource;
import dev.edu.javaee.spring.factory.FactoryBean;

public class ProxyFactoryBean extends AdvisedSupport implements FactoryBean<Object> {

	private String proxyInterfaces;
	private Object target;
	private String interceptorNames;
	public boolean flag = false;
	public String getProxyInterfaces() {
		return proxyInterfaces;
	}

	public void setProxyInterfaces(String proxyInterfaces) {
		this.proxyInterfaces = proxyInterfaces;
	}

	public Object getTarget() {
		return target;
	}

	public void setTarget(Object target) {
		this.target = target;
	}

	public String getInterceptorNames() {
		return interceptorNames;
	}

	public void setInterceptorNames(String interceptorNames) {
		this.interceptorNames = interceptorNames;
	}

	@Override
	public Object getObject() throws Exception {
		TargetSource ts = new TargetSource();
		ts.setTarget(this.getTarget());
		this.setTargetSource(ts);
		this.setInterfaces(Class.forName(proxyInterfaces));
		//this.setMethodInterceptor((MethodInterceptor)Class.forName(interceptorNames).newInstance());
		this.setMethodInterceptor(this.getMethodInterceptor());
		NameMatchMethodPointcut mm = new NameMatchMethodPointcut();
		//mm.setMappedName("printFoo");
		this.setMethodMatcher(mm);
		return this.getProxy();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Class<Object> getObjectType() {
		return (Class<Object>) this.getTargetSource().getTarget().getClass();
	}

	@Override
	public boolean isSingleton() {
		return false;
	}
	
	public Object getProxy() {
		return new JdkDynamicAopProxy(this).getProxy();
	}

}
