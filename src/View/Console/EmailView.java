package View.Console;

import Model.Email;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EmailView {
    Scanner sc = new Scanner(System.in);

    public List<Email> CriarEmail() {
        List<Email> listaEmails = new ArrayList<Email>();
        String validation = "";

        do {
            System.out.println("Informe seu endereço email: ");
            String resp = sc.nextLine();
            listaEmails.add(new Email(resp));

            System.out.println("Deseja adicionar mais emails? (S/N)");
            validation = sc.next().toUpperCase();
            sc.nextLine();
        } while (!validation.equals("N"));

        return listaEmails;
    }
}
