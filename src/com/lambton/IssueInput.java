package com.lambton;

import com.lambton.common.AppConstant;
import com.lambton.common.util.AppUtil;
import com.lambton.enums.issue.IssueStatus;
import com.lambton.enums.issue.IssueType;
import com.lambton.enums.issue.Priority;
import com.lambton.enums.project.ProjectType;
import com.lambton.model.comment.Comment;
import com.lambton.model.issue.Bug;
import com.lambton.model.issue.Issue;
import com.lambton.model.issue.Story;
import com.lambton.model.issue.Task;
import com.lambton.model.project.Project;
import com.lambton.model.user.User;
import com.lambton.service.IssueService;
import com.lambton.utility.AccountUtility;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class IssueInput extends InputUtility {
    static IssueService issueService = new IssueService();

    private static void printHeader() {
        System.out.println(
                AppUtil.formatString(
                        "SN",
                        "Title",
                        "Description",
                        "Project",
                        "Parent Issue",
                        "Assigned By",
                        "Type",
                        "Priority",
                        "Status"
                )
        );
    }

    private static IssueType getIssueType(int choice) {
        switch (choice) {
            case 1:
                return IssueType.STORY;
            case 2:
                return IssueType.TASK;
            default:
                return IssueType.BUG;
        }
    }

    private static Priority getPriority(int choice) {
        switch (choice) {
            case 1:
                return Priority.HIGH;
            case 2:
                return Priority.MODERATE;
            default:
                return Priority.LOW;
        }
    }

    static IssueType getIssueType() {
        int choice = 0;
        while (!(choice == 1 || choice == 2 || choice == 3)) {
            choice = getInt("1.Story\t2.Task\t3.Bug\nEnter Option:");
        }
        return getIssueType(choice);
    }

    static IssueStatus getIssueStatus() {
        List<Integer> choices = List.of(1, 2, 3, 4);
        int choice = 0;
        while (!choices.contains(choice)) {
            choice = getInt("1.TODO\t2.IN_PROGRESS\t3.REVIEW\t4.COMPLETED\nEnter Option:");
        }
        switch (choice) {
            case 1:
                return IssueStatus.TODO;
            case 2:
                return IssueStatus.IN_PROGRESS;
            case 3:
                return IssueStatus.REVIEW;
            default:
                return IssueStatus.COMPLETED;
        }
    }

    static Project selectAProject() {
        while (true) {
            String projectTitle = getString("Project Title:");
            List<Project> projects = ProjectInput.projectService.search(0, 10, Optional.of(projectTitle), Optional.of(ProjectType.ENHANCEMENT));
            ProjectInput.displayProjects(projects);
            String input = getString("S for search again. Or enter index for project to select");
            Project project = null;
            if (!input.equalsIgnoreCase("s")) {
                try {
                    project = projects.get(Integer.parseInt(input) - 1);
                    System.out.println(project);
                    return project;
                } catch (Exception ex) {
                    System.out.println("Invalid input!!!");
                }
            }
        }
    }

    static Issue createNewIssue(IssueType issueType, String title, String description, Project project, Priority priority) {
        if (issueType == IssueType.BUG) {
            return new Bug(
                    title,
                    description,
                    project,
                    AccountUtility.loggedInUser,
                    priority,
                    IssueStatus.TODO
            );
        } else if (issueType == IssueType.STORY) {
            return new Story(
                    title,
                    description,
                    project,
                    AccountUtility.loggedInUser,
                    priority,
                    IssueStatus.TODO
            );
        } else {
            return new Task(
                    title,
                    description,
                    project,
                    AccountUtility.loggedInUser,
                    priority,
                    IssueStatus.TODO
            );
        }
    }

    static Issue getIssueInput() {
        System.out.println("Create new issue(Story, Task or Bug):");
        int choice = 0;
        IssueType issueType = getIssueType();
        String title = getString("Title:");
        String description = getString("Description:");
        System.out.println("Select a project it belongs");
        List<Project> projects = ProjectInput.projectService.search(
                AppConstant.DEFAULT_PAGE,
                AppConstant.DEFAULT_SIZE,
                Optional.empty(),
                Optional.empty()
        );
        Project project = ProjectInput.displayProjectsForSelect(projects);
        while (true) {
            choice = 0;
            while (!(choice == 1 || choice == 2 || choice == 3)) {
                choice = getInt("Select priority 1.High\t2.Moderate\t3.Low\nEnter Option:");
            }
            Priority priority = getPriority(choice);

            return createNewIssue(issueType, title, description, project, priority);
        }

    }

    static void assignTo(Issue issue) {
        List<User> users = new ArrayList<>();
        String choice = "";
        System.out.println("Select users to assign:(Enter 0) to exit:");
        do {
            List<User> defaultUsers = UserInput.userService.search(
                    AppConstant.DEFAULT_PAGE,
                    AppConstant.DEFAULT_SIZE,
                    Optional.empty(),
                    Optional.empty(),
                    Optional.empty()
            );
            User user = UserInput.displayUserForSelect(defaultUsers);
            users.add(user);
            choice = getString("Want to add another one?Y/N ");
        } while (choice.equalsIgnoreCase("y"));
        issue.setAssignedTos(users);
        issueService.update(issue.getId(), issue);
    }

    static void addIssuesToSuggestionList(Issue issue) {
        String title = getString("Other issue title:");
        List<Issue> issues = new ArrayList<>();
        if (issue.getIssueType() == IssueType.STORY) {
            issues = issueService.searchIssue(
                    0, 10,
                    Optional.of(title),
                    Optional.empty(),
                    Optional.empty(),
                    Optional.empty(),
                    Optional.empty()
            );
        } else {
            IssueType issueType = getIssueType();
            issues = issueService.searchIssue(
                    0, 10,
                    Optional.of(title),
                    Optional.empty(),
                    Optional.empty(),
                    Optional.empty(),
                    Optional.empty()
            );
        }
        System.out.println("Title\t\tDescription");
        issues.stream().filter(issue1 -> !issue1.getId().equals(issue.getId())).forEach(issue1 -> {
            System.out.printf("%s\t\t%s", issue1.getTitle(), issue1.getDescription());
        });
        int choice = 0;
        while (true) {
            choice = getInt("Select SN to Add:");
            if (choice < 1 || choice > issues.size()) {
                System.out.println("Invalid selection!!!");
            } else {
                issue.setParent(issues.get(choice - 1));
                return;
            }
        }
    }

    static void addParent(Issue issue) {
        String choice = getString("Y to Link to parent other No:");
        if (choice.equalsIgnoreCase("Y")) {
            addIssuesToSuggestionList(issue);
            issueService.update(issue.getId(), issue);
            System.out.println("Parent linked");
        }
    }

    static void createNewIssue() {
        Issue issue = issueService.create(getIssueInput());
        if (issue != null) {
            assignTo(issue);
            addParent(issue);
        }
        System.out.println("New Issue Created!!!");
    }

    static void updateIssue(Issue issue) {
        String input = getString("Do you want to update the project(Y/N):");
        String title = getString(String.format("Title(%s):", issue.getTitle()));
        String description = getString(String.format("Description(%s):", issue.getDescription()));
        System.out.println("Select a project it belongs");
        System.out.printf("Old Project:%s\n", issue.getProject());
        input = getString("Do you want to update the project(Y/N):");
        Project project = null;
        if (input.equals("Y")) {
            project = selectAProject();
        }
        int choice = 0;
        while (!(choice == 1 || choice == 2 || choice == 3)) {
            choice = getInt("Select priority 1.High\t2.Moderate\t3.Low\nEnter Option:");
        }
        Priority priority = getPriority(choice);
        Issue updatedIssue = createNewIssue(issue.getIssueType(), title, description, project, priority);
        updatedIssue.setId(issue.getId());
        issueService.update(issue.getId(), updatedIssue);
        input = getString("Do you want to update assign to(Y/N):");
        if (input.equalsIgnoreCase("Y")) {
            assignTo(issue);
        }
        input = getString("Do you want to change the project(Y/N):");
        if (input.equalsIgnoreCase("Y")) {
            addParent(issue);
        }
        System.out.println("New Issue Created!!!");

    }

    static void removeIssue(Issue issue) {
        issueService.remove(issue.getId());
        //issues.remove(issue);
        //displayList(issues);
    }

    static void searchIssue() {
        long page = getInt("Page:");
        long size = getInt("Size:");
        String title = getString("Title:");
        IssueType issueType = getIssueType();

        List<Issue> issues = issueService.searchIssue(
                page,
                size,
                Optional.of(title),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty()
        );
        displayIssues(issues);

    }

    static void displayComments(Issue issue) {
        CommentInput.displayComments(issue.getComment());
        List<Integer> choices = List.of(1, 2, 3);
        while (true) {
            int choice = getInt("1. Add 2. Remove 3. Go Back");
            if (choices.contains(choice)) {
                if (choice == 1) {
                    Comment comment = CommentInput.getComment();
                    issue.getComment().add(comment);
                    issueService.update(issue.getId(), issue);
                } else if (choice == 2) {
                    Comment comment = CommentInput.selectComment(issue.getComment());
                    issue.getComment().remove(comment);
                    issueService.update(issue.getId(), issue);
                } else {
                    return;
                }
            }
        }
    }

    public static void issueDetails(Issue issue) {
        System.out.println(issue);
        List<Integer> choices = List.of(1, 2, 3);
        while (true) {
            int choice = getInt("1. Update 2. Remove 3. Comments 4. Change Status 5. Back\nSelect Option:");
            if (choices.contains(choice)) {
                if (choice == 1) {
                    updateIssue(issue);
                } else if (choice == 2) {
                    removeIssue(issue);
                } else if (choice == 3) {
                    displayComments(issue);
                } else if (choice == 4) {
                    System.out.println("Old status");
                    IssueStatus status = getIssueStatus();
                    issue.setIssueStatus(status);
                    issueService.update(issue.getId(), issue);
                } else {
                    return;
                }
            }
        }
    }

    static void displayIssues(List<Issue> issues) {
        printHeader();
        for (int i = 0; i < issues.size(); i++) {
            Issue issue = issues.get(i);
            System.out.printf("%s%s%n", getSN(Integer.toString(i + 1)), issue);
        }
    }

    static Issue getIssueFromList(List<Issue> issues) {
        while (true) {
            int choice = getInt("Select From the list:");
            if (choice > 0 && choice <= issues.size()) return issues.get(choice - 1);
            else System.out.println("Invalid selection!!!");
        }
    }

    static void display() {
        List<Issue> issues = issueService.searchIssue(
                AppConstant.DEFAULT_PAGE,
                AppConstant.DEFAULT_SIZE,
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty()
        );
        displayIssues(issues);
        List<Integer> choices = List.of(1, 2, 3);
        while (true) {
            int choice = getInt("1. Details  2. Filter 3. Back\nSelect Option:");
            if (choices.contains(choice)) {
                if (choice == 1) {
                    issueDetails(getIssueFromList(issues));
                } else if (choice == 2) {
                    searchIssue();
                }
                return;
            } else {
                System.out.println("Invalid Input");
            }
        }
    }

    public static void issueMenu() {
        while (true) {
            System.out.println("\nIssue Menu:");
            System.out.println("1. Create");
            System.out.println("2. View(Update or Remove)");
            System.out.println("5. Main menu");
            int choice = ProjectInput.getInt("Enter your choice:");

            if (choice == 1) {
                createNewIssue();
            } else if (choice == 2) {
                display();
            }
        }
    }
}
