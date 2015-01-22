package com.example.piotrek.yami.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Piotrek on 2015-01-18.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Comments {
    @JsonProperty("record")
    public List<Comment> opinions = new ArrayList<Comment>();
}
