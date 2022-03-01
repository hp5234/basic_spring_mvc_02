package hello.springmvc.basic.request;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

@Slf4j
@Controller
public class RequestBodyStringController {

    // Http 메세지 바디에 text 를 넣어 전송 확인
    @PostMapping("/request-body-string-v1")
    public void requestBodyString(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // inputStream 사용
        ServletInputStream inputStream = request.getInputStream();
        // stream 은 바이트코드 -> stream 을 문자로 받을때는 어떠한 인코딩인지 명시해주어야한다.
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messageBody={}", messageBody);

        response.getWriter().write("ok");
    }

    // Http 메세지 바디에 text 를 넣어 전송 확인
    // 파라미터로 InputStream 과 Writer 를 직접 선언
    @PostMapping("/request-body-string-v2")
    public void requestBodyStringV2(InputStream inputStream, Writer responseWriter) throws IOException {

        // stream 은 바이트코드 -> stream 을 문자로 받을때는 어떠한 인코딩인지 명시해주어야한다.
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        log.info("messageBody={}", messageBody);

        responseWriter.write("ok");
    }

    // Http 메세지 바디에 text 를 넣어 전송 확인
    // 위와같은 처리 로직을 스프링이 처리하도록 변경 : http message 컨버터
    // http message body 자체를 주고받는듯이 동작
    @PostMapping("/request-body-string-v3")
    public HttpEntity<String> requestBodyStringV3(HttpEntity<String> httpEntity) throws IOException {

        // stream 은 바이트코드 -> stream 을 문자로 받을때는 어떠한 인코딩인지 명시해주어야한다.
        String messageBody = httpEntity.getBody();
        log.info("messageBody={}", messageBody);

        // HttpEntity 는 응답에도 사용 가능
        return new HttpEntity<>("ok");
    }

    // Http 메세지 바디에 text 를 넣어 전송 확인
    // 위와같은 처리 로직을 스프링이 처리하도록 변경 : http message 컨버터
    // http message body 자체를 주고받는듯이 동작
    // HttpEntity 를 상속받은 RequestEntity<String> 를 파라미터로 받을 수 있다. -> 추가 기능 제공
    // return ResponseEntity 으로 http 상태코드 응답 가능
    @PostMapping("/request-body-string-v3-1")
    public HttpEntity<String> requestBodyStringV31(RequestEntity<String> httpEntity) throws IOException {

        // stream 은 바이트코드 -> stream 을 문자로 받을때는 어떠한 인코딩인지 명시해주어야한다.
        String messageBody = httpEntity.getBody();
        log.info("messageBody={}", messageBody);

        // RequestEntity 와 ResponseEntity 를 사용한다.
        // http 상태 코드 응답 가능
        return new ResponseEntity<>("ok",HttpStatus.CREATED);
    }

    // Http 메세지 바디에 text 를 넣어 전송 확인
    // 위와같은 처리 로직을 스프링이 처리하도록 변경 : http message 컨버터
    // http message body 자체를 주고받는듯이 동작
    // 스프링에서 제공하는 기능을 사용하도록 변경
    @ResponseBody
    @PostMapping("/request-body-string-v4")
    public String requestBodyStringV4(@RequestBody String messageBody) throws IOException {

        log.info("messageBody={}", messageBody);

        // return new HttpEntity<>("ok");
        return "ok"; // @ResponseBody 에 의해 스프링에서 자동으로 처리
    }
}
