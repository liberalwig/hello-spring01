package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @GetMapping("hello")
    public String hello(Model model) {
        model.addAttribute("data", "hello!");
        return "hello";
    }

    @GetMapping("hello-mvc") // helloController --> return: hello-template / model: name("spring") --> ViewResolver
    public String helloMVC(@RequestParam("name") String name, Model model) { // Model 에 담으면 View 에서 랜더링
        model.addAttribute("name", name); // hello-template.html 보면 $는 모델에서 값을 빼는 일
        return "hello-template"; // Thymeleaf 라는 템플릿 엔진이 html 을 정적으로 바꿔서 화면에 넘겨줌
    }

    @GetMapping("hello-string")
    @ResponseBody // html 의 바디가 아니고 http 통신프로토콜의 헤더부와 바디부 중 바디부에 이 데이터를 직접 넣어주겠다. 거의 쓸 일 없음.
    public String helloString(@RequestParam(value="name") String name) {
        return "hello " + name; // template Engine 과의 차이 : View 없이 이 문자가 그대로 내려가게 됨
    }

    // 하단부터가 진짜임. API 방식
    @GetMapping("hello-api") // json: 키 + value 방식. html+xml 방식에서 거의 Json 으로 넘어옴.
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name) {
        Hello hello = new Hello();
        hello.setName(name);
        return hello; // hello 라는 객체
    }
    static class Hello {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    /**
     *
     @ResponseBody 사용할 시
     - Http 의 Body 에 문자 내용을 직접 반환함
     - viewResolver 대신에 HttpMessageConverter 가 동작함
        기본 문자처리: StringHttpMessageConverter,
        기본 객체처리: MappingJackson2HttpMessageConverter
     - byte 처리 등 기타 여러 HttpMessageConverter 가 기본으로 등록돼 있음
     - SpringBoot 는 기본으로 Jackson 이라는 라이브러리를 탑재하고 있음. Jackson 이란 라이브러리가 객체를 json 으로 바꿔줌
     * 참고: 클라이언트의 HTTP Accept 헤더와 서버의 컨트롤러 반환 타입 정보 둘을 조합해서 HttpMessageConverter 가 선택됨. 생략.

     정적 컨텐츠: 그냥 파일을 그대로 내려준다
     MVC-템플릿 엔진: 템플릿 엔진을 MVC 방식으로 쪼개서 view 를 템플릿엔진 통해 랜더링 한 html 을 한국어로 만들어 고객에 전달함
     API: 객체를 반환함. view 없이 json 으로 바꿔서 HttpResponse 에 전달하는 방식.

     **/
}