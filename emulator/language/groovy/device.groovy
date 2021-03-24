// https://mvnrepository.com/artifact/org.eclipse.paho/org.eclipse.paho.client.mqttv3

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.MqttCallback
@Grab(group='org.eclipse.paho', module='org.eclipse.paho.client.mqttv3', version='1.2.5')
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;


static void main(String[] args) {
    def option = new MqttConnectOptions();
    option.setCleanSession(false);
    option.setAutomaticReconnect(true);


    def mqttClient = new MqttClient("tcp://broker.emqx.io", "xx1", new MemoryPersistence());


    print("连接")
    mqttClient.setCallback(new MqttCallback() {
        @Override
        void connectionLost(Throwable throwable) {
            System.out.print("connecttion lost")
        }

        @Override
        void messageArrived(String s, MqttMessage msg) throws Exception {
            System.out.print(msg.toString())
        }

        @Override
        void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

        }
    })
    mqttClient.connected
    mqttClient.connect(option);
    mqttClient.subscribe("test/productSn/deviceSn");
    mqttClient.publish("test/productSn/deviceSn",new MqttMessage("aaa".getBytes()))
}