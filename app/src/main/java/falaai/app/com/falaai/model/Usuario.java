package falaai.app.com.falaai.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

@JsonIgnoreProperties({ "id", "senha" })
@
public class Usuario {
    private String id;
    private String nome;
    private String email;
    private String senha;
    private DatabaseReference mDatabase;

    public Usuario() {
    }

    public void salvar(){
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Usuarios").child(getId());
        mDatabase.setValue(this);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
