/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.snowy;

import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.server.UserError;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author snowyowl
 */
public class NewUserSubWindow extends Window {
    
    
    
    public NewUserSubWindow() {
        super("New User");
        build();
    }
    
    public void build(){
        //setClosable(false);
        setModal(true);
        setResizable(false);
        setResponsive(true);
        setDraggable(false);
        FormLayout fl = new FormLayout();
        fl.setMargin(true);
        //fl.setSizeFull();
        fl.setSizeUndefined();
        fl.setSpacing(true);
        TextField uname = new TextField("Username");
        uname.setRequired(true);
        
        //uname.addValidator(null);
        fl.addComponent(uname);
        TextField email = new TextField("Email");
        
        email.setRequired(true);
        email.addValidator(new EmailValidator("A Valid Email is Required"));
        fl.addComponent(email);
        PasswordField pf1 = new PasswordField("Password");
        pf1.setRequired(true);
        pf1.addValidator(new StringLengthValidator("Password must be between 8 and 60 characters",8,60,false));
        fl.addComponent(pf1);
        
        PasswordField pf2 = new PasswordField("Confirm Password");
        pf2.setRequired(true);
        pf2.addValidator((Object value) -> {
            if(!pf2.getValue().equals(pf1.getValue())){
                throw new InvalidValueException("Passwords Must Match");
            }
        });
        //pf2.setImmediate(true);
        fl.addComponent(pf2);
        Button b = new Button("Submit");
        
        b.addClickListener((Button.ClickEvent e)->{
            data d = new data();
            if(uname.isValid()&& email.isValid()&& pf1.isValid()&& pf2.isValid()){
                
                String result =d.createUser(uname.getValue(), pf2.getValue(), email.getValue());
                if(result.equals("Creation Sucess")){
                    fl.removeAllComponents();
                    fl.addComponent(new Label("User Created Sucessfully"));
                    fl.addComponent(new Button("Close",(ee)->{
                        this.close();
                    }));
                    
                }else{
                    Notification.show(result);
                }
            }else{
                b.setComponentError(new UserError("Issues with required fields"));
            }
            //d.close();
        });
        fl.addComponent(b);
        setContent(fl);
        
    }
   
    
}
