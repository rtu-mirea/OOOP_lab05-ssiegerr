package com.company;
public class Client extends User {

    public Client(String name, String login, String password) {
        super(name, login, password);
    }

    public String takeResult(Request request) {
        return this.getName() + " нашел работу, которую хотел, у работодателя " + request.getRequester().getName();
    }
}
