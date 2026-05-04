import java.io.*;
import java.util.*;

public class VersionControl implements Serializable {

    private Map<String, LinkedList<Commit>> branches;
    private String currentBranch;
    private String headCommitId;

    private HashMap<String, String> workingFiles;
    private HashMap<String, String> stagedFiles;

    private static final String FILE_NAME = "vc_data.ser";

    public VersionControl() {
        loadData();
    }

    // ADD (Git-like: ask content here)
    public void addFile(String fileName) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter content: ");
        String content = sc.nextLine();

        workingFiles.put(fileName, content);
        stagedFiles.put(fileName, content);

        System.out.println("Staged: " + fileName);
    }

    // REMOVE
    public void removeFile(String fileName) {
        if (workingFiles.containsKey(fileName)) {
            workingFiles.remove(fileName);
            stagedFiles.put(fileName, null);
            System.out.println("Marked for deletion: " + fileName);
        } else {
            System.out.println("File not found.");
        }
    }

    // COMMIT
    public void commit(String message) {
        if (stagedFiles.isEmpty()) {
            System.out.println("No changes to commit.");
            return;
        }

        String id = UUID.randomUUID().toString();
        Commit commit = new Commit(id, message, workingFiles);

        branches.get(currentBranch).add(commit);
        headCommitId = id;

        stagedFiles.clear();
        saveData();

        System.out.println("Committed: " + id);
    }

    // LOG
    public void log() {
        LinkedList<Commit> list = branches.get(currentBranch);

        if (list.isEmpty()) {
            System.out.println("No commits.");
            return;
        }

        for (Commit c : list) {
            System.out.println("----------------");
            c.printCommit();
        }
    }

    // CHECKOUT
    public void checkout(String id) {
        Commit c = findCommitById(id);
        if (c == null) {
            System.out.println("Commit not found.");
            return;
        }

        workingFiles = new HashMap<>(c.getFileVersions());
        stagedFiles.clear();
        headCommitId = id;

        System.out.println("Checked out: " + id);
    }

    // STATUS
    public void status() {
        System.out.println("Branch: " + currentBranch);

        System.out.println("\nWorking Files:");
        workingFiles.forEach((k, v) -> System.out.println(" - " + k + ": " + v));

        System.out.println("\nStaged Files:");
        if (stagedFiles.isEmpty()) {
            System.out.println("None");
        } else {
            stagedFiles.forEach((k, v) ->
                    System.out.println(" - " + k + (v == null ? " (Deleted)" : ""))
            );
        }
    }

    // DIFF
    public void diff(String id1, String id2) {
        Commit c1 = findCommitById(id1);
        Commit c2 = findCommitById(id2);

        if (c1 == null || c2 == null) {
            System.out.println("Invalid commit IDs.");
            return;
        }

        for (String file : c1.getFileVersions().keySet()) {
            String f1 = c1.getFileVersions().get(file);
            String f2 = c2.getFileVersions().get(file);

            if (f2 == null) {
                System.out.println(file + " deleted.");
                continue;
            }

            if (!f1.equals(f2)) {
                System.out.println("\nChanges in " + file);

                String[] l1 = f1.split("\n");
                String[] l2 = f2.split("\n");

                int max = Math.max(l1.length, l2.length);

                for (int i = 0; i < max; i++) {
                    String s1 = i < l1.length ? l1[i] : "";
                    String s2 = i < l2.length ? l2[i] : "";

                    if (!s1.equals(s2)) {
                        System.out.println("- " + s1);
                        System.out.println("+ " + s2);
                    }
                }
            }
        }
    }

    // BRANCH
    public void createBranch(String name) {
        branches.put(name, new LinkedList<>(branches.get(currentBranch)));
        System.out.println("Branch created: " + name);
    }

    public void switchBranch(String name) {
        if (!branches.containsKey(name)) {
            System.out.println("Branch not found.");
            return;
        }

        currentBranch = name;
        LinkedList<Commit> list = branches.get(name);

        if (!list.isEmpty()) {
            Commit latest = list.getLast();
            workingFiles = new HashMap<>(latest.getFileVersions());
            headCommitId = latest.getCommitId();
        }

        System.out.println("Switched to: " + name);
    }

    // RESET
    public void reset(String id) {
        Commit c = findCommitById(id);
        if (c == null) {
            System.out.println("Commit not found.");
            return;
        }

        workingFiles = new HashMap<>(c.getFileVersions());
        stagedFiles.clear();
        headCommitId = id;

        LinkedList<Commit> list = branches.get(currentBranch);
        while (!list.isEmpty() && !list.getLast().getCommitId().equals(id)) {
            list.removeLast();
        }

        System.out.println("Reset to: " + id);
    }

    // MERGE
    public void merge(String sourceBranch) {
        if (!branches.containsKey(sourceBranch)) {
            System.out.println("Branch not found.");
            return;
        }

        LinkedList<Commit> source = branches.get(sourceBranch);
        LinkedList<Commit> target = branches.get(currentBranch);

        if (source.isEmpty()) {
            System.out.println("Nothing to merge.");
            return;
        }

        HashMap<String, String> merged = new HashMap<>(workingFiles);

        Commit sourceLatest = source.getLast();

        for (String file : sourceLatest.getFileVersions().keySet()) {
            String s = sourceLatest.getFileVersions().get(file);
            String t = merged.get(file);

            if (t == null) {
                merged.put(file, s);
            } else if (!t.equals(s)) {
                String conflict = "<<<<<<< HEAD\n" + t +
                        "\n=======\n" + s +
                        "\n>>>>>>> " + sourceBranch;

                merged.put(file, conflict);
                System.out.println("Conflict in: " + file);
            }
        }

        String id = UUID.randomUUID().toString();
        Commit mergeCommit = new Commit(id, "Merge " + sourceBranch, merged);

        target.add(mergeCommit);
        workingFiles = merged;
        headCommitId = id;

        saveData();
        System.out.println("Merged successfully.");
    }

    private Commit findCommitById(String id) {
        for (Commit c : branches.get(currentBranch)) {
            if (c.getCommitId().equals(id)) return c;
        }
        return null;
    }

    private void saveData() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(branches);
            out.writeObject(currentBranch);
            out.writeObject(headCommitId);
        } catch (Exception e) {
            System.out.println("Save error.");
        }
    }

    private void loadData() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            branches = (Map<String, LinkedList<Commit>>) in.readObject();
            currentBranch = (String) in.readObject();
            headCommitId = (String) in.readObject();

            LinkedList<Commit> list = branches.get(currentBranch);

            workingFiles = list.isEmpty()
                    ? new HashMap<>()
                    : new HashMap<>(list.getLast().getFileVersions());

            stagedFiles = new HashMap<>();

        } catch (Exception e) {
            branches = new HashMap<>();
            currentBranch = "main";
            branches.put("main", new LinkedList<>());
            workingFiles = new HashMap<>();
            stagedFiles = new HashMap<>();
        }
    }
}