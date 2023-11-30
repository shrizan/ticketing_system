package com.lambton.model.project;

import com.lambton.enums.ProjectType;

public class GeneralProject extends Project{
    public GeneralProject(String title, String description) {
        super(title, description, ProjectType.NEW);
    }
}
