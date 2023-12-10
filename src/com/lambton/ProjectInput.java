package com.lambton;

import com.lambton.enums.project.ProjectType;
import com.lambton.model.project.Dev;
import com.lambton.model.project.EnhancementProject;
import com.lambton.model.project.Project;
import com.lambton.model.user.User;
import com.lambton.service.ProjectService;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ProjectInput extends InputUtility {

    public static ProjectService projectService = new ProjectService();

    public static Project getProjectUserInput() {
        String title = getString("Title:");
        String description = getString("Description:");
        int choice = 0;
        while (!(choice == 1 || choice == 2)) {
            choice = getInt("Enter 1 for New Project or 2 For Enhancement Project:");
        }
        if (choice == 1) {
            return new Dev(title, description);
        } else {
            return new EnhancementProject(title, description);
        }
    }

    static void searchProject(List<Project> projects) {
        System.out.println("SN\t\tTitle\t\tDescription");
        for (int i = 0; i < projects.size(); i++) {
            Project project = projects.get(i);
            System.out.printf("%d\t\t%s\t\t%s\n", i + 1, project.getTitle(), project.getDescription());
        }
    }

    static void searchProject() {
        int page = -1;
        while (!(page >= 0)) {
            page = ProjectInput.getInt("Page(Valid page start from 1):");
            page = page - 1;
        }
        int size = 4;
        while (!(size >= 5)) {
            size = ProjectInput.getInt("Page(Valid size is greater than 5):");
        }
        String title = ProjectInput.getString("Project title(optional):");
        List<Project> projects = projectService.search(page, size, Optional.of(title), Optional.of(ProjectType.ENHANCEMENT));
        searchProject(projects);
        int choice = 0;
        while (!(choice == 1 || choice == 2 || choice == 3)) {
            choice = ProjectInput.getInt("1. Filter Again\t\t2. Delete\t\t3. Update\nSelect Option:");
        }
        if (choice == 1) searchProject();
        else if (choice == 2) {
            choice = ProjectInput.getInt("Enter SN from the view:");
            if (!(choice < 1 || choice > projects.size())) {
                Project project = projects.get(choice - 1);
                projectService.removeProject(project.getId(), project.getProjectType());
                searchProject(projectService.search(page, size, Optional.of(title), Optional.of(ProjectType.ENHANCEMENT)));
            } else {
                System.out.println("You selected wrong!!!");
            }
        }
    }

    static void projectMenu() {
        while (true) {
            System.out.println("\nProject Menu:");
            System.out.println("1. Create");
            System.out.println("2. Update");
            System.out.println("3. Remove");
            System.out.println("4. View");
            System.out.println("5. Main menu");
            int choice = ProjectInput.getInt("Enter your choice:");
            if (choice == 5) {
                return;
            } else if (choice == 1) {
                projectService.createProject(ProjectInput.getProjectUserInput());
            } else if (choice == 4) {
                searchProject();
            } else {
                System.out.println("Please select a option provided in the menu!!!");
            }
        }
    }
}
