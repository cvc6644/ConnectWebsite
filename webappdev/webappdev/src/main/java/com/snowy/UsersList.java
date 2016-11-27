/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.snowy;

import com.vaadin.data.Container;
import com.vaadin.data.Container.Ordered;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PopupView;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;
import java.util.HashMap;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;

/**
 *
 * @author Snowy
 */
public class UsersList extends VerticalLayout{
    private HashMap<String,Integer> hm = new HashMap<>();
    private ListSelect ls = new ListSelect();
    private Container c = new IndexedContainer();
    public UsersList(){
       c.addContainerProperty("id", Integer.class, "");
       retrieveActiveUsers();
       //this.addItem("Chase");
       //this.addItem("Cole");
       
       //ll.addComponent(ll);
       //PopupView pop = new PopupView(null,ll);
       //pop.s
       //pop.addPopupVisibilityListener(e->{
        //   ll.addComponent(hl);
       //});
       
       //TODO add the select listener
       ls.addValueChangeListener(e->{
           if(e.getProperty().getValue() !=null){
                Window w = new Window("Confirm Challenge");
                int id = Integer.parseInt(c.getItem(e.getProperty().getValue().toString()).getItemProperty("id").getValue().toString());
                String Username = e.getProperty().getValue().toString();
                Logger.getLogger(UsersList.class.getName()).info(Username);
                Logger.getLogger(UsersList.class.getName()).info(id+"");
                VerticalLayout ll = new VerticalLayout();
                VerticalLayout bb = new VerticalLayout();
                HorizontalLayout hl = new HorizontalLayout();
                Label la = new Label("Send challenge to "+Username+"?");
                bb.addComponent(la);
                ll.addComponent(bb);
                
                ll.setSizeUndefined();
                bb.setComponentAlignment(la, Alignment.MIDDLE_CENTER);
                
                ll.addComponent(hl);
                ll.setSpacing(true);
                ll.setMargin(new MarginInfo(true,true,false,true));
                hl.setMargin(new MarginInfo(false,true,true,true));
                hl.setSpacing(true);
                Button cancle = new Button("Cancel",b->{
                    w.close();
                });
                Button send = new Button("Send",c->{
                    if(new data().sendChallenge(id)){
                        ll.removeAllComponents();
                        ll.addComponent(new Label("Challenge Sent Succesfully!"));
                        ll.addComponent(new Button("Close",dd->{
                            w.close();
                        }));
                        w.setCaption("Success");
                        ll.setSpacing(true);
                        ll.setMargin(true);
                    }else{
                        ll.removeAllComponents();
                        ll.addComponent(new Label("Challenge Dend Failed"));
                        ll.addComponent(new Button("Close",dd->{
                            w.close();
                        }));
                        w.setCaption("Failure");
                        ll.setSpacing(true);
                        ll.setMargin(true);
                    }
                });
                hl.addComponents(cancle,send);
          //      this.addComponent(pop);
            //    ll.addComponent(la);
             //   pop.setPopupVisible(true);
             //w.setPosition(null, null);
             w.center();
             w.setModal(true);
             w.setClosable(false);
             w.setResizable(false);
             w.setContent(ll);
             this.getUI().addWindow(w);
                
           }
        });


       this.setSizeFull();
       this.addStyleName("mine");
       this.addComponent(ls);
       ls.setContainerDataSource(c);
       //ls.setContainerDataSource((Container) hm.keySet());
       ls.setSizeFull();
       ls.setImmediate(true);
       
    }
    public void retrieveActiveUsers(){
        
        c.removeAllItems();
        hm = new data().retrieveActiveUsers();
        for(String i: hm.keySet()){
            c.addItem(i).getItemProperty("id").setValue(hm.get(i));
            
        }
        //ls.addItems(hm.keySet());
    }
}
