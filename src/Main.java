import View.Console.EnderecoView;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        EnderecoView enderecoView = new EnderecoView();

        System.out.println("\n\nSelecione a rota desejada\n\n");
        System.out.println(Arrays.toString(Rotas.values()));
        System.out.println();
        String escolha = sc.next();

        switch (Rotas.valueOf(escolha)) {
            case Endereco -> enderecoView.SelecionarAcaoEndereco();
        }
    }

    enum Rotas {
        Endereco
    }
}