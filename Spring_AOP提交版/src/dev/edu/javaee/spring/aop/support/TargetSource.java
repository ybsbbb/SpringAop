package dev.edu.javaee.spring.aop.support;

public class TargetSource {
	private Object target;

	public TargetSource() {
	}

	public Object getTarget() {
		return target;
	}

	public void setTarget(Object target) {
		this.target = target;
	}
}