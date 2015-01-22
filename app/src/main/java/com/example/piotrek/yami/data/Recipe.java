package com.example.piotrek.yami.data;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

@JsonIgnoreProperties (ignoreUnknown = true)
public class Recipe implements Serializable {

public Integer id;
public Integer ownerId;
public String title;
public String introduction;
    public String ingredients;
    public String steps;
    public String created;
    public Integer preparationMinutes;
    public Integer cookingMinutes;
    public Integer servings;
    public Integer picture1Id;
    public Integer picture2Id;
    public Integer picture3Id;

    @JsonIgnore
    public String pictureBytes;

}
