package com.example.piotrek.yami.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by Piotrek on 2015-01-18.
 */
@JsonIgnoreProperties (ignoreUnknown = true)
public class Comment implements Serializable {
    public Integer id;
    public Integer recipeId;
    public Integer ownerId;
    public String text;
    public String created;
}
