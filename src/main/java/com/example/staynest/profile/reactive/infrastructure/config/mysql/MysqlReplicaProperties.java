package com.example.staynest.profile.reactive.infrastructure.config.mysql;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "mysql.replica")
public class MysqlReplicaProperties extends MysqlProperties {

}
