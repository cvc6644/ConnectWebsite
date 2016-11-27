/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.snowy;

import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.BrowserFrame;
import com.vaadin.ui.Button;
import com.vaadin.ui.JavaScript;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Snowy
 */
public class Game extends VerticalLayout{
    private JavaScript js = JavaScript.getCurrent();
    private String path = "./src/main/webapp/game.js";
    private int gameId,p1,p2,currentTurn;
    
    private boolean hasUpdate =false;
    public boolean rendered =false;
    private ArrayList<ArrayList<Integer>> gameBoard;
    public Game(int id){ 
        gameId = id;
        
       CheckForUpdate();
       this.setId("GameForMe");
       
       /*this.addComponent(new Button("c",e->{
           create();
           //js.execute("hello();");
       }));*/
       
       this.setSizeFull();
      
    }
    public int getGameId(){
        return gameId;
    }
    public void create(){
        try {
            Path p = new File(path).toPath();
            File f =new File("./src/main/webapp/game.js");
            String s =new String(Files.readAllBytes(p));
            js.execute(s);
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void CheckForUpdate(){
        data d = new data();
        ArrayList<ArrayList<Integer>> tempGameBoard = d.getGameBoard(gameId);
        if(!tempGameBoard.equals(gameBoard)){
            hasUpdate =true;
            gameBoard = tempGameBoard;
            p1 = d.getPlayerOne(gameId);
            p2 = d.getPlayerTwo(gameId);
            currentTurn = d.getLastTurn(gameId)==0? p1: d.getLastTurn(gameId);
        }
        if(this.isConnectorEnabled()&& hasUpdate && !rendered){
            Update();
            hasUpdate =false;
            rendered =true;
        }else if(this.isConnectorEnabled() && !rendered){
            renderCurrent();
            this.rendered =true;
        }else if(!this.isConnectorEnabled() && rendered){
            rendered =false;
        }
        
    }
    public void renderCurrent(){
        String s = ""+
        "var ss = document.getElementById(\"GameForMe\").lastChild;"+
        "var svgns = \"http://www.w3.org/2000/svg\";"+
        "var svg = document.createElement(\"p\");\n" +
        "svg.innerHTML=\"hello world\";\n" +
        "ss.appendChild(svg);";
        js.execute(s);
    }
    public void Update(){
        //create();
        //Logger.getLogger(Game.class.getName()).info("ran update");
        String s = ""+
        "var ss = document.getElementById(\"GameForMe\").lastChild;"+
        "var svgns = \"http://www.w3.org/2000/svg\";"+
        "var svg = document.createElement(\"p\");\n" +
        "svg.innerHTML=\"hello world\";\n" +
        "ss.appendChild(svg);";
        js.execute(s);
        
    }
}
