package team.tjusw.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class FoodCacheGetGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {
    private static final String PATH_PATTERN = "foods/(\\d+)";

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String path = request.getURI().getPath();
            Pattern pattern = Pattern.compile(PATH_PATTERN);
            Matcher matcher = pattern.matcher(path);

            if (matcher.find()) {
                Integer foodId = Integer.valueOf(matcher.group(1));

                if (FoodCache.foodMap.containsKey(foodId)) {
                	if(CacheConfiguration.cache_log)
                		CacheConfiguration.log_cached("食品");
                	exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
                    return exchange.getResponse().writeWith(Mono.just(
                            exchange.getResponse().bufferFactory().wrap(
                                    FoodCache.foodMap.get(foodId).getBytes()
                            )
                    )).doOnNext(__ -> exchange.getResponse().setStatusCode(HttpStatus.OK));
                }
            }
            return chain.filter(exchange);
        };
    }
}
