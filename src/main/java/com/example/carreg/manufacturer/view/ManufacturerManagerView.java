package com.example.carreg.manufacturer.view;

import com.example.carreg.manufacturer.entity.ManufacturerEntity;
import com.example.carreg.manufacturer.service.ManufacturerService;
import com.example.carreg.core.component.MenuComponent;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

// http://localhost:8081/manufacturer
@Route
public class ManufacturerManagerView extends VerticalLayout {

    private ManufacturerEntity selectedManufacturer;
    private VerticalLayout form;
    private TextField name;

    private Binder<ManufacturerEntity> binder;

    @Autowired
    private ManufacturerService service;

    @PostConstruct
    public void init() {
        add(new MenuComponent());
        Grid<ManufacturerEntity> grid = new Grid<>();
        grid.setItems(service.findAll());
        grid.addColumn(ManufacturerEntity::getId).setHeader("ID").setSortable(true);
        grid.addColumn(ManufacturerEntity::getName).setHeader("Name").setSortable(true);
        addButtonBar(grid);
        add(grid);
        addForm(grid);
    }
    private void addForm(Grid<ManufacturerEntity> grid) {
        form = new VerticalLayout();
        binder = new Binder<>(ManufacturerEntity.class);
        name = new TextField();
        form.add(new Text("Name"), name);


        Button saveBtn = new Button();
        saveBtn.setText("Save");
        saveBtn.addClickListener(buttonClickEvent -> {
            if(!name.isEmpty()) {
                if (selectedManufacturer.getId() != null) {
                    service.update(selectedManufacturer);
                } else {
                    service.create(selectedManufacturer);
                }
                grid.setItems(service.findAll());
                form.setVisible(false);
            }else{
                Notification.show("Has to be a name, to create a record!");
            }
        });
        form.add(saveBtn);
        add(form);
        form.setVisible(false);
        binder.bindInstanceFields(this);
    }


    private void addButtonBar(Grid<ManufacturerEntity> grid) {
        HorizontalLayout buttonBar = new HorizontalLayout();

        Button deleteBtn = new Button();
        deleteBtn.setEnabled(false);
        deleteBtn.setText("Delete");
        deleteBtn.setIcon(VaadinIcon.TRASH.create());
        deleteBtn.addClickListener(buttonClickEvent -> {
            service.deleteById(selectedManufacturer.getId());
            grid.setItems(service.findAll());
            selectedManufacturer = null;
            deleteBtn.setEnabled(false);
            form.setVisible(false);
            Notification.show("Successfully deleted");
        });
        grid.asSingleSelect().addValueChangeListener(event -> {
            selectedManufacturer = event.getValue();
            deleteBtn.setEnabled(selectedManufacturer != null);
            form.setVisible(selectedManufacturer != null);
            binder.setBean(selectedManufacturer);
        });

        Button addBtn = new Button();
        addBtn.setText("Add");
        addBtn.addClickListener(buttonClickEvent -> {
            selectedManufacturer = new ManufacturerEntity();
            binder.setBean(selectedManufacturer);
            form.setVisible(true);
        });
        addBtn.setIcon(VaadinIcon.PLUS.create());
        buttonBar.add(deleteBtn, addBtn);
        add(buttonBar);
    }
}
