package com.lambton.store.project;

import com.lambton.exception.EntityCreationException;
import com.lambton.exception.EntityExistException;
import com.lambton.exception.EntityNotFoundException;
import com.lambton.model.project.Project;
import com.lambton.utility.FileUtility;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

abstract public class BaseProjectStoreImp implements ProjectStore {
    private final FileUtility fileUtility;

    public BaseProjectStoreImp(FileUtility fileUtility) {
        this.fileUtility= fileUtility;
    }

    protected Map<String, Project> readAllEntities() {
        try (
                FileInputStream fileInputStream = new FileInputStream(fileUtility.getFilePath());
                ObjectInputStream inputStream = new ObjectInputStream(fileInputStream)
        ) {
            return (Map<String, Project>) inputStream.readObject();
        } catch (EOFException e) {
            return new HashMap<>();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException();
        }
    }

    protected boolean writeToFile(Map<String, Project> entities) {
        try (
                ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(fileUtility.getFilePath()))
        ) {
            outputStream.writeObject(entities);
            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Project createProject(Project project) {
        Map<String, Project> projects = readAllEntities();
        if (projects.containsKey(project.getId())) {
            throw new EntityExistException();
        }
        projects.put(project.getId(), project);
        boolean isSuccess = writeToFile(projects);
        if (isSuccess) return project;
        else throw new EntityCreationException();
    }

    @Override
    public Project updateProject(String projectId, Project project) {
        Map<String, Project> projects = readAllEntities();
        if (!projects.containsKey(projectId)) throw new EntityNotFoundException();
        projects.put(projectId, project);
        boolean isSuccess = writeToFile(projects);
        if (isSuccess) return project;
        else throw new EntityCreationException();
    }

    @Override
    public Project deleteProject(String projectId) {
        Map<String, Project> projects = readAllEntities();
        if (!projects.containsKey(projectId)) throw new EntityNotFoundException();
        Project project = projects.get(projectId);
        projects.remove(projectId);
        boolean isSuccess = writeToFile(projects);
        if (isSuccess) return project;
        else throw new EntityCreationException();
    }

    @Override
    public Project getProjectById(String projectId) {
        Map<String, Project> projects = readAllEntities();
        if (!projects.containsKey(projectId)) throw new EntityNotFoundException();
        return projects.get(projectId);
    }
}
