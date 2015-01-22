package com.example.piotrek.yami.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Piotrek on 2015-01-18.
 */
@JsonIgnoreProperties (ignoreUnknown = true)
public class Likes {

    @JsonProperty("record")
    public List<Like> likes = new ArrayList<Like>();
}
