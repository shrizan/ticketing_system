package com.lambton;

import com.lambton.enums.issue.IssueStatus;
import com.lambton.enums.issue.IssueType;
import com.lambton.enums.issue.Priority;
import com.lambton.enums.project.ProjectType;
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

    static Project selectAProject() {
        while (true) {
            String projectTitle = getString("Project Title:");
            List<Project> projects = ProjectInput.projectService.search(0, 10, Optional.of(projectTitle), ProjectType.ENHANCEMENT);
            ProjectInput.searchProject(projects);
            String input = getString("S for search again. Or enter index for project to select");
            Project project = null;
            if (input.equalsIgnoreCase("s")) {
                continue;
            } else {
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
        while (true) {
            Project project = selectAProject();
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
        UserInput.addUsersToSuggestionList(users);
        issue.setAssignedTos(users);
        issueService.updateIssue(issue.getId(), issue);
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
                    IssueType.STORY
            );
        } else {
            IssueType issueType = getIssueType();
            issues = issueService.searchIssue(
                    0, 10,
                    Optional.of(title),
                    Optional.empty(),
                    Optional.empty(),
                    Optional.empty(),
                    issueType
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
            issueService.updateIssue(issue.getId(), issue);
            System.out.println("Parent linked");
        }
    }

    static void createNewIssue() {
        Issue issue = issueService.createIssue(getIssueInput());
        if (issue != null) {
            assignTo(issue);
            addParent(issue);
        }
        System.out.println("New Issue Created!!!");
    }

    static void displayList(List<Issue> issues) {
        System.out.println("SN \t\t | Title | \t\t | Description |");
        for (int i = 0; i < issues.size(); i++) {
            Issue issue = issues.get(i);
            System.out.printf("%d \t\t | %s | \t\t | %s |\n", i + 1, issue.getTitle(), issue.getDescription());
        }
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
        issueService.updateIssue(issue.getId(), updatedIssue);
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

    static void removeIssue(Issue issue, List<Issue> issues) {
        issueService.removeIssue(issue.getId(), issue.getIssueType());
        issues.remove(issue);
        displayList(issues);
    }

    static void display() {
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
                issueType
        );
        displayList(issues);
        int choice = 0;
        while (!(choice == 1 || choice == 2)) {
            choice = getInt("1. Update 1. Remove");
        }
        Issue issue = null;
        if (choice == 1) {
            choice = 0;
            while (true) {
                choice = getInt("Select a SN:");
                if (choice < 1 || choice > issues.size()) System.out.println("Invalid input!!!");
                else {
                    issue = issues.get(choice - 1);
                    break;
                }
            }
            updateIssue(issue);
        } else {
            choice = 0;
            while (true) {
                choice = getInt("Select a SN:");
                if (choice < 1 || choice > issues.size()) System.out.println("Invalid input!!!");
                else {
                    issue = issues.get(choice - 1);
                    break;
                }
            }
            removeIssue(issue, issues);
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
