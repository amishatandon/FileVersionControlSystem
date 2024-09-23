import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

public class Commit implements Serializable {
    private static final long serialVersionUID = 1L; // Recommended for Serializable classes
    private String commitId;
    private String message;
    private Date timestamp;
    private HashMap<String, String> fileVersions; // File name -> File content

    public Commit(String commitId, String message, HashMap<String, String> fileVersions) {
        this.commitId = commitId;
        this.message = message;
        this.timestamp = new Date();
        this.fileVersions = new HashMap<>(fileVersions); // Snapshot of current files
    }

    public String getCommitId() {
        return commitId;
    }

    public String getMessage() {
        return message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public HashMap<String, String> getFileVersions() {
        return fileVersions;
    }

    public void printCommit() {
        System.out.println("Commit ID: " + commitId);
        System.out.println("Message: " + message);
        System.out.println("Timestamp: " + timestamp);
        System.out.println("Files:");
        for (String file : fileVersions.keySet()) {
            System.out.println(" - " + file + ": " + fileVersions.get(file));
        }
    }
}
