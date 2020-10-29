package bank;

import security.Encryptor;
import security.Encrypted;
import security.Message;
import security.key.BankPublicKey;
import security.key.BankSymmetricKey;

public class MobileApp {

    private String randomUniqueStringGen(){
        return Encryptor.randomUniqueStringGen();
    }
    private final String AppId = randomUniqueStringGen();
    public String getAppId() {
        return AppId;
    }

    String id, password;
    public MobileApp(String id, String password){
        this.id = id;
        this.password = password;
    }

    BankSymmetricKey symmetricKey;
    public Encrypted<BankSymmetricKey> sendSymKey(BankPublicKey publickey){
        //TODO: Problem 1.3
        symmetricKey = new BankSymmetricKey(randomUniqueStringGen());
        return new Encrypted<>(symmetricKey, publickey);
    }

    public Encrypted<Message> deposit(int amount){
        //TODO: Problem 1.3
        Message depositMessage = new Message("deposit", id, password, amount);
        return new Encrypted<>(depositMessage, symmetricKey);
    }

    public Encrypted<Message> withdraw(int amount){
        //TODO: Problem 1.3
        Message withdrawMessage = new Message("withdraw", id, password, amount);
        return new Encrypted<>(withdrawMessage, symmetricKey);
    }

    public boolean processResponse(Encrypted<Boolean> obj){
        //TODO: Problem 1.3
        if(obj==null)
            return false;

        Boolean response = obj.decrypt(symmetricKey);
        if(response == null)
            return false;

        return response;
    }

}

