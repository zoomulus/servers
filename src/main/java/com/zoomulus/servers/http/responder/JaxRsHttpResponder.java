package com.zoomulus.servers.http.responder;

import com.google.common.collect.Sets;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.List;
import java.util.Set;

public class JaxRsHttpResponder extends HttpResponder
{
    private static Logger log = LoggerFactory.getLogger(JaxRsHttpResponder.class);

    private final Set<Class<?>> responders = Sets.newConcurrentHashSet();
    
    @Inject
    public JaxRsHttpResponder(List <Class<?>> responders)
    {
        for (final Class<?> responder : responders)
        {
            addResponderIfJaxRS(responder);
        }
    }
    
    private void addResponderIfJaxRS(final Class<?> responder)
    {
        log.debug("Checking whether class {} is a JaxRS resource", responder.getName());
//        if (null != responder.getAnnotation(Path.class))
//        {
//            log.debug("Found JaxRS resource {}", responder.getName());
//            responders.add(responder);
//        }
    }    

    @Override
    protected boolean haveMatchingResource(HttpRequest request)
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    protected boolean isSupportedMethod(HttpRequest request)
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    protected FullHttpResponse generateResponse(HttpRequest request,
            HttpHeaders headers)
    {
        // TODO Auto-generated method stub
        return null;
    }

}
