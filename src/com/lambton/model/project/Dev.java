package com.lambton.model.project;

import com.lambton.enums.project.ProjectType;

public class Dev extends Project{
    public Dev(String title, String description) {
        super(title, description, ProjectType.NEW);
    }
}
