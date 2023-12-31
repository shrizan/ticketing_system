package com.lambton;

import com.lambton.common.AppConstant;
import com.lambton.common.util.AppUtil;
import com.lambton.enums.issue.IssueType;
import com.lambton.enums.project.ProjectStatus;
import com.lambton.enums.project.ProjectType;
import com.lambton.enums.user.UserType;
import com.lambton.model.comment.Comment;
import com.lambton.model.project.NewProject;
import com.lambton.model.project.EnhancementProject;
import com.lambton.model.project.Project;
import com.lambton.model.user.User;
import com.lambton.service.ProjectService;
import com.lambton.utility.AccountUtility;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class ProjectInput extends InputUtility {

    public static ProjectService projectService = new ProjectService();

    private static ProjectType getProjectType() {
        String choice = getString("1.New Project\t2.Enhancement\nEnter Option(or press enter):");
        switch (choice) {
            case "1":
                return ProjectType.NEW;
            case "2":
                return ProjectType.ENHANCEMENT;
            default:
                return null;
        }
    }

    static void printHeader() {
        System.out.println(AppUtil.formatString(
                30,
                "SN",
                "Title",
                "Description",
                "Members",
                "Project Status",
                "Start Date",
                "End Date"
        ));
    }

    public static ProjectStatus getStatus() {
        List<Integer> choices = List.of(1, 2);
        while (true) {
            int choice = getInt("1.In Progress 2.Completed\nSelect Option:");
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
            Project parent = null;
            String linkToTheParent = getString("Would you like to add parent project?(Y/N)");
            if (linkToTheParent.equalsIgnoreCase("y")) {
                List<Project> projects = projectService.search(
                        AppConstant.DEFAULT_PAGE,
                        AppConstant.DEFAULT_SIZE, Optional.empty(),
                        Optional.empty());
                parent = ProjectInput.displayProjectsForSelect(projects);
            }
            return new EnhancementProject(title, description, ProjectStatus.IN_PROGRESS, parent);
        }
    }

    static Project displayProjectsForSelect(List<Project> projects) {
        while (true) {
            displayProjectsList(projects);

            int choice = getInt("1.Choose\t2.Filter\t3.Back(Skip)\nSelect Option:");
            if (choice == 1) {
                return selectProject(projects);

            } else if (choice == 2) {
                List<Project> filteredProject = searchProject();
                return displayProjectsForSelect(filteredProject);
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
        while (true) {
            int choice = getInt("Select from SN:");
            if (choice < 1 || choice > projects.size()) {
                System.out.println("Invalid selection!!!");
            } else {
                return projects.get(choice - 1);
            }
        }
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

        if (project.getProjectType() == ProjectType.ENHANCEMENT) {
            if (project.getParent() != null) {
                System.out.println(project.getParent());
            }
            String choice = getString("Would you like to update the parent project?(Y/N):");
            if (choice.equalsIgnoreCase("y")) {
                List<Project> projects = projectService.search(
                        AppConstant.DEFAULT_PAGE,
                        AppConstant.DEFAULT_SIZE, Optional.empty(),
                        Optional.empty());
                project.setParent(ProjectInput.displayProjectsForSelect(projects));
            }
        }

        projectService.update(project.getId(), project);
    }

    static void remove(Project project) {
        projectService.remove(project.getId());
    }

    static void removeTeamMember(Project project) {
        User user = UserInput.fixedUserList(project.getTeam());
        if (null == user) {
            System.out.println("None selected!!!");
        } else if (project.getTeam().stream().noneMatch(teamMember -> teamMember.getId().equals(user.getId()))) {
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
        } else if (project.getTeam().stream().anyMatch(teamUser -> teamUser.getId().equals(user.getId()))) {
            System.out.println("User is already in the team");
        } else {
            project.getTeam().add(user);
            projectService.update(project.getId(), project);
            System.out.println("Team member added");
        }
    }

    static void displayComments(Project project) {
        List<Integer> choices = List.of(1, 2, 3);
        while (true) {
            CommentInput.displayComments(project.getComments());
            int choice = getInt("1. Add 2. Remove 3. Go Back\nSelect Option:");
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

    static void displayUserDetails(Project project) {
        List<Integer> choices = List.of(1, 2, 3);
        while (true) {
            printHeader();
            System.out.printf("%s%s%n", getSN(Integer.toString(1), 30), project);
            int choice = getInt("1.Comments\t2.Update Status \t3.Back\nSelect Option:");
            if (choices.contains(choice)) {
                if (choice == 1) {
                    displayComments(project);
                } else if (choice == 2) {
                    ProjectStatus status = getStatus();
                    project.setProjectStatus(status);
                    project.setEndDate(LocalDate.now());
                    projectService.update(project.getId(), project);
                } else if (choice == 3) {
                    return;
                }
            }
        }
    }

    static void displayDetails(Project project) {
        if (AccountUtility.loggedInUser.getUserType().equals(UserType.MANAGER)) {
            List<Integer> choices = List.of(1, 2, 3, 4, 5, 6,7);
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
                        project.setEndDate(LocalDate.now());
                        projectService.update(project.getId(), project);
                    } else if (choice == 7) {
                        return;
                    }
                }
            }
        } else {
            displayUserDetails(project);
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
                List<Project> filteredProject = searchProject();
                displayProjects(filteredProject);
                return;
            } else if (choice == 3) {
                return;
            }
        }
    }

    static List<Project> searchProject() {
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
        ProjectType projectType = getProjectType();
        return projectService.search(page, size, Optional.of(title), projectType == null ? Optional.empty() : Optional.of(projectType));
    }

    static void userProjectMenu() {
        while (true) {
            System.out.println("\nProject Menu:");
            System.out.println("1.View");
            System.out.println("2.Main menu");
            int choice = ProjectInput.getInt("Enter your choice:");
            if (choice == 1) {
                List<Project> projects = projectService.search(
                        AppConstant.DEFAULT_PAGE,
                        AppConstant.DEFAULT_SIZE, Optional.empty(),
                        Optional.empty());
                displayProjects(projects);
            } else if (choice == 2) {
                return;
            }
        }
    }

    static void managerProjectMenu() {
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
