package com.lambton.model.project;

import com.lambton.enums.project.ProjectType;

public class EnhancementProject extends Project {
    public EnhancementProject(String title, String description) {
        super(title, description, ProjectType.ENHANCEMENT);
    }
}
