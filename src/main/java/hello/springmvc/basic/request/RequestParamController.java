package hello.springmvc.basic.request;

import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Slf4j
@Controller
public class RequestParamController {

    /**
     * 반환 타입이 없으면서 이렇게 응답에 값을 직접 집어넣으면, view 조회X
     */
    @RequestMapping("/request-param-v1")
    public void requestParamV1(HttpServletRequest request, HttpServletResponse
            response) throws IOException {

        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));

        log.info("username={}, age={}", username, age);
        response.getWriter().write("ok");
    }

    @ResponseBody // return "ok" 의 String 을 http 응답메세지에 넣어서 반환 , @RestController 와 같은 효과
    @RequestMapping("request-param-v2")
    public String requestParamV2(
            @RequestParam("username") String memberName,
            @RequestParam("age") int memberAge
    ) {

        log.info("username={}, age={}", memberName, memberAge);
        return "ok"; // @ResponseBody 또는 @RestController 가 없다면 ok 라는 이름의 view 를 찾는다.
    }

    @ResponseBody // return "ok" 의 String 을 http 응답메세지에 넣어서 반환 , @RestController 와 같은 효과
    @RequestMapping("request-param-v3")
    public String requestParamV3(
            @RequestParam String username,
            @RequestParam int age
    ) {
        // @RequestParam 의 일부를 생략한 모습 , 파라미터 이름과 변수 이름이 같아야 한다.

        log.info("username={}, age={}", username, age);
        return "ok"; // @ResponseBody 또는 @RestController 가 없다면 ok 라는 이름의 view 를 찾는다.
    }

    @ResponseBody // return "ok" 의 String 을 http 응답메세지에 넣어서 반환 , @RestController 와 같은 효과
    @RequestMapping("request-param-v4")
    public String requestParamV4(
            String username,
            int age
    ) {
        // @RequestParam 를 생략한 모습  , 파라미터 이름과 변수 이름이 같아야 한다.

        log.info("username={}, age={}", username, age);
        return "ok"; // @ResponseBody 또는 @RestController 가 없다면 ok 라는 이름의 view 를 찾는다.
    }

    /**
     * @RequestParam.required
     * /request-param -> username이 없으므로 예외
     *
     * 주의!
     * /request-param?username= -> 빈문자로 통과
     *
     * 주의!
     * /request-param
     * int age -> null을 int에 입력하는 것은 불가능, 따라서 Integer 변경해야 함(또는 다음에 나오는
    defaultValue 사용)
     */
    @ResponseBody
    @RequestMapping("/request-param-required")
    public String requestParamRequired(
            @RequestParam(required = true) String username,
            @RequestParam(required = false) Integer age) {
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    /**
     * @RequestParam
     * - defaultValue 사용
     *
     * 참고: defaultValue는 빈 문자의 경우에도 적용
     * /request-param?username=
     */
    @ResponseBody
    @RequestMapping("/request-param-default")
    public String requestParamDefault(
            @RequestParam(required = true, defaultValue = "guest") String username,
            @RequestParam(required = false, defaultValue = "-1") int age) {
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    /**
     * @RequestParam Map, MultiValueMap
     * Map(key=value)
     * MultiValueMap(key=[value1, value2, ...] ex) (key=userIds, value=[id1, id2])
     */
    @ResponseBody
    @RequestMapping("/request-param-map")
    public String requestParamMap(@RequestParam Map<String, Object> paramMap) {

        // paramMap 을 받아와서 Map.get() 을 통한 데이터 획득
        log.info("username={}, age={}", paramMap.get("username"), paramMap.get("age"));
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/model-attribute-v1")
    public String modelAttributeV1(
            @ModelAttribute HelloData helloData
    ){
        log.info("username={}, age={}", helloData.getUsername(),
                helloData.getAge());

        return "ok";
    }

    @ResponseBody
    @RequestMapping("/model-attribute-v2")
    public String modelAttributeV2( HelloData helloData
    ){
        // @ModelAttribute 를 생략
        log.info("username={}, age={}", helloData.getUsername(),
                helloData.getAge());

        return "ok";
    }


}
