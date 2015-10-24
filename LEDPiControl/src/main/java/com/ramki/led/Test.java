/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ramki.led;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 *
 * @author ramki
 */
public class Test {

    public static void main(String[] args) throws MqttException {

        MqttClient mqttClient = new MqttClient("tcp://ec2-52-89-108-152.us-west-2.compute.amazonaws.com:1883", //1
                "refcard-client1");

        mqttClient.connect();
        mqttClient.publish(
                "my/led", //3
                "message".getBytes(), //4
                2, //5
                false); //6
        mqttClient.disconnect();

    }

}
