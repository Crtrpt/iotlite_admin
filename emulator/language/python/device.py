#!/usr/bin/python
# -*- coding: UTF-8 -*-

import paho.mqtt.client as mqtt
import json
import logging

logging.basicConfig(format='%(asctime)s %(message)s')

import sys
reload(sys)
sys.setdefaultencoding("utf-8")

def on_connect(client, userdata, flags, rc):
    client.subscribe("default/3/10")

def on_message(client, userdata, msg):
    print("↓"+msg.topic+" "+str(msg.payload))
    
    cmd=json.loads(msg.payload)
    
    response= {'code':0,'v':cmd['v'],'action':'response'}
    
    data=json.dumps(response)
    print("↑"+"response "+data)
    client.publish("/default/3/温度计10",payload=data,qos=2,retain=False)
    client.publish("/default/3/温度计10",payload=data,qos=2,retain=False)
    client.publish("/default/3/温度计10",payload=data,qos=2,retain=False)



client = mqtt.Client(client_id="device")
client.on_connect = on_connect
client.on_message = on_message


client.connect("127.0.0.1", 41884, 60)

client.loop_forever()