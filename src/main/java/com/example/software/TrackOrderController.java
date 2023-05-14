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
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class TrackOrderController implements Initializable {
    static Logger logger = Logger.getLogger(TrackOrderController.class.getName());

    @FXML
    private TableView<TrackOrderTable> table;
    @FXML
    private TableColumn<TrackOrderTable, Integer> AID;
    @FXML
    private TableColumn<TrackOrderTable, Integer>CID;

    @FXML
    private TableColumn<TrackOrderTable, Integer> Money;

    @FXML
    private TableColumn<TrackOrderTable, Integer> OrderId;
    @FXML
    private TableColumn<TrackOrderTable, Integer>Quantity;

    @FXML
    private TableColumn<TrackOrderTable, String> Color;
    @FXML
    private TableColumn<TrackOrderTable, String> Name;
    @FXML
    private TableColumn<TrackOrderTable, String> Size;
    @FXML
    private TableColumn<TrackOrderTable, String>Status;
    @FXML
    private Label back;
    @FXML
    private Button track;
    @FXML
    private TextField id2;

    @FXML
    private TextField status2;
    @FXML
    void trackClicked(MouseEvent event) {
        String q=id2.getText();
       String w= status2.getText();
        Database.createDatabase("update product set status ='"+w+ "' where pid="+q);
        table.setItems(list);
        ResultSet rs= Database.createDatabase("select status,wid from worker");
        int flag=1;
        try{
            if(w.equals("in treatment")) {
                while (rs.next()) {
                    String status = rs.getString(1);
                    if (rs.getString(1).equals("available")) {
                        Database.insertIntoDatabase("update worker set status='not available' where wid='"+rs.getString(2)+"'");
                        Database.insertIntoDatabase("insert into WORKONSHINING values(workerShining.nextval,'" + rs.getString(2) + "','" + q + "')");
                        return;
                    }
                }
                JOptionPane.showMessageDialog(null, "No Worker Available to do this Order !");


            }
            else if(w.equals("complete")){
                ResultSet rs2= Database.createDatabase("select WIDSHINING from WORKONSHINING where PIDSHINING='"+q+"'");
                rs2.next();
                Database.insertIntoDatabase("update worker set status='available' where wid='"+rs2.getString(1)+"'");
                ResultSet result1= Database.createDatabase("select cid from product where pid="+Integer.parseInt(q));
                result1.next();
                ResultSet result2= Database.createDatabase("select gmail from customer where cid="+result1.getInt(1));
                result2.next();
                Mail m=new Mail();
                m.rasheedEmail(result2.getString(1));
            }
        } catch (Exception e) {
            System.out.println("iam email when complete");
        }
    }
    @FXML
    void backClicked(MouseEvent event) {
        try {
            Parent root;
            FXMLLoader fxmlLoader;
            root = FXMLLoader.load(getClass().getResource("menu2.fxml"));
            Stage stage = (Stage) back.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
            new FadeIn(root).play();
        }catch (IOException e){
            logger.log(null," An error occurred while opening a new window:");
        }
    }
    @FXML
    ObservableList<TrackOrderTable> list= FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        OrderId.setCellValueFactory(new PropertyValueFactory<TrackOrderTable,Integer>("OrderId"));
        AID.setCellValueFactory(new PropertyValueFactory<TrackOrderTable,Integer>("AID"));
        CID.setCellValueFactory(new PropertyValueFactory<TrackOrderTable,Integer>("CID"));
        Quantity.setCellValueFactory(new PropertyValueFactory<TrackOrderTable,Integer>("Quantity"));
        Money.setCellValueFactory(new PropertyValueFactory<TrackOrderTable,Integer>("Money"));
        Name.setCellValueFactory(new PropertyValueFactory<TrackOrderTable,String>("Name"));
        Size.setCellValueFactory(new PropertyValueFactory<TrackOrderTable,String>("Size"));
        Color.setCellValueFactory(new PropertyValueFactory<TrackOrderTable,String>("Color"));
        Status.setCellValueFactory(new PropertyValueFactory<TrackOrderTable,String>("Status"));
            ResultSet rs = Database.createDatabase("select * from PRODUCT");
        try {
            while (rs.next()) {
                int pid = Integer.parseInt(rs.getString(1));
                int aid = Integer.parseInt(rs.getString(2));
                int cid = Integer.parseInt(rs.getString(3));
                String name = rs.getString(4);
                int quantity = Integer.parseInt(rs.getString(5));
                String size = rs.getString(6);
                String color = rs.getString(7);
                String status = rs.getString(8);
                int money =Integer.parseInt( rs.getString(9));
                list.add(new TrackOrderTable(pid,aid,cid,name,quantity,size,color,status,money));
            }
        } catch (SQLException e) {
            logger.log(null,"Database connection error: ");
        }
        table.setItems(list);

    }
}