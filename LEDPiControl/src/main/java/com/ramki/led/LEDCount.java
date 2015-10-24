/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ramki.led;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author ramki
 */
public class LEDCount {

    public static void main(String[] args) {

        System.out.println("<--Pi4J--> GPIO Control Example ... started.");

        // create gpio controller
        final GpioController gpio = GpioFactory.getInstance();

        // provision gpio pin #01 as an output pin and turn on
        final GpioPinDigitalOutput redPin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_07, "RedLED");

        // set shutdown state for this pin
        redPin.setShutdownOptions(true, PinState.LOW);

           // provision gpio pin #01 as an output pin and turn on
        final GpioPinDigitalOutput greenPin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_15, "GreenLED");

        // set shutdown state for this pin
        greenPin.setShutdownOptions(true, PinState.LOW);

        try {
            MqttClient mqttClient = new MqttClient("tcp://ec2-52-89-108-152.us-west-2.compute.amazonaws.com:1883", //1
                    "refcard-client");

            mqttClient.setCallback(new MqttCallback() { //1
                @Override
                public void connectionLost(Throwable throwable) {
                    //Called when connection is lost.
                }

                @Override
                public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
                    //System.out.println("Topic: " + topic);
                    String str = new String(mqttMessage.getPayload());

                    if ("led/red".equals(topic)) {

                        if (str.trim().equals("true")) {
                            redPin.high();
                        } else {
                            redPin.low();
                        }
                    }

                     if ("led/green".equals(topic)) {

                        if (str.trim().equals("true")) {
                            greenPin.high();
                        } else {
                            greenPin.low();
                        }
                    }

                }

                @Override
                public void deliveryComplete(final IMqttDeliveryToken iMqttDeliveryToken) {
                    //When message delivery was complete
                }
            });
            mqttClient.connect();
            mqttClient.subscribe("led/#", 2);

            System.out.println("Waiting for Message....");
        } catch (Exception MqttException) {
            MqttException.printStackTrace();
        }

    }
}
