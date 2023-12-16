package com.lambton.common.model;

import java.io.*;
import java.util.*;

public class BaseModel implements Serializable {
    private String id;

    public BaseModel() {
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
