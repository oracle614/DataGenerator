package utils;


import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang3.StringUtils;

import generator.BooleanGenerator;
import generator.ByteGenerator;
import generator.DateGenerator;
import generator.DoubleGenerator;
import generator.FloatGenerator;
import generator.IntegerGenerator;
import generator.LongGenerator;
import generator.StringGenerator;


/**
 * 成成器配置
 * @author Administrator
 *
 */
public class GenerateConfig implements IGenerateConfig{
	
	private int generateCount;
	private boolean isOpenMessageTip;
	private Map<String,Generator<?>> classToGenerators = new HashMap<>();
	private Map<String,Generator<?>> fieldNameToGenerators = new HashMap<>();

	public static GenerateConfig DEFAULT_CONFIG = new GenerateConfig();
	
	public GenerateConfig() {
		this.generateCount=10;
		setDefaultGenerators();
	}
	public GenerateConfig(Integer generateCount) {
		this.generateCount = generateCount;
		setDefaultGenerators();
	}

	private void setDefaultGenerators() {
		isOpenMessageTip =false;
		putGenerator(new StringGenerator(10));
		putGenerator(new IntegerGenerator(3000,8000));
		putGenerator(new LongGenerator(100000,30000000));
		putGenerator(new FloatGenerator(200.3f,3000.8f));
		putGenerator(new DoubleGenerator(2000000.5,3000000.7));
		putGenerator(new BooleanGenerator(20,80));
		putGenerator(new DateGenerator());
		putGenerator(new ByteGenerator());
	}

	int getGenerateCount() {
		return generateCount;
	}
	
	Generator<?> getObjectGenerator(Class<?> classType, String fieldName){
		
		Class<?> wrapperClassType = ClassUtils.primitiveToWrapper(classType);
		
		if (StringUtils.isEmpty(fieldName)) {
			return classToGenerators.get(wrapperClassType.getName());
		}
		
		String fieldKey = fieldName+"_"+wrapperClassType.getName();
		Generator<?> generator = fieldNameToGenerators.get(fieldKey);
		if (Objects.nonNull(generator)) {
			return generator;
		}else {
			return classToGenerators.get(wrapperClassType.getName());
		}
	}
	
	boolean isOpenMessageTip() {
		return isOpenMessageTip;
	}
	
	
	@Override
	public void setGenerateCount(Integer generateCount) {
		this.generateCount = generateCount;
	}
	
	@Override
	public void putGenerator(Generator<?> objectGenerator){
		classToGenerators.put(objectGenerator.getClassType().getName(), objectGenerator);
	}
	
	@Override
	public void putGenerator(String fieldName,Generator<?> objectGenerator){
		String fieldKey = fieldName+"_"+objectGenerator.getClassType().getName();
		fieldNameToGenerators.put(fieldKey, objectGenerator);
	}
	
	@Override
	public void setOpenMessageTip(boolean isOpenMessageTip) {
		this.isOpenMessageTip = isOpenMessageTip;
	}
	
}