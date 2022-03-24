package com.example.netty.netty.Controller;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.stereotype.Component;
@Data
@Component
@ConfigurationPropertiesScan
public class ConfigBeanValue {
    @Value("${baosight.ip}")
    public String baosight_ip;

    @Value("${baosight.port}")
    public int baosight_Port;

    @Value("${baosight.configxml}")
    public String baosight_configxml;

    @Value("${baosight.Center.sendPostUrl}")
    public String sendPostUrl;

    @Value("${baosight.IsReadSql}")
    public boolean IsReadSql;

}