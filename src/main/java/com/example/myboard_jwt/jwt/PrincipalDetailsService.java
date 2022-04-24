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

@Service
@Slf4j
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomAuthenticationException("로그인 요청한 회원이 DB에 없다", LOGIN_ERROR));

        return new PrincipalDetails(user.getId(), user.getUsername(), user.getPw());
    }
}