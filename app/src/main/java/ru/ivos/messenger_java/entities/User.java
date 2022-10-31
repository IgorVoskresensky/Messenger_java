package ru.ivos.messenger_java.entities;

public class User {

    private String id;
    private String name;
    private String secondName;
    private int age;
    private boolean online;

    public User() {
    }

    public User(String id, String name, String secondName, int age, boolean status) {
        this.id = id;
        this.name = name;
        this.secondName = secondName;
        this.age = age;
        this.online = status;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSecondName() {
        return secondName;
    }

    public int getAge() {
        return age;
    }

    public boolean isOnline() {
        return online;
    }
}
