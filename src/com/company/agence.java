package com.company;

import javax.swing.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class agence {

    private int NumeroAgence;
    private String Libelle;
    private String AdresseAgence;
    private String VilleAgence;
    private int cpt = 0;
    private Connection con;
    private Statement st;
    private ResultSet rs;
    static Scanner key = new Scanner(System.in);

    public agence() {
        try {
            if ((con = connection.getConexionMYSQL()) == null) {
                JOptionPane.showMessageDialog(null, "Erreur de conexion à la BD");
                return;
            }
            st = con.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getNumeroAgence() {
        return NumeroAgence;
    }

    public String getAdresseAgence() {
        return AdresseAgence;
    }

    public String getLibelle() {
        return Libelle;
    }

    // Insérez un nouveau client dans la base de données
    public boolean insertAgence() {

        try {

            System.out.print("Saisissez le libelle  : ");
            this.Libelle = key.nextLine();
            System.out.print("Saisissez l'adresse de l'agence   : ");
            this.AdresseAgence = key.nextLine();
            System.out.print("Saisissez la ville  : ");
            this.VilleAgence = key.nextLine();

            String query = "INSERT INTO agence VALUES('" + this.NumeroAgence+ "','" + this.Libelle + "','" + this.AdresseAgence+ "','" + this.VilleAgence + "');";
            st.executeUpdate(query);

            System.out.println("-----------------------------");
            System.out.println("agence inseret dans la base de donée");
            System.out.println("libelle : " + this.Libelle);
            System.out.println("Adresse agence : " + this.AdresseAgence);
            System.out.println("Ville agence :  " + this.VilleAgence);
            System.out.println("------------------------------\n");
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void SelectAgence() {

        try {

            // notre requête SQL SELECT.
            // si vous n'avez besoin que de quelques colonnes, spécifiez-les par nom au lieu d'utiliser "*"
                String query = "SELECT * FROM  agence";

            // crée l'instruction java
            Statement st = con.createStatement();

            // exécute la requête et obtient un ensemble de résultats java
            ResultSet rs = st.executeQuery(query);

            // itérer dans le jeu de résultats java
            while (rs.next()) {
                int NumeroAgence = rs.getInt("NumeroAgence");
                String Libelle = rs.getString("Libelle");
                String AdresseAgence = rs.getString("AdresseAgence");
                String VilleAgence = rs.getString("VilleAgence");


                // Afficher les résultats
                System.out.format("%s, %s, %s, %s\n", NumeroAgence, Libelle, AdresseAgence, VilleAgence);
            }
            st.close();
        } catch (Exception e) {
            System.err.println("Vous avez une exception!");
            System.err.println(e.getMessage());
        }
    }

    // Résultats de la recherche avec les données des disques avec un choix de génération
    public void selectIdAgence(int para) {

        try {

            String query = "SELECT * FROM agence WHERE NumeroAgence = '" + para + "'";
            rs = st.executeQuery(query);
            while (rs.next()) {
                String NumeroAgence = rs.getString("NumeroAgence");
                String Libelle = rs.getString("Libelle");
                String AdresseAgence = rs.getString("AdresseAgence");
                String VilleAgence = rs.getString("VilleAgence");
                int IdClient = rs.getInt("IdClient");
                System.out.format(
                        "\nId Client : " + IdClient +
                                "\nNumeroAgence : " + NumeroAgence +
                                "\nLibelle : " + Libelle +
                                "\nAdresseAgence : " + AdresseAgence +
                                "\nVilleAgence : " + VilleAgence);
            }
            st.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public boolean deleteAgence(int id) {

        try {

            String query = "DELETE FROM agence WHERE NumeroAgence = '" + id + "'";
            st.executeUpdate(query);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateAgence(int NumeroAgence, String Libelle, String AdresseAgence, String VilleAgence) {

        try {

            String query = "UPDATE agence SET"
                    + " Nom = '" + Libelle + "',"
                    + " Prenom = '" + AdresseAgence + "',"
                    + " AdresseClient = '" + VilleAgence + "' WHERE IdClient = '" + NumeroAgence + "';";
            st.executeUpdate(query);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
