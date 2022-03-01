package hello.springmvc.basic.response;

import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Controller
//@RestController
public class ResponseBodyController {

    // response 에 직접 응답 메세지를 전달
    @GetMapping("/response-body-string-v1")
    public void responseBodyV1(HttpServletResponse response) throws IOException
    {
        response.getWriter().write("ok");
    }

    /**
     * HttpEntity, ResponseEntity(Http Status 추가)
     * @return
     *
     * ResponseEntity 엔티티는 HttpEntity 를 상속 받았는데,
     * HttpEntity는 HTTP 메시지의 헤더, 바디 정보를 가지고 있다.
     *
     * ResponseEntity 는 여기에 더해서 HTTP 응답 코드를 설정할 수 있다
     */
    @GetMapping("/response-body-string-v2")
    public ResponseEntity<String> responseBodyV2() {

        // 상태값을 조건에 따라 동적으로 처리하고 싶을때는 해당 방식을 이용 -> ResponseEntity
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    // @ResponseBody 를 사용하면 view를 사용하지 않고, HTTP 메시지 컨버터를 통해서 HTTP 메시지를 직접 입력할 수 있다. ResponseEntity 도 동일한 방식으로 동작한다
    @ResponseBody
    @GetMapping("/response-body-string-v3")
    public String responseBodyV3() {
        return "ok";
    }

    // JSON 처리
    @GetMapping("/response-body-json-v1")
    public ResponseEntity<HelloData> responseBodyJsonV1() {
        HelloData helloData = new HelloData();
        helloData.setUsername("userA");
        helloData.setAge(20);

        // 상태값을 조건에 따라 동적으로 처리하고 싶을때는 해당 방식을 이용 -> ResponseEntity
        return new ResponseEntity<>(helloData, HttpStatus.OK);
    }

    // JSON 처리
    @ResponseStatus(HttpStatus.OK) // HTTP status 처리  -> 상태값이 픽스값이므로 동적 처리 불가능
    @ResponseBody
    @GetMapping("/response-body-json-v2")
    public HelloData responseBodyJsonV2() {
        HelloData helloData = new HelloData();
        helloData.setUsername("userA");
        helloData.setAge(20);

        return helloData;
    }
}
