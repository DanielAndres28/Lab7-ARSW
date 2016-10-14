/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.msgbroker.model;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author 2087052
 */
@RestController
@RequestMapping(value = "/puntos")
public class PointController {
    @Autowired
    SimpMessagingTemplate msgt;
    @Autowired
    PointDriver pd;
    
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> manejadorPostRecursos(@RequestBody Point pt) {
        try {
            System.out.println("Nuevo punto recibido en el REST!:" + pt);
            pd.addPoint(pt);
            msgt.convertAndSend("/topic/newpoint", pt);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception ex) {
            Logger.getLogger(STOMPMessagesHandler.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error", HttpStatus.FORBIDDEN);
        }
    }

}
