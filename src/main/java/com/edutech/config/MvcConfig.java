package com.edutech.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
//@Configuration이 부여된 클래스에는 @Bean을 사용하여 빈을 정의한다. 이러한 빈들은 스프링 컨테이너에 의해 관리되며, 필요한 곳에서 주입하여 사용 가능.
//빈을 주입받는다 -> 어떤 클래스나 컴포넌트에서 다른 클래스나 컴포넌트를 필요로할때 이를 직접 생성하지 않고 외부에서 주입받아 사용한다.
//클래스-객체를 만들기 위한 설계도 틀.
// 컴포넌트-클래스나 여러 클래스의 집합으로 이루어져있다. 다른 컴포넌트와 상호작용하며 독립적 존재가능.
// ex)컨트롤러,서비스,리포지토리등은 하나의 컴포넌트다.@Controller, @Service, @Repository 등도 모두 @Component의 특수한 형태
public class MvcConfig {
    @Bean
    public ModelMapper modelMapper() {
        //ModelMapper는 java간의 매핑을 담담하는 라이브러리, 객체 간의 필드 복사를 자동화하고, 복잡한 매핑 규칙을 간소화한다.
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)//매핑 대상 필드의 접근레벨 설정, private 필드에 대해서 매핑 수행
                .setFieldMatchingEnabled(true) // 필드 이름 매칭 활성화. 동일한 이름을 가진 필드끼리 자동으로 매핑
                .setMatchingStrategy(MatchingStrategies.STRICT); //매칭전략을 설정. strict-매우 엄격한 매핑규칙 적용
        return modelMapper;
    }
}
