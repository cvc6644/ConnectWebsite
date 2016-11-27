/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.snowy;

//import com.sun.istack.internal.logging.Logger;
import com.vaadin.event.FieldEvents.BlurEvent;
import com.vaadin.event.FieldEvents.BlurListener;
import com.vaadin.event.FieldEvents.FocusEvent;
import com.vaadin.event.FieldEvents.FocusListener;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import java.util.Collections;
import java.util.HashMap;

/**
 *
 * @author snowyowl
 */
public class Chat extends VerticalLayout{
    private TextArea tA = new TextArea();
    private TextField tf = new TextField();
    private int id,lastMessageId;
    //private java.sql.Date date;
    //private java.sql.Timestamp ts;
    public Chat(int id){
        this.id =id;
        tf.setSizeFull();
        //ts = new data().getCurrentSqlTimestamp();
        //Logger.getLogger(Chat.class).info(ts.toString());
        //tA.setValue("hello\n");
        lastMessageId = new data().lastMessageIdFromCurrent(id);
        HorizontalLayout fl = new HorizontalLayout();
        fl.addComponent(tf);
        Button b = new Button("Send");
        fl.addComponent(b);
        tf.setSizeFull();
        fl.setExpandRatio(tf, 1);
        b.addClickListener(e->{
            if(tf.getValue().length()!=0){
                new data().sendMessage(id,tf.getValue());
                
                tf.clear();
            }
           
        
        });
        /*tf.addTextChangeListener(e->{
            Logger.getLogger(Chat.class).info(e.getText().charAt(e.getText().length()-1)+"");
            if(e.getText().charAt(e.getText().length()-1)==10){
                b.click();
            }
        
        });*/
        tf.addFocusListener(new FocusListener() {
            @Override
            public void focus(final FocusEvent event) {
                b.setClickShortcut(KeyCode.ENTER);
            }

            
        });
        tf.addBlurListener(new BlurListener() {
            @Override
            public void blur(final BlurEvent event) {
                b.removeClickShortcut();
            }
        });
        fl.setSizeFull();
        fl.setHeightUndefined();
        tA.setSizeFull();
        //tA.setReadOnly(true);
        tf.setHeightUndefined();
        tA.setWordwrap(true);
        tA.setImmediate(true);
        
        this.addComponent(tA);
        this.addComponent(fl);
        this.setExpandRatio(tA, 2);
        tA.setReadOnly(true);
        
        //this.setExpandRatio(fl, 0);
        this.setSizeFull();
    }
    public void append(String message){
        tA.setValue(tA.getValue()+message);
    }
    public void update(){
        tA.setReadOnly(false);
        HashMap<Integer,String> hss = new HashMap<>();
        data d = new data();
        hss = d.chatsSince(id,lastMessageId);
        //ts = d.getCurrentSqlTimestamp();
        hss.forEach((k,v)->{
            append(v);
        });
        if(hss.size()>0){
            lastMessageId = Collections.max(hss.keySet());
        }
        tA.setReadOnly(true);
    }
}
