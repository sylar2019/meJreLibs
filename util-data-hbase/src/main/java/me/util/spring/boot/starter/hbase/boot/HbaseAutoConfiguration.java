package me.util.spring.boot.starter.hbase.boot;

import me.util.spring.boot.starter.hbase.api.HbaseTemplate;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * JThink@JThink
 *
 * @author JThink
 * @version 0.0.1
 * desc： hbase auto configuration
 * date： 2016-11-16 11:11:27
 */
@org.springframework.context.annotation.Configuration
@EnableConfigurationProperties(HbaseProperties.class)
@ConditionalOnClass(HbaseTemplate.class)
public class HbaseAutoConfiguration {

    public static final String HADOOP_USER_NAME = "hbase.user.name";
    private static final String HBASE_QUORUM = "hbase.zookeeper.quorum";
    private static final String HBASE_ROOTDIR = "hbase.rootdir";
    private static final String HBASE_ZNODE_PARENT = "zookeeper.znode.parent";
    @Autowired
    private HbaseProperties hbaseProperties;

    @Bean
    @ConditionalOnMissingBean(HbaseTemplate.class)
    public HbaseTemplate hbaseTemplate() {
        Configuration configuration = HBaseConfiguration.create();
        configuration.set(HBASE_QUORUM, this.hbaseProperties.getQuorum());
        if (StringUtils.isNotBlank(hbaseProperties.getRootDir())) {
            configuration.set(HBASE_ROOTDIR, hbaseProperties.getRootDir());
        }

        if (StringUtils.isNotBlank(hbaseProperties.getNodeParent())) {
            configuration.set(HBASE_ZNODE_PARENT, hbaseProperties.getNodeParent());
        }

        if (StringUtils.isNotBlank(hbaseProperties.getUsername())) {
            configuration.set(HADOOP_USER_NAME, hbaseProperties.getUsername());
            System.setProperty("HADOOP_USER_NAME", hbaseProperties.getUsername());
        }

        return new HbaseTemplate(configuration);
    }
}
