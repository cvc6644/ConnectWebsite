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
        gm.put(0, new Game(100));
        games.addTab(gm.get(0),"Test");
        games.addTab(new Label("l"),"test2");
        games.addSelectedTabChangeListener((TabSheet.SelectedTabChangeEvent e)->{
            if(!e.getTabSheet().getSelectedTab().toString().equals("l")){
                //Logger.getLogger(GameWindow.class.getName()).info(((Game)e.getTabSheet().getSelectedTab()).getGameId()+"");
                ((Game)e.getTabSheet().getSelectedTab()).create();
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
    public void addGame(int gameId){
        
    }
    public ArrayList<Integer> gameIds(){
        ArrayList<Integer> arr = new ArrayList<>();
        gm.forEach((k,v)->{
            arr.add(k);
        });
        return arr;
    }
}
