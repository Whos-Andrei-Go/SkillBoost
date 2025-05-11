package ph.edu.usc.skillboost.model;

public class HistoryLog {
    private String logId;
    private String uid;
    private String action;
    private long timestamp;

    public HistoryLog() {}

    public HistoryLog(String logId, String uid, String action, long timestamp) {
        this.logId = logId;
        this.uid = uid;
        this.action = action;
        this.timestamp = timestamp;
    }

    public String getLogId() { return logId; }
    public void setLogId(String logId) { this.logId = logId; }

    public String getUid() { return uid; }
    public void setUid(String uid) { this.uid = uid; }

    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }

    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
}
