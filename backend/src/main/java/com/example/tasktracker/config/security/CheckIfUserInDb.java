package com.example.tasktracker.config.security;

import com.example.tasktracker.model.Users;
import com.example.tasktracker.repositories.UsersRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Component
public class CheckIfUserInDb extends OncePerRequestFilter {

    private UsersRepository usersRepository;
    private WebClient webClient;

    @Value("${AUTH0_DOMAIN}")
    private String AUTH0_DOMAIN;

    public CheckIfUserInDb(UsersRepository usersRepository, WebClient webClient) {
        this.usersRepository = usersRepository;
        this.webClient = webClient;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        var auth = SecurityContextHolder.getContext().getAuthentication();

        if(auth != null && auth.getPrincipal() instanceof Jwt jwt){
            String sub = jwt.getClaim("sub");

            Optional<Users> optionalUser = usersRepository.findUsersByAuth0Id(sub);
            if(optionalUser.isEmpty()){
                try{
                    Map<String, Object> userInfo = webClient.get()
                            .uri(AUTH0_DOMAIN + "/userinfo")
                            .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwt.getTokenValue())
                            .retrieve()
                            .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                            .block();
                    System.out.println(userInfo);
                    Users user = new Users();
                    user.setAuth0Id(sub);
                    user.setEmail(userInfo.get("email").toString());
                    user.setUsername(userInfo.get("name").toString());

                    usersRepository.save(user);
                } catch (Exception e){
                    throw new ServletException(e);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
