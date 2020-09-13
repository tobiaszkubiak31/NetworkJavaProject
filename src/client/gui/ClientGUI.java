//package client.gui;
//
//import client.tcpclient.TCPClient;
//import javafx.application.Application;
//import javafx.application.Platform;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
//import javafx.scene.control.TextField;
//import javafx.scene.layout.HBox;
//import javafx.stage.Stage;
//
//public class ClientGUI extends Application {
//
//  @Override
//  public void start(Stage stage) {
//    Label ipText = new Label("Ip");
//    Label portText = new Label("port");
//    final TextField ipTextField = new TextField("192.168.0.102");
//    final TextField portTextField = new TextField("7");
//    Button button = new Button("Connect");
//    HBox hbox = new HBox(ipText,ipTextField,portText,portTextField,button);
//    Scene scene = new Scene(hbox, 600, 300);
//
//    button.setOnAction(value ->  {
//      CharSequence[] textToMessage = new CharSequence[2];
//      textToMessage[0] = ipTextField.getCharacters();
//      textToMessage[1] = portTextField.getCharacters();
//      stage.hide();
//      connect(textToMessage[0].toString(),Integer.valueOf(textToMessage[1].toString()));
//    });
//
//    stage.setTitle("Connect to server");
//    stage.setScene(scene);
//    stage.show();
//  }
//
//  private void connect(String ip, int port) {
//    Platform.runLater(new TCPClient(ip, port));
//  }
//  public static void main(String args[])
//  {
//    ClientGUI clientGUI = new ClientGUI();
//    clientGUI.run();
//  }
//  public void run(){
//    launch();
//    }
//}
