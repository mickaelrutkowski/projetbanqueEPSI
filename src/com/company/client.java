package com.company;

import javax.swing.*;
import java.sql.*;
import java.util.Scanner;

public class client extends agence {
    private int IdClient = 0;
    private String NomClient = null;
    private String prenomClient = null;
    private String AdresseClient = null;
    private String VilleClient = null;
    private Connection con;
    private Statement st;
    private ResultSet rs;
    Scanner key = new Scanner(System.in);
    private String DepartementClient = null;
    private int IdAgence;

    public client() {

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
    public boolean insert() {

        try {

            System.out.print("Saisissez le nom  : ");
            this.NomClient = key.nextLine();
            System.out.print("Saisissez le prenom  : ");
            this.prenomClient = key.nextLine();
            System.out.print("Saisissez l'adresse  : ");
            this.AdresseClient = key.nextLine();
            System.out.print("Saisissez le departement  : ");
            this.DepartementClient = key.nextLine();
            System.out.print("Saisissez la ville  : ");
            this.VilleClient = key.nextLine();
            System.out.print("Saisissez l'agence : ");
            this.IdAgence = Integer.parseInt(key.nextLine());

            String query = "INSERT INTO clients VALUES('" + this.IdClient + "','" + this.NomClient + "','" + this.prenomClient + "','" + this.AdresseClient + "','" + this.DepartementClient + "','" + this.VilleClient + "','" + this.IdAgence + "');";
            st.executeUpdate(query);

            System.out.println("-----------------------------");
            System.out.println("Client inseret dans la base de donée");
            System.out.println("Nom Client : " + this.NomClient);
            System.out.println("Prenom Client : " + this.prenomClient);
            System.out.println("Adresse :  " + this.AdresseClient);
            System.out.println("DepartementClient :  " + this.DepartementClient);
            System.out.println("Ville :  " + this.VilleClient);
            System.out.println("Agence :  " + this.IdAgence);
            System.out.println("------------------------------\n");

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void selectClient() {

        try {

            // notre requête SQL SELECT.
            // si vous n'avez besoin que de quelques colonnes, spécifiez-les par nom au lieu d'utiliser "*"
            String query = "SELECT * FROM clients";

            // crée l'instruction java
            Statement st = con.createStatement();

            // exécute la requête et obtient un ensemble de résultats java
            ResultSet rs = st.executeQuery(query);

            // itérer dans le jeu de résultats java
            while (rs.next()) {
                int IdClient = rs.getInt("IdClient");
                String Nom = rs.getString("Nom");
                String Prenom = rs.getString("Prenom");
                String Adresse = rs.getString("AdresseClient");
                String DepartementClient = rs.getString("DepartementClient");
                String Ville = rs.getString("VilleClient");
                int IdAgence = rs.getInt("IdAgence");

                // Afficher les résultats
                System.out.format("%s, %s, %s, %s, %s,%s, %s\n", IdClient, Nom, Prenom, Adresse,DepartementClient, Ville, IdAgence);
            }
            st.close();
        } catch (Exception e) {
            System.err.println("Vous avez une exception!");
            System.err.println(e.getMessage());
        }
    }

    // Résultats de la recherche avec les données des disques avec un choix de génération
    public void selectId(int para) {

        try {

            String query = "SELECT * FROM client WHERE IdClient = '" + para + "'";
            rs = st.executeQuery(query);
            while (rs.next()) {
                String Nom = rs.getString("Nom");
                String Prenom = rs.getString("Prenom");
                String Adresse = rs.getString("AdresseClient");
                String Ville = rs.getString("VilleClient");
                int IdClient = rs.getInt("IdClient");
                System.out.format(
                        "\nId Client : " + IdClient +
                        "\nNom Client : " + Nom +
                        "\nPrenom Client : " + Prenom +
                        "\nAdresse : " + Adresse +
                        "\nVille Client : " + Ville);

            }
            st.close();
        } catch (SQLException ex) {
             ex.printStackTrace();
        }

    }

    public boolean delete(int id) {

        try {

            String query = "DELETE FROM client WHERE IdClient = '" + id + "'";
            st.executeUpdate(query);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(int id, String Nom, String Prenom, String AdresseClient, String VilleClient) {

        try {

            String query = "UPDATE client SET"
                    + " Nom = '" + Nom + "',"
                    + " Prenom = '" + Prenom + "',"
                    + " AdresseClient = '" + AdresseClient + "',"
                    + " VilleClient = '" + VilleClient + "' WHERE IdClient = '" + id + "';";
            st.executeUpdate(query);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public int getIdClient() {
        return IdClient;
    }

    public String getNom() {
        return NomClient;
    }

    public void setNom(String nom) {
        NomClient = nom;
    }
}
