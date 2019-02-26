package cn.forgeeks.awesome.springboot.rabbitmq.common;

/**
 * Consts args config.
 */
public class Consts {
    // 主题
    public static final String EXCHANGE_MYTH_TOPIC = "EXCHANGE.MYTH.TOPIC";
    public static final String EXCHANGE_DIRECT ="EXCHANGE.DIRECT" ;
    public static final String EXCHANGE_FANOUT ="EXCHANGE.FANOUT" ;

    // 日志队列
    public static final String QUEUE_MYTH_LOG_INFO ="QUEUE.MYTH.LOG.INFO" ;
    public static final String QUEUE_MYTH_LOG_ERROR ="QUEUE.MYTH.LOG.ERROR" ;
    public static final String QUEUE_MYTH_LOG_ALL ="QUEUE.MYTH.LOG.ALL" ;
    // 邮件队列
    public static final String QUEUE_MYTH_MAIL_ALL ="QUEUE.MYTH.MAIL.ALL" ;
    // 订单秒杀队列
    public static final String QUEUE_MYTH_ORDER_ALL = "QUEUE.MYTH.ORDER.ALL";
    // 构建direct模式专用的队列
    public static final String  QUEUE_DIRECT_MSG = "QUEUE.DIRECT.MSG";
    // 构建广播队列
    public static final String QUEUE_FANOUT_A = "QUEUE.FANOUT.A";
    public static final String QUEUE_FANOUT_B ="QUEUE.FANOUT.B" ;

    // 路由
    public static final String ROUTING_MYTH_LOG_INFO = "ROUTING.MYTH.LOG.INFO";
    public static final String ROUTING_MYTH_LOG_WARN = "ROUTING.MYTH.LOG.WARN";
    public static final String ROUTING_MYTH_LOG_ERROR = "ROUTING.MYTH.LOG.ERROR";
    public static final String ROUTING_MYTH_LOG_SIMPLE_DIRECT ="ROUTING.MYTH.LOG.SIMPLE.DIRECT" ;
    public static final String ROUTING_WITH_DIRECT_QUEUE_AND_EXCHANGE = "ROUTING.WITH.DIRECT.QUEUE.AND.EXCHANGE";
    // 邮件路由
    public static final String ROUTING_WITH_REGULER_MODULE_ALL_MYTH_MAIL= "QUEUE.MYTH.MAIL.#";
    // All modules and all types of log.
    public static final String ROUTING_WITH_REGULER_MODULE_ALL_MYTH_LOG_ALL = "QUEUE.MYTH.LOG.#";
    // ALL modules and error log.
    public static final String ROUTING_WITH_REGULER_MODULE_ALL_MYTH_LOG_ERROR = "QUEUE.MYTH.LOG.ERROR";
    public static final String ROUTING_WITH_REGULER_MODULE_ALL_MYTH_LOG_INFO= "QUEUE.MYTH.LOG.INFO";
    public static final String ROUTING_WITH_REGULER_MODULE_ALL_MYTH_ORDER_ALL= "QUEUE.MYTH.ORDER.#";

    // Job works take time.
    public static final long TIME_OF_WORKJOB = 5000;
    public static final String ROUTING_WITH_FANOUT_QUEUE ="ROUTING.WITH.FANOUT.QUEUE" ;
}
