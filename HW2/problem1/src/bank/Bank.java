package bank;

import bank.event.Event;
import security.*;
import security.key.*;

import javax.swing.plaf.basic.BasicLookAndFeel;
import java.util.GregorianCalendar;
import java.util.HashMap;

public class Bank {
    private int numAccounts = 0;
    final static int maxAccounts = 100;
    private BankAccount[] accounts = new BankAccount[maxAccounts];
    private String[] ids = new String[maxAccounts];

    public void createAccount(String id, String password) {
        createAccount(id, password, 0);
    }

    public void createAccount(String id, String password, int initBalance) {
        int accountId = numAccounts;
        accounts[accountId] = new BankAccount(id, password, initBalance);
        ids[accountId] = id;
        numAccounts+=1;
    }

    public boolean deposit(String id, String password, int amount) {
        //TODO: Problem 1.1
        BankAccount account = find(id);
        if(account == null || !account.authenticate(password))
            return false;

        account.deposit(amount);
        return true;
    }

    public boolean withdraw(String id, String password, int amount) {
        //TODO: Problem 1.1
        BankAccount account = find(id);
        if(account == null || !account.authenticate(password))
            return false;

        return account.withdraw(amount);
    }

    public boolean transfer(String sourceId, String password, String targetId, int amount) {
        //TODO: Problem 1.1
        BankAccount sourceAccount = find(sourceId);
        if(sourceAccount == null || !sourceAccount.authenticate(password))
            return false;

        BankAccount targetAccount = find(targetId);
        if(targetAccount == null)
            return false;

        if(sourceAccount.send(amount)){
            targetAccount.receive(amount);
            return true;
        }

        return false;
    }

    public Event[] getEvents(String id, String password) {
        //TODO: Problem 1.1
        BankAccount account = find(id);
        if(account == null || !account.authenticate(password))
            return null;

        return account.getEvents();
    }

    public int getBalance(String id, String password) {
        //TODO: Problem 1.1
        BankAccount account = find(id);
        if(account == null || !account.authenticate(password))
            return -1;
        return account.getBalance();
    }

    private static String randomUniqueStringGen(){
        return Encryptor.randomUniqueStringGen();
    }
    private BankAccount find(String id) {
        for (int i = 0; i < numAccounts; i++) {
            if(ids[i].equals(id)){return accounts[i];};
        }
        return null;
    }
    final static int maxSessionKey = 100;
    int numSessionKey = 0;
    String[] sessionKeyArr = new String[maxSessionKey];
    BankAccount[] bankAccountmap = new BankAccount[maxSessionKey];
    String generateSessionKey(String id, String password){
        BankAccount account = find(id);
        if(account == null || !account.authenticate(password)){
            return null;
        }
        String sessionkey = randomUniqueStringGen();
        sessionKeyArr[numSessionKey] = sessionkey;
        bankAccountmap[numSessionKey] = account;
        numSessionKey += 1;
        return sessionkey;
    }
    BankAccount getAccount(String sessionkey){
        for(int i = 0 ;i < numSessionKey; i++){
            if(sessionKeyArr[i] != null && sessionKeyArr[i].equals(sessionkey)){
                return bankAccountmap[i];
            }
        }
        return null;
    }

    boolean deposit(String sessionkey, int amount) {
        //TODO: Problem 1.2
        BankAccount account = getAccount(sessionkey);
        if(account == null) return false;

        account.deposit(amount);
        return true;
    }

    boolean withdraw(String sessionkey, int amount) {
        //TODO: Problem 1.2
        BankAccount account = getAccount(sessionkey);
        if(account == null) return false;

        return account.withdraw(amount);
    }

    boolean transfer(String sessionkey, String targetId, int amount) {
        //TODO: Problem 1.2
        BankAccount sourceAccount = getAccount(sessionkey);
        if(sourceAccount == null)
            return false;

        BankAccount targetAccount = find(targetId);
        if(targetAccount == null)
            return false;

        if(sourceAccount.send(amount)){
            targetAccount.receive(amount);
            return true;
        }
        return false;
    }

    private BankSecretKey secretKey;
    public BankPublicKey getPublicKey(){
        BankKeyPair keypair = Encryptor.publicKeyGen();
        secretKey = keypair.deckey;
        return keypair.enckey;
    }

    private HashMap<String, BankSymmetricKey> symmetricKeyHashMap = new HashMap<String, BankSymmetricKey>();

    public void fetchSymKey(Encrypted<BankSymmetricKey> encryptedKey, String AppId){
        //TODO: Problem 1.3
        if(encryptedKey == null)
            return;

        BankSymmetricKey symmetricKey = encryptedKey.decrypt(secretKey);

        if(symmetricKey == null)
            return;

        symmetricKeyHashMap.put(AppId, symmetricKey);
    }

    public Encrypted<Boolean> processRequest(Encrypted<Message> messageEnc, String AppId){
        //TODO: Problem 1.3
        if(!symmetricKeyHashMap.containsKey(AppId) || messageEnc == null)
            return null;

        BankSymmetricKey symmetricKey = symmetricKeyHashMap.get(AppId);
        Message requestMessage = messageEnc.decrypt(symmetricKey);
        if(requestMessage == null)
            return null;

        boolean result = false;
        if(requestMessage.getRequestType().equals("deposit"))
            result = deposit(requestMessage.getId(), requestMessage.getPassword(), requestMessage.getAmount());
        if(requestMessage.getRequestType().equals("withdraw"))
            result = withdraw(requestMessage.getId(), requestMessage.getPassword(), requestMessage.getAmount());

        return new Encrypted<>(result, symmetricKey);
    }
}