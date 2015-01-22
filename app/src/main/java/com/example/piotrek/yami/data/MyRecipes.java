package com.example.piotrek.yami.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Piotrek on 2015-01-21.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MyRecipes {

    @JsonProperty("record")
    public List<Recipe> favors = new ArrayList<Recipe>();
}
