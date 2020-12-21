package com.dj.iotlite.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class Main {

    private int port;

    public static void main(String[] argv) throws Exception{

        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();
//                            p.addLast(new LoggingHandler());
                            p.addLast(new HttpRequestDecoder());
                            p.addLast(new HttpResponseEncoder());
                            p.addLast(new HttpServerHandler());


                            p.addLast(new GatewayOutHandler());
                            p.addLast(new ProductOutHandler());
                            p.addLast(new DeviceOutHandler());

                            p.addLast(new GatewayInHandler());
                            p.addLast(new ProductInHandler());
                            p.addLast(new DeviceInHandler());

                        }
                    });

            ChannelFuture f = b.bind(29001)
                    .sync();
            f.channel()
                    .closeFuture()
                    .sync();

        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
