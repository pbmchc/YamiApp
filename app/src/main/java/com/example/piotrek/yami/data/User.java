package com.example.piotrek.yami.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by Piotrek on 2015-01-17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements Serializable {

    public Integer id;
    public String display_name;

    @JsonProperty("session_id")
    public String sessionId;
}
