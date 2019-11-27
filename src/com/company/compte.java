package com.company;

import javax.swing.*;
import java.sql.*;
import java.util.Scanner;

import static java.time.LocalDateTime.now;

public class compte {

    private Connection con;
    private Statement st;
    private ResultSet rs;
    private int IdCompte;
    private int IdCompte_1;
    private int IdCompte_2;
    private int IdClient;
    private int IdAgence;
    private int IdTRansaction = 0;
    private float Solde;
    private float Somme;
    static Scanner key = new Scanner(System.in);
    transaction transaction = new transaction();
    private boolean SuppresionCompte = true;

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
    // Insérez un nouveau Compte dans la base de données

    public boolean insertCompte() {

        try {

            this.Solde = 50;
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

    //********************* Select un compte *********************************************

    public void selectCompte() {

        try {

            String query = "SELECT * FROM compte";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                int IdCompte = rs.getInt("IdCompte");
                String Solde = rs.getString("Solde");
                String IdAgence = rs.getString("IdAgence");
                String IdClient = rs.getString("IdClient");

            }
            st.close();
        } catch (Exception e) {
            System.err.println("Vous avez une exception!");
            System.err.println(e.getMessage());
        }
    }

//***************************************Select par ID avec parametre****************************************

    public void selectIdCompteV(int Compte) {

        try {

            String query = "SELECT * FROM compte WHERE IdCompte = '" + Compte + "'";
            rs = st.executeQuery(query);

            while (rs.next()) {
                int IdCompte = rs.getInt("IdCompte");
                String Solde = rs.getString("Solde");
                String IdClient = rs.getString("IdClient");
                int IdAgence = rs.getInt("IdAgence");
                System.out.format(
                        "\nIdCompte: " + IdCompte +
                                "\nSolde : " + Solde + " € " +
                                "\nID Client : " + IdClient +
                                "\nNumero Agence : " + IdAgence);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    //***************************** SelectCompte par ID sans paramentre***********************************

    public void selectIdCompte() {

        System.out.print("Saisissez l'Id du Compte  : ");
        this.IdCompte = Integer.parseInt(key.nextLine());

        try {

            String query = "SELECT * FROM compte WHERE IdCompte = '" + IdCompte + "'";
            rs = st.executeQuery(query);
            while (rs.next()) {
                int IdCompte = rs.getInt("IdCompte");
                String Solde = rs.getString("Solde");
                String IdClient = rs.getString("IdClient");
                int IdAgence = rs.getInt("IdAgence");
                System.out.format(
                        "\nIdCompte: " + IdCompte +
                                "\nSolde : " + Solde + " € " +
                                "\nID Client : " + IdClient +
                                "\nNumero Agence : " + IdAgence);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    //******************************* Virement **********************************************

    public void virement() {

        System.out.print("Saisissez le Montant  : ");
        this.Somme = Float.parseFloat(key.nextLine());
        System.out.print("Saisissez l'Id du Compte a crediter : ");
        this.IdCompte_1 = Integer.parseInt(key.nextLine());
        System.out.print("Saisissez l'Id du Compte a debiter: ");
        this.IdCompte_2 = Integer.parseInt(key.nextLine());

        try {

            String req = "SELECT Solde FROM compte WHERE IdCompte = '" + IdCompte_2 + "'";
            Statement sta = con.createStatement();
            ResultSet res = sta.executeQuery(req);

            while (res.next()) {

                float Solde = res.getFloat("Solde");
                System.out.println("Le montant du compte a debiter est de : " + Solde + " € ");

                if ((Solde - Somme) >= 0) {

                    selectIdCompteV(IdCompte_1);
                    System.out.println("Compte avant virement");
                    selectIdCompteV(IdCompte_2);
                    System.out.println("Compte avant virement ");
                    String Credit = "UPDATE compte SET Solde = Solde + '" + Somme + "' WHERE IdCompte = '" + IdCompte_1 + "';";
                    st.executeUpdate(Credit);
                    String Debit = "UPDATE compte SET Solde = Solde - '" + Somme + "' WHERE IdCompte = '" + IdCompte_2 + "';";
                    st.executeUpdate(Debit);
                    selectIdCompteV(IdCompte_1);
                    System.out.println("Compte apres virement ");
                    selectIdCompteV(IdCompte_2);
                    System.out.println("Compte apres virement ");
                    String insertTran = "INSERT INTO transaction VALUES('" + this.IdTRansaction + "','" + Somme + "','" + now() + "','" + this.IdCompte_1 + "','" + this.IdCompte_2 + "');";
                    st.executeUpdate(insertTran);
                    System.out.println(" ");
                    transaction.selectIdTransaction();

                } else {

                    System.out.println("Solde insuffisant");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//***************************** supprimer les comptes a zero ************************************************

    public void AfficherCompteZero() {

        try {

            while (SuppresionCompte) {

                System.out.println("");
                System.out.println("      +-------------- Menu suppression ------------------+  ");
                System.out.println("      | Quelle operation voulez-vous effectuer?          |  ");
                System.out.println("      |                                                  |  ");
                System.out.println("      |  1) Afficher tous les Comptes a zero par agence  |  ");
                System.out.println("      |  2) Afficher tous les Comptes a zero d'un client |  ");
                System.out.println("      |  3) Supprimer un compte a zero                   |  ");
                System.out.println("      |                                                  |  ");
                System.out.println("      |  4) Menu Agence                                  |  ");
                System.out.println("      +--------------------------------------------------+  ");
                System.out.println("");
                System.out.print("Votre choix: ");
                String reponse1 = key.nextLine();
                System.out.println();
                switch (reponse1) {

                    case "1":
                        System.out.print("Saisissez l'Id de l'agence : ");
                        this.IdAgence = Integer.parseInt(key.nextLine());
                        String CZ = "SELECT * FROM  projetbanque2.compte " +
                                "WHERE  Solde = 0 AND IdAgence =  '" + IdAgence + "';";
                        rs = st.executeQuery(CZ);

                        while (rs.next()) {
                            int IdCompte = rs.getInt("IdCompte");
                            String Solde = rs.getString("Solde");

                            String IdClient = rs.getString("IdClient");
                            int IdAgence = rs.getInt("IdAgence");
                            System.out.format(
                                    "\nIdCompte: " + IdCompte +
                                            "\nSolde : " + Solde + " € " +
                                            "\nID Client : " + IdClient +
                                            "\nNumero Agence : " + IdAgence);
                            System.out.println("");
                        }

                        break;

                    case "2":

                        System.out.print("Saisissez l'Id du client  : ");
                        this.IdClient = Integer.parseInt(key.nextLine());

                        String query = "SELECT * FROM  projetbanque2.compte " +
                                "INNER JOIN clients on projetbanque2.clients.IdClient = projetbanque2.compte.IdClient " +
                                "WHERE  Solde = 0 AND projetbanque2.compte.IdClient =  '" + IdClient + "';";
                        rs = st.executeQuery(query);

                        while (rs.next()) {
                            int IdCompte = rs.getInt("IdCompte");
                            String Solde = rs.getString("Solde");

                            String IdClient = rs.getString("IdClient");
                            int IdAgence = rs.getInt("IdAgence");
                            System.out.format(
                                    "\nIdCompte: " + IdCompte +
                                            "\nSolde : " + Solde + " € " +
                                            "\nID Client : " + IdClient +
                                            "\nNumero Agence : " + IdAgence);
                            System.out.println("");
                        }
                        break;

                    case "3":

                        System.out.print("Saisissez l'Id du Compte  a supprimer : ");
                        this.IdCompte = Integer.parseInt(key.nextLine());

                        String Delete = "DELETE FROM compte WHERE Solde = 0 AND projetbanque2.compte.IdCompte =  '" + IdCompte + "'";
                        st.executeUpdate(Delete);
                        System.out.println("Le compte a etait supprimer !");
                        break;

                    case "4":

                        SuppresionCompte = false;
                        break;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}