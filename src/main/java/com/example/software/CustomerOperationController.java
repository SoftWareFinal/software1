package com.example.software;

import animatefx.animation.FadeIn;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class CustomerOperationController implements Initializable {
    static Logger logger = Logger.getLogger(CustomerOperationController.class.getName());

    @FXML
    private Button add;
    @FXML
    private Button add2;
    @FXML
    private Button delete;
    @FXML
    private Button delete2;
    @FXML
    private Button update;
    @FXML
    private Button update2;

    @FXML
    private VBox addBox;
    @FXML
    private VBox updateBox;
    @FXML
    private TextField address;
    @FXML
    private TextField address1;
    @FXML
    private TextField customerIdDelete;
    @FXML
    private TextField gmail;
    @FXML
    private TextField gmail1;
    @FXML
    private TextField id;
    @FXML
    private TextField id1;
    @FXML
    private TextField phoneNumber;
    @FXML
    private TextField phoneNumber1;
    @FXML
    private TextField userName;
    @FXML
    private TextField userName1;

    @FXML
    private Label back;
    @FXML
    private HBox deleteBox;
    @FXML
    private PasswordField password;

    @FXML
    private PasswordField password1;

    @FXML
    private Button gitInformation;
    private String m="Database connection error: ";
    private String em="ERROR";
    private String d="DONE";
    private String i="INSERTED";
    @FXML
    void gitInformationClicked(MouseEvent event) {
        ResultSet r= Database.createDatabase("select * from customer where CID='"+id1.getText()+"'");
        try{
            while (r.next()) {
                id1.setText(r.getString(1));
                phoneNumber1.setText(r.getString(2));
                address1.setText(r.getString(3));
                gmail1.setText(r.getString(4));
                userName1.setText(r.getString(5));
                password1.setText(r.getString(6));
            }
        } catch (SQLException e) {
            logger.log(null,m);
        }
    }
    @FXML
    void add2Clicked(MouseEvent event) {
        try {
            if (!TESTINPUT.idTest(id.getText())){
                JOptionPane.showMessageDialog(null, "wrong id !", em, JOptionPane.ERROR_MESSAGE);
            return;}
            else if (!TESTINPUT.passwordTest(password.getText())){
                JOptionPane.showMessageDialog(null, "wrong PASSWORD !", em, JOptionPane.ERROR_MESSAGE);
                return;}
            else if (!TESTINPUT.phoneNumberTest(phoneNumber.getText())){
                JOptionPane.showMessageDialog(null, "wrong PHONE NUMBER !", em, JOptionPane.ERROR_MESSAGE);
                return;}
            else if (!TESTINPUT.gmailTest(gmail.getText())){
                JOptionPane.showMessageDialog(null, "wrong GMAIL !", em, JOptionPane.ERROR_MESSAGE);
                return;}
            else if (id.getText().isEmpty() || phoneNumber.getText().isEmpty() || gmail.getText().isEmpty() || userName.getText().isEmpty() || password.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Field is Empty", em, JOptionPane.ERROR_MESSAGE);
                return;
            }
            ResultSet rs = Database.createDatabase("select CID,USERNAME,PASSWORD from customer");
            while (rs.next()) {
                String id2 = rs.getString(1);
                String username2 = rs.getString(2);
                if (id2.equals(id.getText())) {
                    JOptionPane.showMessageDialog(null, "The ID is already contains", em, JOptionPane.ERROR_MESSAGE);
                    return;
                } else if (username2.equals(userName.getText())) {
                    JOptionPane.showMessageDialog(null, "The USERNAME is already contains", em, JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
                Database.insertIntoDatabase("INSERT INTO CUSTOMER values('" + id.getText() + "','" + phoneNumber.getText() + "','" + address.getText() + "','" + gmail.getText() + "','" + userName.getText() + "','" + password.getText() + "')");
                JOptionPane.showMessageDialog(null, d, i, JOptionPane.INFORMATION_MESSAGE);
                addBox.setVisible(false);
        } catch (SQLException e) {
            logger.log(null,m);
        }
    }

    @FXML
    void addClicked(MouseEvent event) {
        addBox.setVisible(true);
        deleteBox.setVisible(false);
        updateBox.setVisible(false);
        addBox.setLayoutX(119);
        addBox.setLayoutX(152);
    }

    @FXML
    void backClicked(MouseEvent event) {
        try {
            Parent root;
            root = FXMLLoader.load(getClass().getResource("menu2.fxml"));
            Stage stage = (Stage) back.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
            new FadeIn(root).play();
        }catch (IOException e){
            logger.log(null," An error occurred while opening a new window:");        }
    }

    @FXML
    void delete2Clicked(MouseEvent event) {
        if(customerIdDelete.getText().isEmpty()) JOptionPane.showMessageDialog(null, "Please Fill Id Customer First !", "ERROR", JOptionPane.ERROR_MESSAGE);
        else {
            Database.insertIntoDatabase("delete from customer where cid ='"+customerIdDelete.getText()+"'");
            JOptionPane.showMessageDialog(null, d, i, JOptionPane.INFORMATION_MESSAGE);
            deleteBox.setVisible(false);
        }
    }
    @FXML
    void deleteClicked(MouseEvent event) {
        addBox.setVisible(false);
        deleteBox.setVisible(true);
        updateBox.setVisible(false);
        deleteBox.setLayoutX(120);
        deleteBox.setLayoutY(215);
    }
    @FXML
    void update2Clicked(MouseEvent event) {
        String r=id1.getText();
        try {
            if (!TESTINPUT.idTest(id1.getText())){
                JOptionPane.showMessageDialog(null, "wrong id !", "e", JOptionPane.ERROR_MESSAGE);
                return;}
            else if (!TESTINPUT.passwordTest(password1.getText())){
                JOptionPane.showMessageDialog(null, "wrong PASSWORD !", "e", JOptionPane.ERROR_MESSAGE);
                return;}
            else if (!TESTINPUT.phoneNumberTest(phoneNumber1.getText())){
                JOptionPane.showMessageDialog(null, "wrong PHONE NUMBER !", "e", JOptionPane.ERROR_MESSAGE);
                return;}
            else if (!TESTINPUT.gmailTest(gmail1.getText())){
                JOptionPane.showMessageDialog(null, "wrong GMAIL !", "e", JOptionPane.ERROR_MESSAGE);
                return;}
            else if (id1.getText().isEmpty() || phoneNumber1.getText().isEmpty() || gmail1.getText().isEmpty() || userName1.getText().isEmpty() || password1.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Field is Empty", "e", JOptionPane.ERROR_MESSAGE);
                return;
            }
            ResultSet rs = Database.createDatabase("select CID,USERNAME,PASSWORD from customer");
            while (rs.next()) {
                String idc = rs.getString(1);
                String usernamec = rs.getString(2);
                if (idc.equals(id.getText())) {
                    JOptionPane.showMessageDialog(null, "The ID is already contains", "e", JOptionPane.ERROR_MESSAGE);
                    return;
                } else if (usernamec.equals(userName.getText())) {
                    JOptionPane.showMessageDialog(null, "The USERNAME is already contains", "e", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        Database.insertIntoDatabase("update customer set phonenumber='"+phoneNumber1.getText()+"',address='"+
                address1.getText()+"',gmail='"+gmail1.getText()+"',username='"+userName1.getText()+"',password='"+password1.getText()+"'"
                +"where CID='"+r+"'");
        JOptionPane.showMessageDialog(null, d, i, JOptionPane.INFORMATION_MESSAGE);
        updateBox.setVisible(false);
        } catch (SQLException e) {
            logger.log(null,m);
        }
    }

    @FXML
    void updateClicked(MouseEvent event) {
        addBox.setVisible(false);
        deleteBox.setVisible(false);
        updateBox.setVisible(true);
        updateBox.setLayoutX(120);
        updateBox.setLayoutX(151);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addBox.setVisible(false);
        deleteBox.setVisible(false);
        updateBox.setVisible(false);
    }
}
