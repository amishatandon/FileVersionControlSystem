import java.util.Scanner;

public class FileVersionContolSystem {
    public static void main(String[] args) {
        System.out.println("***** THIS IS NEW CODE *****");
        VersionControl vc = new VersionControl();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nCommands: add, remove, commit, log, checkout, status, diff, branch, switch, reset, merge, exit");
            System.out.print("Enter command: ");
            String command = scanner.nextLine();

            switch (command) {

                case "add":
                    System.out.print("File name: ");
                    vc.addFile(scanner.nextLine());
                    break;

                case "remove":
                    System.out.print("File name: ");
                    vc.removeFile(scanner.nextLine());
                    break;

                case "commit":
                    System.out.print("Message: ");
                    vc.commit(scanner.nextLine());
                    break;

                case "log":
                    vc.log();
                    break;

                case "checkout":
                    System.out.print("Commit ID: ");
                    vc.checkout(scanner.nextLine());
                    break;

                case "status":
                    vc.status();
                    break;

                case "diff":
                    System.out.print("Commit 1: ");
                    String c1 = scanner.nextLine();
                    System.out.print("Commit 2: ");
                    String c2 = scanner.nextLine();
                    vc.diff(c1, c2);
                    break;

                case "branch":
                    System.out.print("Branch name: ");
                    vc.createBranch(scanner.nextLine());
                    break;

                case "switch":
                    System.out.print("Branch name: ");
                    vc.switchBranch(scanner.nextLine());
                    break;

                case "reset":
                    System.out.print("Commit ID: ");
                    vc.reset(scanner.nextLine());
                    break;

                case "merge":
                    System.out.print("Source branch: ");
                    vc.merge(scanner.nextLine());
                    break;

                case "exit":
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid command.");
            }
        }
    }
}