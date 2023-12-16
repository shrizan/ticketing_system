package com.lambton.store.project;

import com.lambton.common.store.*;
import com.lambton.enums.project.ProjectType;
import com.lambton.enums.user.UserType;
import com.lambton.exception.EntityCreationException;
import com.lambton.exception.EntityExistException;
import com.lambton.exception.EntityNotFoundException;
import com.lambton.model.project.Project;
import com.lambton.utility.AccountUtility;
import com.lambton.utility.FileUtility;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ProjectStoreImp<T extends Project> extends StoreImpl<T> implements ProjectStore<T> {

    public ProjectStoreImp(FileUtility<T> fileUtility) {
        super(fileUtility);
    }

    @Override
    public Map<String, T> allEntities() {
        return fileUtility.readAllEntities();
    }

    @Override
    public List<Project> search(long page, long size, Optional<String> optionalTitle, Optional<ProjectType> optionalProjectType) {
        return
                fileUtility.readAllEntities()
                        .values()
                        .stream()
                        .filter(this::filterByUser)
                        .skip(page * size).limit(size)
                        .filter(filterByTitle(optionalTitle))
                        .filter(filterByProjectType(optionalProjectType))
                        .collect(Collectors.toList());
    }

    private static <T extends Project> Predicate<T> filterByProjectType(Optional<ProjectType> optionalProjectType) {
        return project -> optionalProjectType.map(projectType -> project.getProjectType().equals(projectType)).orElse(true);
    }

    private static <T extends Project> Predicate<T> filterByTitle(Optional<String> optionalTitle) {
        return project -> optionalTitle.map(title -> project.getTitle().toLowerCase().contains(title.toLowerCase())).orElse(true);
    }

    private boolean filterByUser(Project project) {
        if (AccountUtility.loggedInUser.getUserType().equals(UserType.MANAGER)) return true;
        else
            return project.getTeam().stream().anyMatch(member -> member.getId().equals(AccountUtility.loggedInUser.getId()));
    }
}
