package com.fadl.common;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.processor.IProcessor;

@Component
public class DictDialect extends AbstractDialect {

	private static final String PREFIX = "dict";

	@Override
	public String getPrefix() {
		return PREFIX;
	}
	
	@Override
	public Set<IProcessor> getProcessors() {
		final Set<IProcessor> processors = new HashSet<>();
		processors.add(new DictProcessor());
		return processors;
	}
}
