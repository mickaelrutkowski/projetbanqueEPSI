package com.company;

import javax.swing.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class transaction extends compte {

    private Connection con;
    private Statement st;
    private ResultSet rs;
    private int IdTRansaction = 0;
    private float MontantTransaction;
    private String DateTransaction;
    private int IdCompte_1;
    private int IdCompte_2;
    Scanner key = new Scanner(System.in);

    public transaction()
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
    //********************* Insertion *********************************************
    // Insérez un nouveau client dans la base de données
    public boolean insertTransaction() {

        try {

            System.out.print("Saisissez le Montant de la Transaction  : ");
            this.MontantTransaction = Float.parseFloat(key.nextLine());
            System.out.print("Saisissez la date : ");
            this.DateTransaction = String.valueOf(key.nextLine());
            System.out.print("Saisissez le Numero de compte a debiter  : ");
            this.IdCompte_1 = Integer.parseInt(key.nextLine());
            System.out.print("Saisissez le Numero de compte a crediter  : ");
            this.IdCompte_2 = Integer.parseInt(key.nextLine());

            String query = "INSERT INTO transaction VALUES('" + this.IdTRansaction + "','" + this.MontantTransaction + "','" + this.DateTransaction + "','" + this.IdCompte_1 + "','" + this.IdCompte_2 + "');";
            st.executeUpdate(query);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void SelectTransaction() {

        try {

            // notre requête SQL SELECT.
            // si vous n'avez besoin que de quelques colonnes, spécifiez-les par nom au lieu d'utiliser "*"
            String query = "SELECT * FROM  transaction";

            // crée l'instruction java
            Statement st = con.createStatement();

            // exécute la requête et obtient un ensemble de résultats java
            ResultSet rs = st.executeQuery(query);

            // itérer dans le jeu de résultats java
            while (rs.next()) {
                int IdTRansaction = rs.getInt("IdTRansaction");
                String MontantTransaction = rs.getString("MontantTransaction");
                String DateTransaction = rs.getString("DateTransaction");
                int IdCompte_1 = rs.getInt("IdCompte_1");
                int IdCompte_2 = rs.getInt("IdCompte_2");

                // Afficher les résultats
                System.out.println("Transaction : ");
                System.out.format("%s, %s, %s, %s, %s\n","IdTRansaction : " + IdTRansaction,"MontantTransaction : " + MontantTransaction, "Date de Transaction : " + DateTransaction, "IdCompte_1 : " + IdCompte_1, "IdCompte_2 : " + IdCompte_2);
                System.out.println("-------------------------");
            }
            st.close();
        } catch (Exception e) {
            System.err.println("Vous avez une exception!");
            System.err.println(e.getMessage());
        }
    }

    // Résultats de la recherche avec les données des disques avec un choix de génération
    public void selectIdTransaction(int para) {

        try {

            String query = "SELECT * FROM transaction WHERE IdTRansaction = '" + para + "'";
            rs = st.executeQuery(query);
            while (rs.next()) {
                String IdTRansaction = rs.getString("IdTRansaction");
                String MontantTransaction = rs.getString("MontantTransaction");
                String DateTransaction = rs.getString("DateTransaction");
                int IdCompte_1 = rs.getInt("IdCompte_1");
                int IdCompte_2 = rs.getInt("IdCompte_2");
                System.out.format(
                        "\nId Client : " + IdTRansaction +
                                "\nNumeroAgence : " + MontantTransaction +
                                "\nLibelle : " + DateTransaction +
                                "\nAdresseAgence : " + IdCompte_1 +
                                "\nVilleAgence : " + IdCompte_2);
            }
            st.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public boolean deleteTransaction(int id) {

        try {

            String query = "DELETE FROM transaction WHERE IdTRansaction = '" + id + "'";
            st.executeUpdate(query);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateTransaction(int IdTRansaction, float MontantTransaction, String DateTransaction, int IdCompte_1, int IdCompte_2) {

        try {

            String query = "UPDATE transaction SET"
                    + " MontantTransaction = '" + MontantTransaction + "',"
                    + " DateTransaction = '" + DateTransaction + "',"
                    + " IdCompte_1 = '" + IdCompte_1 + "',"
                    + " IdCompte_2 = '" + IdCompte_2 + "' WHERE IdTRansaction = '" + IdTRansaction + "';";
            st.executeUpdate(query);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public int getIdCompte_1() {
        return IdCompte_1;
    }

    public float getMontantTransaction() {
        return MontantTransaction;
    }

    public void CrediterCompte( int IdCompte) {

        try {

            String query = "UPDATE compte SET Solde = Solde + 200 WHERE IdClient = IdClient;";
            st.executeUpdate(query);
            selectIdCompte(IdCompte);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void DebitCompte( int IdCompte) {

        try {

            String query = "UPDATE compte SET Solde = Solde - 200 WHERE IdClient = IdClient;";
            st.executeUpdate(query);
            selectIdCompte(IdCompte);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public int getIdCompte() {
        return IdCompte_1;
    }
    public int getIdCompte_2() {
        return IdCompte_2;
    }
}
