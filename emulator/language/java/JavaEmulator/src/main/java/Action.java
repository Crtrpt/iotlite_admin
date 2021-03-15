import java.nio.charset.StandardCharsets;

public class Action {
    String action;
    String name;
    Integer payload;
    public Action(String action, String name, Integer s) {
        this.action=action;
        this.name=name;
        this.payload=s;
    }
    public byte[] toJson(){
        var msg= String.format("{\"action\":\"%s\",\"payload\":%s,\"name\":\"%s\"}",this.action,this.payload,this.name);
        return msg.getBytes(StandardCharsets.UTF_8);
    }
}
