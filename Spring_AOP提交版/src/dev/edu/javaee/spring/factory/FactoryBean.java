/**
 * 
 */
package dev.edu.javaee.spring.factory;

/**
 * @author yb775802151
 *
 */
public interface FactoryBean<T> {
	T getObject() throws Exception;
	Class<T> getObjectType();
	boolean isSingleton();
}
