package com.nativeninjas.vista;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.nativeninjas.prod1.R;

// video java:
//https://www.bing.com/videos/riverview/relatedvideo?&q=firebase+authentication+android+en+java&&mid=6A2B5C7830D9298DD03F6A2B5C7830D9298DD03F&&FORM=VRDGAR
// video Kotlin:
//https://www.bing.com/videos/riverview/relatedvideo?q=firebase%20authentication%20android%20en%20java&mid=DA341C217F49B5409429DA341C217F49B5409429&ajaxhist=0
public class login_firebase extends AppCompatActivity {
   Button btn_Acceder;
   Button btn_Registro;
   Button btn_Gmail;
   EditText usuario, password;
   FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_firebase);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        usuario = findViewById(R.id.EditTextEmail);
        password = findViewById(R.id.EditTextPassword);
        btn_Acceder = findViewById(R.id.btn_Acceder);
        btn_Registro = findViewById(R.id.btn_Registro);
        btn_Gmail = findViewById(R.id.btn_Gmail);
        mAuth = FirebaseAuth.getInstance();


        btn_Gmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailUser = usuario.getText().toString().trim();
                String passUser = password.getText().toString().trim();

                if(emailUser.isEmpty() || passUser.isEmpty()){
                    Toast.makeText(login_firebase.this,"Ingresar los datos",Toast.LENGTH_SHORT).show();
                }else {
                    loginUser(emailUser, passUser);
                }
            }
        });
    }
    private void loginUser(String emailUser, String passUser){
        mAuth.signInWithEmailAndPassword(emailUser,passUser).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    finish();
                    startActivity(new Intent(login_firebase.this,MainActivity.class));
                    Toast.makeText(login_firebase.this,"Bienvenido",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(login_firebase.this,"Error",Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(login_firebase.this,"Error al iniciar sesión", Toast.LENGTH_SHORT).show();
            }
        });
    }
}