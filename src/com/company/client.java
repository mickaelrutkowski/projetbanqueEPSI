package com.company;

import javax.swing.*;
import java.sql.*;
import java.util.Scanner;

public class client {

    private Connection con;
    private Statement st;
    private ResultSet rs;
    private int IdClient;
    private String NomClient = null;
    private String prenomClient = null;
    private String AdresseClient = null;
    private String DepartementClient = null;
    private String VilleClient = null;
    private int IdAgence;
    Scanner key = new Scanner(System.in);
    compte compte = new compte();
    private int IdCompte;

//************************************* Connection ************************************************************************

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

//*************************************** Insert un client *******************************************************************
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

            agence agence = new agence();
            agence.SelectAgence();

            System.out.print("Saisissez l'agence : ");
            System.out.print(" ");
            this.IdAgence = Integer.parseInt(key.nextLine());

            String query = "INSERT INTO clients VALUES('" + this.IdClient + "','" + this.NomClient + "','" + this.prenomClient + "','" + this.AdresseClient + "','" + this.DepartementClient + "','" + this.VilleClient + "','" + this.IdAgence + "');";
            st.executeUpdate(query);
            selectClientV();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

//******************************************* Select un client ******************************************************************

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

//******************************************* Select un client ******************************************************************

    public void selectClientCompte() {

        System.out.print("Saisissez l'Id du Client  : ");
        this.IdClient = Integer.parseInt(key.nextLine());

        try {

            String query = "SELECT * FROM  projetbanque2.compte " +
                    "INNER JOIN clients on projetbanque2.clients.IdClient = projetbanque2.compte.IdClient " +
                    "WHERE projetbanque2.compte.IdClient =  '" + IdClient + "';";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                int IdCompte = rs.getInt("IdCompte");
                int IdClient = rs.getInt("IdClient");
                String Nom = rs.getString("NomClient");
                String Prenom = rs.getString("PrenomClient");
                String Adresse = rs.getString("AdresseClient");
                String DepartementClient = rs.getString("DepartementClient");
                String Ville = rs.getString("VilleClient");
                int IdAgence = rs.getInt("IdAgence");

                // Afficher les résultats
                System.out.format("%s, %s, %s, %s, %s, %s,%s, %s\n",IdCompte, IdClient, Nom, Prenom, Adresse, DepartementClient, Ville, IdAgence);
            }
            st.close();
        } catch (Exception e) {
            System.err.println("Vous avez une exception!");
            System.err.println(e.getMessage());
        }
    }
    //************************************ Select Client*********************************************

    public void selectClientV() {

        try {
            String Select = "SELECT * FROM Clients WHERE IdClient = ( SELECT MAX(IdClient) FROM projetbanque2.clients)";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(Select);

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

    //************************************ Select Client par ID*********************************************

    public void selectIdClient() {

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
                                "\nId Agence : " + IdAgence);
            }
            st.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    //******************************* Modifier un client **********************************************

    public boolean updateClient() {

        System.out.print("Saisissez l'Id du Client a modifier: ");
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

    public void deleteClient() {

        System.out.print("Saisissez l'Id du Compte : ");
        this.IdCompte = Integer.parseInt(key.nextLine());
        System.out.print("Saisissez l'Id du Client : ");
        this.IdClient = Integer.parseInt(key.nextLine());

        try {

            String req = "SELECT Solde,IdClient FROM compte WHERE IdCompte = '" + IdCompte + "' " +
                    "AND IdClient = '" + IdClient + "'";
            Statement sta = con.createStatement();
            ResultSet res = sta.executeQuery(req);

            while (res.next()) {

                float Solde = res.getFloat("Solde");
                int IdClient = res.getInt("IdClient");
                System.out.println("Le compte : " + IdClient);
                System.out.println("Le montant du compte est de : " + Solde + " € ");

                if (Solde != 0) {
                    System.out.println("Vous devez solder le compte avant de pouvoir supprimer");

                } else {
                    String query = "DELETE FROM compte WHERE IdClient = '" + IdClient + "'" +
                            "AND IdCompte = '" + IdCompte + "'";
                    st.executeUpdate(query);
                    System.out.println("client supprimer ! ");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
