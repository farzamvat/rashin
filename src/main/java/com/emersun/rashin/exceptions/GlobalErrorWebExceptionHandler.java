package com.emersun.rashin.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

@Component
@Order(-2)
public class GlobalErrorWebExceptionHandler extends AbstractErrorWebExceptionHandler {
    private final static Logger logger = LoggerFactory.getLogger(GlobalErrorWebExceptionHandler.class);
    @Autowired
    private GlobalErrorAttributes globalErrorAttributes;

    public GlobalErrorWebExceptionHandler(GlobalErrorAttributes g, ApplicationContext applicationContext,
                                          ServerCodecConfigurer serverCodecConfigurer) {
        super(g, new ResourceProperties(), applicationContext);
        super.setMessageWriters(serverCodecConfigurer.getWriters());
        super.setMessageReaders(serverCodecConfigurer.getReaders());

    }


    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(),request -> renderErrorResponse(request,errorAttributes));
    }

    private Mono<ServerResponse>  renderErrorResponse(ServerRequest serverRequest,ErrorAttributes errorAttributes) {
        Throwable throwable = errorAttributes.getError(serverRequest);
        logger.error("Exception has thrown ",throwable);
        HttpStatus httpStatus;
        if(throwable instanceof UnauthorizedException)
            httpStatus = HttpStatus.UNAUTHORIZED;
        else if(throwable instanceof BadRequestException)
            httpStatus = HttpStatus.BAD_REQUEST;
        else if(throwable instanceof InternalServerException)
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        else
            httpStatus = HttpStatus.BAD_REQUEST;
        return ServerResponse.status(httpStatus)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(BodyInserters.fromObject(globalErrorAttributes.getErrorAttributes(serverRequest,false)));
    }
}
