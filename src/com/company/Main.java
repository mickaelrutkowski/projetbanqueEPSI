package com.company;


public class Main {

    public static void main(String args[]){

        //--------------------------------------------
        //Parti Clients
       // client client = new client();
        //client.delete(2);
        //client.update(1,"davida","pedro","rue","lille");
        //client.insert();
        //client.selectId(8);
        //System.out.println("\n");
        //---------------------------------------------
        //Parti Agence
        //agence agence = new agence();
        //agence.insertAgence();


        //----------------------------------------------
        //Parti Compte
        //compte compte = new compte();
        //compte.insertCompte();
        //compte.selectIdCompte(12);
        //System.out.println("\n");

        //---------------------------------------------
        //Parti Transaction
        transaction transaction = new transaction();
        transaction.DebitCompte(2);


    }
}
