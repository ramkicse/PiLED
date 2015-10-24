/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ramki.led.piled;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Model;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author ramki
 */

@Model
@SessionScoped
public class LedToggle implements Serializable{
    
    private boolean redLed;

    private boolean greenLed;
    
    MqttClient mqttClient;

    public LedToggle() throws MqttException {
        mqttClient = new MqttClient("tcp://ec2-52-89-108-152.us-west-2.compute.amazonaws.com:1883", //1
                "refcard-client1");
    }

  
    
    
      

    
    public void redSwitch() throws MqttException{
        
        mqttClient.connect();
        
        mqttClient.publish(
                "led/red", //3
                new String(isRedLed()+"").getBytes(), //4
                2, //5
                false); //6
        mqttClient.disconnect();
    }

      public void greenSwitch() throws MqttException{
        
        mqttClient.connect();
        
        mqttClient.publish(
                "led/green", //3
                new String(isGreenLed()+"").getBytes(), //4
                2, //5
                false); //6
        mqttClient.disconnect();
    }
      
    public boolean isRedLed() {
        return redLed;
    }

    public void setRedLed(boolean redLed) {
        this.redLed = redLed;
    }

    public boolean isGreenLed() {
        return greenLed;
    }

    public void setGreenLed(boolean greenLed) {
        this.greenLed = greenLed;
    }
    
    
}
