package test.edu.javaee.spring.aop;

import dev.edu.javaee.spring.factory.BeanFactory;
import dev.edu.javaee.spring.factory.XMLBeanFactory;
import dev.edu.javaee.spring.resource.LocalFileResource;

public class AopTestMain {
	public static void main(String[] args) {
        LocalFileResource resource = new LocalFileResource("aop.xml");
		BeanFactory beanFactory = new XMLBeanFactory(resource);
	    FooInterface foo = (FooInterface)beanFactory.getBean("foo");
	    foo.printFoo();
	    foo.dummyFoo();
	  }

}
