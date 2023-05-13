package com.example.software;
import animatefx.animation.FadeIn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javax.swing.*;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class report implements Initializable{

    @FXML
    private Label back;

    @FXML
    private TableColumn<reportTable, Integer> customerId;

    @FXML
    private TableView<reportTable> reportTable;
    @FXML
    private TableColumn<reportTable, Integer> totalAmountOfMoney;

    @FXML
    void backClicked(MouseEvent event) {
        try{
            Parent root;
            FXMLLoader fxmlLoader;
            root = FXMLLoader.load(getClass().getResource("menu2.fxml"));
            Stage stage = (Stage) back.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
            new FadeIn(root).play();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    @FXML
    ObservableList<reportTable> list1= FXCollections.observableArrayList();

    public  void initialize(URL url, ResourceBundle resourceBundle){
        customerId.setCellValueFactory(new PropertyValueFactory<reportTable,Integer>("customerId"));
        totalAmountOfMoney.setCellValueFactory(new PropertyValueFactory<reportTable,Integer>("totalAmountOfMoney"));
        ResultSet r=database.createDatabase("select cid,moneyamount from product");
        try {
            while (r.next()) {
                int CID = Integer.parseInt(r.getString(1));
                int  moneyamount = Integer.parseInt(r.getString(2));

                list1.add(new reportTable(CID,moneyamount));
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        reportTable.setItems(list1);




    }




}