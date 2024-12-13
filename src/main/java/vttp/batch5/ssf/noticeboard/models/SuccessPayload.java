package vttp.batch5.ssf.noticeboard.models;

// task 4
public class SuccessPayload {

    private String id; 
    private long timestamp;
    
    public SuccessPayload() {
    }

    public SuccessPayload(String id, long timestamp) {
        this.id = id;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
    
}
