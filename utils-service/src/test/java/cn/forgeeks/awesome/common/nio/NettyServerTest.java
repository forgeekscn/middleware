package cn.forgeeks.awesome.common.nio;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

import static org.junit.Assert.*;


@Slf4j
public class NettyServerTest {




	@Test
	public void testA(){
		SimpleServer server = new SimpleServer(9999);
		server.start();
	}


	@Test
	public void testB(){
		SimpleClient client = new SimpleClient();
		client.start("127.0.0.1",9999);

	}

}