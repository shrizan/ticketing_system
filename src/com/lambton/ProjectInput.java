package com.lambton;

import com.lambton.common.AppConstant;
import com.lambton.enums.project.ProjectStatus;
import com.lambton.enums.project.ProjectType;
import com.lambton.model.project.NewProject;
import com.lambton.model.project.EnhancementProject;
import com.lambton.model.project.Project;
import com.lambton.model.user.User;
import com.lambton.service.ProjectService;

import java.util.List;
import java.util.Optional;

public class ProjectInput extends InputUtility {

    public static ProjectService projectService = new ProjectService();

    public static Project getProjectUserInput() {
        String title = getString("Title:");
        String description = getString("Description:");
        int choice = 0;
        while (!(choice == 1 || choice == 2)) {
            choice = getInt("1. New Project \t 2. Enhancement Project\nSelect Option:");
        }
        if (choice == 1) {
            return new NewProject(title, description, ProjectStatus.IN_PROGRESS);
        } else {
            return new EnhancementProject(title, description, ProjectStatus.IN_PROGRESS);
        }
    }

    static Project displayProjectsForSelect(List<Project> projects) {
        while (true) {
            displayProjectsList(projects);

            int choice = getInt("1. ℹ️Choose\t2. 🔍Filter\t3. ⬅️Back\nSelect Option:");
            if (choice == 1) {
                return selectProject(projects);

            } else if (choice == 2) {
                searchProject();
            } else if (choice == 3) {
                return null;
            }
        }
    }

    static void displayProjectsList(List<Project> projects) {
        System.out.println("SN\t\tTitle\t\tDescription");
        for (int i = 0; i < projects.size(); i++) {
            Project project = projects.get(i);
            System.out.printf("%d\t\t%s\t\t%s\n", i + 1, project.getTitle(), project.getDescription());
        }
    }

    static Project selectProject(List<Project> projects) {
        int choice = getInt("Select from SN:");
        while (true) {
            if (choice < 1 || choice > projects.size()) {
                System.out.println("Invalid selection!!!");
            } else {
                break;
            }
        }
        return projects.get(choice - 1);
    }

    static void update(Project project) {
        String title = getString(String.format("Title(%s):", project.getTitle()));
        String description = getString(String.format("Description(%s):", project.getDescription()));

        if (!title.isEmpty()) {
            project.setTitle(title);
        }
        if (!description.isEmpty()) {
            project.setDescription(description);
        }
        projectService.updateProject(project.getId(), project);
    }

    static void remove(Project project) {
        projectService.removeProject(project.getId());
    }

    static void removeTeamMember(Project project) {
        List<User> users = UserInput.userService.search(
                AppConstant.DEFAULT_PAGE,
                AppConstant.DEFAULT_SIZE,
                Optional.empty(),
                Optional.empty(),
                Optional.empty()
        );
        User user = UserInput.fixedUserList(users);
        if (null == user) {
            System.out.println("None selected!!!");
        } else if (!project.getTeam().contains(user)) {
            System.out.println("Team member does not exist!!!");
        } else {
            project.getTeam().remove(user);
            projectService.updateProject(project.getId(), project);
            System.out.println("Team member removed!!!");
        }
    }

    static void addTeamMember(Project project) {
        List<User> users = UserInput.userService.search(
                AppConstant.DEFAULT_PAGE,
                AppConstant.DEFAULT_SIZE,
                Optional.empty(),
                Optional.empty(),
                Optional.empty()
        );
        User user = UserInput.displayUserForSelect(users);
        if (null == user) {
            System.out.println("None selected!!!");
            return;
        } else if (project.getTeam().contains(user)) {
            System.out.println("User is already in the team");
        } else {
            project.getTeam().add(user);
            projectService.updateProject(project.getId(), project);
            System.out.println("Team member added");
        }
    }

    static void displayDetails(Project project) {
        List<Integer> choices = List.of(1, 2, 3, 4, 5);
        while (true) {
            System.out.println(project);
            int choice = getInt("1. ⬆️Update \t2. ⛔Delete \t3.➕ Add team member \t4: ➖ Remove Team member \t5. ⬅️Back\nSelect Option:");
            if (choices.contains(choice)) {
                if (choice == 1) {
                    update(project);
                } else if (choice == 2) {
                    remove(project);
                } else if (choice == 3) {
                    addTeamMember(project);
                } else if (choice == 4) {
                    removeTeamMember(project);
                }
                return;
            }
        }
    }

    static void displayProjects(List<Project> projects) {
        while (true) {
            displayProjectsList(projects);
            int choice = getInt("1. ℹ️Details\t2. 🔍Filter\t3. ⬅️Back\nSelect Option:");
            if (choice == 1) {
                Project project = selectProject(projects);
                displayDetails(project);
            } else if (choice == 2) {
                searchProject();
            } else if (choice == 3) {
                return;
            }
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
        displayProjects(projects);
        int choice = 0;
        while (!(choice == 1 || choice == 2 || choice == 3)) {
            choice = ProjectInput.getInt("1. Filter Again\t\t2. Delete\t\t3. Update\nSelect Option:");
        }
        if (choice == 1) searchProject();
        else if (choice == 2) {
            choice = ProjectInput.getInt("Enter SN from the view:");
            if (!(choice < 1 || choice > projects.size())) {
                Project project = projects.get(choice - 1);
                projectService.removeProject(project.getId());
                displayProjects(projectService.search(page, size, Optional.of(title), Optional.of(ProjectType.ENHANCEMENT)));
            } else {
                System.out.println("You selected wrong!!!");
            }
        }
    }

    static void projectMenu() {
        while (true) {
            System.out.println("\nProject Menu:");
            System.out.println("1.➕ Create");
            System.out.println("2.ℹ️ View(Update and Remove)");
            System.out.println("3.⬅️ Main menu");
            int choice = ProjectInput.getInt("Enter your choice:");
            if (choice == 1) {
                projectService.createProject(getProjectUserInput());
            } else if (choice == 2) {
                List<Project> projects = projectService.search(
                        AppConstant.DEFAULT_PAGE,
                        AppConstant.DEFAULT_SIZE, Optional.empty(),
                        Optional.empty());
                displayProjects(projects);
            } else if (choice == 3) {
                return;
            }
        }
    }
}
