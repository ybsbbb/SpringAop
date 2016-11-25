package dev.edu.javaee.spring.factory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import dev.edu.javaee.spring.aop.MethodInterceptor;
import dev.edu.javaee.spring.aop.framework.ProxyFactoryBean;
import dev.edu.javaee.spring.bean.BeanDefinition;

public abstract class AbstractBeanFactory implements BeanFactory{
	private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>();
	
	@SuppressWarnings("rawtypes")
	public Object getBean(String beanName)
	{
		Object bean = this.beanDefinitionMap.get(beanName).getBean();
		if(bean instanceof FactoryBean){
			try {
				if(bean instanceof ProxyFactoryBean){
					if(((ProxyFactoryBean)bean).getMethodInterceptor() == null)
						((ProxyFactoryBean)bean).setMethodInterceptor((MethodInterceptor)this.getBean(((ProxyFactoryBean)bean).getInterceptorNames()));
				}
				return ((FactoryBean)bean).getObject();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return bean;
	}

	public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition)
	{
		beanDefinition = GetCreatedBean(beanDefinition);
		this.beanDefinitionMap.put(beanName, beanDefinition);
	}
	//¸Ä¶¯µÄ
	public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition,Map<String,Object> map)
	{
		beanDefinition = GetCreatedBean(beanDefinition,beanName,map);
		this.beanDefinitionMap.put(beanName, beanDefinition);
	}
	
	protected BeanDefinition getBeanDefination(String beanName){
		return this.beanDefinitionMap.get(beanName);
	}
	
	
	protected abstract BeanDefinition GetCreatedBean(BeanDefinition beanDefinition);
	protected abstract BeanDefinition GetCreatedBean(BeanDefinition beanDefinition,String beanName,Map<String,Object> map);
}
