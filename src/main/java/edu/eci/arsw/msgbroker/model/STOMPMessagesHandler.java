/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.msgbroker.model;

import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

/**
 *
 * @author 2087052
 */
@Controller
public class STOMPMessagesHandler {
    @Autowired
    SimpMessagingTemplate msgt;
    List<Point> puntos = new LinkedList<>();

    @MessageMapping("/newpoint")    
    public void getLine(Point pt) throws Exception {
        System.out.println("Nuevo punto recibido en el servidor!:"+pt);
        puntos.add(pt);
        if(puntos.size()==4){
            msgt.convertAndSend("/topic/newpolygon", puntos);
            puntos.clear();
        }
        else{
            msgt.convertAndSend("/topic/newpoint", pt);
        }
        
    }
}
    

