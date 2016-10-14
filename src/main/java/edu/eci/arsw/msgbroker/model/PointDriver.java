/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.msgbroker.model;

import java.util.concurrent.ConcurrentLinkedQueue;
import org.springframework.stereotype.Service;

/**
 *
 * @author 2087052
 */
@Service
public class PointDriver {
    public static ConcurrentLinkedQueue<Point> points=new ConcurrentLinkedQueue<>();
    
    public void addPoint(Point p){
        points.add(p);
    }
    public ConcurrentLinkedQueue<Point> getPoints(){
        return points;
        
    }
}
