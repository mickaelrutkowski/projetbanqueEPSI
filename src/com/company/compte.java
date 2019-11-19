package com.company;

import javax.swing.*;
import java.sql.*;
import java.util.Scanner;

public class compte extends client {

    private int IdCompte = 0;
    private Connection con;
    private Statement st;
    private ResultSet rs;
    private float decouvert;
    private float decouvertMax = 800;
    private float debitMax = 1000;
    static Scanner key = new Scanner(System.in);
    private float Solde = 0;
    private int IdClient = 0;
    private int IdAgence = 0;


    //********************* Connection *********************************************

    public compte() {

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

    //********************* Insertion *********************************************
    // Insérez un nouveau client dans la base de données
    public boolean insertCompte() {

        try {

            System.out.print("Saisissez le Solde  : ");
            this.Solde = Float.parseFloat(key.nextLine());
            System.out.print("Saisissez le Numero d'agence  : ");
            this.IdAgence = Integer.parseInt(key.nextLine());
            System.out.print("Saisissez l'Id du CLient  : ");
            this.IdClient = Integer.parseInt(key.nextLine());


            String query = "INSERT INTO compte VALUES('" + this.IdCompte + "','" + this.Solde + "','" + this.IdAgence + "','" + this.IdClient + "');";
            st.executeUpdate(query);

            selectCompte();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //********************* Select *********************************************

    public void selectCompte() {

        try {

            // notre requête SQL SELECT.
            // si vous n'avez besoin que de quelques colonnes, spécifiez-les par nom au lieu d'utiliser "*"
            String query = "SELECT * FROM compte";

            // crée l'instruction java
            Statement st = con.createStatement();

            // exécute la requête et obtient un ensemble de résultats java
            ResultSet rs = st.executeQuery(query);

            // itérer dans le jeu de résultats java
            while (rs.next()) {
                int IdCompte = rs.getInt("IdCompte");
                String Solde = rs.getString("Solde");
                String IdAgence = rs.getString("IdAgence");
                String IdClient = rs.getString("IdClient");


                // Afficher les résultats
                System.out.format("%s, %s, %s, %s\n", IdCompte, Solde, IdAgence, IdClient);
            }
            st.close();
        } catch (Exception e) {
            System.err.println("Vous avez une exception!");
            System.err.println(e.getMessage());
        }
    }

    //********************* SelectCompte par ID *********************************************
    // Résultats de la recherche avec les données des disques avec un choix de génération

    public void selectIdCompte(int para) {

        try {

            String query = "SELECT * FROM compte WHERE IdCompte = '" + para + "'";
            rs = st.executeQuery(query);
            while (rs.next()) {
                int IdCompte = rs.getInt("IdCompte");
                String Solde = rs.getString("Solde");
                String IdClient = rs.getString("IdClient");
                int IdAgence = rs.getInt("IdAgence");
                System.out.format(
                        "\nIdCompte: " + IdCompte +
                                "\nSolde : " + Solde +
                                "\nID Client : " + IdClient +
                                "\nNumero Agence : " + IdAgence);

            }
            st.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    //********************* Delete *********************************************

    public boolean delete(int id) {

        try {

            String query = "DELETE FROM compte WHERE IdCompte = '" + id + "'";
            st.executeUpdate(query);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //********************* modifier *********************************************

    public boolean update(int IdCompte, String Solde) {

        try {

            String query = "UPDATE compte SET"
                    + " Solde = '" + Solde
                    + "' WHERE IdCompte = '" + IdCompte + "';";
            st.executeUpdate(query);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public void crediter(float Somme)
    {
        this.Solde = this.Solde + Somme;
        if (Solde < 0)
        {
            this.decouvert = -1 * this.Solde;
        }
        else this.decouvert = 0;
    }

    public boolean debiter(float Somme)
    {
        if(Somme > 0 && Somme<= this.debitMax
                && (this.Solde - Somme) > -this.decouvertMax)
        {
            this.Solde = this.Solde - Somme;
            if (this.Solde < 0)
            {
                this.decouvert = -Solde;
            }
            else { this.decouvert = 0; }
            return true;
        }
        else {return false;}
    }

    //**************************************************************
    public void setDecouvertMax(float decouvertMax)
    {
        this.decouvertMax = decouvertMax;
    }
    //*************************************************************
    public void setDebitMax(float debitMax)
    {
        this.debitMax = debitMax;
    }
    //**************************************************************************

    public void virement(float Somme,int CompteDebiteur, int CompteCrediteur)
    {
        this.debiter(Somme);

            System.out.println("Solde insuffisant ...........");

    }


    public int getIdCompte() {
        return IdCompte;
    }
}
