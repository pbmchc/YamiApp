package com.example.piotrek.yami.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Piotrek on 2015-01-17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmailAndPassword {

    public String email;
    public String password;
}
