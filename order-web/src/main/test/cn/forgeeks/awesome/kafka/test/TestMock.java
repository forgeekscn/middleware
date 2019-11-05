package cn.forgeeks.awesome.kafka.test;

import cn.forgeeks.awesome.order.RedisApp;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@SpringBootTest( webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT , classes = RedisApp.class)
@RunWith(SpringRunner.class)
@ActiveProfiles("work")
@Slf4j
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


