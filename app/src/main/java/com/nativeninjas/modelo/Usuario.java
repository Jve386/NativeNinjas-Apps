package com.example.piedrapapeltijera.modelo;

public class Usuario {
    String id;
    int money;

//BUILDER
    public Usuario(String id) {
        this.id = id;
        this.money = 0;
    }

    //GETTERS & SETTERS
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getMoney() {
        return this.money;
    }

    public void setMoney(int money) {
        if(this.money < money){
            this.money = money;
        }
    }

    //METHODS

    @Override
    public String toString() {
        return "Usuario{" +
                "id='" + id + '\'' +
                ", money=" + money +
                '}';
    }

}
