package com.itqf.order.config;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * projectName: demo_end
 *
 * @author: xinxin
 * time:  2020/11/520:30
 * description:
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    //创建一个swagger的对象
    @Bean  //使用IOC 创建对象
    public Docket ceateD(){
        return new Docket(DocumentationType.SWAGGER_2) //指定生成的文档类型
                .apiInfo(createAI())  // 文档的信息  这里需要一个文档的信息类
                .groupName("yangl")////分组名称(可以创建多个Docket就有多个组名)
                .enable(true)//enable表示是否开启Swagger
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.itqf.order.controller")) //扫描注解所在的包  这个很重要的，不能忘了
                .build() ;
    }

    //返回一个API文档的信息类
    public ApiInfo createAI(){
        return new ApiInfoBuilder()
                .title("订单模块的在线接口文档")
                //作者的联系方式
                .contact(new Contact("java2004" , "www.baidu.com" ,"java2004@qq.com"))
                //版本
                .version("1.0")
                //描述
                .description("这是一个订单模块的在线接口文档，可以查看这个项目的所有的接口")
                .build();
    }


}
