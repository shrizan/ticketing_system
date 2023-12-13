package com.lambton;

import com.lambton.common.AppConstant;
import com.lambton.common.util.AppUtil;
import com.lambton.enums.project.ProjectStatus;
import com.lambton.enums.project.ProjectType;
import com.lambton.model.comment.Comment;
import com.lambton.model.project.NewProject;
import com.lambton.model.project.EnhancementProject;
import com.lambton.model.project.Project;
import com.lambton.model.user.User;
import com.lambton.service.ProjectService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class ProjectInput extends InputUtility {

    public static ProjectService projectService = new ProjectService();

    static void printHeader() {
        System.out.println(AppUtil.formatString(
                30,
                "SN",
                "Title",
                "Description",
                "Project Status",
                "Start Date",
                "End Date"
        ));
    }

    public static ProjectStatus getStatus() {
        List<Integer> choices = List.of(1, 2);
        while (true) {
            int choice = getInt("1.In Progress 2.Completed");
            if (choices.contains(choice)) {
                if (choice == 1) return ProjectStatus.IN_PROGRESS;
                else return ProjectStatus.COMPLETED;
            } else {
                System.out.println("Invalid input!!!");
            }
        }

    }

    public static Project getProjectUserInput() {
        String title = getString("Title:");
        String description = getString("Description:");
        int choice = 0;
        while (!(choice == 1 || choice == 2)) {
            choice = getInt("1.New Project \t 2.Enhancement Project\nSelect Option:");
        }
        if (choice == 1) {
            return new NewProject(title, description, ProjectStatus.IN_PROGRESS);
        } else {
            System.out.println("Link to the project:");
            List<Project> projects = projectService.search(
                    AppConstant.DEFAULT_PAGE,
                    AppConstant.DEFAULT_SIZE, Optional.empty(),
                    Optional.empty());
            Project project = ProjectInput.displayProjectsForSelect(projects);
            return new EnhancementProject(title, description, ProjectStatus.IN_PROGRESS, project);
        }
    }

    static Project displayProjectsForSelect(List<Project> projects) {
        while (true) {
            displayProjectsList(projects);

            int choice = getInt("1.Choose\t2.Filter\t3.Back(Skip)\nSelect Option:");
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
        printHeader();
        for (int i = 0; i < projects.size(); i++) {
            Project project = projects.get(i);
            System.out.printf("%s%s%n", getSN(Integer.toString(i + 1), 30), project);
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
        projectService.update(project.getId(), project);
    }

    static void remove(Project project) {
        projectService.remove(project.getId());
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
            projectService.update(project.getId(), project);
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
            projectService.update(project.getId(), project);
            System.out.println("Team member added");
        }
    }

    static void displayComments(Project project) {
        CommentInput.displayComments(project.getComments());
        List<Integer> choices = List.of(1, 2, 3);
        while (true) {
            int choice = getInt("1. Add 2. Remove 3. Go Back");
            if (choices.contains(choice)) {
                if (choice == 1) {
                    Comment comment = CommentInput.getComment();
                    project.getComments().add(comment);
                    projectService.update(project.getId(), project);
                } else if (choice == 2) {
                    Comment comment = CommentInput.selectComment(project.getComments());
                    project.getComments().remove(comment);
                    projectService.update(project.getId(), project);
                } else {
                    return;
                }
            }
        }
    }

    static void displayDetails(Project project) {
        List<Integer> choices = List.of(1, 2, 3, 4, 5, 6);
        while (true) {
            printHeader();
            System.out.printf("%s%s%n", getSN(Integer.toString(1), 30), project);
            int choice = getInt("1.Update \t2.Delete \t3.Add team member \t4.Remove Team member\t5.Comments\t6.Update Status \t7.Back\nSelect Option:");
            if (choices.contains(choice)) {
                if (choice == 1) {
                    update(project);
                } else if (choice == 2) {
                    remove(project);
                } else if (choice == 3) {
                    addTeamMember(project);
                } else if (choice == 4) {
                    removeTeamMember(project);
                } else if (choice == 5) {
                    displayComments(project);
                } else if (choice == 6) {
                    ProjectStatus status = getStatus();
                    project.setProjectStatus(status);
                    projectService.update(project.getId(), project);
                }
                return;
            }
        }
    }

    static void displayProjects(List<Project> projects) {
        while (true) {
            displayProjectsList(projects);
            int choice = getInt("1.Details\t2.Filter\t3.Back\nSelect Option:");
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
                projectService.remove(project.getId());
                displayProjects(projectService.search(page, size, Optional.of(title), Optional.of(ProjectType.ENHANCEMENT)));
            } else {
                System.out.println("You selected wrong!!!");
            }
        }
    }

    static void projectMenu() {
        while (true) {
            System.out.println("\nProject Menu:");
            System.out.println("1.Create");
            System.out.println("2.View(Update and Remove)");
            System.out.println("3.Main menu");
            int choice = ProjectInput.getInt("Enter your choice:");
            if (choice == 1) {
                Project project = getProjectUserInput();
                project.setStartDate(LocalDate.now());
                projectService.create(project);
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
