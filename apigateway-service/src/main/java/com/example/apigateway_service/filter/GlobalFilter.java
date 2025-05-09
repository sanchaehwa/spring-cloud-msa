package com.example.apigateway_service.filter;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j

public class GlobalFilter extends AbstractGatewayFilterFactory<GlobalFilter.Config> {

    public GlobalFilter() { //설정 클래스 등록
        super(Config.class);
    }

    @Override //실제 필터 로직을 구현하는 매서드
    public GatewayFilter apply(Config config) {
        //custom pre filter

        return (exchange, chain) -> { //chain은 다음 필터로 넘기기위해 사용
            //요청 객체
            ServerHttpRequest request = exchange.getRequest();
            //응답 객체
            ServerHttpResponse response = exchange.getResponse();

            //Filter Logging 작업

            log.info("Global Filter baseMassage{}",config.getBaseMessage()); //GlobalFilter는 공통처리
            //요청
            if (config.isPreLogger()) {
                log.info("Global Filter Start: request id -> {}",request.getId()); //GlobalFilter는 공통처리
            }

            //Custom Post Filter
            return chain.filter(exchange).then(Mono.fromRunnable(() -> { //비동기 방식의 서버를 지원할 때, 단일값 전달할때, 모노
                //응답
                if (config.isPostLogger()) {
                    log.info("Global Filter End: response id -> {}",response.getStatusCode()); //GlobalFilter는 공통처리
                }
            }));
        };
    }

    @Data
    public static class Config {
        //Put the configuration properties
        private String baseMessage;
        private boolean preLogger;
        private boolean postLogger;
    }
}
