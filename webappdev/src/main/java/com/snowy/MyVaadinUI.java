/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.snowy;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.event.UIEvents;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.CustomizedSystemMessages;
import com.vaadin.server.DefaultErrorHandler;
import com.vaadin.server.ErrorHandler;
import com.vaadin.server.Page;
import com.vaadin.server.SystemMessages;
import com.vaadin.server.SystemMessagesInfo;
import com.vaadin.server.SystemMessagesProvider;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.JavaScript;
import com.vaadin.ui.JavaScriptFunction;
import com.vaadin.ui.Label;
import com.vaadin.ui.LoginForm;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import elemental.json.JsonArray;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author snowyowl
 */
@SpringUI
@Theme("valo")
//@PreserveOnRefresh
public class MyVaadinUI extends UI {
    //private data d;
    @Autowired
    private SpringViewProvider viewProvider;
    
    
    @Override
    protected void init(VaadinRequest request) {
        //d = new data(template);
         if(Page.getCurrent().getWebBrowser().isTooOldToFunctionProperly()){
            Page.getCurrent().setLocation("https://www.google.com/chrome/browser/");
        }
         //VaadinService.getCurrent().
        Page.getCurrent().setTitle("Connect 4");
        
        
        //this.access(()->{
        
        //n.addView("postLogin", new PostLoginView());
        //Navigator n = this.getNavigator();
        //n.addProvider();
        //});
        /*
        final TextField name = new TextField();
        name.setCaption("Type your name here:");
        
        Button button = new Button("Click Me");
        button.addClickListener( e -> {
            layout.addComponent(new Label("Thanks " + name.getValue() 
                    + ", it works!"));
        });
        */
        JavaScript.getCurrent().addFunction("closeMyApplication", new JavaScriptFunction() {
             @Override
             public void call(JsonArray arguments) {
                 Logger.getLogger(MyVaadinUI.class.getName()).info(VaadinSession.getCurrent().getCsrfToken());
                 new data().logout(VaadinSession.getCurrent().getCsrfToken());
                //new data().setInActive(VaadinSession.getCurrent().getCsrfToken());
                 //VaadinSession.getCurrent().close();
             }
                     


              
        });

        Page.getCurrent().getJavaScript().execute("window.onbeforeunload = function (e) { var e = e || window.event; closeMyApplication(); return; };");
        VaadinService.getCurrent().addSessionDestroyListener(e->{
            Logger.getLogger(MyVaadinUI.class.getName()).info(e.getSession().getCsrfToken());
            new data().logout(e.getSession().getCsrfToken());
            
        
        });
        //Navigator n = new Navigator(this,this);
        //n.addView("Main", new PostLoginView());
//        this.setNavigator(n);
        //layout.setComponentAlignment(MainL, Alignment.TOP_CENTER);
        this.setPollInterval(1000);
        this.addPollListener((UIEvents.PollEvent e)->{
            if(e.getUI().getContent().toString().contains("PostLoginView")){
                PostLoginView plv = (PostLoginView)e.getUI().getContent();
                
                plv.getChatWindow().getUsersList().retrieveActiveUsers();
                
                plv.getChatWindow().updateChats();
                plv.getGameWindow().updateRequests();
                plv.getGameWindow().updateGames();
                plv.getGameWindow().checkForNewGames();
                //Logger.getLogger(MyVaadinUI.class.getName()).log(Level.INFO,plv.getChatWindow().getUsersList().getValue().toString());
            }
            
        });
       Navigator navigator = new Navigator(this,this);
        navigator.addProvider(viewProvider);
        
        navigator.addViewChangeListener(new ViewChangeListener(){
             @Override
             public boolean beforeViewChange(ViewChangeListener.ViewChangeEvent event) {
                 if(Page.getCurrent().getLocation().getPath().equals("/") || VaadinSession.getCurrent().getAttribute("token")!=null){
                     
                     return true;
                 }else{
                     
                     //Notification.show(,Notification.Type.ERROR_MESSAGE);
                     return false;
                 }
             }

             @Override
             public void afterViewChange(ViewChangeListener.ViewChangeEvent event) {
             }
        
        
        });
        
       /*UI.getCurrent().setErrorHandler(new DefaultErrorHandler(){
             @Override
             public void error(com.vaadin.server.ErrorEvent event) {
                 UI.getCurrent().setContent(new Label("error"));
             }
           
       });*/
       //VaadinService.createCriticalNotificationJSON("","", "", "");
        //navigator.setErrorView(navigator.getCurrentView());
        //layout.setComponentAlignment(link, Alignment.MIDDLE_CENTER);
        //setContent(new Login());
        
        
    }
    
}
