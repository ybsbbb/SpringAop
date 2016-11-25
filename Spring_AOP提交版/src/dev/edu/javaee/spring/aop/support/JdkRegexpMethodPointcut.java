package dev.edu.javaee.spring.aop.support;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dev.edu.javaee.spring.aop.MethodMatcher;

public class JdkRegexpMethodPointcut extends StaticMethodMatcherPointcut implements MethodMatcher{

	private List<String> patterns = new LinkedList<>();
	
	private List<Pattern> compiledPatterns = new LinkedList<>();
	
	public void setPattern(String pattern) {
		setPatterns(pattern);
	}
	
	public void setPatterns(String... patterns)
	{
		this.patterns = Arrays.asList(patterns);
		this.compiledPatterns = getCompiledPattern();
	}
	
	private List<Pattern> getCompiledPattern()
	{
		List<Pattern> compiledPattern = new LinkedList<>();
		for(String s : patterns)
		{
			compiledPattern.add(Pattern.compile(s));
		}
		return compiledPattern;
	}
	
	@Override
	public boolean matches(Method method, Class<?> targetClass) {
		for(Pattern p : compiledPatterns)
		{
			Matcher matcher = p.matcher(method.getName());
			if(matcher.matches())
				return true;
		}
		return false;
	}

}
