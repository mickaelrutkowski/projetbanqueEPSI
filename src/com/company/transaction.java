package com.company;

import javax.swing.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Scanner;

import static java.time.LocalDateTime.now;

public class transaction {

    private Connection con;
    private Statement st;
    private ResultSet rs;
    private int IdTRansaction = 0;
    private float MontantTransaction;
    private int IdCompte_1;
    private int IdCompte_2;
    Scanner key = new Scanner(System.in);
    private float Somme;

    public transaction() {

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

/*   public boolean insertTransaction() {

        try {
          compte compte = new compte();
          Somme = compte.getSomme();
            System.out.println( Somme);
            System.out.println(IdCompte_2 + IdCompte_1);
            System.out.println(this.IdCompte_2 + this.IdCompte_1);
            String insertTran = "INSERT INTO transaction VALUES('" + this.IdTRansaction + "','" + Somme + "','" + now() + "','" + this.IdCompte_1 + "','" + this.IdCompte_2 + "');";
            st.executeUpdate(insertTran);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }*/

//*****************************************************************************************

    public void SelectTransaction() {

        try {

            String SelectTransaction = "SELECT * FROM  transaction";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(SelectTransaction);
            // itérer dans le jeu de résultats java
            while (rs.next()) {
                int IdTRansaction = rs.getInt("IdTRansaction");
                String MontantTransaction = rs.getString("MontantTransaction");
                String DateTransaction = rs.getString("DateTransaction");
                int IdCompte_1 = rs.getInt("IdCompte_1");
                int IdCompte_2 = rs.getInt("IdCompte_2");

                // Afficher les résultats
                System.out.println("Transaction : ");
                System.out.format("%s, %s, %s, %s, %s\n", "IdTRansaction : " + IdTRansaction, "MontantTransaction : " + MontantTransaction, "Date de Transaction : " + DateTransaction, "IdCompte_1 : " + IdCompte_1, "IdCompte_2 : " + IdCompte_2);
                System.out.println("-------------------------");
            }
            st.close();
        } catch (Exception e) {
            System.err.println("Vous avez une exception!");
            System.err.println(e.getMessage());
        }
    }

//*****************************************************************************************************************************
    // Résultats de la recherche avec les données des disques avec un choix de génération

    public void selectIdTransaction() {

        try {

            String query = "SELECT * FROM transaction WHERE IdTRansaction = ( SELECT MAX(IdTRansaction) FROM transaction)";

            rs = st.executeQuery(query);
            while (rs.next()) {
                String IdTRansaction = rs.getString("IdTRansaction");
                String MontantTransaction = rs.getString("MontantTransaction");
                String DateTransaction = rs.getString("DateTransaction");
                int IdCompte_1 = rs.getInt("IdCompte_1");
                int IdCompte_2 = rs.getInt("IdCompte_2");
                System.out.format(
                        "\nId Transaction : " + IdTRansaction +
                                "\nMontant transaction : " + MontantTransaction +
                                "\nDate de transaction : " + DateTransaction +
                                "\nId Compte 1 : " + IdCompte_1 +
                                "\nId Compte 2 : " + IdCompte_2);
            }
            st.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

//*******************************************************************************************************************

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

//*************************************************************************************************************************

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
}
