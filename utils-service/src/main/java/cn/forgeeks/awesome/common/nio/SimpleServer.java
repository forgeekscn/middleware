package cn.forgeeks.awesome.common.nio;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SimpleServer {
	private int port;

	public SimpleServer(int port) {
		this.port = port;
	}

	public static void main(String[] args) throws Exception {
		new SimpleServer(9999).run();
	}

	public void run() throws Exception {
		// EventLoopGroup是用来处理IO操作的多线程事件循环器
		// bossGroup 用来接收进来的连接
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		// workerGroup 用来处理已经被接收的连接
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			// 启动 NIO 服务的辅助启动类
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup)
				// 配置 Channel
				.channel(NioServerSocketChannel.class)
				.childHandler(
					new ChannelInitializer<SocketChannel>() {
						@Override
						public void initChannel(SocketChannel ch) throws Exception {
							// 注册handler
							ch.pipeline().addLast(new SimpleServerHandler());
						}
					})
				.option(ChannelOption.SO_BACKLOG, 128)
				.childOption(ChannelOption.SO_KEEPALIVE, true);


			log.info("### server started");


			// 绑定端口，开始接收进来的连接
			ChannelFuture f = b.bind(port).sync();
			// 等待服务器 socket 关闭 。
			f.channel().closeFuture().sync();
		} finally {
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
	}

	public  void start() {
		try {
			run();
		} catch (Exception e) {
			log.error("### server error", e);
		}
	}
}
