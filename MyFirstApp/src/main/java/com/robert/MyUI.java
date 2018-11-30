package com.robert;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of an HTML page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class MyUI extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final HorizontalLayout layout = new HorizontalLayout();
        Label logo = new Label("<H1><u>Work CO.</u></H1> <p/> <h3>employee record system</h3><br>", ContentMode.HTML);
      
        TextField staffID = new TextField("Staff ID");
        staffID.setMaxLength(5);
        TextField name = new TextField("Name of Person");
        TextField phone = new TextField("Phone number");
        
        Button addButton = new Button("Add");
 
        final VerticalLayout vlayout = new VerticalLayout();

        List<Person> people = new ArrayList<Person>();
               
        Grid<Person> myGrid = new Grid<> ();
        myGrid.setItems(people);
        myGrid.addColumn(Person::getStaffID).setCaption("Staff ID");
        myGrid.addColumn(Person::getName).setCaption("Name");
        myGrid.addColumn(Person::getPhone).setCaption("phone");
        
      

        Button clear = new Button("Clear");
        clear.addClickListener(e -> {
            myGrid.removeAllColumns();
            people.clear(); // Clear the list of people
            name.setValue("");
            phone.setValue("");
           staffID.setValue("");
        });  
       
        addButton.addClickListener(e -> {
            people.add(new Person(staffID.getValue(),name.getValue(),phone.getValue()));
            myGrid.removeAllColumns();
            myGrid.setItems(people);
            myGrid.addColumn(Person::getStaffID).setCaption("Staff ID");
            myGrid.addColumn(Person::getName).setCaption("Name");
            myGrid.addColumn(Person::getPhone).setCaption("phone");
            
        });  
        vlayout.addComponents(staffID, name, phone, addButton,clear);
                
        layout.addComponents(logo, vlayout , myGrid); 
           
        setContent(layout);
       
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}