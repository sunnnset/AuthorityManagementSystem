package com.xy.ams.backup.datasource;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "ams.backup.datasource")
@EnableConfigurationProperties()
@Data
public class BackupDataSourceProperties {
    private String host;
    private String userName;
    private String password;
    private String database;
    private boolean printShellOutput;
}
