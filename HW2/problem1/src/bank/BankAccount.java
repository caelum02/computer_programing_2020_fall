package bank;

import bank.event.*;
import jdk.management.jfr.RecordingInfo;

class BankAccount {
    private Event[] events = new Event[maxEvents];
    final static int maxEvents = 100;

    private String id, password;
    private int balance, countEvents;

    BankAccount(String id, String password, int balance) {
        //TODO: Problem 1.1
        this.id = id;
        this.password = password;
        this.balance = balance;
    }

    boolean authenticate(String password) {
        //TODO: Problem 1.1
        return password.equals(this.password);
    }

    void deposit(int amount) {
        //TODO: Problem 1.1
        balance += amount;
        events[countEvents++] = new DepositEvent();
    }

    boolean withdraw(int amount) {
        //TODO: Problem 1.1
        if(balance >= amount) {
            balance -= amount;
            events[countEvents++] = new WithdrawEvent();
            return true;
        }
        return false;
    }

    void receive(int amount) {
        //TODO: Problem 1.1
        balance += amount;
        events[countEvents++] = new ReceiveEvent();
    }

    boolean send(int amount) {
        //TODO: Problem 1.1
        if(balance >= amount) {
            balance -= amount;
            events[countEvents++] = new SendEvent();
            return true;
        }
        return false;
    }

    Event[] getEvents() {
        Event[] retEvents = new Event[countEvents];
        for(int i=0; i<countEvents; i++)
            retEvents[i] = events[i];

        return retEvents;
    }

    int getBalance() {
        return balance;
    }
}
