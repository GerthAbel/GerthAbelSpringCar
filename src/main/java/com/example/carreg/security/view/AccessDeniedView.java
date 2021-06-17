package com.example.carreg.security.view;

import com.example.carreg.core.component.MenuComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("403")
public class AccessDeniedView extends VerticalLayout {

    public AccessDeniedView(){
        add(new MenuComponent());
        add("Not enough permissions");
    }

}
