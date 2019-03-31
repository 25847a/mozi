package com.fadl.common;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.dom.Node;
import org.thymeleaf.dom.Text;
import org.thymeleaf.processor.element.AbstractMarkupSubstitutionElementProcessor;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.fadl.SpringContextHolder;
import com.fadl.common.dao.ConfigMapper;
import com.fadl.common.entity.Config;

@Component
public class DictProcessor extends AbstractMarkupSubstitutionElementProcessor {
	
	private static final String TAG_NAME = "getDictLable";// 标签名
	private static final int PRECEDENCE = 10000;// 优先级
	
	public DictProcessor() {
		super(TAG_NAME);
	}

	@Override
	protected List<Node> getMarkupSubstitutes(Arguments arguments, Element element) {
		final String type = element.getAttributeValue("type");
		final String value = element.getAttributeValue("value");
		Wrapper<Config> wr = new EntityWrapper<Config>();
		wr.eq("type", type);
		wr.eq("value", value);
		List<Config> lst = SpringContextHolder.getApplicationContext().getBean(ConfigMapper.class).selectList(wr);
		String logoimgText = null;
		if (lst.size() > 0) {
			logoimgText = lst.get(0).getLable();
		}
		final Element container = new Element("div");
		final Text text = new Text(logoimgText);
		container.addChild(text);
		final List<Node> nodes = new ArrayList<>();
		nodes.add(container);
		return nodes;
	}

	@Override
	public int getPrecedence() {
		return PRECEDENCE;
	}

	

}
