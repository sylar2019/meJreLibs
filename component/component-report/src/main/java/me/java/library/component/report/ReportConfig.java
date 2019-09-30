package me.java.library.component.report;

import com.fr.web.ReportServlet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

/**
 * @author luhao
 * @date 2019-5-30 14:49
 * @description
 */

@Configuration
public class ReportConfig {
    @Value("${report.cpt.path}")
    private String cptPath;

    @Bean
    public ServletRegistrationBean servletBean() {
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new ReportServlet(), "/report/server");
        servletRegistrationBean.setLoadOnStartup(1);
        return servletRegistrationBean;
    }

    @Bean
    public EmbeddedServletContainerCustomizer webServerFactoryCustomizer() {
        return factory -> {
            factory.setDocumentRoot(new File(cptPath));
        };
    }
}