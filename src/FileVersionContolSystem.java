import java.util.Scanner;

public class FileVersionContolSystem {
    public static void main(String[] args) {
        VersionControl vc = new VersionControl();
        Scanner scanner = new Scanner(System.in);
        String command;

        while (true) {
            System.out.println("\nCommands: add, commit, log, checkout, status, exit");
            System.out.print("Enter command: ");
            command = scanner.nextLine();

            switch (command) {
                case "add":
                    System.out.print("Enter file name: ");
                    String fileName = scanner.nextLine();
                    System.out.print("Enter file content: ");
                    String content = scanner.nextLine();
                    vc.addFile(fileName, content);
                    break;

                case "commit":
                    System.out.print("Enter commit message: ");
                    String message = scanner.nextLine();
                    vc.commit(message);
                    break;

                case "log":
                    vc.log();
                    break;

                case "checkout":
                    System.out.print("Enter commit ID: ");
                    String commitId = scanner.nextLine();
                    vc.checkout(commitId);
                    break;

                case "status":
                    vc.status();
                    break;

                case "exit":
                    System.out.println("Exiting the version control system.");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid command. Try again.");
                    break;
            }
        }
    }
}
