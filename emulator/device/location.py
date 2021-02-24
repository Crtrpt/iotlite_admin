#!/usr/bin/python
# -*- coding: UTF-8 -*-

import paho.mqtt.client as mqtt
import json
import time
import logging

logging.basicConfig(format='%(asctime)s %(message)s')

import sys
reload(sys)
sys.setdefaultencoding("utf-8")

# 发送设备的位置信息
def on_connect(client, userdata, flags, rc):
    response= {'code':0,'position':'121.48956298828126,31.209498105111447','action':'location'}
    topic= '/default/3/温度计10'
    data=json.dumps(response)
    print(topic)
    logging.warning('send location'+data)
    client.publish(topic,payload=data,qos=0,retain=False)

def on_message(client, userdata, msg):
    print("↓"+msg.topic+" "+str(msg.payload))

def on_disconnect(client, userdata, rc):
    print("断开连接")

client = mqtt.Client(client_id="location",clean_session=True, transport="tcp")
client.on_connect = on_connect
client.on_message = on_message
client.on_disconnect=on_disconnect

client.connect("127.0.0.1", 41883, 60)

print ("start..."+"127.0.0.1"+":"+str(41883))
client.loop_forever()