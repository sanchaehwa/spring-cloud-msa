package com.example.apigateway_service.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest; //reactive - 비동기
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
/*
###

1. Spring Cloud Gateway : WebFlux 기반 , 비동기 + 논블로킹으로 동작함.
2. Spring Cloud Gateway - Filter를 구현할떄, Blocking 동기 방식으로 쓰면 성능 저하
3. Gateway - Route 목록을 봐서 매핑을 수행하는데 매핑하는 과정에서 요청, 응답을 가로챔으로써 로깅을 찍든, 인증을 수행하든, 무언가를 처리하는 역할이기때문에, Custom FIlter 를 구현할때,
Reactive  프로그래밍 방식으로 작성.
4. Custom Filter 구현을 위해 `AbstractGatewayFilterFactory` 를 상속받음.
 */

public class CustomFilter extends AbstractGatewayFilterFactory<CustomFilter.Config> {

    public CustomFilter() { //설정 클래스 등록
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
            log.info("Custom PRE filter: request id -? {}",request.getId());

            //Custom Post Filter
            return chain.filter(exchange).then(Mono.fromRunnable(() -> { //비동기 방식의 서버를 지원할 때, 단일값 전달할때, 모노
                log.info("Custom POST filter: response code -> {}",response.getStatusCode());
            }));
        };
    }

    public static class Config {
        //Put the configuration properties

    }
}
