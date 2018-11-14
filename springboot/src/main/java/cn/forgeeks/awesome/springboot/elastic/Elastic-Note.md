
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