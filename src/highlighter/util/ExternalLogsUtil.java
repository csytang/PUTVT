package highlighter.util;

public class ExternalLogsUtil {
    private static ExternalLogsUtil instance;
    private Boolean beingUsed = false;
    private String logs = "";

    public static ExternalLogsUtil getInstane(){
        if (instance == null){
            instance = new ExternalLogsUtil();
        }
        return instance;
    }

    public void concatLogs(String str){
        logs = logs.concat(str);
        logs = logs.trim();
        if (!logs.substring(logs.length(),logs.length()).equals("\n")) {
            logs = logs.concat("\n");
        }
    }

    public String getLogs(){
        return this.logs;
    }

    public Boolean getBeingUsed() {
        return beingUsed;
    }

    public void cleanLogs(){
        logs = "";
    }

    public void setBeingUsed(Boolean beingUsed) {
        this.beingUsed = beingUsed;
    }
}

