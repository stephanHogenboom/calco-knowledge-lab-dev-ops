package com.stephanHogenboom.view;

import com.stephanHogenboom.acces.MasterClassDAO;
import com.stephanHogenboom.elements.AlertBox;
import com.stephanHogenboom.model.*;
import com.stephanHogenboom.util.AddressHelper;
import com.stephanHogenboom.util.MasterClasserHelper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.controlsfx.control.CheckComboBox;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AddMasterClasserScreen {
    private Stage window;
    private ComboBox<JobType> jobTypes;
    private ComboBox<Company> companies = new ComboBox<>();;
    private ComboBox<FieldManager> fieldManagers = new ComboBox<>();
    private CheckComboBox<Specialization> specializationCheckComboBox;
    private TextField fullNameTextField, streetNameEntry, extension, postalCode, houseNumber, city;
    private Label nameMasterClasser, companyOfMC, fmOfMc, specializationLabel;
    private final MasterClassDAO dao = new MasterClassDAO();
    private TextField telephoneField, emailText;
    private Company calco = new Company(0, "Calco");
    //TODO use address helper to verify data
    private AddressHelper addressHelper = new AddressHelper();
    private MasterClasserHelper masterClasserHelper = new MasterClasserHelper();



    public void display() {
        window = new Stage();
        window.setMinWidth(500);
        window.setMinHeight(500);

        if (!window.getModality().equals(Modality.APPLICATION_MODAL)) {
            window.initModality(Modality.APPLICATION_MODAL);
        }

        VBox vbox = new VBox(10);

        HBox masterClasserBox = new HBox(20);
        VBox nameBox = new VBox();

        nameMasterClasser = new Label("Full name");
        nameMasterClasser.setTextAlignment(TextAlignment.CENTER);
        fullNameTextField = getTextField("full name");
        nameBox.getChildren().addAll(nameMasterClasser, fullNameTextField);

        VBox companyBox = new VBox();
        companyOfMC = new Label("Company");
        companies.getItems().addAll(dao.getAllCompanies());
        companyBox.getChildren().addAll(companyOfMC, companies);

        VBox fieldManagerBox = new VBox();
        fmOfMc = new Label("Field Manager");
        fieldManagers.getItems().addAll(dao.getAllFieldManagers());
        fieldManagers.getSelectionModel().selectFirst();
        fieldManagerBox.getChildren().addAll(fmOfMc, fieldManagers);

        VBox specializationBox = new VBox();
        specializationLabel = new Label("Specialization");
        ObservableList<Specialization> specials = FXCollections.observableArrayList();
        specials.addAll(dao.getAllSpecilaizations());
        specializationCheckComboBox = new CheckComboBox<>(specials);
        specializationCheckComboBox.setMaxWidth(150);
        specializationBox.getChildren().addAll(specializationLabel, specializationCheckComboBox);


        VBox jobBox = new VBox();
        Label jobLabel = new Label("job of the master classer");
        jobTypes = new ComboBox<>();
        jobTypes.getItems().addAll(dao.getAllJobTypes());
        jobBox.getChildren().addAll(jobLabel, jobTypes);
        masterClasserBox.getChildren().addAll(nameBox, jobBox, companyBox, fieldManagerBox, specializationBox);

        VBox contactDetails = new VBox();
        Label telephone = new Label("Telephone Number");
        telephoneField = getTextField("Telephone Number");

        Label email = new Label("e-mail");
        emailText = getTextField("e-mail");

        contactDetails.getChildren().addAll(telephone, telephoneField, email, emailText);

        //Address
        streetNameEntry = getTextField("street name");
        HBox numberExtension = new HBox();
        //TODO input valuer should be numeric!!
        houseNumber = getTextField("house number");
        extension = getTextField("extension");
        numberExtension.getChildren().addAll(houseNumber, extension);
        postalCode = getTextField("postal code");
        city = getTextField("city");

        Button saveEntry = new Button("save");
        saveEntry.setOnAction(e -> addMasterClasser());

        Button cancelButton = new Button("cancel");
        cancelButton.setOnAction(e -> window.close());


        ButtonBar buttonBar = new ButtonBar();
        buttonBar.getButtons().addAll(saveEntry, cancelButton);


        vbox.getChildren().addAll(masterClasserBox, contactDetails, streetNameEntry, numberExtension, postalCode, city, buttonBar);
        Scene scene = new Scene(vbox);
        scene.getStylesheets().addAll("index.css");
        window.setScene(scene);
        window.showAndWait();
    }

    private void addMasterClasser() {
        MasterClasser mc = new MasterClasser();
        if (fullNameTextField.getText() == null || fullNameTextField.getText().isEmpty()) {
            AlertBox.display("error", "the name of a master classer cannot be empty!");
            return;
        }
        mc.setFullName(fullNameTextField.getText());
        mc.setAddress(validateAndSetAddress());
        //TODO if address is null this method should not proceed
        //TODO if an email is provided it should be formatted correctly -> <front>@<back>.<domain>
        //TODo the last part of the email adrres should be between 2 and 6 characters
        mc.setEmail(emailText.getText() == null ? "" : emailText.getText());
        mc.setTelephoneNumber(fullNameTextField.getText() == null ? "" : fullNameTextField.getText());
        mc.setStartDate(LocalDate.now());
        mc.setJobType(jobTypes.getValue());
        mc.setFieldManager(fieldManagers.getValue());
        if (companies.getSelectionModel().getSelectedItem() != null) {
            mc.setCompany(companies.getSelectionModel().getSelectedItem());
        } else {
            mc.setCompany(calco);
        }
        List<Specialization> specializations = specializationCheckComboBox.getCheckModel().getCheckedIndices()
                .stream()
                .map(i -> specializationCheckComboBox.getItems().get(i))
                .peek(System.out::println)
                .collect(Collectors.toList());
        mc.setSpecializations(specializations);
        dao.insertMasterClasser(mc);
        AlertBox.display("Succes", "Master classer succesfully inserted!");
        window.close();
    }

    private TextField getTextField(String name) {
        TextField textField = new TextField();
        textField.setPromptText(name);
        textField.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        return textField;
    }

    private Address validateAndSetAddress() {
        Address address = new Address();
        //All mandatory address information should be present
        List<TextField> list = new ArrayList<>(Arrays.asList(streetNameEntry, houseNumber, postalCode, city));
        for (TextField field : list) {
            if (field.getText() == null || field.getText().trim().isEmpty()) {
                AlertBox.display("error", field.getPromptText() + " must be non empty!");
                return null;
            }
        }

        //TODO postal code should match the format NNNNAA for example 2265GH
        //TODO see the util.AddressHelper.java
        // addressHelper.validatePostalCode()
        String postalCodeText = postalCode.getText().toUpperCase();

        AddressBuilder bldr = new AddressBuilder();


        String ext = (extension.getText() != null || !extension.getText().isEmpty()) ? extension.getText() : "";
        bldr.setCountry("NL")
                .setPostalCode(postalCodeText)
                .setStreet(streetNameEntry.getText())
                //TODO verify that houseNumber is numeric before inserting here!
                .setHouseNumber(Integer.parseInt(houseNumber.getText()))
                .setExtension(ext)
                .setCity(city.getText())
                .setKixCode();
        return bldr.build();
    }

}
