package converter;


import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.util.converter.DoubleStringConverter;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private ComboBox<Currency> outputCurrencyComboBox;

    @FXML
    private ComboBox<Currency> inputCurrencyComboBox;

    @FXML
    private TextField outputAmount;

    @FXML
    private TextField inputAmount;

    @FXML
    private CheckBox autoCheckBoxButton;

    @FXML
    private Button convertButton;

    private final static DoubleStringConverter DOUBLE_STRING_CONVERTER = new DoubleStringConverter();

    private final static DecimalFormat CURRENCY_FORMAT = new DecimalFormat("#0.00");

    private final ObservableList<Currency> currencies = FXCollections.observableArrayList();

    private final ChangeListener<String> inputAmountChangeListener = (observable, oldValue, newValue) -> convertAction(null);

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        currencies.addAll(Currency.values());
        inputCurrencyComboBox.setItems(currencies);
        outputCurrencyComboBox.setItems(currencies);
        inputCurrencyComboBox.getSelectionModel().selectFirst();
        outputCurrencyComboBox.getSelectionModel().selectLast();
        autoCheckBoxButton.setSelected(false);

        clearAction(null);

        inputCurrencyComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            convertAction(null);
        });
        outputCurrencyComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            convertAction(null);
        });

    }

    @FXML
    private void convertAction(ActionEvent actionEvent) {
        Currency inputCurrency = inputCurrencyComboBox.getValue();
        Currency outputCurrency = outputCurrencyComboBox.getValue();
        double inputValue;
        if (!inputAmount.getText().equals("") && isNumeric(inputAmount.getText())) {
            inputValue = DOUBLE_STRING_CONVERTER.fromString(inputAmount.getText());
            double inputValueInDollars = inputValue * inputCurrency.getRupeeConversionRate();
            double outputValue = inputValueInDollars / outputCurrency.getRupeeConversionRate();
            outputAmount.setText(CURRENCY_FORMAT.format(outputValue));
        }
    }

    @FXML
    private void clearAction(ActionEvent actionEvent) {
        inputAmount.setText("");
        outputAmount.setText("");
    }

    @FXML
    private void switchAutomaticConversion(ActionEvent actionEvent) {
        if (autoCheckBoxButton.isSelected()) {
            convertButton.setDisable(true);
            inputAmount.textProperty().addListener(inputAmountChangeListener);
            convertAction(null);
        } else {
            convertButton.setDisable(false);
            inputAmount.textProperty().removeListener(inputAmountChangeListener);
        }
    }

    private static boolean isNumeric(String str) {
        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

}