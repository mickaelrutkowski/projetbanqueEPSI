package com.company;

import javax.swing.*;
import java.sql.*;
import java.util.Scanner;

public class agence {

    private int IdAgence = 0;
    private String Libelle;
    private String AdresseAgence;
    private String DepartementAgence;
    private String VilleAgence;
    private Connection con;
    private Statement st;
    private ResultSet rs;
    static Scanner key = new Scanner(System.in);
    boolean fini = true;

//**********************************************************************************************************

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

//***********************************************************************************************************

    public void interactionAgence() {
        while (fini) {
            System.out.println("");
            System.out.println("         __________________________________________      ");
            System.out.println("                       [| Bienvenu  |]                   ");
            System.out.println("      +-------------------  Menu  --------------------+  ");
            System.out.println("      | Quelle operation voulez-vous effectuer?       |  ");
            System.out.println("      |  1) Ajouter une Agence                        |  ");
            System.out.println("      |  2) Afficher toutes les agences               |  ");
            System.out.println("      |  3) Afficher une agence                       |  ");
            System.out.println("      |  4) Modifier Agence                           |  ");
            System.out.println("      |  5) supprime agence                           |  ");
            System.out.println("      |                                               |  ");
            System.out.println("      |  6) Menu Principal                            |  ");
            System.out.println("      +-----------------------------------------------+  ");
            System.out.print("Votre choix: ");
            String reponse = key.nextLine();
            System.out.println();

            switch (reponse) {
                case "1":
                    insertAgence();
                    break;

                case "2":
                    SelectAgence();
                    break;

                case "3":
                    selectIdAgence();
                    break;

                case "4":
                    updateAgence();
                    break;

                case "5":
                    deleteAgence();
                    break;

                case "6":
                    Main MenuP = new Main();
                    MenuP.MenuP();
                    break;

                default:
                    System.out.println("Erreur de saisi clavier !! \n");
                    break;
            }
        }
    }

//*****************************************************************************************************
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

            String query = "INSERT INTO agence VALUES('" + this.IdAgence + "','" + this.Libelle + "','" + this.AdresseAgence + "','" + this.DepartementAgence + "','" + this.VilleAgence + "');";
            st.executeUpdate(query);
            SelectAgence();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

//*********************************************************************************************

    public void SelectAgence() {

        try {

            String query = "SELECT * FROM  agence";

            // crée l'instruction java
            Statement st = con.createStatement();

            // exécute la requête et obtient un ensemble de résultats java
            ResultSet rs = st.executeQuery(query);

            // itérer dans le jeu de résultats java
            while (rs.next()) {
                int IdAgence = rs.getInt("IdAgence");
                String Libelle = rs.getString("Libelle");
                String AdresseAgence = rs.getString("AdresseAgence");
                String DepartementAgence = rs.getString("DepartementAgence");
                String VilleAgence = rs.getString("VilleAgence");

                // Afficher les résultats
                System.out.println("Agence : ");
                System.out.format("%s, %s, %s, %s, %s\n", "NumeroAgence : " + IdAgence, "libelle : " + Libelle, "AdresseAgence : " + AdresseAgence, "DepartementAgence : " + DepartementAgence, "VilleAgence : " + VilleAgence);
                System.out.println("-------------------------");
            }

        } catch (Exception e) {
            System.err.println("Vous avez une exception!");
            System.err.println(e.getMessage());
        }
    }

//*****************************************************************************************************
    // Résultats de la recherche avec les données des disques avec un choix de génération

    public void selectIdAgence() {

        System.out.print("Saisissez l'Id Agence : ");
        this.IdAgence = Integer.parseInt(key.nextLine());
        try {

            String query = "SELECT * FROM agence WHERE IdAgence = '" + IdAgence + "'";
            rs = st.executeQuery(query);
            while (rs.next()) {
                int IdAgence = Integer.parseInt(rs.getString("IdAgence"));
                String Libelle = rs.getString("Libelle");
                String AdresseAgence = rs.getString("AdresseAgence");
                String DepartementAgence = rs.getString("DepartementAgence");
                String VilleAgence = rs.getString("VilleAgence");
                System.out.format(
                        "\nIdAgence : " + IdAgence +
                                "\nLibelle : " + Libelle +
                                "\nAdresseAgence : " + AdresseAgence +
                                "\nDepartementAgence : " + DepartementAgence +
                                "\nVilleAgence : " + VilleAgence);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

//*********************************************************************************************

    public boolean updateAgence() {

        System.out.print("Saisissez l'Id Agence  : ");
        this.IdAgence = Integer.parseInt(key.nextLine());
        System.out.print("Saisissez le libelle  : ");
        this.Libelle = key.nextLine();
        System.out.print("Saisissez l'adresse de l'agence   : ");
        this.AdresseAgence = key.nextLine();
        System.out.print("Saisissez le departement  : ");
        this.DepartementAgence = key.nextLine();
        System.out.print("Saisissez la ville  : ");
        this.VilleAgence = key.nextLine();

        try {

            String query = "UPDATE agence SET"
                    + " Libelle = '" + Libelle + "',"
                    + " AdresseAgence = '" + AdresseAgence + "',"
                    + " DepartementAgence = '" + DepartementAgence + "',"
                    + " VilleAgence = '" + VilleAgence + "' WHERE IdAgence = '" + IdAgence + "';";
            st.executeUpdate(query);
            SelectAgence();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

//*****************************************************************************************************

    public boolean deleteAgence() {

        System.out.print("Saisissez l'Id Agence a supprimer  : ");
        this.IdAgence = Integer.parseInt(key.nextLine());

        try {

            String query = "DELETE FROM agence WHERE IdAgence = '" + IdAgence + "'";
            st.executeUpdate(query);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
