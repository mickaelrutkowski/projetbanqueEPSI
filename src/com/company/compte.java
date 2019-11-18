package com.company;

import javax.swing.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class compte extends client {

    private int NumeroCompte = 0;
    private Connection con;
    private Statement st;
    private ResultSet rs;
    private float decouvert;
    private float decouvertMax = 800;
    private float debitMax = 1000;
    static Scanner key = new Scanner(System.in);
    private float Solde = 0;
    private int IdClient = 0;
    private int NumeroAgence = 0;

    //********************* Méthodes *********************************************

    public compte()
    {

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
        public boolean insertCompte() {

        try {

            System.out.print("Saisissez le Solde  : ");
            this.Solde = Float.parseFloat(key.nextLine());
            System.out.print("Saisissez l'Id du CLient  : ");
            this.IdClient = Integer.parseInt(key.nextLine());
            System.out.print("Saisissez le Numero d'agence  : ");
            this.NumeroAgence = Integer.parseInt(key.nextLine());

            String query = "INSERT INTO compte VALUES('" + this.NumeroCompte + "','" + this.Solde + "','" + this.IdClient + "','" + this.NumeroAgence + "');";
            st.executeUpdate(query);

            System.out.println("-----------------------------");
            System.out.println("Client inseret dans la base de donée");
            System.out.println("Numero de compte : " + this.NumeroCompte);
            System.out.println("Solde Client : " + this.Solde);
            System.out.println("ID Client : " + this.IdClient);
            System.out.println("Numero agence : " + this.NumeroAgence);

            System.out.println("------------------------------\n");
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

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
                int NumeroCompte = rs.getInt("NumeroCompte");
                String Solde = rs.getString("Solde");
                String IdClient = rs.getString("IdClient");
                String NumeroAgence = rs.getString("NumeroAgence");

                // Afficher les résultats
                System.out.format("%s, %s, %s, %s\n", NumeroCompte, Solde, IdClient, NumeroAgence);
            }
            st.close();
        } catch (Exception e) {
            System.err.println("Vous avez une exception!");
            System.err.println(e.getMessage());
        }
    }

        // Résultats de la recherche avec les données des disques avec un choix de génération
        public void selectIdCompte(int para) {

        try {

            String query = "SELECT * FROM compte WHERE NumeroCompte = '" + para + "'";
            rs = st.executeQuery(query);
            while (rs.next()) {
                int NumeroCompte = rs.getInt("NumeroAgence");
                String Solde = rs.getString("Solde");
                String IdClient = rs.getString("NumeroCompte");
                int NumeroAgence = rs.getInt("IdClient");
                System.out.format(
                        "\nNumeroCompte: " + NumeroCompte +
                        "\nSolde : " + Solde +
                        "\nID Client : " + IdClient +
                        "\nNumero Agence : " + NumeroAgence );

            }
            st.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

        public boolean delete(int id) {

        try {

            String query = "DELETE FROM compte WHERE NumeroCompte = '" + id + "'";
            st.executeUpdate(query);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

        public boolean update(int NumeroCompte, String Solde) {

        try {

            String query = "UPDATE compte SET"
                    + " Solde = '" + Solde
                    + "' WHERE NumeroCompte = '" + NumeroCompte + "';";
            st.executeUpdate(query);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


        //**************************************************************************

    public void crediter(float montant)
    {
        this.Solde = this.Solde + montant;
        if (Solde < 0)
        {
            this.decouvert = -1 * this.Solde;
        }
        else this.decouvert = 0;
    }

    //**************************************************************************
    public int getNumCompte()
    {
        return this.NumeroCompte;
    }
    //**************************************************************************

    public boolean debiter(float montant)
    {
        if(montant > 0 && montant<= this.debitMax
                && (this.Solde - montant) > -this.decouvertMax)
        {
            this.Solde = this.Solde - montant;
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

    public void virement(float montant,compte c)
    {
        if(this.debiter(montant)==true)
        {
            c.crediter(montant);
        }
        else
        {
            System.out.println("Solde insuffisant ...........");
        }
    }

    //**************************************************************************

    static float readFloat(String message)
    {
        float x = 0;
        boolean fin;
        do
        { fin = true;
            try
            {
                System.out.print(message);
                x = key.nextFloat();
            }
            catch (Exception e)
            { System.out.println("Problème de lecture au clavier d'un réel !!" + e);
                //On vide la ligne avant d'en lire une autre
                key.nextLine();
                fin = false;
            }
        }while (fin == false);
        return x;
    }

}
