# bender-bridge

## configure
*install RXTX
```
mkdir /var/lock
chmod go+rwx /var/lock
```


# run MQTT
```
docker run -it -p 1883:1883 -p 9001:9001 eclipse-mosquitto
```
