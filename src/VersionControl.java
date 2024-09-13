import java.util.HashMap;
import java.util.LinkedList;
import java.util.UUID;

public class VersionControl {
    private LinkedList<Commit> commits; // List of commits
    private HashMap<String, String> files; // Current working files

    public VersionControl() {
        this.commits = new LinkedList<>();
        this.files = new HashMap<>();
    }

    // Add a file or modify existing
    public void addFile(String fileName, String content) {
        files.put(fileName, content);
        System.out.println("File " + fileName + " added/modified.");
    }

    // Commit the current state of files
    public void commit(String message) {
        String commitId = UUID.randomUUID().toString();
        Commit newCommit = new Commit(commitId, message, files);
        commits.add(newCommit);
        System.out.println("Committed with ID: " + commitId);
    }

    // Log all commits
    public void log() {
        if (commits.isEmpty()) {
            System.out.println("No commits found.");
            return;
        }
        System.out.println("Commit History:");
        for (Commit commit : commits) {
            System.out.println("-------------------");
            commit.printCommit();
        }
    }

    // Checkout to a specific commit
    public void checkout(String commitId) {
        for (Commit commit : commits) {
            if (commit.getCommitId().equals(commitId)) {
                files = new HashMap<>(commit.getFileVersions());
                System.out.println("Checked out to commit ID: " + commitId);
                return;
            }
        }
        System.out.println("Commit ID not found.");
    }

    // Show the current working files
    public void status() {
        System.out.println("Current Files:");
        for (String file : files.keySet()) {
            System.out.println(" - " + file + ": " + files.get(file));
        }
    }
}
