package dev.edu.javaee.spring.factory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import dev.edu.javaee.spring.bean.BeanDefinition;
import dev.edu.javaee.spring.bean.BeanUtil;
import dev.edu.javaee.spring.bean.PropertyValue;
import dev.edu.javaee.spring.bean.PropertyValues;
import dev.edu.javaee.spring.resource.Resource;

public class XMLBeanFactory extends AbstractBeanFactory{
	
	public XMLBeanFactory(Resource resource)
	{
		try {
			//类与其依赖的映射
			Map<String,List<String>> refMap = new HashMap<String,List<String>>();
			Map<String,Object> beanNameMap = new HashMap<String,Object>();
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
			Document document = dbBuilder.parse(resource.getInputStream());
            NodeList beanList = document.getElementsByTagName("bean");
            for(int i = 0 ; i < beanList.getLength(); i++)
            {
            	Node bean = beanList.item(i);
            	BeanDefinition beandef = new BeanDefinition();
            	String beanClassName = bean.getAttributes().getNamedItem("class").getNodeValue();
            	String beanName = bean.getAttributes().getNamedItem("id").getNodeValue();
            	
        		beandef.setBeanClassName(beanClassName);
        		
        		//
        		List<String> refs = new ArrayList<String>();
        		refMap.put(beanName, refs);
        		
        		
				try {
					Class<?> beanClass = Class.forName(beanClassName);
					beandef.setBeanClass(beanClass);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
        		
        		PropertyValues propertyValues = new PropertyValues();
        		
        		NodeList propertyList = bean.getChildNodes();
            	for(int j = 0 ; j < propertyList.getLength(); j++)
            	{
            		Node property = propertyList.item(j);
            		if (property instanceof Element) {
        				Element ele = (Element) property;
        				
        				String name = ele.getAttribute("name");
        				Class<?> type;
						try {
							type = beandef.getBeanClass().getDeclaredField(name).getType();
							Object value = ele.getAttribute("value");
	        				
							if(value != null && !value.equals("")){
		        				if(type == Integer.class){
		        					value = Integer.parseInt((String) value);
		        				}		        				
		        				propertyValues.AddPropertyValue(new PropertyValue(
		        						name,value));
							} else {
								String ref = ele.getAttribute("ref");
								if(ref !=null && !ref.equals("")){
									refs.add(name);
									refs.add(ref);
								} else {
									NodeList valueList = ele.getChildNodes();
									for(int k = 0 ; k < valueList.getLength() ; k++){
										Node val = valueList.item(k);
										if(val instanceof Element){
											if(val.getNodeName().equals("value")){
												propertyValues.AddPropertyValue(new PropertyValue(
														name, val.getTextContent()));
											} else {
												if(val.getNodeName().equals("list")){
													NodeList valList2 = val.getChildNodes();
													List<String> valList3 = new ArrayList<String>();
													for(int l = 0; l < valList2.getLength(); l++){
														Node val2 = valList2.item(l);
														if(val2 instanceof Element){
															if(val2.getNodeName().equals("value")){
																valList3.add(val2.getTextContent());
															}
														}
													}
													//临时版本，只为作业
													propertyValues.AddPropertyValue(new PropertyValue(
															name, valList3.get(0)));
												} else if(val.getNodeName().equals("ref")){
													String refid = ((Element) val).getAttribute("bean");
													if(refid==null || refid.equals("")){
														refid = ((Element) val).getAttribute("local");
													}
													refs.add(name);
													refs.add(refid);
												}
											}
										}
									}
								}
							}
						} catch (NoSuchFieldException e) {
							e.printStackTrace();
						} catch (SecurityException e) {
							e.printStackTrace();
						}
        			}
            	}
            	beandef.setPropertyValues(propertyValues);
            	
            	//this.registerBeanDefinition(beanName, beandef);
            	this.registerBeanDefinition(beanName, beandef,beanNameMap);
            }
            
            Set<String> keys = refMap.keySet();
            Iterator<String> it = keys.iterator();
            while(it.hasNext()){
            	String key = it.next();
            	List<String> refs = refMap.get(key);
            	if(!refs.isEmpty()){
                	Iterator<String> refit = refs.iterator();
                	Object bean = beanNameMap.get(key);
                	while(refit.hasNext()){
                		String name = refit.next();
                		String refid = refit.next();
                		BeanUtil.invokeSetterMethod(bean, name, this.getBean(refid));
                	}            		
            	}
            }
		} catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
		
	}

	@Override
	protected BeanDefinition GetCreatedBean(BeanDefinition beanDefinition,String beanName,Map<String,Object> map) {
		
		try {
			// set BeanClass for BeanDefinition
			
			Class<?> beanClass = beanDefinition.getBeanClass();
			// set Bean Instance for BeanDefinition
			Object bean = beanClass.newInstance();	
			map.put(beanName, bean);
			
			List<PropertyValue> fieldDefinitionList = beanDefinition.getPropertyValues().GetPropertyValues();
			for(PropertyValue propertyValue: fieldDefinitionList)
			{
				BeanUtil.invokeSetterMethod(bean, propertyValue.getName(), propertyValue.getValue());
			}
			
			beanDefinition.setBean(bean);
			
			return beanDefinition;
			
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	
	@Override
	protected BeanDefinition GetCreatedBean(BeanDefinition beanDefinition) {
		
		try {
			// set BeanClass for BeanDefinition
			
			Class<?> beanClass = beanDefinition.getBeanClass();
			// set Bean Instance for BeanDefinition
			Object bean = beanClass.newInstance();	
			
			List<PropertyValue> fieldDefinitionList = beanDefinition.getPropertyValues().GetPropertyValues();
			for(PropertyValue propertyValue: fieldDefinitionList)
			{
				BeanUtil.invokeSetterMethod(bean, propertyValue.getName(), propertyValue.getValue());
			}
			
			beanDefinition.setBean(bean);
			
			return beanDefinition;
			
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
