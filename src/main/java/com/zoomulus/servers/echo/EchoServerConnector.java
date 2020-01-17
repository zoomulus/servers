package com.zoomulus.servers.echo;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

import com.zoomulus.servers.ServerConnector;

public class EchoServerConnector implements ServerConnector
{
    int port;

    public int getPort() {
        return port;
    }

    private EchoServerConnector(int port) {
        this.port = port;
    }

    static EchoServerConnectorBuilder builder() {
        return new EchoServerConnectorBuilder();
    }

    public static class EchoServerConnectorBuilder {
        int port;

        public EchoServerConnectorBuilder withPort(int port) {
            this.port = port;
            return this;
        }

        public EchoServerConnector build() {
            return new EchoServerConnector(port);
        }
    }

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
