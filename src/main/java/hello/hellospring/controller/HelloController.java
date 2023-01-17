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

    @GetMapping("hello-mvc")
    public String helloMVC(@RequestParam("name") String name, Model model) { // Model 에 담으면 View 에서 랜더링
        model.addAttribute("name", name); // hello-template.html 보면 $는 모델에서 값을 빼는 일
        return "hello-template"; // Thymeleaf 라는 템플릿 엔진이 html 을 정적으로 바꿔서 화면에 넘겨줌
    }

    @GetMapping("hello-string")
    @ResponseBody // http통신프로토콜의 바디부에 이 데이터를 직접 넣어주겠다. 거의 쓸 일 없음.
    public String helloString(@RequestParam(value="name") String name) {
        return "hello " + name; // 뷰 없이 이 문자가 그대로 내려가게 됨
    }

    @GetMapping("hello-api") // json: 키 + value 방식. html+xml 방식에서 거의 json으로 넘어옴.
    @ResponseBody // ResponseBody로 갈 시엔 viewResolver대신에 HttpMessageConverter 중 객체면 jsonConverter, 문자열이면 StringConverter
    public Hello helloApi(@RequestParam("name") String name) {
        Hello hello = new Hello();
        hello.setName(name);
        return hello;
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

    // @ResponseBody를 사용할 시
    // - Http의 Body에 문자 내용을 직접 반환함
    // - viewResolver대신에 HttpMessageConverter가 동작함
    // - 기본 문자처리: StringHttpMessageConerter, 기본 객체처리: MappingJackson2HttpMessageConverter
    // - byte처리 등 기타 여러 HttpMessageConverter가 기본으로 등록돼 있음
    // - SpringBoot는 기본으로 Jackson이라는 라이브러리를 탑재하고 있음. Jackson이란 라이브러리가 객체를 json으로 바꿔줌
    // 참고: 클라이언트의 ATTP Accept헤더와 서버의 컨트롤ㄹ러 반환 타입 정보 둘을 조합해서 HttpMessageConverter가선택됨. 생략.

    // 정적 컨텐츠: 그냥 파일을 그대로 내려준다
    // MVC - 템플릿 엔진: 템플릿 엔진을 MVC방식으로 쪼개서 view를 템플릿엔진으ㄹ로 html을 한국어로 랜더링해서 고객에 전달함
    // API: 객체를 반환함. view없이 json으로 바꿔서 전달하는 방식.
}