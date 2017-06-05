package app;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by xdcao on 2017/6/5.
 */
@SpringBootApplication
@MapperScan("app.mapper")
public class BootApplication {

    public static void main(String[] args){
        SpringApplication.run(BootApplication.class);
    }

}
