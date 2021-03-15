using Newtonsoft.Json;
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
            parms["productSn"] = "language";
            parms["deviceSn"] = "csharp";

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
            var topic = "/"+parms["productSn"] + "/" + parms["deviceSn"];
            Console.WriteLine("{0} subscribe topic:{1}", DateTime.Now.ToString(), topic);
            client.Subscribe(new String[] { topic }, new byte[] { MqttMsgBase.QOS_LEVEL_EXACTLY_ONCE });
            Console.ReadKey();
        }

        private static void Client_MqttMsgSubscribed(object sender, MqttMsgSubscribedEventArgs e)
        {
            var topic = parms["productSn"];

            var action = new Action
            {
                action = "registered",
                name="device",
            };
            string payload = JsonConvert.SerializeObject(action);

            var msg = Encoding.ASCII.GetBytes(payload);
            Console.WriteLine("{2} topic:{0} payload:{1}", topic, System.Text.Encoding.ASCII.GetString(msg), DateTime.Now.ToString());
            client.Publish(topic, msg);
            var  t = new System.Timers.Timer(5000);
            t.Elapsed += T_Elapsed;
            t.AutoReset = true;
            t.Enabled = true;
        }

        private static void T_Elapsed(object sender, System.Timers.ElapsedEventArgs e)
        {
            var topic = "/" + parms["productSn"] + "/" + parms["deviceSn"];
            var action = new Action
            {
                action = "property",
                name= "temperature",
                payload = new Random().Next(1,100)
            };
            string payload = JsonConvert.SerializeObject(action);

            var msg = Encoding.ASCII.GetBytes(payload);
            Console.WriteLine("{2} topic:{0} payload:{1}", topic, System.Text.Encoding.ASCII.GetString(msg), DateTime.Now.ToString());
            client.Publish(topic, msg);
        }

        private static void client_MqttMsgPublishReceived(object sender, MqttMsgPublishEventArgs e)
        {
            Console.WriteLine("{0} topic:{1}  payload:{2}", DateTime.Now.ToString(),e.Topic, Encoding.ASCII.GetString(e.Message));
        }
    }
}
