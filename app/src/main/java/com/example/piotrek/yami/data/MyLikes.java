package com.example.piotrek.yami.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MyLikes {

        @JsonProperty("record")
        public List<Like> mylikes = new ArrayList<Like>();
    }

