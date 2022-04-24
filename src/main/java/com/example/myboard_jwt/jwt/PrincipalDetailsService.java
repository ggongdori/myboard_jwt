package com.example.myboard_jwt.jwt;

import com.example.myboard_jwt.entity.User;
import com.example.myboard_jwt.exception.CustomAuthenticationException;
import com.example.myboard_jwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.example.myboard_jwt.exception.ErrorConstant.LOGIN_ERROR;


//시큐리티 설정에서 loginProcessingUrl("/login")
//login요청이 오면 자동으로 UserDetailsService 타입으로 IoC 되어있는 loadUserByUsername 함수가 실행
@Service
@Slf4j
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    // 시큐리티 세션 => Authentication -> UserDetails
    // 시큐리티 SessionAuthentication(UserDetails)))
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomAuthenticationException("로그인 요청한 회원이 DB에 없다", LOGIN_ERROR));

        return new PrincipalDetails(user.getId(), user.getUsername(), user.getPw());
    }
}