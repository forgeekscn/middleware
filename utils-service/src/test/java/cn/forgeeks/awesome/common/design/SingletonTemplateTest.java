package cn.forgeeks.awesome.common.design;

import cn.forgeeks.awesome.common.thread.SpringThreadUtil;
import org.junit.Test;


public class SingletonTemplateTest {


	@Test
	public void testA(){

//		SpringThreadUtil.submitTasksFor100Times(()->SingletonTemplate.getInstanceB());
		SpringThreadUtil.submitTasksFor100Times(()->SingletonTemplate.getInstanceC());

	}


}