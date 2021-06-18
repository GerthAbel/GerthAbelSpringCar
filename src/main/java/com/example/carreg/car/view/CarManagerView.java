package com.example.carreg.car.view;


import com.example.carreg.manufacturer.entity.ManufacturerEntity;
import com.example.carreg.manufacturer.service.ManufacturerService;
import com.example.carreg.car.entity.CarEntity;
import com.example.carreg.car.service.CarService;
import com.example.carreg.core.component.MenuComponent;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.data.validator.StringLengthValidator;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

// http://localhost:8081/carmanager
@Route
public class CarManagerView extends VerticalLayout {

    private CarEntity selectedmanufacturer;
    private VerticalLayout form;
    private TextField type;
    private TextField numberOfDoors;
    private TextField yearOfProduction;
    private ComboBox<ManufacturerEntity> manufacturer;

    private Binder<CarEntity> binder;

    @Autowired
    private CarService service;
    @Autowired
    private ManufacturerService manufacturerService;

    @PostConstruct
    public void init() {
        add(new MenuComponent());
        Grid<CarEntity> grid = new Grid<>();
        grid.setItems(service.findAll());
        grid.addColumn(CarEntity::getId).setHeader("ID").setSortable(true);
        grid.addColumn(CarEntity::getType).setHeader("Type").setSortable(true);
        grid.addColumn(carEntity -> {
            if (carEntity.getmanufacturer() != null) {
                return carEntity.getmanufacturer().getName();
            }
            return "";
        }).setHeader("Manufacturer").setSortable(true);
        grid.addColumn(CarEntity::getNumberOfDoors).setHeader("Number Of Doors").setSortable(true);
        grid.addColumn(CarEntity::getYearOfProduction).setHeader("Year Of Production").setSortable(true);
        addButtonBar(grid);
        add(grid);
        addForm(grid);
    }

    private void addForm(Grid<CarEntity> grid) {
        form = new VerticalLayout();
        binder = new Binder<>(CarEntity.class);
        type = new TextField();
        type.setRequired(true);
        form.add(new Text("Type"), type);
        manufacturer = new ComboBox<>();
        manufacturer.setRequired(true);
        manufacturer.setItems(manufacturerService.findAll());
        manufacturer.setItemLabelGenerator(manufacturerEntity -> manufacturerEntity.getName());
        form.add(new Text("Manufacturer"), manufacturer);
        numberOfDoors = new TextField();
        binder.forField(numberOfDoors)
                .withNullRepresentation("")
                .withConverter(new StringToIntegerConverter("must be integer"))
                .bind(CarEntity::getNumberOfDoors, CarEntity::setNumberOfDoors);
        form.add(new Text("Number Of Doors"), numberOfDoors);
        yearOfProduction = new TextField();
        binder.forField(yearOfProduction)
                .withNullRepresentation("")
                .withConverter(new StringToIntegerConverter("must be integer"))
                .bind(CarEntity::getYearOfProduction, CarEntity::setYearOfProduction);
        form.add(new Text("Year Of Production"), yearOfProduction);

        Button saveBtn = new Button();
        saveBtn.setText("Save");
        saveBtn.addClickListener(buttonClickEvent -> {
            if(
                    !type.isEmpty()&&
                    !manufacturer.isEmpty()&&
                    !numberOfDoors.isEmpty()&&
                    !yearOfProduction.isEmpty()
            ){
                if (selectedmanufacturer.getId() != null) {
                    service.update(selectedmanufacturer);
                } else {
                    service.create(selectedmanufacturer);
                }
                grid.setItems(service.findAll());
                form.setVisible(false);
            }else{
                Notification.show("Has to be Type, Manufacturer, Number Of Doors and Year Of Production, to create a record!");
            }
        });
        form.add(saveBtn);
        add(form);
        form.setVisible(false);
        binder.bindInstanceFields(this);
    }




    private void addButtonBar(Grid<CarEntity> grid) {
        HorizontalLayout buttonBar = new HorizontalLayout();

        Button deleteBtn = new Button();
        deleteBtn.setEnabled(false);
        deleteBtn.setText("Delete");
        deleteBtn.setIcon(VaadinIcon.TRASH.create());
        deleteBtn.addClickListener(buttonClickEvent -> {
            service.deleteById(selectedmanufacturer.getId());
            grid.setItems(service.findAll());
            selectedmanufacturer = null;
            deleteBtn.setEnabled(false);
            form.setVisible(false);
            Notification.show("Successfully deleted");
        });
        grid.asSingleSelect().addValueChangeListener(event -> {
            selectedmanufacturer = event.getValue();
            deleteBtn.setEnabled(selectedmanufacturer != null);
            form.setVisible(selectedmanufacturer != null);
            binder.setBean(selectedmanufacturer);
        });

        Button addBtn = new Button();
        addBtn.setText("Add");
        addBtn.addClickListener(buttonClickEvent -> {
            selectedmanufacturer = new CarEntity();
            binder.setBean(selectedmanufacturer);
            form.setVisible(true);
        });
        addBtn.setIcon(VaadinIcon.PLUS.create());
        buttonBar.add(deleteBtn, addBtn);
        add(buttonBar);
    }
}
