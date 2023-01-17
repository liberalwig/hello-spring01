package hello.hellospring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HelloSpringApplication {

	public static void main(String[] args) { // 라이브러리 하나 빌드해서 그걸 웹 서버에 올리면 톰캣서버 깔지 않고도 다 웹서버가 뜨는 시대

		SpringApplication.run(HelloSpringApplication.class, args);
	}

}
