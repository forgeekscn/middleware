package cn.forgeeks.springcloud.rabbitmq.common;

/**
 * Consts args config.
 */
public class Consts {
    public static final String EXCHANGE_MYTH_TOPIC = "EXCHANGE.MYTH.TOPIC";

    public static final String ROUTING_MYTH_LOG_INFO = "ROUTING.MYTH.LOG.INFO";
    public static final String ROUTING_MYTH_LOG_WARN = "ROUTING.MYTH.LOG.WARN";
    public static final String ROUTING_MYTH_LOG_ERROR = "ROUTING.MYTH.LOG.ERROR";
    public static final String ROUTING_MYTH_LOG_SIMPLE_DIRECT ="ROUTING.MYTH.LOG.SIMPLE.DIRECT" ;


    public static final String QUEUE_MYTH_LOG_ERROR ="QUEUE.MYTH.LOG.ERROR" ;
    public static final String QUEUE_MYTH_LOG_ALL ="QUEUE.MYTH.LOG.ALL" ;

    // Config binding rules.
    // All modules and all types of log.
    public static final String ROUTING_WITH_REGULER_MODULE_ALL_MYTH_LOG_ALL = "QUEUE.MYTH.LOG.#";
    // ALL modules and error log.
    public static final String ROUTING_WITH_REGULER_MODULE_ALL_MYTH_LOG_ERROR = "QUEUE.MYTH.LOG.ERROR";
    // Module MYTH with all logs.
    public static final String ROUTING_WITH_REGULER_MODULE_MYTH_LOG_ALL = "QUEUE.MYTH.LOG.#";

    // Job works take time.
    public static final long TIME_OF_WORKJOB = 5000;
}
