package org.example;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import org.example.models.Animal;
import org.example.models.Jogo;
import org.example.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class Application {
    private Scanner scan;
    private SessionFactory sessionFactory;

    public Application(Scanner scanner) {
        this.scan = scanner;
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    private void renderMenu(){
        System.out.println("Jogo do Bicho");
        System.out.println("1- Cadastrar Aposta;");
        System.out.println("2- Listar Aposta;");
        System.out.println("3- Sortear.");
        System.out.println("0- Sair.");
        System.out.println("Escolha uma opção: ");
    }

public void exec(){
    boolean sair = false;
    while (!sair){
        this.renderMenu();
        int opcao = this.scan.nextInt();
        this.scan.nextLine();
        sair = this.processaOption(opcao);
    }
}

private boolean processaOption(int option) {
    if (option == 0) return true;

    if (option == 1){
        this.cadastrarAposta();
        return false;
    }
        if (option == 2) {
            this.listarAposta();
            return false;
        }
        if (option == 3) {
            this.sortear();
            return false;
        }

    System.out.println("Opção inválida.");
        return false;

}



private void sortear(){

        Session session = this.sessionFactory.openSession();

        List<Animal> animals = session

                .createQuery("FROM org.example.models.Animal", Animal.class)
                .list();
        Random random = new Random();
        int index = random.nextInt(animals.size());
        Animal animal = animals.get(index);

    System.out.println("Animal do Dia...");
    System.out.println(animal.getNome());
    System.out.println(DateFormat.getDateInstance().format(new Date()));
    System.out.println("Vencedores...");

    List<Jogo> jogos = session.createQuery("FROM org.example.models.Jogo WHERE animal_id = " + animal.getId(), Jogo.class)
            .list();

    if(jogos.size() == 0){
        System.out.println("Nenhum vencedor. :(");

    }
    for(Jogo jogo: jogos){
        System.out.println(jogo.getUsuario());
    }

    session.close();
    System.out.println("---");
    }
private void listarAposta(){
        Session session = this.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        List<Jogo> jogos = session.createQuery("FROM org.example.models.Jogo", Jogo.class).list();

        for (Jogo jogo : jogos){

            Animal animal = session
                    .createQuery("FROM org.example.models.Animal a WHERE a.id = "+jogo.getAnimalId(), Animal.class)
                    .list()
                    .get(0);

            System.out.println("Nome: "+ jogo.getUsuario());
            System.out.println("Animal: "+ animal.getNome());
            System.out.println("Valor da Aposta: "+jogo.getValorAposta());
            System.out.println("Data: "+ DateFormat.getDateInstance().format(jogo.getDia()));
            System.out.println("---");
        }
        session.close();
}
    private void cadastrarAposta(){
    Jogo jogo = new Jogo();

    System.out.print("Usuário: ");
    jogo.setUsuario(this.scan.nextLine());

    System.out.print("Número: ");
    jogo.setAnimalId(this.scan.nextInt());

    System.out.print("Valor Aposta: ");
    jogo.setValorAposta(this.scan.nextDouble());

    System.out.println("Salvando...");
    jogo.setDia(new Date());

    Session session = this.sessionFactory.openSession();

    Transaction transaction = session.beginTransaction();

    try {
        session.save(jogo);
        transaction.commit();
    }catch (Exception e) {
        transaction.rollback();
    }finally {
        session.close();
    }
    }
}