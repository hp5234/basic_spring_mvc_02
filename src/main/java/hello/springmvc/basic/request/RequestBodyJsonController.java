package hello.springmvc.basic.request;


import com.fasterxml.jackson.databind.ObjectMapper;
import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 요청데이터
 * {"username":"hello", "age":20}
 * content-type: application/json
 */

@Slf4j
@Controller
public class RequestBodyJsonController {

    private ObjectMapper objectMapper = new ObjectMapper();

    // V1. InputStream 를 사용한 처리
    @PostMapping("/request-body-json-v1")
    public void requestBodyJsonV1(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // InputStream 을 사용한 Http messagebody 획득
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messageBody={}", messageBody);

        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());

        response.getWriter().write("ok");
    }

    // V2. @RequestBody 를 사용한 처리
    @ResponseBody // 응답을 위한 ResponseBody
    @PostMapping("/request-body-json-v2")
    public String requestBodyJsonV2(@RequestBody String messageBody ) throws IOException {

        log.info("messageBody={}", messageBody);

        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());

        return "ok";
    }

    // V3. @RequestBody 를 사용하여 바로 객체생성
    @ResponseBody // 응답을 위한 ResponseBody
    @PostMapping("/request-body-json-v3")
    public String requestBodyJsonV3(@RequestBody HelloData helloData ) throws IOException {
        // @RequestBody : 파라미터로 HelloData 를 직접 선언

        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());

        return "ok";
    }

    // V4. HttpEntity 를 사용하여 처리
    @ResponseBody // 응답을 위한 ResponseBody
    @PostMapping("/request-body-json-v4")
    public String requestBodyJsonV4(HttpEntity<HelloData> httpEntity ) throws IOException {
        HelloData data = httpEntity.getBody();
        log.info("username={}, age={}", data.getUsername(), data.getAge());

        return "ok";
    }

    // V5. @RequestBody 를 사용하여 생성한 객체를 바로 return
    @ResponseBody // 응답을 위한 ResponseBody
    @PostMapping("/request-body-json-v5")
    public HelloData requestBodyJsonV5(@RequestBody HelloData helloData ) throws IOException {
        // @RequestBody : 파라미터로 HelloData 를 직접 선언
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());

        // 파라미터로 받은 helloData 객체를 바로 return
        return helloData;

        // json -> 객체 -> 응답
    }
}
