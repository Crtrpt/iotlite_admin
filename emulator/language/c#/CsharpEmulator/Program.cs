using System;
using System.Collections.Generic;
using System.Net;
using System.Text;
using uPLibrary.Networking.M2Mqtt;
using uPLibrary.Networking.M2Mqtt.Messages;

namespace CsharpEmulator
{
    class Program
    {
        private static Dictionary<string, string> parms = new Dictionary<String, String>();
        private static MqttClient client;

        static void Main(string[] args)
        {
            parms = new Dictionary<String,String>();
            parms["broker"] = "";
            parms["productSn"] = "0001";
            parms["deviceSn"] = "00001";

            foreach (var item in args)
            {
                Console.WriteLine(item);
                var pars = item.Split("=");
                parms[pars[0]] = pars[1];
            }
            client = new MqttClient(parms["broker"]);
            client.MqttMsgPublishReceived += client_MqttMsgPublishReceived;
            client.MqttMsgSubscribed += Client_MqttMsgSubscribed;
            string clientId = Guid.NewGuid().ToString();
            Console.WriteLine("connect..." + parms["broker"]);
            client.Connect(clientId);
            if (client.IsConnected) {
                Console.WriteLine("connect success");
            }
            var topic = parms["productSn"] + "/" + parms["deviceSn"];
            Console.WriteLine("subscribe" + topic);
            client.Subscribe(new String[] { topic }, new byte[] { MqttMsgBase.QOS_LEVEL_EXACTLY_ONCE });

            Console.ReadKey();
           
        }

        private static void Client_MqttMsgSubscribed(object sender, MqttMsgSubscribedEventArgs e)
        {
            var topic = parms["productSn"] + "/" + parms["deviceSn"];
            var msg = Encoding.ASCII.GetBytes("hello iot");
            Console.WriteLine("broker subscrib {0} {1}", topic, System.Text.Encoding.ASCII.GetString(msg));
            client.Publish(topic, Encoding.ASCII.GetBytes("hello iot"));
        }

        private static void client_MqttMsgPublishReceived(object sender, MqttMsgPublishEventArgs e)
        {
            
            Console.WriteLine("收到mqtt消息"+ e.Message);
        }
    }
}
