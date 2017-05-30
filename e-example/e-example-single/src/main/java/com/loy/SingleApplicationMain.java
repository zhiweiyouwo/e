/*
 * Copyright   Loy Fu. 付厚俊
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
*/
package com.loy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.h2.server.web.WebServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.loy.e.core.repository.impl.DefaultRepositoryFactoryBean;
import com.loy.e.core.web.filter.LoginRedirectFilter;

import io.swagger.annotations.ApiOperation;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 
 * @author Loy Fu qq群 540553957  http://www.17jee.com
 * @since 1.8
 * @version 3.0.0
 * 
 */
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableAutoConfiguration()
@ComponentScan(basePackages = { "com.xx", "com.loy" })
@EnableJpaRepositories(repositoryFactoryBeanClass = DefaultRepositoryFactoryBean.class, basePackages = {
        "com.xx", "com.loy" })
@EnableCaching
@EnableSwagger2
@EntityScan({ "com.xx", "com.loy" })
public class SingleApplicationMain {
    static final Log logger = LogFactory.getLog(SingleApplicationMain.class);

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SingleApplicationMain.class, args);
    }

    @Bean
    public FilterRegistrationBean loginRedirectFilterRegistration() {

        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new LoginRedirectFilter());
        registration.addUrlPatterns("/login");
        registration.addUrlPatterns("/login.html");
        registration.setName("loginRedirectFilter");
        registration.setOrder(-1000);
        return registration;
    }
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class)).paths(PathSelectors.any())
                .build();
    }
    @Bean
    ServletRegistrationBean h2servletRegistration(){
        ServletRegistrationBean registrationBean = new ServletRegistrationBean( new WebServlet());
        registrationBean.addUrlMappings("/console/*");
        return registrationBean;
    }
}
