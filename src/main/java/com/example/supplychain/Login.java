package com.example.supplychain;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;

public class Login {

    public static byte[] getSHA(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }

    static String getEncryptedPassword(String passwordText) throws NoSuchAlgorithmException {
        try{
            BigInteger number = new BigInteger(1, getSHA(passwordText));

            StringBuilder hexString = new StringBuilder(number.toString(16));
//        while (hexString.length())
            return hexString.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    public static boolean customerLogin(String email, String password)  {
        try{
            DatabaseConnection dbCon = new DatabaseConnection();
            String encryptedPassword = getEncryptedPassword(password);
            String query = String.format("SELECT * FROM customer WHERE email = '%s' AND password = '%s' ", email,encryptedPassword);
            ResultSet rs = dbCon.getQueryTable(query);
            if(rs.next()){
                return  true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
//        String pass = "angad123";
//        System.out.println(pass);
//        System.out.println(Login.getEncriptedPassword(pass));

        System.out.println(Login.registerUser("accio1@gmail.com", "rahul123", "Rahul", 77609));
    }

    public static String registerUser(String email, String password, String name, int mobile){
        try{
            DatabaseConnection dbCon = new DatabaseConnection();
            String encryptedPassword = getEncryptedPassword(password);
            String query = String.format("INSERT INTO customer(email,password,first_name,mobile) VALUES(\'%s\',\'%s\',\'%s\',\'%s\');",email,encryptedPassword,name,mobile);
            System.out.println(query);
            dbCon.executeQuery(query);
            return  password;
        }catch (Exception e){
            e.printStackTrace();
        }
        return "registration_failed";
    }


}