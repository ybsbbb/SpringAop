package test.edu.javaee.spring.aop;

public class FooImpl implements FooInterface {

	@Override
	public void printFoo() {
		System.out.println("In FooImpl.printFoo");
	}

	@Override
	public void dummyFoo() {
		 System.out.println("In FooImpl.dummyFoo");		
	}

}
