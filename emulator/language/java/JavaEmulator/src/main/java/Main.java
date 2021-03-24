import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.text.SimpleDateFormat;
import java.util.*;

public class Main {
    static MqttClient mqttClient;

    static  Calendar calendar=Calendar.getInstance();

    static HashMap<String,Object> params=new HashMap<>();

    static SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");

    public static void main(String[] args) throws MqttException {

        Arrays.stream(args).forEach(s->{
            var p=  s.split("=");
            params.put(p[0],p[1]);
        });
        var option = new MqttConnectOptions();
        option.setCleanSession(false);
        option.setAutomaticReconnect(true);

        System.out.println(args);

        mqttClient = new MqttClient((String) params.get("broker"), "java", new MemoryPersistence());
        mqttClient.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable throwable) {

            }

            @Override
            public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {

            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

            }
        });
        mqttClient.connect(option);
        var topic=String.format("/%s/%s",(String) params.get("productSn"),(String) params.get("deviceSn"));
        System.out.println("订阅消息"+topic);
        mqttClient.subscribe(topic, (s, mqttMessage) -> {
            System.out.printf("time: %s topic %s payload: %s \r\n",format.format(calendar.getTime()),s, mqttMessage.toString());
        });


        try {
            mqttClient.publish(topic,new MqttMessage(new Action("registered","device",0).toJson()));
        } catch (MqttException e) {
            e.printStackTrace();
        }

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    var rand=new Random();
                    mqttClient.publish(topic,new MqttMessage(new Action("property","temperature", rand.nextInt(100)).toJson()));
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            }
        }, 5000,  5 * 1000);
    }
}
