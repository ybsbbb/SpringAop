package dev.edu.javaee.spring.aop.framework;

import dev.edu.javaee.spring.aop.support.AdvisedSupport;

public class ProxyFactory extends AdvisedSupport{
	
	public Object getProxy() {
		return new JdkDynamicAopProxy(this).getProxy();
	}
}
