package com.nativeninjas.vista;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nativeninjas.prod1.R;

public class LoginFirebase extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private EditText email, password;
    private Button btn_Acceder, btn_Registro, btn_Gmail;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 9001;
    private static final String TAG = "LoginFirebase";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_firebase);

        // Inicializa Firebase Auth y Database
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        email = findViewById(R.id.EditTextEmail);
        password = findViewById(R.id.EditTextPassword);
        btn_Acceder = findViewById(R.id.btn_Acceder);
        btn_Registro = findViewById(R.id.btn_Registro);
        btn_Gmail = findViewById(R.id.btn_Gmail);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        btn_Gmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        btn_Acceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailUser = email.getText().toString().trim();
                String passUser = password.getText().toString().trim();

                if (emailUser.isEmpty() || passUser.isEmpty()) {
                    Toast.makeText(LoginFirebase.this, "Ingresar los datos", Toast.LENGTH_SHORT).show();
                } else {
                    loginUser(emailUser, passUser);
                }
            }
        });

        btn_Registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailUser = email.getText().toString().trim();
                String passUser = password.getText().toString().trim();

                if (emailUser.isEmpty() || passUser.isEmpty()) {
                    Toast.makeText(LoginFirebase.this, "Ingresar los datos", Toast.LENGTH_SHORT).show();
                } else {
                    registerUser(emailUser, passUser);
                }
            }
        });
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            firebaseAuthWithGoogle(account.getIdToken());
        } catch (ApiException e) {
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            Toast.makeText(LoginFirebase.this, "Falló la autenticación con Google", Toast.LENGTH_SHORT).show();
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithCredential:success");
                            startActivity(new Intent(LoginFirebase.this, MainActivity.class));
                            finish();
                        } else {
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginFirebase.this, "Falló la autenticación con Google", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void loginUser(String emailUser, String passUser) {
        mAuth.signInWithEmailAndPassword(emailUser, passUser).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    startActivity(new Intent(LoginFirebase.this, MainActivity.class));
                    finish();
                    Toast.makeText(LoginFirebase.this, "Bienvenido", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginFirebase.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginFirebase.this, "Error al iniciar sesión", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void registerUser(final String emailUser, String passUser) {
        // Crea un nuevo usuario en Firebase Authentication
        mAuth.createUserWithEmailAndPassword(emailUser, passUser)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Obtiene el UID del usuario
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null) {
                                String userId = user.getUid();
                                // Guarda el email en Firebase Realtime Database
                                mDatabase.child("users").child(userId).child("email").setValue(emailUser)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(LoginFirebase.this, "Email guardado en Firebase", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(LoginFirebase.this, "Error al guardar el email", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            }
                        } else {
                            Toast.makeText(LoginFirebase.this, "Error al registrar el usuario", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
