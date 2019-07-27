package cn.forgeeks.test.service;

import cn.forgeeks.service.common.MainApplication;
import cn.forgeeks.service.common.controller.LogSpyController;
import cn.forgeeks.service.common.service.OrderService;
import com.alibaba.fastjson.JSONObject;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;



@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TestMock {

//    @InjectMocks
//    BookServiceImpl bookService;
//    @Mock
//    OrderService orderService;

    @Autowired
    private MockMvc mockMvc;


//    @Autowired
//    private WebApplicationContext webApplicationContext;
//
//
//    @Before
//    public void setup() {
//        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
//    }

    //验证controller是否正常响应并打印返回结果
    @Test
    public void getHello() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .post("/rabbit/createOrder")
                .accept(MediaType.APPLICATION_JSON)
                .param("dayNum","8")
                .param("date","2017-7-18 00:00:00")
                .param("pageNum","1")
                .param("pageSize","10"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        System.out.println("输出 " + mvcResult.getResponse().getContentAsString());
    }

}


