package cn.forgeeks.awesome.common.design;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import static org.junit.Assert.*;



@Slf4j
public class AbsractFactoryTemplateTest {



	@Test
	public void testA(){
		AbsractFactoryTemplate.getCar("auto").run();
		AbsractFactoryTemplate.getCar("tesla").run();
		AbsractFactoryTemplate.getCar("toyota").run();
	}

}