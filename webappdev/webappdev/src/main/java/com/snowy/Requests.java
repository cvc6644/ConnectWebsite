/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.snowy;

import com.vaadin.annotations.Theme;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import java.util.ArrayList;

import java.util.logging.Logger;
/**
 *
 * @author Snowy
 */
@Theme("valo")
public class Requests extends Accordion{
    ArrayList<ArrayList<String>> dl = new ArrayList<>();
    
    private int x = 0;
    public Requests(){
       
        this.setImmediate(true);
        
    }
    public void updateRequests(){
        if(this.getComponentCount()>0){
            x = Integer.parseInt(this.getSelectedTab().getId().substring(3));
            //Logger.getLogger(Requests.class.getName()).info(this.getSelectedTab().getId());
        }
        this.removeAllComponents();
        data d = new data();
        ArrayList<ArrayList<String>> al =d.retriveChallenges();
        
        int i=0;
        for(ArrayList<String> ss : al){
            
            
            
            
            VerticalLayout vl = new VerticalLayout();
            HorizontalLayout hl = new HorizontalLayout();
                if((!d.getUsernameFromToken().equals(ss.get(0))|| (d.getUsernameFromToken().equals(ss.get(0)) && d.getUsernameFromToken().equals(ss.get(1)))) && Integer.parseInt(ss.get(3))==0){
                    vl.addComponent(new Label("You have been challenged by "+ss.get(0)+" on "+new java.sql.Timestamp(Long.parseLong(ss.get(2))).toString()));
                    hl.addComponent(new Button("Accept",ee->{
                        new data().acceptRefuse(Integer.parseInt(ss.get(4)),true);
                    }));
                    hl.addComponent(new Button("Refuse",rr->{
                        new data().acceptRefuse(Integer.parseInt(ss.get(4)),false);
                    }));
                    vl.addComponent(hl);
                }else{
                    vl.addComponent(new Label("You challenged "+ss.get(1)+" on "+new java.sql.Timestamp(Long.parseLong(ss.get(2))).toString()));
                    
                    switch(Integer.parseInt(ss.get(3))){
                        case 0:
                            vl.addComponent(new Label("Status: Awaiting Response"));
                            break;
                        case 1:
                            PostLoginView plv = ((PostLoginView)this.getUI().getContent());
                            vl.addComponent(new Label("Status: Accepted"));
                            plv.getGameWindow().initGame(ss.get(0),ss.get(1),Integer.parseInt(ss.get(4)));
                                //Logger.getLogger(Requests.class.getName()).info(ss.get(4));
                            int gameId =new data().getGameIdfromRequest(Integer.parseInt(ss.get(4)));
                            if(plv.getGameWindow().gameIds().contains(gameId)!=true &&
                                    plv.getChatWindow().getChatIds().contains(gameId)!=true){
                                plv.getChatWindow().addChat(gameId);
                                //add game
                            }    
                            
                            //Logger.getLogger(Requests.class.getName()).info(plv.getGameWindow().gameIds().get(0)+" | "+ss.get(4));
                            
                            break;
                        case 2:
                            vl.addComponent(new Label("Status: Refused"));
                            break;
                    }
                    
                }
                vl.setSpacing(true);
                hl.setSpacing(true);
                vl.setMargin(true);
                vl.setId("set"+al.indexOf(ss));
                this.addTab(vl,ss.get(0)+"\t"+new java.sql.Timestamp(Long.parseLong(ss.get(2))).toString());
                //this.getTab(vl).setId("set"+i);
                i++;
            
        }
        this.setSelectedTab(x);
    }
}
