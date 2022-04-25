package com.example.myboard_jwt.controller;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;


/*
통합테스트(모든 빈들을 똑같이 Ioc에 올리고 테스트
WebEnvironment.mock = 실제 톰캣 대신 다른 톰캣으로 테스트
WebEnvironment.Randomport = 실제 톰캣 테스트
@AutoConfigureMockMvc = Mockmvc를 ioc에 등록해줌
@Transactional = 각각의 테스트 함수가 종료될 때마다 트랜잭션을 roll back, 각 테스트 메소드 종료될 때 롤백

 */

@Transactional
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class PostControllerTest {

}