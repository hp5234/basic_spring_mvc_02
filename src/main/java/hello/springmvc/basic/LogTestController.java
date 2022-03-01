package hello.springmvc.basic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class LogTestController {

    // 로그 선언
    // 롬복이 제공하는 애노테이션 @Slf4j 로 처리가능 -> log
    // private final Logger log = LoggerFactory.getLogger(getClass());

    @RequestMapping("/log-test")
    public String logTest() {
        String name = "Spring";


        /*
            보통은 운영서버는 info 레벨 부터 출력하고
            개발서버는 trace 로 설정한다.
        */

        // 로그 호출

        // 디버그 레벨 순서
        // 출력레벨 미설정 시 보이지 않는 레벨
        log.trace("trace log={}", name);
        log.debug("debug log={}", name);
        // 운영서버는 주로 info 레벨 부터 출력하도록 한다.
        log.info(" info log={}", name);
        log.warn(" warn log={}", name);
        log.error("error log={}", name);

        return "ok";
    }

}
