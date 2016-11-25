package dev.edu.javaee.spring.aop.support;

import dev.edu.javaee.spring.aop.ClassFilter;
import dev.edu.javaee.spring.aop.Pointcut;

public class NameMatchMethodPointcutAdvisor extends AbstractPointcutAdvisor{

	private final NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
	@Override
	public Pointcut getPointcut() {
		return this.pointcut;
	}

	public void setClassFilter(ClassFilter classFilter) {
		this.pointcut.setClassFilter(classFilter);
	}
	
	public void setMappedName(String mappedName) {
		this.pointcut.setMappedName(mappedName);
	}
	
	public void setMappedNames(String... mappedNames) {
		this.pointcut.setMappedNames(mappedNames);
	}
}
