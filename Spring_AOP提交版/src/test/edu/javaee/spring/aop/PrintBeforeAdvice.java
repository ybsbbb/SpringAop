package test.edu.javaee.spring.aop;

import java.lang.reflect.Method;

import dev.edu.javaee.spring.aop.MethodInterceptor;

public class PrintBeforeAdvice implements MethodInterceptor {

	@Override
	public Object invoke(Object target, Method method, Object[] args) throws Throwable {
		//System.out.println("before advice");
		return method.invoke(target, args);
	}
}
