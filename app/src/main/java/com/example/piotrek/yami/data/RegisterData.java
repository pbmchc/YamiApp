package com.example.piotrek.yami.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by Piotrek on 2015-01-18.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RegisterData {
        public String email;
        public String new_password;
        public String first_name;
        public String last_name;
    }
