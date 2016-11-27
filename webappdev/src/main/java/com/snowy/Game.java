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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Snowy
 */
public class Game extends VerticalLayout{
    private JavaScript js = JavaScript.getCurrent();
    private String path = "./src/main/webapp/game.js";
    private int gameId;
    public Game(int id){ 
       this.setId("GameForMe");
       js.addFunction("ballstothewall", (e)->{
           js.execute("alert('hello')");
       });
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
    public void Update(){
        create();
    }
}
