import mqtt from 'mqtt'
import eventHub from './eventhub'
console.log(process.env.VUE_APP_MQTT)
var client  = mqtt.connect(process.env.VUE_APP_MQTT,{port:4202})

client.on('connect', function () {
  console.log("mqtt连接成功")
})

client.on('message', function (topic, message) {
  console.log(topic,message)
  eventHub.emit(topic,message)
})

export default client