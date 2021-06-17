package com.example.carreg.user.view;


import com.example.carreg.core.component.MenuComponent;
import com.example.carreg.user.entity.AppRoleEntity;
import com.example.carreg.user.entity.AppUserEntity;
import com.example.carreg.user.service.AppRoleService;
import com.example.carreg.user.service.AppUserService;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.nio.charset.Charset;
import java.util.Random;

// http://localhost:8081/carmanager
@Route
public class UserManagerView extends VerticalLayout {

    private AppUserEntity selectedUser;
    private VerticalLayout form;
    private TextField username;
    private TextField firstName;
    private TextField lastName;
    private CheckboxGroup<AppRoleEntity> authorities;
    private Binder<AppUserEntity> binder;

    @Autowired
    private AppUserService service;
    @Autowired
    private AppRoleService roleService;

    @PostConstruct
    public void init() {
        add(new MenuComponent());
        Grid<AppUserEntity> grid = new Grid<>();
        grid.setItems(service.findAll());
        grid.addColumn(AppUserEntity::getId).setHeader("ID").setSortable(true);
        grid.addColumn(AppUserEntity::getUsername).setHeader("Username").setSortable(true);
        grid.addColumn(AppUserEntity::getFirstName).setHeader("First Name").setSortable(true);
        grid.addColumn(AppUserEntity::getLastName).setHeader("Last Name").setSortable(true);
        grid.addColumn(appUserEntity -> {
            StringBuilder builder = new StringBuilder();
            appUserEntity.getAuthorities().forEach(appRoleEntity -> {
                builder.append(appRoleEntity.getAuthority()).append(", ");
            });
            return builder.toString();
        }).setHeader("Role");

        addButtonBar(grid);
        add(grid);
        addForm(grid);
    }

    private void addForm(Grid<AppUserEntity> grid) {
        form = new VerticalLayout();
        binder = new Binder<>(AppUserEntity.class);
        username = new TextField();
        form.add(new Text("Username"), username);
        firstName= new TextField();
        form.add(new Text("First Name"), firstName);
        lastName= new TextField();
        form.add(new Text("Last NAme"), lastName);
        authorities = new CheckboxGroup<>();
        authorities.setItems(roleService.findAll());
        authorities.setItemLabelGenerator(authorEntity -> authorEntity.getAuthority());
        form.add(new Text("Roles"), authorities);

        Button saveBtn = new Button();
        saveBtn.setText("Save");
        saveBtn.addClickListener(buttonClickEvent -> {
            if (selectedUser.getId() != null) {
                service.update(selectedUser);
            } else {
                String generatedPass =  generatePass();
                selectedUser.setPassword(new BCryptPasswordEncoder().encode(generatedPass));
                service.create(selectedUser);
                Dialog dialog = new Dialog();
                dialog.add(new Text("The password of the new user:  "+ generatedPass));
                dialog.setWidth("400px");
                dialog.setHeight("35px");
                dialog.open();
            }
            grid.setItems(service.findAll());
            form.setVisible(false);
        });
        form.add(saveBtn);
        add(form);
        form.setVisible(false);
        binder.bindInstanceFields(this);
    }

    private String generatePass(){
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) {
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }

    private void addButtonBar(Grid<AppUserEntity> grid) {
        HorizontalLayout buttonBar = new HorizontalLayout();

        Button deleteBtn = new Button();
        deleteBtn.setEnabled(false);
        deleteBtn.setText("Delete");
        deleteBtn.setIcon(VaadinIcon.TRASH.create());
        deleteBtn.addClickListener(buttonClickEvent -> {
            service.deleteById(selectedUser.getId());
            grid.setItems(service.findAll());
            selectedUser = null;
            deleteBtn.setEnabled(false);
            form.setVisible(false);
            Notification.show("Successfully deleted");
        });
        grid.asSingleSelect().addValueChangeListener(event -> {
            selectedUser = event.getValue();
            deleteBtn.setEnabled(selectedUser != null);
            form.setVisible(selectedUser != null);
            binder.setBean(selectedUser);
        });

        Button addBtn = new Button();
        addBtn.setText("Add");
        addBtn.addClickListener(buttonClickEvent -> {
            selectedUser = new AppUserEntity();
            binder.setBean(selectedUser);
            form.setVisible(true);
        });
        addBtn.setIcon(VaadinIcon.PLUS.create());
        buttonBar.add(deleteBtn, addBtn);
        add(buttonBar);
    }
}
