/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.snowy;

import com.vaadin.server.ExternalResource;
import com.vaadin.server.FileResource;
import com.vaadin.server.Page;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.BrowserFrame;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.JavaScript;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Snowy
 */
public class GameWindow extends Panel{
    private TabSheet games,main;
    private Requests rq=new Requests();
    HashMap<Integer,Game> gm = new HashMap<>();
    ArrayList<Integer> gamesInited = new ArrayList<>();
    public GameWindow(){
        games = new TabSheet();
        main = new TabSheet();
        games.addSelectedTabChangeListener(e->{
           ((Game)e.getTabSheet().getSelectedTab()).rendered =false;
        });
        main.addSelectedTabChangeListener(e->{
            if(!e.getTabSheet().getSelectedTab().equals(games)){
                //((Game)games.getSelectedTab()).rendered=false;
                games.iterator().forEachRemaining(s->{
                    //Logger.getLogger(GameWindow.class.getName()).info(s.toString());
                    ((Game)s).rendered=false;
                });
            }
        });
        main.setSizeFull();
        games.setSizeFull();
        main.addTab(rq,"Requests");
        main.addTab(games,"Games");
        setContent(main);
    }
    public void updateRequests(){
        rq.updateRequests();
        
        //Logger.getLogger(GameWindow.class.getName()).info(gm.get(0).isConnectorEnabled()+"");
    }
    public void updateGames(){
        gm.forEach((k,v)->{
            v.CheckForUpdate();
        });
    }

    public void initGame(String requestor,String requested,int requestId) {
        
        //int i =
        new data().startGame(requestor,requested,requestId);
        //if(!gm.containsKey(i) && i!=0){
        //    gm.put(i, new Game(i));
        //}
        
        //if(i!=-1){
         //   ((PostLoginView)this.getUI().getContent()).getChatWindow().addChat(i);
         //   gm.put(i, new Game(i));
        //}
        
    }
    public void checkForNewGames(){
        ArrayList<Integer> availableGames = new data().getRecientGames();
        availableGames.forEach(e->{
            if(!gm.containsKey(e)){
                addGame(e);
            }
        });
    }
    public void addGame(int gameId){
        
        gm.put(gameId, new Game(gameId));
        games.addTab(gm.get(gameId),"Game "+(gm.size()));
    }
    public ArrayList<Integer> gameIds(){
        ArrayList<Integer> arr = new ArrayList<>();
        gm.forEach((k,v)->{
            arr.add(k);
        });
        return arr;
    }
}
