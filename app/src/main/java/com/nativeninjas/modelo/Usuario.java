<<<<<<< HEAD
package com.nativeninjas.modelo;
=======
package com.example.piedrapapeltijera.modelo;

>>>>>>> 624b1fe91fddbaf159ccc3246799c4a384cbad6f
public class Usuario {
    String id;
    int money;

//BUILDER
<<<<<<< HEAD
    public Usuario() {
=======
    public Usuario(String id) {
        this.id = id;
        this.money = 0;
>>>>>>> 624b1fe91fddbaf159ccc3246799c4a384cbad6f
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
