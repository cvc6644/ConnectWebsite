    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.snowy;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.event.UIEvents;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.spring.access.ViewAccessControl;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import javax.annotation.PostConstruct;

/**
 *
 * @author snowyowl
 */
@SpringView(name = "main")
@Theme("valo")
@PreserveOnRefresh
public class PostLoginView extends VerticalLayout implements View{
    private GameWindow g=new GameWindow();
    private ChatWindow c = new ChatWindow();
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        //this.getSession().getConfiguration().
        if(new data().getUsernameFromToken()==""){
            Page.getCurrent().setLocation("");
        }
    }
    
    //http://stackoverflow.com/questions/24363588/how-to-use-polllistener-in-vaadin/24364359#24364359
    @PostConstruct
    void init(){
        HorizontalLayout hl = new HorizontalLayout();
        setMargin(true);
        /*this.getUI().addPollListener(new UIEvents.PollListener(){
            @Override
            public void poll(UIEvents.PollEvent event) {
                c.getUsersList().retrieveActiveUsers();
            }
        
        });*/
        
        this.setSizeFull();
        Button b = new Button("Logout");
        b.addClickListener(e ->{
            
            new data().logout(VaadinSession.getCurrent().getCsrfToken());
            VaadinSession.getCurrent().close();
                    
            Page.getCurrent().setLocation("/");
        });
        Button ccc = new Button("Unpause polling");
        ccc.setEnabled(false);
        Button cc = new Button("Pause polling");
        cc.addClickListener(e->{
            if(ccc.isEnabled()==false){
                UI.getCurrent().setPollInterval(1000000000);
                cc.setEnabled(false);
                ccc.setEnabled(true);
            }
        });
        ccc.addClickListener(e->{
            if(cc.isEnabled()==false){
                UI.getCurrent().setPollInterval(1000);
                cc.setEnabled(true);
                ccc.setEnabled(false);
            }
        });
        hl.setSizeFull();
        hl.setSpacing(true);
        //whdjwandjawd
        this.addComponent(cc);
        this.addComponent(ccc);
        this.addComponent(b);
        
        //this.setHeightUndefined();
        this.setSpacing(true);
        this.setComponentAlignment(b, Alignment.TOP_RIGHT);
        //this.addComponent(c);
        //this.addComponent(ul);
        hl.addComponent(g);
        
        hl.addComponent(c);
        
        
        //this.addComponent(c);
        hl.setExpandRatio(c,1);
        hl.setExpandRatio(g, 3);
        g.setSizeFull();
        c.setSizeFull();
        this.addComponent(hl);
        //this.setExpandRatio(g, 2);
        this.setExpandRatio(hl, 1);
    }

    public ChatWindow getChatWindow(){
        return c;
    }

    public GameWindow getGameWindow() {
        return g;
    }
    
    
    
}
