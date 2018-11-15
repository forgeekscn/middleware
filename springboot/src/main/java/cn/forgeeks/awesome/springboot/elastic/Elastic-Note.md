
# Elasticsearch

- 电商搜索场景
    - 关注点:
        + java api
        + 分词搜索
        + 索引构建
        + 性能优化 
   
   - 需求分析库表设计
        - 数据量1000W+
        - 支持实时检索
        - 支持中文分词搜索
        - 持久化mysql
        - 库表: 商品,商铺,客户,订单
        
   - 业务关注点
        - 参考天猫搜索首页,实现基本搜索功能
      
   - 链接
       - 架构: https://juejin.im/post/5b63c79b6fb9a04fa01d5f39
       - rest api : https://blog.csdn.net/stark_summer/article/details/48830493
       - es语法：https://blog.csdn.net/laoyang360/article/details/77623013
    
    
- 脚本

   ```
        curl -XPUT "http://localhost:9200/tmall"
        
        curl -XDELETE "http://localhost:9200/tmall"
        
        curl -XPUT "http://localhost:9200/tmall/_mapping/product" -H 'Content-Type: application/json' -d'
        {
              "properties":{
                 "productId":{
                    "type":"long"
                 },
                 "productName":{
                    "type": "keyword" ,
                    "index": true,
                    "ignore_above": 256
                 },
                 "class":{
                    "type": "keyword" ,
                    "index": false,
                    "ignore_above": 64
                 },
                 "productCategory":{
                   "type":"keyword",
                   "index": true,
                   "ignore_above": 128
                 },
                 "productDesc":{
                    "type":"text",
                    "index": true
                 },
                 "productPrice":{
                    "type":"double"
                 },
                 "saleNumber":{
                   "type":"long"
                 }, 
                 "onSale":{
                    "type":"boolean"
                 },
                 "createTime":{
                    "type":"date",
                    "format": "yyyy-MM-dd HH:mm:ss"
                 }
              }
        }'
          
           
        curl -XPUT "http://localhost:9200/tmall/_mapping/customer" -H 'Content-Type: application/json' -d'
        {
              "properties":{
                 "customer_name":{
                    "type":"text"
                 },
                 "customer_desc":{
                    "type":"text"
                 },
                 "customer_address":{
                   "type":"text"
                 },
                 "customer_tags":{
                    "type":"text"
                 },
                 "product_age":{
                    "type":"long"
                 },
                 "bought_product":{
                   "type":"text"
                 },
                 "login_often":{
                   "type":"float"
                 },
                 "last_login_time":{
                    "type":"date",
                    "format": "yyyy-MM-dd HH:mm:ss"
                 }
              }
        }'
           
        curl -XPUT "http://localhost:9200/tmall/_mapping/order" -H 'Content-Type: application/json' -d'
        {
              "properties":{
                 "order_name":{
                    "type":"text"
                 },
                 "order_desc":{
                    "type":"text"
                 },
                 "product_name":{
                   "type":"text"
                 },
                 "product_id":{
                    "type":"text"
                 },
                 "product_price":{
                    "type":"double"
                 },
                 "product_number":{
                   "type":"long"
                 },
                 "total_money":{
                   "type":"double"
                 },
                 "customer_name":{
                    "type":"text"
                 },
                 "customer_id":{
                    "type":"text"
                 },
                 "create_time":{
                    "type":"date",
                    "format": "yyyy-MM-dd HH:mm:ss"
                 } 
              }
        }'
        
        curl -XPUT "http://localhost:9200/tmall/_mapping/store" -H 'Content-Type: application/json' -d'
        {
              "properties":{
                 "store_name":{
                    "type":"text" 
                 },
                 "store_desc":{
                    "type":"text"
                 },
                 "store_category":{
                   "type":"text"
                 },
                 "comment_score":{
                    "type":"double"
                 },
                 "create_time":{
                    "type":"date",
                    "format": "yyyy-MM-dd HH:mm:ss"
                 },
                 "update_time":{
                    "type":"date",
                    "format": "yyyy-MM-dd HH:mm:ss"
                 }
              }
        }'
         
        curl -XPOST "http://localhost:9200/tmall/product" -H 'Content-Type: application/json' -d'
        {
          "product_name":"绿米Aqara 智能卧室套装 两室一厅初版智能套装 可智能控制灯具 含网关+开关面板+插座+传感器 7件礼品套装",
          
          "product_desc":"11.12返场价699-叠加每满400减50； 满499再送无线开关mini升级版-需登记； 更多返场每满400减50》》Aqara智能卧室套装是以空调伴侣为中心，搭配6种智能家居的套装组合，可实现当卧室有人时，室内温度高自动打开空调调节室温、卧室五仁自动关空调。无线开关双控墙壁开关、晚上有人自动亮灯、无线开关控制墙壁插座、起夜自动亮夜灯等职能场景。",
          
          "product_category":"电工电料,开关插座,智能卧室套装",
          "product_price":1699,
          "on_sale":"true",
          "sale_number": 8700,
          "create_time":"2018-11-10 11:12:00",
          "update_time":"2018-11-12 18:12:00"
        }'
         
         
         
        curl -XGET "http://localhost:9200/tmall/_search"
        
        curl -XGET "http://localhost:9200/tmall/_mapping"
         
        curl -XGET "http://localhost:9200/tmall/_search" -H 'Content-Type: application/json' -d'
        {
           "query":{
              "bool":{
                 "must":[
                    {
                       "match":{
                          "product_name":"绿米 小米 智能门锁S1"
                       }
                    },
                    {
                       "match":{
                          "product_desc":"报警提示 霸王锁"
                       }
                    },
                     {
                       "match":{
                          "product_category":"家装建材"
                       }
                    }
                 ],
                 "filter":[
                    {
                       "term":{
                          "on_sale":"true"
                       }
                    },
                    {
                       "range":{
                          "update_time":{
                             "gte":"2018-11-10 00:00:00"
                          }
                       }
                    }
                 ]
              }
           }
        }'
         
        curl -XGET "http://localhost:9200/tmall/product/_search" -H 'Content-Type: application/json' -d'
        {
          "query": {
            "term": {
              "product_price": {
                "value": 1405.5
              }
            }
          }
        }'
         
        curl -XGET "http://localhost:9200/tmall/product/_search" -H 'Content-Type: application/json' -d'
        {
          "query": {
            "constant_score": {
              "filter": {
                "term": {
                  "product_name": "绿米Aqara 智能门锁 左开标准锁体 小米生态链企业 指纹锁 磁卡密"
                }
              },
              "boost": 1
            }
          }
        }'
        
         
        curl -XGET "http://localhost:9200/tmall/product/_search" -H 'Content-Type: application/json' -d'
        {
          "query": {
            "match": {
              "productDesc": "Aqara"
            }
          }
        }'
         
         
        curl -XGET "http://localhost:9200/tmall/product/_search" -H 'Content-Type: application/json' -d'
        {
          "query": {
            "bool": {
              "should": [
                {"match": {
                  "productDesc": "飞球 锁"
                }}
              ]
            }
          }
          , 
          "sort": [
            {
              "createTime": {"order": "desc" }
            },
            {
              "_score": { "order": "desc" }
            }
          ]
        }'
         
         
         
        curl -XGET "http://localhost:9200/tmall/_analyze?analyzer=standard&text=Aqara"
         
         
        curl -XPUT "http://localhost:9200/index_analys_test" -H 'Content-Type: application/json' -d'
        {
            "settings": {
                "analysis": {
                    "char_filter": {
                        "&_to_and": {
                            "type":       "mapping",
                            "mappings": [ "&=> and "]
                    }},
                    "filter": {
                        "my_stopwords": {
                            "type":       "stop",
                            "stopwords": [ "the", "a" ]
                    }},
                    "analyzer": {
                        "my_analyzer": {
                            "type":         "custom",
                            "char_filter":  [ "html_strip", "&_to_and" ],
                            "tokenizer":    "standard",
                            "filter":       [ "lowercase", "my_stopwords" ]
                    }}
        }}}'
         
        
        curl -XPOST "http://localhost:9200/index_analys_test/_analyze?analyzer=my_analyzer" -H 'Content-Type: application/json' -d'
        {
          "text":"The quick & brown 小写写HAHAHA a fox "
        }'
         
         
         
         
        curl -XGET "http://localhost:9200/tmall/product/_search" -H 'Content-Type: application/json' -d'
        {
          "query": {
            "terms": {
              "productName":["飞球( Fly.Globe) 门锁室内卧室房门锁静音锁具 FQ-A888"]
            }
          }
        }'
         
         
         
        curl -XGET "http://localhost:9200/tmall/product/_search" -H 'Content-Type: application/json' -d'
        {
          
          "query": {
            
            "bool": {
              "must": [
                {
                  "range": {
                    "createTime": {
                      "gte":"now-30d/d",
                      "lte": "now/d"
                      
                    }
                  }
                }
                ,{
                  "range": {
                    "saleNumber": {
                      "gt": 450
                      
                    }
                  }
                }
              ]
            }
          }
        }'
         
         
        
        curl -XGET "http://localhost:9200/tmall/product/_search" -H 'Content-Type: application/json' -d'
        {
          "query": {
            "multi_match": {
              "query": "",
              "type": "best_fields", 
              "fields": ["productDesc","productCategory^2","productName^3"]
              
            }
          }
        }'
         
        
        
        curl -XDELETE "http://localhost:9200/tmall"
        curl -XPUT "http://localhost:9200/tmall"
        
        curl -XGET "http://localhost:9200/tmall/_mapping"
        
        curl -XPUT "http://localhost:9200/tmall/_mapping/product" -H 'Content-Type: application/json' -d'
        {
              "properties":{
                 "productId":{
                    "type":"long"
                 },
                 "productName":{
                    "type": "text" ,
                   "analyzer": "ik_max_word",
                   "search_analyzer": "ik_smart"
                 },
                 "productCategory":{
                   "type":"text",
                   "analyzer": "ik_max_word",
                   "search_analyzer": "ik_smart"
                 },
                 "productDesc":{
                    "type":"text",
                   "analyzer": "ik_max_word",
                   "search_analyzer": "ik_smart"
                 },
                 "productPrice":{
                    "type":"double"
                 },
                 "saleNumber":{
                   "type":"long"
                 }, 
                 "onSale":{
                    "type":"boolean"
                 },
                 "productMaster" : {
                   "type": "keyword",
                    "ignore_above": 64
                 },
                 "productShopName" : {
                   "type": "keyword",
                    "ignore_above": 64
                 },
                 "productPic":{
                   "type": "keyword",
                   "ignore_above": 128
                 },
                 "class":{
                    "type": "keyword" ,
                    "ignore_above": 64
                 },
                 "createTime":{
                    "type":"date"
                 }
              }
        }'
          
          
          
        curl -XPOST "http://localhost:9200/tmall/_analyze" -H 'Content-Type: application/json' -d'
        {
          "analyzer":"ik_max_word",
          "text":"卡贝门锁室内卧室 雨花泽"
          
        }'
         
        curl -XGET "http://localhost:9200/tmall/_search"

    ``` 
    
    
    
    
    
    
    
    
- Elasticsearch是如何实现Master选举的？
 
- Elasticsearch中的节点（比如共20个），其中的10个选了一个master，另外10个选了另一个master，怎么办？

- 客户端在和集群连接时，如何选择特定的节点执行请求的？

- 详细描述一下Elasticsearch索引文档的过程?

- 详细描述一下Elasticsearch更新和删除文档的过程?

- 详细描述一下Elasticsearch搜索的过程?

- 在Elasticsearch中，是怎么根据一个词找到对应的倒排索引的？

- Elasticsearch在部署时，对Linux的设置有哪些优化方法？

- 对于GC方面，在使用Elasticsearch时要注意什么？

- Elasticsearch对于大数据量（上亿量级）的聚合如何实现？

- 在并发情况下，Elasticsearch如果保证读写一致？

- 如何监控Elasticsearch集群状态？

- 介绍下你们电商搜索的整体技术架构?

- 是否了解字典树？

- 拼写纠错是如何实现的？



#### Linked Url
- https://blog.csdn.net/u012516166/article/details/76643882 
- https://blog.csdn.net/sang1203/article/details/52415910