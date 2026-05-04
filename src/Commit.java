import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

public class Commit implements Serializable {
    private String commitId;
    private String message;
    private Date timestamp;
    private HashMap<String, String> fileVersions;

    public Commit(String id, String msg, HashMap<String, String> files) {
        this.commitId = id;
        this.message = msg;
        this.timestamp = new Date();
        this.fileVersions = new HashMap<>(files);
    }

    public String getCommitId() {
        return commitId;
    }

    public HashMap<String, String> getFileVersions() {
        return fileVersions;
    }

    public void printCommit() {
        System.out.println("ID: " + commitId);
        System.out.println("Msg: " + message);
        System.out.println("Time: " + timestamp);

        fileVersions.forEach((k, v) ->
                System.out.println(" - " + k + ": " + v));
    }
}