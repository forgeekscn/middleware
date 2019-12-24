package cn.forgeeks.awesome.common.nio;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;


@Slf4j
public class MyClientHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		while(true) {
			log.info("SimpleClientHandler.channelRead");
			ByteBuf result = (ByteBuf) msg;
			byte[] result1 = new byte[result.readableBytes()];
			if(result.isReadable()) {
				result.readBytes(result1);
				log.info("Server said: {} ", new String(result1));
				result.release();
			}
			LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(1L));
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		// 当出现异常就关闭连接
		cause.printStackTrace();
		ctx.close();
	}


	// 连接成功后，向server发送消息
	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		int i=10000;
		while(i-->0) {
			new Thread(() -> {
				log.info("### 连接成功后，向server发送消息");
				String msg = "hello Server!";
				ByteBuf encoded = ctx.alloc().buffer(4 * msg.length());
				encoded.writeBytes(msg.getBytes());
				ctx.write(encoded);
				ctx.flush();
				LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(1L));
			}).start();
		}
	}
}  