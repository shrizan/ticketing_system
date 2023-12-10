package com.lambton.store.project;

import com.lambton.enums.project.ProjectType;
import com.lambton.model.project.*;
import com.lambton.utility.FileUtility;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GeneralProjectStore extends ProjectStoreImp<Dev> {

    public GeneralProjectStore(FileUtility<Dev> fileUtility) {
        super(fileUtility);
    }

    @Override
    public List<Project> search(long page, long size, Optional<String> optionalTitle) {
        Map<String, Dev> projects = fileUtility.readAllEntities();
        Stream<Dev> projectStream = projects.values()
                .stream()
                .skip(page * size)
                .limit(size)
                .filter(project -> project.getProjectType().equals(ProjectType.NEW));

        return optionalTitle.map(title -> projectStream
                        .filter(project -> project.getTitle().toLowerCase().contains(title.toLowerCase())))
                .orElse(projectStream)
                .collect(Collectors.toList());
    }
}
