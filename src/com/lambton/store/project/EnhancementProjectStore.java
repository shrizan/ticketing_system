package com.lambton.store.project;

import com.lambton.enums.ProjectType;
import com.lambton.model.project.Project;
import com.lambton.utility.FileUtility;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EnhancementProjectStore extends BaseProjectStoreImp {

    public EnhancementProjectStore(FileUtility fileUtility) {
        super(fileUtility);
    }

    @Override
    public List<Project> search(int page, int size, Optional<String> optionalTitle) {
        Map<String, Project> projects = readAllEntities();
        Stream<Project> projectStream = projects.values()
                .stream()
                .skip(page * size)
                .limit(size)
                .filter(project -> project.getProjectType().equals(ProjectType.ENHANCEMENT));

        return optionalTitle.map(title -> projectStream
                        .filter(project -> project.getTitle().toLowerCase().contains(title.toLowerCase())))
                .orElse(projectStream)
                .collect(Collectors.toList());
    }
}
