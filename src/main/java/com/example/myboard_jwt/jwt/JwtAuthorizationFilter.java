package com.example.myboard_jwt.jwt;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.myboard_jwt.exception.CustomAuthenticationException;
import com.example.myboard_jwt.exception.ErrorConstant;
import com.example.myboard_jwt.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.example.myboard_jwt.jwt.JwtTokenUtils.*;

@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private final UserRepository userRepository;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager,
                                  AuthenticationEntryPoint authenticationEntryPoint,
                                  UserRepository userRepository) {
        super(authenticationManager, authenticationEntryPoint);
        this.userRepository = userRepository;
    }

    //인증이나 권한이 필요하면 doFilterInternal 탄다.
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("인증 시도중...");
        try {
            String jwtHeader = request.getHeader(TOKEN_HEADER_NAME);

            //헤더가 있는지 확인
            if (jwtHeader == null || !jwtHeader.startsWith(TOKEN_NAME_WITH_SPACE)) {
                throw new CustomAuthenticationException("no header request", ErrorConstant.TOKEN_ERROR);
            }
            String jwtToken = getTokenFromHeader(request);

            DecodedJWT decodedJWT = verifyToken(jwtToken);

            PrincipalDetails principalDetails = new PrincipalDetails(
                    decodedJWT.getClaim(CLAIM_ID).asLong(),
                    ((Claim) decodedJWT.getClaim(CLAIM_USERNAME)).asString(),
                    null
            );

            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());

            //강제로 시큐리티의 세션에 접근하여 Authentication 객체를 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);

        }catch (CustomAuthenticationException e){
            request.setAttribute("custom", e);
        }
        catch (Exception e) {
            request.setAttribute("error", e);
        } finally { //에러가 발생시 authenticationentrypoint로
            chain.doFilter(request, response);
        }
    }
}