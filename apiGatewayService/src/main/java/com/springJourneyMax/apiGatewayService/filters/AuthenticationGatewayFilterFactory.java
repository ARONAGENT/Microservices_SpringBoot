package com.springJourneyMax.apiGatewayService.filters;

import com.springJourneyMax.apiGatewayService.service.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthenticationGatewayFilterFactory extends AbstractGatewayFilterFactory<AuthenticationGatewayFilterFactory.Config> {


    private final JwtService jwtService;

    public AuthenticationGatewayFilterFactory(JwtService jwtService) {
        super(Config.class);
        this.jwtService=jwtService;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {

            String authenticationHeader=exchange.getRequest().getHeaders()
                    .getFirst("Authorization");
            if(authenticationHeader==null) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
            String token=authenticationHeader.split("Bearer ")[1];

            Long userId=jwtService.getUserIdFromToken(token);

            // Spring Boot 3.4.1
            ServerHttpRequest mutatedRequest=exchange.getRequest().mutate()
                    .header("X_User-Id",userId.toString())
                    .build();


            return chain.filter(exchange.mutate().request(mutatedRequest).build());
        };
    }

    public static class Config {

    }
}

/*
 * ❌ Pehla Code (Galat in Spring Boot 3.4.1):
 * exchange.getRequest()
 *         .mutate()
 *         .header("X-User-Id", userId.toString())
 *         .build();
 * return chain.filter(exchange);

 * ➡️ Isme tum request ke header change kar rahe ho, par tumne naya request object bana toh liya build() se, lekin usko use hi nahi kiya.
 * Tum purane exchange ke saath hi aage ka kaam kar rahe ho, jisme koi header add nahi hua tha.
 * 🧠 Spring 3.4.1 me ye kaam nahi karega, kyunki changes apply nahi ho rahe filter ke aage.

 * ✅ Doosra Code (Sahi Tarika in Spring Boot 3.4.1):

 * ServerHttpRequest mutatedRequest = exchange.getRequest()
 *         .mutate()
 *         .header("X-User-Id", userId.toString())
 *         .build();

 * return chain.filter(exchange.mutate().request(mutatedRequest).build());
 * ➡️ Isme tumne:

 * Request mutate karke ek naya request banaya with new header.
 * Us naye request ko exchange me set kiya using exchange.mutate().request(...)
 * Aur fir us naye exchange ko filter chain me pass kiya.
 * Is wajah se header downstream tak properly jaata hai, jaise X-User-Id.

 * 🔥 Simple Summary:
 * Version	Kya Zaroori Hai
 * Spring Boot 3.3.6	Tum directly .mutate().build() karke kaam chala sakte ho, bina naye request ko assign kiye
 * Spring Boot 3.4.1	Tumhe naye request ko save karke, exchange.mutate().request(...).build() me daalna zaroori hai

 * 🗣️ Layman Hinglish Line:
 * Naya request banaya par use kiya hi nahi toh change ka fayda kya?
 * Agar request mutate kiya hai toh usko aage bhi use karo, tabhi naye headers ya modifications ka asar dikhega.
 */
