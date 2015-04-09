package com.guestlist.web.server;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;
import com.google.common.net.HostAndPort;
import org.hibernate.validator.constraints.NotEmpty;

import io.dropwizard.validation.ValidationMethod;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Configuration class for Elasticsearch related settings.
 */
public class EsConfiguration {
	
	//TODO: figure out how to include this in the config file
    @JsonProperty
    @NotNull
    private List<HostAndPort> servers = Lists.newArrayList(HostAndPort.fromParts("127.0.0.1",9300));

    @JsonProperty
    @NotEmpty
    private String clusterName = "elasticsearch";

    @JsonProperty
    private boolean nodeClient = false;

    @JsonProperty
    @NotNull
    private Map<String, String> settings = Collections.emptyMap();

    public List<HostAndPort> getServers() {
        return servers;
    }

    public String getClusterName() {
        return clusterName;
    }

    public boolean isNodeClient() {
        return nodeClient;
    }

    public Map<String, String> getSettings() {
        return settings;
    }

    @ValidationMethod
    @JsonIgnore
    public boolean isValidConfig() {
        return nodeClient || !servers.isEmpty();
    }
}
