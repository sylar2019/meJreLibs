package me.java.library.rpc.thrift.client.scanner;

import lombok.extern.slf4j.Slf4j;
import me.java.library.utils.base.PackageUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.context.annotation.ScopedProxyMode;

import java.util.*;

@Slf4j
public class ThriftClientBeanScanProcessor implements ApplicationContextAware, BeanFactoryPostProcessor {

    private static final String SPRING_THRIFT_CLIENT_PACKAGE_TO_SCAN = "spring.thrift.client.package-to-scan";

    private static final String DEFAULT_SCAN_PACKAGE = "";

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        BeanDefinitionRegistry definitionRegistry = (BeanDefinitionRegistry) beanFactory;
        ThriftClientBeanScanner beanScanner = new ThriftClientBeanScanner(definitionRegistry);
        beanScanner.setResourceLoader(applicationContext);
        beanScanner.setBeanNameGenerator(new AnnotationBeanNameGenerator());
        beanScanner.setScopedProxyMode(ScopedProxyMode.INTERFACES);

        String packageToScan = applicationContext.getEnvironment().getProperty(
                SPRING_THRIFT_CLIENT_PACKAGE_TO_SCAN,
                PackageUtils.getAppBasePackageName());
        setScannedPackages(beanScanner, packageToScan);
    }

    private void setScannedPackages(ThriftClientBeanScanner beanScanner, String basePackages) {
        if (StringUtils.isBlank(basePackages)) {
            beanScanner.scan(DEFAULT_SCAN_PACKAGE);
            return;
        }

        int delimiterIndex = StringUtils.indexOf(basePackages, ",");
        if (delimiterIndex > -1) {
            StringTokenizer tokenizer = new StringTokenizer(basePackages, ",");
            Set<String> packageToScanSet = new HashSet<>();
            if (tokenizer.hasMoreTokens()) {
                do {
                    String subPackage = tokenizer.nextToken();
                    packageToScanSet.add(subPackage);
                    log.info("Subpackage {} is to be scanned by {}", subPackage, beanScanner);
                } while (tokenizer.hasMoreTokens());
            }
            List<String> packageToScanList = new ArrayList<>(packageToScanSet);
            String[] packagesToScan = packageToScanList.toArray(new String[0]);
            beanScanner.scan(packagesToScan);
        } else {
            log.info("Base package {} is to be scanned by {}", basePackages, beanScanner);
            beanScanner.scan(basePackages);
        }
    }
}
