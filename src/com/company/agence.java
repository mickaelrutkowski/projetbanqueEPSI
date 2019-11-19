package com.company;

import javax.swing.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class agence {

    private int idAgence = 0;
    private String Libelle;
    private String AdresseAgence;
    private String VilleAgence;
    private Connection con;
    private Statement st;
    private ResultSet rs;
    static Scanner key = new Scanner(System.in);
    private String DepartementAgence;

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

    // Insérez un nouveau client dans la base de données
    public boolean insertAgence() {

        try {

            System.out.print("Saisissez le libelle  : ");
            this.Libelle = key.nextLine();
            System.out.print("Saisissez l'adresse de l'agence   : ");
            this.AdresseAgence = key.nextLine();
            System.out.print("Saisissez lae departement  : ");
            this.DepartementAgence = key.nextLine();
            System.out.print("Saisissez la ville  : ");
            this.VilleAgence = key.nextLine();

            String query = "INSERT INTO agence VALUES('" + this.idAgence+ "','" + this.Libelle + "','" + this.AdresseAgence + "','" + this.DepartementAgence + "','" + this.VilleAgence + "');";
            st.executeUpdate(query);

            SelectAgence();
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
                int idAgence = rs.getInt("idAgence");
                String Libelle = rs.getString("Libelle");
                String AdresseAgence = rs.getString("AdresseAgence");
                String DepartementAgence = rs.getString("DepartementAgence");
                String VilleAgence = rs.getString("VilleAgence");

                // Afficher les résultats
                System.out.println("Agence : ");
                System.out.format("%s, %s, %s, %s, %s\n","NumeroAgence : " + idAgence,"libelle : " + Libelle, "AdresseAgence : " + AdresseAgence,"DepartementAgence : " + DepartementAgence, "VilleAgence : " + VilleAgence);
                System.out.println("-------------------------");
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
    public int getNumeroAgence() {
        return idAgence;
    }

    public String getAdresseAgence() {
        return AdresseAgence;
    }

    public String getLibelle() {
        return Libelle;
    }
}
