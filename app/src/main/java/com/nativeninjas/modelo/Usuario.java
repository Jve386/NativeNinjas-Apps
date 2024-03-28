
package com.nativeninjas.modelo;

public class Usuario {
    String id;

//BUILDER

    public Usuario() {

    }

    //GETTERS & SETTERS
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    //METHODS

    @Override
    public String toString() {
        return "Usuario{" +
                "id='" + id + '\'' +
                '}';
    }

}
