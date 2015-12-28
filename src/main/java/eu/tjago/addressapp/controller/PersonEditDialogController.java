package eu.tjago.addressapp.controller;

import eu.tjago.addressapp.model.Person;
import eu.tjago.addressapp.util.DateUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Created by tjago on 2015-12-25.
 */
public class PersonEditDialogController {
    @FXML
    private TextField firstNameInput;
    @FXML
    private TextField lastNameInput;
    @FXML
    private TextField streetInput;
    @FXML
    private TextField postalCodeInput;
    @FXML
    private TextField cityInput;
    @FXML
    private TextField birthdayInput;


    private Stage dialogStage;
    private Person person;
    private boolean saveClicked = false;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    }

    /**
     * Sets the stage of this dialog.
     *
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Sets the person to be edited in the dialog.
     *
     * @param person
     */
    public void setPerson(Person person) {
        this.person = person;

        firstNameInput.setText(person.getFirstName());
        lastNameInput.setText(person.getLastName());
        streetInput.setText(person.getStreet());
        postalCodeInput.setText(Integer.toString(person.getPostalCode()));
        cityInput.setText(person.getCity());
        birthdayInput.setText(DateUtil.format(person.getBirthday()));
        birthdayInput.setPromptText("dd.mm.yyyy");
    }

    /**
     * Returns true if the user clicked Save, false otherwise.
     *
     * @return
     */
    public boolean isSaveClicked() {
        return saveClicked;
    }

    /**
     * Called when the user clicks save.
     */
    @FXML
    private void onSave() {
        if (isInputValid()) {
            person.setFirstName(firstNameInput.getText());
            person.setLastName(lastNameInput.getText());
            person.setStreet(streetInput.getText());
            person.setPostalCode(Integer.parseInt(postalCodeInput.getText()));
            person.setCity(cityInput.getText());
            person.setBirthday(DateUtil.parse(birthdayInput.getText()));

            saveClicked = true;
            dialogStage.close();
        }
    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void onCancel() {
        dialogStage.close();
    }

    /**
     * Validates the user input in the text fields.
     *
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (firstNameInput.getText() == null || firstNameInput.getText().length() == 0) {
            errorMessage += "No valid first name!\n";
        }
        if (lastNameInput.getText() == null || lastNameInput.getText().length() == 0) {
            errorMessage += "No valid last name!\n";
        }
        if (streetInput.getText() == null || streetInput.getText().length() == 0) {
            errorMessage += "No valid street!\n";
        }

        if (postalCodeInput.getText() == null || postalCodeInput.getText().length() == 0) {
            errorMessage += "No valid postal code!\n";
        } else {
            // try to parse the postal code into an int.
            try {
                Integer.parseInt(postalCodeInput.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid postal code (must be an integer)!\n";
            }
        }

        if (cityInput.getText() == null || cityInput.getText().length() == 0) {
            errorMessage += "No valid city!\n";
        }

        if (birthdayInput.getText() == null || birthdayInput.getText().length() == 0) {
            errorMessage += "No valid birthday!\n";
        } else {
            if (!DateUtil.validDate(birthdayInput.getText())) {
                errorMessage += "No valid birthday. Use the format dd.mm.yyyy!\n";
            }
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
}
