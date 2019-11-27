package com.company;

import javax.swing.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class transaction {

    private Connection con;
    private Statement st;
    private ResultSet rs;
    private int IdTransaction;
    static Scanner key = new Scanner(System.in);

    //*************************************************************************************

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

    //**********************************************************************************************

    public void SelectTransaction() {

        try {

            String SelectTransaction = "SELECT * FROM  transaction";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(SelectTransaction);

            while (rs.next()) {
                int IdTRansaction = rs.getInt("IdTRansaction");
                String MontantTransaction = rs.getString("MontantTransaction");
                String DateTransaction = rs.getString("DateTransaction");
                int IdCompte_1 = rs.getInt("IdCompte_1");
                int IdCompte_2 = rs.getInt("IdCompte_2");

                // Afficher les résultats
                System.out.println("Transaction : ");
                System.out.format("%s, %s, %s, %s, %s\n", "IdTRansaction : " + IdTRansaction, "MontantTransaction : " + MontantTransaction + " € ", "Date de Transaction : " + DateTransaction, "Compte Crediteur : " + IdCompte_1, "Compte Debiteur : " + IdCompte_2);
                System.out.println("-------------------------");
            }
            st.close();
        } catch (Exception e) {
            System.err.println("Vous avez une exception!");
            System.err.println(e.getMessage());
        }
    }

//*****************************************************************************************************************************

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
                                "\nMontant transaction : " + MontantTransaction + " € " +
                                "\nDate de transaction : " + DateTransaction +
                                "\nId Compte Crediteur : " + IdCompte_1 +
                                "\nId Compte Debiteur : " + IdCompte_2);
            }
            st.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    //*********************************** select transaction par idCompte ***************************************************

    public void SelectTransactionCompte() {

        System.out.print("Saisissez l'Id du compte : ");
        int CompteTemp = Integer.parseInt(key.nextLine());
        try {

            String query = "SELECT * FROM  projetbanque2.transaction " +
                    "WHERE projetbanque2.transaction.IdCompte_1  = '" + CompteTemp + "'" +
                    "OR projetbanque2.transaction.IdCompte_2 =  '" + CompteTemp + "'";

            Statement stat = con.createStatement();
            ResultSet res = stat.executeQuery(query);

            while (res.next()) {
                int IdTRansaction = res.getInt("IdTRansaction");
                String DateTransaction = res.getString("DateTransaction");
                String MontantTransaction = res.getString("MontantTransaction");
                int IdCompte_1 = res.getInt("IdCompte_1");
                int IdCompte_2 = res.getInt("IdCompte_2");

                // Afficher les résultats
                System.out.println(" ");
                System.out.format(" %s, %s, %s, %s, %s\n", "IdTransaction : " + IdTRansaction, "Montant Transaction : " + MontantTransaction,"Date transaction : " + DateTransaction, "Compte Crediteur : " + IdCompte_1, "Compte Debiteur : " + IdCompte_2);
                System.out.println("-------------------------");
            }

        } catch (Exception e) {
            System.err.println("Vous avez une exception!");
            System.err.println(e.getMessage());
        }
    }


}
