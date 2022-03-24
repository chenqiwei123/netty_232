package com.example.netty.netty;
import com.example.netty.netty.Controller.ConfigBeanValue;
import com.example.netty.netty.Controller.HelloController;
import com.example.netty.netty.Server.NettyServer;
import com.example.netty.netty.Socket.Parsing.PackDealImpl;
import com.example.netty.netty.Socket.SimpleServer;
import com.example.netty.netty.service.Impl.UserServiceImpl;
//import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import com.example.netty.netty.Socket.SimpleServer;
import javax.xml.ws.Endpoint;


@SpringBootApplication(exclude={DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
//@MapperScan(basePackages = "com.example.netty.netty.Dao")
//@SpringBootApplication//注解
//compile 'org.projectlombok:lombok:1.16.18'
public class NettyApplication{
    static Logger logger = LoggerFactory.getLogger(NettyApplication.class);
    public static void main(String[] args) {
        logger.debug(">>This is debug message");
        SpringApplication.run(NettyApplication.class, args);
        logger.trace("trace Log!!!!!!");
        logger.debug("debug Log!!!!!!");
        logger.info("info Log!!!!!!");
        logger.warn("warn Log!!!!!!");
        logger.error("error Log!!!!!!");
        HelloController.StartCapture();
    }

}
