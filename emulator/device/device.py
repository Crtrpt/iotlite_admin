#!/usr/bin/python
# -*- coding: UTF-8 -*-

import paho.mqtt.client as mqtt
import json

import sys
reload(sys)
sys.setdefaultencoding("utf-8")

def on_connect(client, userdata, flags, rc):
    print("connect")
    client.subscribe("default/3/温度计10")

# The callback for when a PUBLISH message is received from the server.
def on_message(client, userdata, msg):
    print("↓"+msg.topic+" "+str(msg.payload))
    
    cmd=json.loads(msg.payload)
    
    response= {'code':0,'v':cmd['v']}
    
    data=json.dumps(response);
    print("↑"+"response "+data)
    client.publish("/response/3/温度计10",payload=data,qos=2,retain=False)

client = mqtt.Client()
client.on_connect = on_connect
client.on_message = on_message

client.connect("broker.emqx.io", 1883, 60)

print ("start...")
client.loop_forever()