//package com.example.myboard_jwt.config.oauth;
//
//import com.example.myboard_jwt.entity.User;
//import com.example.myboard_jwt.jwt.PrincipalDetails;
//import com.example.myboard_jwt.repository.UserRepository;
//import lombok.Builder;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Required;
//import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
//import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//import org.springframework.stereotype.Service;
//
//import javax.servlet.http.HttpSession;
//import java.util.Optional;
//
//@RequiredArgsConstructor
//@Service
//public class PrincipalOauth2UserService extends DefaultOAuth2UserService {
//
//    private final UserRepository userRepository;
////    private final HttpSession HttpSession;
//    //스프링 시큐리티
//    //세션 안에 스프링시큐리티가 관리하는  세션 이 안에 들어갈 수 있는 타입
//    // only Authentication type
//    // authentication type 안에 들어갈 수 있는 타입
//    //1. UserDetails(일반적인 로그인), 2. OAuth2User(소셜 로그인) types
//    //따라서 둘 다 받는 PrincipalDetails를 구현하면 됨
//    //구글로부터 받은 userRequest 데이터 후처리 하는 함수
//    @Override
//    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
////        System.out.println("userRequest: " + userRequest);
//
//        OAuth2User oAuth2User = super.loadUser(userRequest);
//
////        String provider = userRequest.getClientRegistration().getClientId();
////        String providerId = oAuth2User.getAttribute("sub");
//        String nickname = oAuth2User.getAttribute("nickname");
////        String password =
//        String username = oAuth2User.getAttribute("email");
//
//
//        User userEntity = userRepository.findByUsername(username).orElse(null);
//
//        if (username == null) {
//            userEntity = new User(username, null, nickname);
//            userRepository.save(userEntity);
//        }
//        return new PrincipalDetails(userEntity, oAuth2User.getAttributes());
//    }
//}
