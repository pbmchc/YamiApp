package com.example.piotrek.yami.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Piotrek on 2015-01-18.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Like {
    public Integer id;
    public Integer recipeId;
    public Integer ownerId;
    public String created;
}
