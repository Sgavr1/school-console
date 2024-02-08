package org.example;

import org.example.Factory.DaoFactory;
import org.example.Factory.ServiceFactory;

public class Main {
    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        DaoFactory daoFactory = new DaoFactory(connectionFactory);
        ServiceFactory serviceFactory = new ServiceFactory(daoFactory);

        ConsoleApplication consoleApplication = new ConsoleApplication(serviceFactory, connectionFactory);
        consoleApplication.initialisationDatabase();
        consoleApplication.start();
    }
}