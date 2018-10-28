package cn.forgeeks.springcloud.elk.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/elk")
@RestController
public class ElkLogController {
    private final Logger logger = LoggerFactory.getLogger(ElkLogController.class);

    @GetMapping("/index")
    public Object index() {
        for (int i = 0; i < 100; i++) {
            logger.debug("debug" + i);
            logger.info("info" + i);
            logger.warn("warn" + i);
            logger.error("error" + i);
        }
        return "success";
    }
}