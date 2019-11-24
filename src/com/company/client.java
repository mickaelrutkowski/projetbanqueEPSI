package com.company;

import javax.swing.*;
import java.sql.*;
import java.util.Scanner;

public class client {

    private int IdClient = 0;
    private String NomClient = null;
    private String prenomClient = null;
    private String AdresseClient = null;
    private String DepartementClient = null;
    private String VilleClient = null;
    private int IdAgence;
    private Connection con;
    private Statement st;
    private ResultSet rs;
    Scanner key = new Scanner(System.in);
    boolean finis = true;

//*************************************************************************************************************

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

//************************************************************************************************************

    public void interactionClient() {

        while (finis) {
            System.out.println("");
            System.out.println("         __________________________________________      ");
            System.out.println("                       [| Bienvenu  |]                   ");
            System.out.println("      +-------------------  Menu  --------------------+  ");
            System.out.println("      | Quelle operation voulez-vous effectuer?       |  ");
            System.out.println("      |  1) Ajouter un Client                         |  ");
            System.out.println("      |  2) Afficher toute les Clients                |  ");
            System.out.println("      |  3) Afficher un Client                        |  ");
            System.out.println("      |  4) Modifier Client                           |  ");
            System.out.println("      |  5) supprime Client                           |  ");
            System.out.println("      |                                               |  ");
            System.out.println("      |  6) Menu Principal                            |  ");
            System.out.println("      +-----------------------------------------------+  ");
            System.out.print("Votre choix: ");
            String reponse = key.nextLine();
            System.out.println();

            switch (reponse) {
                case "1":
                    insertClient();
                    break;

                case "2":
                    selectClient();
                    break;

                case "3":
                    selectIdS();
                    break;

                case "4":
                    updateClient();
                    break;

                case "5":
                    deleteClient();
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

//**********************************************************************************************************
    // Insérez un nouveau client dans la base de données

    public boolean insertClient() {

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
            selectClient();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

//*********************************************************************************************************************

    public void selectClient() {

        try {
            String query = "SELECT * FROM clients";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                int IdClient = rs.getInt("IdClient");
                String Nom = rs.getString("NomClient");
                String Prenom = rs.getString("PrenomClient");
                String Adresse = rs.getString("AdresseClient");
                String DepartementClient = rs.getString("DepartementClient");
                String Ville = rs.getString("VilleClient");
                int IdAgence = rs.getInt("IdAgence");

                // Afficher les résultats
                System.out.format("%s, %s, %s, %s, %s,%s, %s\n", IdClient, Nom, Prenom, Adresse, DepartementClient, Ville, IdAgence);
            }
            st.close();
        } catch (Exception e) {
            System.err.println("Vous avez une exception!");
            System.err.println(e.getMessage());
        }
    }

//**************************************************************************************************************
    // Résultats de la recherche avec les données des disques avec un choix de génération

    public void selectIdClient(int para) {

        try {
            String query = "SELECT * FROM clients WHERE IdClient = '" + para + "'";
            rs = st.executeQuery(query);

            while (rs.next()) {
                int IdClient = rs.getInt("IdClient");
                String NomClient = rs.getString("NomClient");
                String PrenomClient = rs.getString("PrenomClient");
                String AdresseClient = rs.getString("AdresseClient");
                String DepartementClient = rs.getString("DepartementClient");
                String VilleClient = rs.getString("VilleClient");
                int IdAgence = rs.getInt("IdAgence");
                System.out.format(
                        "\nId Client : " + IdClient +
                                "\nNom Client : " + NomClient +
                                "\nPrenom Client : " + PrenomClient +
                                "\nAdresse : " + AdresseClient +
                                "\nDepartement : " + DepartementClient +
                                "\nVille Client : " + VilleClient +
                                "\nId Agence : " + IdAgence );
            }
            st.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    //*********************************************************************************

    public void selectIdS() {

        System.out.print("Saisissez l'Id du Client  : ");
        this.IdClient = Integer.parseInt(key.nextLine());

        try {
            String query = "SELECT * FROM clients WHERE IdClient = '" + IdClient + "'";
            rs = st.executeQuery(query);

            while (rs.next()) {
                int IdClient = rs.getInt("IdClient");
                String NomClient = rs.getString("NomClient");
                String PrenomClient = rs.getString("PrenomClient");
                String AdresseClient = rs.getString("AdresseClient");
                String DepartementClient = rs.getString("DepartementClient");
                String VilleClient = rs.getString("VilleClient");
                int IdAgence = rs.getInt("IdAgence");
                System.out.format(
                        "\nId Client : " + IdClient +
                                "\nNom Client : " + NomClient +
                                "\nPrenom Client : " + PrenomClient +
                                "\nAdresse : " + AdresseClient +
                                "\nDepartement : " + DepartementClient +
                                "\nVille Client : " + VilleClient +
                                "\nId Agence : " + IdAgence );
            }
            st.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    //*****************************************************************************

    public boolean updateClient() {

        System.out.print("Saisissez l'Id du Client: ");
        this.IdClient = Integer.parseInt(key.nextLine());
        System.out.print("Saisissez le nom : ");
        this.NomClient = key.nextLine();
        System.out.print("Saisissez le prenom : ");
        this.prenomClient = key.nextLine();
        System.out.print("Saisissez l'adresse  : ");
        this.AdresseClient = key.nextLine();
        System.out.print("Saisissez le departement  : ");
        this.DepartementClient = key.nextLine();
        System.out.print("Saisissez la ville  : ");
        this.VilleClient = key.nextLine();
        System.out.print("Saisissez l'agence : ");
        this.IdAgence = Integer.parseInt(key.nextLine());

        try {
            String query = "UPDATE client SET"
                    + " Nom = '" + NomClient + "',"
                    + " Prenom = '" + prenomClient + "',"
                    + " AdresseClient = '" + AdresseClient + "',"
                    + " VilleClient = '" + VilleClient + "' WHERE IdClient = '" + IdClient + "';";
            st.executeUpdate(query);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

//********************************************************************************************************

    public boolean deleteClient() {

        System.out.print("Saisissez l'Id du Client a supprimer : ");
        this.IdClient = Integer.parseInt(key.nextLine());

        try {
            String query = "DELETE FROM clients WHERE IdClient = '" + IdClient + "'";
            st.executeUpdate(query);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //***********************************************************************************************

    public int getIdClient() {
        return IdClient;
    }

    public String getNom() {
        return NomClient;
    }

    public void setNom(String nom) {
        NomClient = nom;
    }

    public String getNomClient() {
        return NomClient;
    }
}
