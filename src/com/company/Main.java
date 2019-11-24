package com.company;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    static Scanner keyy = new Scanner(System.in);
    private static boolean finish = true;

    public static void main(String args[]) throws IOException, InterruptedException {


        while (finish) {
            /*System.out.println("appuyez touche pour continuer");
            System.in.read();*/
            //Thread.sleep(5000);
            MenuP();
        }
    }

    public static void MenuP() {


        System.out.println("  ");
        System.out.println("          __________________________________________      ");
        System.out.println("                        [| Bienvenue |]                   ");
        System.out.println("      +-------------------  Menu  --------------------+  ");
        System.out.println("      | Quelle operation voulez-vous effectuer?       |  ");
        System.out.println("      |  1) Gerer les Agences                         |  ");
        System.out.println("      |  2) Gerer les clients                         |  ");
        System.out.println("      |  3) Gerer les comptes                         |  ");
        System.out.println("      |                                               |  ");
        System.out.println("      |  4) Quitter le programme                      |  ");
        System.out.println("      +-----------------------------------------------+  ");
        System.out.print("Votre choix: ");
        String reponse = keyy.nextLine();
        System.out.println();

        switch (reponse) {
            case "1":
                agence agence = new agence();
                agence.interactionAgence();
                break;

            case "2":
                client client = new client();
                client.interactionClient();
                break;

            case "3":
                compte compte1 = new compte();
                compte1.interactionCompte();
                break;

            case "4":
                finish = false;
                System.out.println("au revoir ! a la prochaine ");
                break;

            default:
                System.out.println("Erreur de saisie clavier !! \n");
                break;
        }
    }
}

