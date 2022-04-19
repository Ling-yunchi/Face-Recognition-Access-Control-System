package com.face;

import com.face.service.UserService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;

@SpringBootTest
class FaceRecognitionApplicationTests {

    @Autowired
    UserService userService;

    @Test
    void contextLoads() {
    }

}
