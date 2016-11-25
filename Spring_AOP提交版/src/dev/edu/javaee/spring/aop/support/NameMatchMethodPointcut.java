package dev.edu.javaee.spring.aop.support;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import dev.edu.javaee.spring.aop.Pointcut;

public class NameMatchMethodPointcut extends StaticMethodMatcherPointcut implements Pointcut{
	
	private List<String> mappedNames = new LinkedList<>();
	
	public void setMappedName(String name)
	{
		setMappedNames(name);
	}
	
	public void setMappedNames(String... names)
	{
		mappedNames = Arrays.asList(names);
	}
	
	@Override
	public boolean matches(Method method, Class<?> targetClass) {
		for(String mappedName: mappedNames)
		{
			if(method.getName().equals(mappedName))
				return true;
		}
		return false;
	}
}
