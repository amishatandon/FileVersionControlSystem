import java.io.*;
import java.util.*;

public class VersionControl implements Serializable {
    private LinkedList<Commit> commits; // List of commits
    private HashMap<String, String> files; // Current working files
    private HashMap<String, String> stagedFiles; // Staging area for files to be committed
    private static final String FILE_NAME = "version_control_data.ser"; // For file persistence

    public VersionControl() {
        this.commits = new LinkedList<>();
        this.files = new HashMap<>();
        this.stagedFiles = new HashMap<>();
        loadData(); // Load persisted data if available
    }

    // Add a file or modify existing, stage it for commit
    public void addFile(String fileName, String content) {
        stagedFiles.put(fileName, content);
        System.out.println("File " + fileName + " added/modified and staged for commit.");
    }

    // Remove file from current state and stage it for deletion
    public void removeFile(String fileName) {
        if (files.containsKey(fileName)) {
            stagedFiles.put(fileName, null); // Mark file as deleted in the next commit
            System.out.println("File " + fileName + " marked for deletion.");
        } else {
            System.out.println("File " + fileName + " does not exist.");
        }
    }

    // Commit the staged files
    public void commit(String message) {
        if (stagedFiles.isEmpty()) {
            System.out.println("No changes to commit.");
            return;
        }

        // Apply staged changes to current files
        for (String fileName : stagedFiles.keySet()) {
            String content = stagedFiles.get(fileName);
            if (content == null) {
                files.remove(fileName); // Delete file if marked null
            } else {
                files.put(fileName, content); // Update or add file
            }
        }

        String commitId = UUID.randomUUID().toString();
        Commit newCommit = new Commit(commitId, message, files);
        commits.add(newCommit);
        stagedFiles.clear(); // Clear the staging area after commit
        System.out.println("Committed with ID: " + commitId);

        saveData(); // Save the updated commit history to disk
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
                stagedFiles.clear(); // Clear any staged changes after checkout
                System.out.println("Checked out to commit ID: " + commitId);
                saveData(); // Save the updated file state after checkout
                return;
            }
        }
        System.out.println("Commit ID not found.");
    }

    // Show the current working files and staged changes
    public void status() {
        System.out.println("Current Files:");
        for (String file : files.keySet()) {
            System.out.println(" - " + file + ": " + files.get(file));
        }

        if (!stagedFiles.isEmpty()) {
            System.out.println("\nStaged Changes:");
            for (String file : stagedFiles.keySet()) {
                String content = stagedFiles.get(file);
                if (content == null) {
                    System.out.println(" - " + file + " (Deleted)");
                } else {
                    System.out.println(" - " + file + " (Modified/Added): " + content);
                }
            }
        } else {
            System.out.println("\nNo staged changes.");
        }
    }

    // Show differences between two commits
    public void diff(String commitId1, String commitId2) {
        Commit commit1 = findCommitById(commitId1);
        Commit commit2 = findCommitById(commitId2);

        if (commit1 == null || commit2 == null) {
            System.out.println("One or both commit IDs not found.");
            return;
        }

        System.out.println("Diff between " + commitId1 + " and " + commitId2 + ":");
        HashMap<String, String> files1 = commit1.getFileVersions();
        HashMap<String, String> files2 = commit2.getFileVersions();

        for (String file : files1.keySet()) {
            if (!files2.containsKey(file)) {
                System.out.println("File " + file + " was deleted in commit " + commitId2);
            } else if (!files1.get(file).equals(files2.get(file))) {
                System.out.println("File " + file + " was modified:");
                System.out.println("  Commit " + commitId1 + ": " + files1.get(file));
                System.out.println("  Commit " + commitId2 + ": " + files2.get(file));
            }
        }

        for (String file : files2.keySet()) {
            if (!files1.containsKey(file)) {
                System.out.println("File " + file + " was added in commit " + commitId2);
            }
        }
    }

    // Helper method to find commit by ID
    private Commit findCommitById(String commitId) {
        for (Commit commit : commits) {
            if (commit.getCommitId().equals(commitId)) {
                return commit;
            }
        }
        return null;
    }

    // Save version control data to file
    private void saveData() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(this);
            System.out.println("Version control data saved.");
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    // Load version control data from file
    private void loadData() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            VersionControl loadedData = (VersionControl) in.readObject();
            this.commits = loadedData.commits;
            this.files = loadedData.files;
            this.stagedFiles = loadedData.stagedFiles;
            System.out.println("Version control data loaded.");
        } catch (FileNotFoundException e) {
            System.out.println("No saved data found, starting fresh.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
    }
}
