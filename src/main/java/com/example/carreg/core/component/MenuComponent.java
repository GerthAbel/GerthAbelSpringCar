package com.example.carreg.core.component;

import com.example.carreg.security.config.SecurityUtils;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class MenuComponent extends HorizontalLayout {

    public MenuComponent() {
        Anchor carLink = new Anchor();
        carLink.setHref("/carmanager");
        carLink.setText("cars");

        Anchor manufacturerLink = new Anchor();
        manufacturerLink.setHref("/manufacturermanager");
        manufacturerLink.setText("manufacturers");
        add(carLink, manufacturerLink);

        if (SecurityUtils.isAdmin()) {
            Anchor userLink = new Anchor();
            userLink.setHref("/usermanager");
            userLink.setText("Users");
            add(userLink);
        }


    }
}
