package cn.forgeeks.service.common.controller;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;

/**
 * @program: common-demo
 * @description: es增删查改接口demo
 * @author: 01
 * @create: 2018-07-01 10:42
 **/
@RestController
@RequestMapping("/es/demo")
public class BookCrudController {

    @Autowired
    private TransportClient client;

    /**
     * 按id查询
     * @param id
     * @return
     */
    @GetMapping("/get/book/novel")
    public ResponseEntity searchById(@RequestParam("id") String id) {
        if (id.isEmpty()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        // 通过索引、类型、id向es进行查询数据
        GetResponse response = client.prepareGet("book", "novel", id).get();

        if (!response.isExists()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        // 返回查询到的数据
        return new ResponseEntity(response.getSource(), HttpStatus.OK);
    }

    /**
     * 添加书籍数据
     *
     * @param title       书籍标题
     * @param author      书籍作者
     * @param wordCount   书籍字数
     * @param publishDate 发行时间
     * @return
     */
    @PostMapping("/add/book/novel")
    public ResponseEntity add(@RequestParam("title") String title,
                              @RequestParam("author") String author,
                              @RequestParam("word_count") int wordCount,
                              @RequestParam("publish_date")
                              @DateTimeFormat(pattern = "yyy-MM-dd HH:mm:ss")
                                      Date publishDate) {
        try {
            // 将参数build成一个json对象
            XContentBuilder content = XContentFactory.jsonBuilder()
                    .startObject()
                    .field("title", title)
                    .field("author", author)
                    .field("word_count", wordCount)
                    .field("publish_date", publishDate.getTime())
                    .endObject();

            IndexResponse response = client.prepareIndex("book", "novel")
                    .setSource(content)
                    .get();

            return new ResponseEntity(response.getId(), HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




}