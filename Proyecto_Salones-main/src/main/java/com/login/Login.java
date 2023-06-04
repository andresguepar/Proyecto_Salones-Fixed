package com.login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Scanner;

public class Login {

    @FXML
    private Button login;
    @FXML
    private Label wrong;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Button signup;

    File file = new File("datos.ax");

    HashMap<String, String> loginInfo = new HashMap<>();
    Encryptor encryptor = new Encryptor();


    @FXML
    void loginHandler(ActionEvent event) throws IOException, NoSuchPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        String user = username.getText();
        String pass = password.getText();
        updateLoginUsernamesAndPasswords();

        String encryptedPassword = loginInfo.get(user);
        wrong.setVisible(false);
        if(encryptor.encryptString(pass).equals(encryptedPassword)){
            Stage stage = new Stage();
            System.out.println("successfully login!");
            wrong.setText("Inicio de Sesion Exitoso");
            wrong.setVisible(true);
            stage.close();

            URI uri = Paths.get("C:\\Programacion\\Proyecto_Salones-main\\src\\main\\resources\\com\\proyecto_salones\\App.fxml").toAbsolutePath().toUri();
            Parent root = FXMLLoader.load(uri.toURL());
            Scene scene2 = new Scene(root, 1000, 600);

            stage.setScene(scene2);
            stage.show();


        } else {
            wrong.setText("Usuario/Contraseña ERRONEA ");
            wrong.setVisible(true);
        }
    }

    @FXML
    void createAccount(ActionEvent event) throws IOException, NoSuchPaddingException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        writeToFile();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Create Account Succes :D");
        alert.setHeaderText(null);
        alert.setContentText("Cuenta creada exitosamente!");
        alert.initStyle(StageStyle.UTILITY);
        alert.showAndWait();
        wrong.setVisible(false);
    }

    private void updateLoginUsernamesAndPasswords() throws IOException {
        Scanner scanner = new Scanner(file);
        loginInfo.clear();
        loginInfo = new HashMap<>();
        while (scanner.hasNext()){
            String[] usernameAndPassword = scanner.nextLine().split(",");
            loginInfo.put(usernameAndPassword[0],usernameAndPassword[1]);
        }
    }

    private void writeToFile() throws IOException, NoSuchPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        String user = username.getText();
        String pass = password.getText();
        BufferedWriter writer = new BufferedWriter(new FileWriter(file,true));

        writer.write(user + "," + encryptor.encryptString(pass) + "\n");
        writer.close();
    }
}


