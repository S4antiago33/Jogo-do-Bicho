package org.example;


import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        Application app = new Application(scan);

        app.exec();

        scan.close();
    }
}
