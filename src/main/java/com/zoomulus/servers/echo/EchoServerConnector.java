package com.zoomulus.servers.echo;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import lombok.Builder;
import lombok.Getter;

import com.zoomulus.servers.ServerConnector;

@Builder
public class EchoServerConnector implements ServerConnector
{
    @Getter
    int port;

    @Override
    public ChannelInitializer<?> getChannelInitializer()
    {
        return new ChannelInitializer<SocketChannel>(){
            @Override
            protected void initChannel(SocketChannel ch) throws Exception
            {
                ch.pipeline().addLast(new EchoHandler());
            }
        };
    }
}
