/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeserver;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import interfaces.PieChartInterface;

/**
 *
 * @author EmanAbobakr
 */
public class FXMLDocumentController implements Initializable,PieChartInterface {

    @FXML
    private PieChart pieChartId;

    Server server;

    int onlineUsers = 0, offlineUser = 0;

    private ScheduledExecutorService scheduledExecutorService;
    @FXML
    private Button startId;
    @FXML
    private Button stopId;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        stopId.setDisable(true);
        // TODO
    }

    @FXML
    public void startServer(ActionEvent event) throws IOException {

        System.out.println("The server has started");
        DatabaseManager.getInstance().connection();
        DatabaseManager.getInstance().piechartRef = this;
        pieChart();
        pieChartId.setVisible(true);
        
        server = new Server();
        startId.setDisable(true);
        stopId.setDisable(false);
        
        System.out.println("I will print scores");
        DatabaseManager.getInstance().getPlayersWithScores();
    }

    @FXML
    public void stopServer(ActionEvent event) throws IOException {
        if (Alerts.showInConfirmationAlert("Are you sure you want to close the server?")) {
            ServerHandler.closeSockets();
            server.serSoc.close();
            pieChartId.setVisible(false);
            DatabaseManager.getInstance().closeConnection();
            startId.setDisable(false);
            stopId.setDisable(true);
            System.out.println("The server has closed");
        }
        System.out.println("The server still working");

    }

    public void updatePieChart() {

        pieChartId.getData().remove(0);
        pieChartId.getData().remove(0);
        
        onlineUsers = DatabaseManager.getInstance().numberOfOnlineUsers();
        offlineUser = DatabaseManager.getInstance().numberOfAllUsers() - onlineUsers;
        
        pieChartId.getData().add(new PieChart.Data("Online players", onlineUsers));
        pieChartId.getData().add(new PieChart.Data("offline players", offlineUser));


    }


    public void pieChart() {
        onlineUsers = DatabaseManager.getInstance().numberOfOnlineUsers();
        offlineUser = DatabaseManager.getInstance().numberOfAllUsers() - onlineUsers;
        
        pieChartId.getData().add(new PieChart.Data("Online players", onlineUsers));
        pieChartId.getData().add(new PieChart.Data("offline players", offlineUser));

        pieChartId.setTitle("Tic Tac Toe Statistics");
    }
}
