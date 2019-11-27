package com.company;

import javax.swing.*;
import java.sql.*;
import java.util.Scanner;

public class agence {

    private Connection con;
    private Statement st;
    private ResultSet rs;
    private int IdAgence = 0;
    static Scanner key = new Scanner(System.in);
    boolean MenuAgence = true;
    client client = new client();
    compte compte = new compte();
    transaction transaction = new transaction();
    private boolean Suppresion = true;
    private boolean compteClient = true;

//***************************************** Connection *****************************************************************

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

//*********************************** Menu Agence ************************************************************************

    public void interactionAgence() {

        while (MenuAgence) {
            System.out.println("");
            System.out.println("         __________________________________________      ");
            System.out.println("                       [| Bienvenue  |]                  ");
            System.out.println("      +-------------------  Menu  --------------------+  ");
            System.out.println("      | Quelle operation voulez-vous effectuer?       |  ");
            System.out.println("      |                                               |  ");
            System.out.println("      |  1) Afficher toutes les agences               |  ");
            System.out.println("      |  2) Afficher une agence                       |  ");
            System.out.println("      |                                               |  ");
            System.out.println("      |  3) Crée un client                            |  ");
            System.out.println("      |  4) Afficher touts les Clients                |  ");
            System.out.println("      |  5) Afficher information client               |  ");
            System.out.println("      |                                               |  ");
            System.out.println("      |  6) Crée un compte                            |  ");
            System.out.println("      |  7) Afficher tous les Comptes                 |  ");
            System.out.println("      |  8) Afficher un Compte                        |  ");
            System.out.println("      |  9) Virement entre Comptes                    |  ");
            System.out.println("      | 10) Information compte(s) à 0€                |  ");
            System.out.println("      |                                               |  ");
            System.out.println("      | 13) Afficher toutes les transactions          |  ");
            System.out.println("      | 14) transaction liée a une Agence             |  ");
            System.out.println("      | 15) transaction liée a un Compte              |  ");
            System.out.println("      |                                               |  ");
            System.out.println("      | 16) Quitter le programme                      |  ");
            System.out.println("      +-----------------------------------------------+  ");
            System.out.print("Votre choix: ");
            String reponse = key.nextLine();
            System.out.println();

            switch (reponse) {

                case "1":
                    SelectAgence();
                    break;

                case "2":
                    selectIdAgence();
                    break;

                case "3":
                    client.insertClient();
                    break;

                case "4":
                    client.selectClient();
                    break;

                case "5":

                    while (compteClient) {

                        System.out.println("");
                        System.out.println("      +-------------- Menu suppression ---------------+  ");
                        System.out.println("      | Quelle operation voulez-vous effectuer?       |  ");
                        System.out.println("      |                                               |  ");
                        System.out.println("      |  1) Afficher un client                        |  ");
                        System.out.println("      |  2) Afficher tous les comptes liée au client  |  ");
                        System.out.println("      |  3) Modifier un client                        |  ");
                        System.out.println("      |  4) Supprimer un client                       |  ");
                        System.out.println("      |                                               |  ");
                        System.out.println("      |  5) Menu Agence                               |  ");
                        System.out.println("      +-----------------------------------------------+  ");
                        System.out.println("");
                        System.out.print("Votre choix: ");
                        String reponse1 = key.nextLine();
                        System.out.println();
                        switch (reponse1) {

                            case "1":
                                client.selectIdClient();
                                break;

                            case "2":
                                client.selectClientCompte();
                                break;

                            case "3":
                                client.updateClient();
                                break;

                            case "4":
                                client.deleteClient();
                                break;

                            case "5":
                                Suppresion = false;
                                break;
                        }
                    }
                    break;

                case "6":
                    compte.insertCompte();
                    break;

                case "7":
                    compte.selectCompte();
                    break;

                case "8":
                    compte.selectIdCompte();
                    break;

                case "9":
                    compte.virement();
                    break;

                case "10":
                    compte.AfficherCompteZero();
                    break;

                case "13":
                    transaction.SelectTransaction();
                    break;

                case "14":
                    SelectTransactionAgence();
                    break;

                case "15":
                    transaction.SelectTransactionCompte();
                    break;

                case "16":
                    MenuAgence = false;
                    System.out.println("au revoir ! a la prochaine ");
                    break;

                default:
                    System.out.println("Erreur de saisie clavier !! \n");
                    break;
            }
        }
    }

    //****************************** Select Transaction par Agence ***************************************************************

    public void SelectTransactionAgence() {

        System.out.print("Saisissez l'Id Agence : ");
        int agenceTemp = Integer.parseInt(key.nextLine());
        try {

            String query = "SELECT * FROM  projetbanque2.agence " +
                    "INNER JOIN compte on agence.IdAgence = projetbanque2.compte.IdAgence " +
                    "INNER JOIN transaction on projetbanque2.compte.IdCompte = projetbanque2.transaction.IdCompte_1 " +
                    "or projetbanque2.compte.IdCompte = projetbanque2.transaction.IdCompte_2 " +
                    "WHERE projetbanque2.compte.IdAgence =  '" + agenceTemp + "';";

            Statement stat = con.createStatement();
            ResultSet res = stat.executeQuery(query);

            while (res.next()) {
                int IdAgence = res.getInt("IdAgence");
                int IdCompte_1 = res.getInt("IdCompte_1");
                int IdCompte_2 = res.getInt("IdCompte_2");
                String DateTransaction = res.getString("DateTransaction");
                String MontantTransaction = res.getString("MontantTransaction");

                // Afficher les résultats
                System.out.println(" ");
                System.out.format(" %s, %s, %s, %s, %s\n", "Agence : " + IdAgence, "Compte Crediteur : " + IdCompte_1, "Compte Debiteur : " + IdCompte_2, "Date transaction : " + DateTransaction, "Montant Transaction : " + MontantTransaction);
                System.out.println("-------------------------");
            }

        } catch (Exception e) {
            System.err.println("Vous avez une exception!");
            System.err.println(e.getMessage());
        }
    }

//************************************ Select toutes les agences *********************************************************

    public void SelectAgence() {

        try {

            String query = "SELECT * FROM  agence";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);

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
                System.out.println(" ");

            }

        } catch (Exception e) {
            System.err.println("Vous avez une exception!");
            System.err.println(e.getMessage());
        }
    }

//************************************* Select agence par ID ****************************************************************

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
}