package com.company;

import javax.swing.*;
import java.sql.*;
import java.util.Scanner;

import static java.time.LocalDateTime.now;

public class compte {

    private int IdCompte = 0;
    private int IdCompte_1 = 0;
    private int IdCompte_2 = 0;
    private int IdClient = 0;
    private int IdAgence = 0;
    private int IdTRansaction = 0;
    private float Solde = 0;
    private float Somme = 0;
    private Connection con;
    private Statement st;
    private ResultSet rs;
    static Scanner key = new Scanner(System.in);
    boolean fin = true;
    private float MontantTransaction;


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

    public void interactionCompte() {

        while (fin) {
            System.out.println("");
            System.out.println("         __________________________________________      ");
            System.out.println("                       [| Bienvenu  |]                   ");
            System.out.println("      +-------------------  Menu  --------------------+  ");
            System.out.println("      | Quelle operation voulez-vous effectuer?       |  ");
            System.out.println("      |  0) Supprimer compte(s) à 0€                  |  ");
            System.out.println("      |  1) Ajouter un Compte                         |  ");
            System.out.println("      |  2) Afficher tous les Comptes                 |  ");
            System.out.println("      |  3) Afficher un Compte                        |  ");
            System.out.println("      |  4) Modifier Compte                           |  ");
            System.out.println("      |  5) supprimer un Compte                       |  ");
            System.out.println("      |  6) Debiter Compte                            |  ");
            System.out.println("      |  7) Crediter Compte                           |  ");
            System.out.println("      |  8) Virement Compte                           |  ");
            System.out.println("      |                                               |  ");
            System.out.println("      |  9) Menu Principal                            |  ");
            System.out.println("      +-----------------------------------------------+  ");
            System.out.print("Votre choix: ");
            String reponse = key.nextLine();
            System.out.println();

            switch (reponse) {
                case "1":
                    insertCompte();
                    break;

                case "2":
                    selectCompte();
                    break;

                case "3":
                    selectIdCompteV(IdCompte);
                    break;

                case "4":
                    updateCompte();
                    break;

                case "5":
                    deleteCompte();
                    break;

                case "6":
                    debiter();
                    break;

                case "7":
                    crediter();
                    break;

                case "8":
                    virement();
                    break;

                case "9":
                    Main MenuP = new Main();
                    MenuP.MenuP();
                    break;

                default:
                    System.out.println("Erreur de saisi clavier !! \n");
                    break;
            }
        }
    }

    //********************* Insertion *********************************************
    // Insérez un nouveau client dans la base de données

    public boolean insertCompte() {

        try {
            /*System.out.print("Saisissez le Solde  : ");
            this.Solde = Float.parseFloat(key.nextLine());*/
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

    //********************* Select *********************************************

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

                System.out.format("%s, %s, %s, %s\n","IdCompte : " + IdCompte,"Solde : " + Solde,"IdAgence : " + IdAgence,"IdClient : " + IdClient);
            }
            st.close();
        } catch (Exception e) {
            System.err.println("Vous avez une exception!");
            System.err.println(e.getMessage());
        }
    }

//********************************************************************************************************************

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
                                "\nSolde : " + Solde +
                                "\nID Client : " + IdClient +
                                "\nNumero Agence : " + IdAgence);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    //********************* SelectCompte par ID *********************************************
    // Résultats de la recherche avec les données des disques avec un choix de génération

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
                                "\nSolde : " + Solde +
                                "\nID Client : " + IdClient +
                                "\nNumero Agence : " + IdAgence);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    //********************* modifier *********************************************

    public boolean updateCompte() {

        System.out.print("Saisissez l'Id du Compte  : ");
        this.IdCompte = Integer.parseInt(key.nextLine());
        System.out.print("Saisissez le nouveau Solde  : ");
        this.Solde = Float.parseFloat(key.nextLine());

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

    //********************* Delete *********************************************

    public boolean deleteCompte() {

        System.out.print("Saisissez l'Id du Compte a supprimer  : ");
        this.IdCompte = Integer.parseInt(key.nextLine());

        try {
            String query = "DELETE FROM compte WHERE IdCompte = '" + IdCompte + "'";
            st.executeUpdate(query);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //*****************************************************************************

    public void virement() {
        System.out.print("Saisissez le Montant  : ");
        this.Somme = Float.parseFloat(key.nextLine());
        System.out.print("Saisissez l'Id du Compte a crediter : ");
        this.IdCompte_1 = Integer.parseInt(key.nextLine());
        System.out.print("Saisissez l'Id du Compte a debiter: ");
        this.IdCompte_2 = Integer.parseInt(key.nextLine());

        try {

            String req = "SELECT Solde FROM compte WHERE IdCompte = '" + IdCompte_1 + "'";
            rs = st.executeQuery(req);
            while (rs.next()) {
                String Solde = rs.getString("Solde");
            }
            if ((Solde - Somme) >= 0) {
                selectIdCompteV(IdCompte_1);
                System.out.println(" ");
                selectIdCompteV(IdCompte_2);
                System.out.println(" ");
                String Credit = "UPDATE compte SET Solde = Solde + '" + Somme + "' WHERE IdCompte = '" + IdCompte_1 + "';";
                st.executeUpdate(Credit);
                String Debit = "UPDATE compte SET Solde = Solde - '" + Somme + "' WHERE IdCompte = '" + IdCompte_2 + "';";
                st.executeUpdate(Debit);
                selectIdCompteV(IdCompte_1);
                System.out.println(" ");
                selectIdCompteV(IdCompte_2);
                transaction transaction = new transaction();
                String insertTran = "INSERT INTO transaction VALUES('" + this.IdTRansaction + "','" + Somme + "','" + now() + "','" + this.IdCompte_1 + "','" + this.IdCompte_2 + "');";
                st.executeUpdate(insertTran);
                System.out.println(" ");
                transaction.selectIdTransaction();
            } else {
                System.out.println("Solde insuffisant");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //**************************************************************************

    public void crediter() {

        System.out.print("Saisissez l'Id du Compte a crediter : ");
        this.IdCompte = Integer.parseInt(key.nextLine());
        System.out.print("Saisissez le Montant  : ");
        this.Somme = Float.parseFloat(key.nextLine());

        try {
            String Credit = "UPDATE compte SET Solde = Solde + '" + Somme + "' WHERE IdCompte = '" + IdCompte + "';";
            st.executeUpdate(Credit);
            selectIdCompteV(IdCompte);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //*****************************************************************************

    public void debiter() {

        System.out.print("Saisissez l'Id du Compte a debiter: ");
        this.IdCompte = Integer.parseInt(key.nextLine());
        System.out.print("Saisissez le Montant  : ");
        this.Somme = Float.parseFloat(key.nextLine());

            try {
                selectIdCompte();
                String Debit = "UPDATE compte SET Solde = Solde - '" + Somme + "' WHERE IdCompte = '" + IdCompte + "';";
                st.executeUpdate(Debit);
                selectIdCompteV(IdCompte);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    //**************************************************************************
    public int getIdCompte() {
        return IdCompte;
    }
    public int getIdCompte1() {
        return IdCompte_1;
    }
    public int getIdCompte2() {
        return IdCompte_2;
    }

    public float getSomme() {
        return Somme;
    }
}
