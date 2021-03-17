// https://mvnrepository.com/artifact/org.eclipse.paho/org.eclipse.paho.client.mqttv3

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.MqttCallback
@Grab(group = 'org.eclipse.paho', module = 'org.eclipse.paho.client.mqttv3', version = '1.2.5')
@Grab(group = 'com.google.code.gson', module = 'gson', version = '2.8.6')

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import com.google.gson.Gson;


static void main(String[] args) {
    def option = new MqttConnectOptions();
    option.setCleanSession(false);
    option.setAutomaticReconnect(true);

    def mqttClient = new MqttClient("tcp://127.0.0.1:41884", "xx1", new MemoryPersistence());

    print("连接")
    mqttClient.setCallback(new MqttCallback() {
        @Override
        void connectionLost(Throwable throwable) {

        }

        @Override
        void messageArrived(String s, MqttMessage msg) throws Exception {

        }

        @Override
        void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

        }
    })
    mqttClient.connected
    mqttClient.connect(option);
    mqttClient.subscribe("/default/5/HK07");
    def rand = Random.newInstance();


    while (true) {
        def data = new HashMap<>();
        data.put("action", "property");
        data.put("name", "humidity");
        data.put("value", rand.nextInt(100))
        mqttClient.publish("/default/5/HK07", new MqttMessage((new Gson()).toJson(data).getBytes()))

        data.put("action", "property");
        data.put("name", "power");
        data.put("value", rand.nextInt(100))
        mqttClient.publish("/default/5/HK07", new MqttMessage((new Gson()).toJson(data).getBytes()))
        sleep(1000)
    }
}