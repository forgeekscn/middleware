package cn.forgeeks.awesome.common.design;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.UUID;

@Slf4j
public class SingletonTemplate {

  private static MySource source = null;
  private static volatile MySource sourceC = null;

  private SingletonTemplate() {}

  public static synchronized MySource getInnstance() {

    if (source == null) {
      source = new MySource(UUID.randomUUID().toString(), new Date());
    }

    log.info("### {}", JSON.toJSONString(source));
    return source;
  }

  public static MySource getInstanceC() {
    if (sourceC == null) {
      synchronized (SingletonTemplate.class) {
        if (sourceC != null) {
          log.info("### {}", JSON.toJSONString(sourceC));
          return sourceC;
        } else {
          sourceC = new MySource(UUID.randomUUID().toString(), new Date());
          log.info("### {}", JSON.toJSONString(sourceC));
          return sourceC;
        }
      }
    }
    log.info("### {}", JSON.toJSONString(sourceC));
    return sourceC;
  }

  public static MySource getInstanceB() {
    MySource source = SingletonTemplateHandler.source;
    log.info("### {}", source);
    return source;
  }

  private static class SingletonTemplateHandler {
    static MySource source = new MySource(UUID.randomUUID().toString(), new Date());
  }
}
