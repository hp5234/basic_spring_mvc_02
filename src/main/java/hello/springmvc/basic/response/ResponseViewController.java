package hello.springmvc.basic.response;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
public class ResponseViewController {

    @RequestMapping("/response-view-v1")
    public ModelAndView responseViewV1() {
        ModelAndView mav = new ModelAndView("response/hello")
                .addObject("data", "hello!!!");

        return mav;
    }

    @RequestMapping("response-view-v2")
    public String responseViewV2(Model model){
        model.addAttribute("data", "hello2 !!! ");

        // view 의 논리적 이름을 반환
        return "response/hello";
    }

    // 권장하지 않는 방식 - 불명확함
    // 명시성이 떨어지고 딱 맞아 떨어지는 경우가 없기때문에 권장하지 않음
    @RequestMapping("/response/hello")
    public void responseViewV3(Model model) {
        model.addAttribute("data", "hello!!");
    }
}

