package cn.forgeeks.awesome.mq.common.config;

/**
 * MqConsts args config.
 */
public class MqConsts {
    // 交换机
    public static final String EXCHANGE_MYTH_TOPIC = "EXCHANGE.MYTH.TOPIC";
    public static final String EXCHANGE_DIRECT ="EXCHANGE.DIRECT" ;
    public static final String EXCHANGE_FANOUT ="EXCHANGE.FANOUT" ;
    public static final String EXCHANGE_DIRECT_ORDER_DEADLETTER ="EXCHANGE.DIRECT_ORDER_DEADLETTER" ;

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

    // 订单服务队列
    public static final String  QUEUE_NOMAL_ORDER = "QUEUE.QUEUE_NOMAL_ORDER";
    public static final String  QUEUE_DEADLETTER_ORDER = "QUEUE.QUEUE_DEADLETTER_ORDER";

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

    public static final String ROUTING_ORDER_NOMAL = "QUEUE.QUEUE_NOMAL_ORDER" ;
    public static final String ROUTING_ORDER_DEADLETTER = "QUEUE.QUEUE_DEADLETTER_ORDER" ;

    // Job works take time.
    public static final long TIME_OF_WORKJOB = 5000;
    public static final String ROUTING_WITH_FANOUT_QUEUE ="ROUTING.WITH.FANOUT.QUEUE" ;


    // 延时消息配置
    public static final String EXCHANGE_DEADLETTER = "EXCHANGE_DEADLETTER";
    public static final String QUEUE_DEADLETTER_PROCESS = "QUEUE_DEADLETTER_PROCESS";
    public static final String QUEUE_DEADLETTER_RECEIVER = "QUEUE_DEADLETTER_RECEIVER";


}
