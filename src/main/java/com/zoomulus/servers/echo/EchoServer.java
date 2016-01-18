package com.zoomulus.servers.echo;

import com.zoomulus.servers.BasicNettyServer;
import com.zoomulus.servers.ServerPort;

public class EchoServer extends BasicNettyServer
{
    public EchoServer()
    {
        super(EchoServerConnector.builder()
                .port(ServerPort.ZoomulusPort(ServerPort.PortNumber.ECHO))
                .build()
        );
    }
}
