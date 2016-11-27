/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.snowy;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinService;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.LoginForm;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author snowyowl
 */
@SpringView(name = "")
public class Login extends VerticalLayout implements View{
    private data d;
    //@Autowired
    //JdbcTemplate template;
    @PostConstruct
    void init(){
        
        this.setSizeFull();
        this.setSpacing(false);
        this.setMargin(true);
        Label MainL = new Label("<h1>Connect 4</h1?>",ContentMode.HTML);
        //layout.addComponent(MainL);
        MainL.setSizeUndefined();
         VerticalLayout lay = new VerticalLayout();
        lay.setMargin(false);
        lay.addComponent(MainL);
        lay.setComponentAlignment(MainL, Alignment.TOP_CENTER);
        HorizontalLayout hz = new HorizontalLayout();
        hz.setMargin(false);
        hz.setSpacing(false);
        LoginForm lf = new LoginForm();
        lf.addLoginListener((e)->{
            d = new data();
            String token = d.genToken(e.getLoginParameter("username"), e.getLoginParameter("password"));
            //String token="true";
            if(!token.equals("false")){
                
                Cookie c = new Cookie("token",token);
                VaadinService.getCurrentResponse().addCookie(c);
                //https://vaadin.com/wiki/-/wiki/Main/Setting+and+reading+Cookies
                //Notification.show(VaadinService.getCurrentRequest().getCookies()[1].getValue(),Notification.Type.ERROR_MESSAGE);
                //this.getNavigator().navigateTo("main");
                //this.getUI().get
                this.getUI().getNavigator().navigateTo("main");
            }else{
                Label l =new Label("<h4 style=\"color:red\">Invalid Username or Password</h4>",ContentMode.HTML);
                l.setId("created");
                
                if(lay.getComponent(lay.getComponentIndex(lf)+1).getId()==null ){
                //lay.addComponent(new Label(String.valueOf(lay.getComponentIndex(l))));
                    lay.addComponent(l,lay.getComponentIndex(lf)+1);
                    l.setSizeUndefined();
                    lay.setComponentAlignment(l, Alignment.TOP_CENTER);
                }
                
            }
            
            //d.close();
        });
        
        lay.addComponent(lf);
        Button newUser = new Button("New User");
        newUser.addClickListener((e)->{
            
            this.getUI().addWindow(new NewUserSubWindow());
        });
        //newUser.setWidth((float)5.5, Unit.EM);
        Button forgotPass = new Button("Forgot Password");
        //temp
        forgotPass.addClickListener((e)->{
            //Notification.show(, Notification.Type.ERROR_MESSAGE);
        });
        forgotPass.setEnabled(false);
        forgotPass.setDescription("Feature Disabled, Contact Administrator for Assistance");
        //forgotPass.setWidth((float) 8.5,Unit.EM);
        forgotPass.setStyleName(ValoTheme.BUTTON_LINK);
        newUser.setStyleName(ValoTheme.BUTTON_LINK);
        
        hz.addComponent(newUser);
        hz.addComponent(forgotPass);
        lay.addComponent(hz);
        lay.setComponentAlignment(lf, Alignment.TOP_CENTER);
        lay.setComponentAlignment(hz, Alignment.MIDDLE_CENTER);
        this.addComponent(lay);
        this.setComponentAlignment(lay, Alignment.MIDDLE_CENTER);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }

    
}
