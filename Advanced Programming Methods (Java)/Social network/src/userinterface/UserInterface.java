package userinterface;

import exceptions.RepositoryException;
import exceptions.ValidationException;
import model.entities.Friendship;
import model.entities.User;
import service.SuperService;

import java.util.Collection;
import java.util.Scanner;

public class UserInterface {
    private final SuperService superService;

    public UserInterface(SuperService superService) {
        this.superService = superService;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            displayMenu();
            System.out.print("Enter command: ");
            String command = scanner.nextLine();
            try {
                switch (command) {
                    case "1":
                        addUser();
                        break;
                    case "2":
                        removeUser();
                        break;
                    case "3":
                        displayUsers();
                        break;
                    case "4":
                        addFriendship();
                        break;
                    case "5":
                        removeFriendship();
                        break;
                    case "6":
                        displayFriendships();
                        break;
                    case "7":
                        displayNumberOfCommunities();
                        break;
                    case "0":
                        return;
                }
                Thread.sleep(1000);
            }
            catch (Exception e) {
                System.out.println(e.getMessage() + "\n");
            }
        }
    }

    private void displayMenu() {
        System.out.println(
                """
                MENU
                1. Add user
                2. Remove user
                3. Display users
                4. Add friendship
                5. Remove friendship
                6. Display friendships
                7. Display number of communities
                0. Exit
                """);
    }

    private void addUser() throws ValidationException, RepositoryException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();
        superService.addUser(firstName, lastName);
        System.out.println("User added successfully.\n");
    }

    private void removeUser() throws RepositoryException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the identifier of the user: ");
        Integer userId = Integer.parseInt(scanner.nextLine());
        superService.removeUser(userId);
        System.out.println("User removed successfully.\n");
    }

    private void displayUsers() {
        Collection<User> users = superService.getUsers();
        if (users.isEmpty())
            System.out.println("There are no users in the repository.\n");
        else {
            System.out.println("Users:");
            users.forEach(System.out::println);
            System.out.println();
        }
    }

    private void addFriendship() throws ValidationException, RepositoryException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the identifier of the first user: ");
        Integer firstUserId = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter the identifier of the second user: ");
        Integer secondUserId = Integer.parseInt(scanner.nextLine());
        superService.addFriendship(firstUserId, secondUserId);
        System.out.println("Friendship added successfully.\n");
    }

    private void removeFriendship() throws RepositoryException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the identifier of the friendship: ");
        Integer friendshipId = Integer.parseInt(scanner.nextLine());
        superService.removeFriendship(friendshipId);
        System.out.println("Friendship removed successfully.\n");
    }

    private void displayFriendships() {
        Collection<Friendship> friendships = superService.getFriendships();
        if (friendships.isEmpty())
            System.out.println("There are no friendships in the repository.\n");
        else {
            System.out.println("Friendships:");
            friendships.forEach(System.out::println);
            System.out.println();
        }
    }

    private void displayNumberOfCommunities() {
        Integer numberOfCommunities = superService.getNumberOfCommunities();
        System.out.println("Numarul de comunitati este egal cu " + numberOfCommunities + ".");
    }
}
