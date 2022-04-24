package com.example.myboard_jwt.jwt;

import com.example.myboard_jwt.dto.UserDto;
import com.example.myboard_jwt.exception.CustomAuthenticationException;
import com.example.myboard_jwt.exception.ExceptionUtil;
import com.example.myboard_jwt.exception.ResultMsg;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Result;
import java.io.IOException;

import static com.example.myboard_jwt.exception.ErrorConstant.LOGIN_ERROR;
import static com.example.myboard_jwt.jwt.JwtTokenUtils.*;

@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
        super.setFilterProcessesUrl("/api/login");
    }


// /login 요청이 오면 로그인 시도를 위해서 실행되는 함수

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("JWT AuthenticationFilter: 로그인 시도중");

        confirmDuplicateLogin(request);

        //1 get username, password
        try {
            UserDto.Session user = getMemberSessionFromRequest(request);

            if (!StringUtils.hasText(user.getUsername()) || !StringUtils.hasText(user.getPassword())) {
                throw new CustomAuthenticationException("아이디 혹은 비밀번호가 비어있습니다.", LOGIN_ERROR);
            }

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());

            //매니저에게 생성한 토큰으로 로그인 시도 -> PrincipalDetailsService의 loadUserBuUserName() 함수가 실행
            //로그인 정보를 authentication에 저장 --- authentication에 UserDetails가 담겨있다.
            Authentication authentication =
                    this.getAuthenticationManager().authenticate(usernamePasswordAuthenticationToken);

            //return시 authentication객체가 session에 저장된다.
            // -> 세션에 저장하는 이유는 권한관리를 시큐리티가 해주기 때문
            return authentication;

        } catch (IOException e) {
            throw new CustomAuthenticationException("IOException!!!!!", LOGIN_ERROR);
        }
    }


    //attemptAuthentication실행 후 인증이 정상적으로 되었으면 successfulAuthentication 실행된다
    //JWT토큰을 만들어서 response해주면 된다
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                              Authentication authResult) throws IOException, ServletException {
        PrincipalDetails principal = (PrincipalDetails) authResult.getPrincipal();

        //token에 id와 username 추가
        String jwtToken = createToken(principal);

        response.addHeader(TOKEN_HEADER_NAME, jwtToken);

        //response Body Msg추가
        ResultMsg success = new ResultMsg("success   " + jwtToken);
        ExceptionUtil.makeResponseInFilter(response, success);
    }


    // ======================================================================================== //

    private UserDto.Session getMemberSessionFromRequest(HttpServletRequest request) throws IOException {
        ObjectMapper om = new ObjectMapper();
        UserDto.Login loginMember = om.readValue(request.getInputStream(), UserDto.Login.class);
        UserDto.Session user= new UserDto.Session(loginMember);
        return user;
    }

    private void confirmDuplicateLogin(HttpServletRequest request) {
        String jwtHeader = request.getHeader(TOKEN_HEADER_NAME);
        if (jwtHeader != null && jwtHeader.startsWith(TOKEN_NAME_WITH_SPACE)) {
            String jwtToken = getTokenFromHeader(request);
            String username = JwtTokenUtils.verifyToken(jwtToken)
                    .getClaim(CLAIM_USERNAME)
                    .asString();

            if (StringUtils.hasText(username)) {
                throw new CustomAuthenticationException("이미 로그인되어있는 유저입니다.", LOGIN_ERROR);
            }
        }
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        super.unsuccessfulAuthentication(request, response, failed);
    }

}