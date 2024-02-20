package org.example;

import org.example.factory.ConnectionFactory;
import org.example.factory.DaoFactory;
import org.example.factory.ServiceFactory;

public class Main {
    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        DaoFactory daoFactory = new DaoFactory(connectionFactory);
        ServiceFactory serviceFactory = new ServiceFactory(daoFactory);

        ConsoleApplication consoleApplication = new ConsoleApplication(serviceFactory, connectionFactory);
        consoleApplication.setupDatabase();
        consoleApplication.start();
    }
}