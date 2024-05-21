package com.nativeninjas.vista;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.nativeninjas.prod1.R;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Premios extends AppCompatActivity {

    private Button btnCamiseta, btnGorra, btnLlavero, btnSalir;
    private FirebaseFirestore db;

    private static final String TAG = "Premios";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_premios);

        int puntuacionFinal = getIntent().getIntExtra("puntuacionFinal", 0);

        btnCamiseta = findViewById(R.id.btnCamiseta);
        btnGorra = findViewById(R.id.btnGorra);
        btnLlavero = findViewById(R.id.btnLlavero);
        btnSalir = findViewById(R.id.btnSalir);

        // Inicializa Firebase
        FirebaseApp.initializeApp(this);

        // Inicializa Firestore
        db = FirebaseFirestore.getInstance();

        // Referencia al documento que queremos recuperar
        DocumentReference docRef = db.collection("premios").document("premios");

        // Obtener el documento
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        // El documento existe, ahora puedes acceder a los campos
                        Long camiseta = document.getLong("camiseta");
                        Long gorra = document.getLong("gorra");
                        Long llavero = document.getLong("llavero");

                        // Convertir Long a int
                        int camisetaCount = camiseta != null ? camiseta.intValue() : 0;
                        int gorraCount = gorra != null ? gorra.intValue() : 0;
                        int llaveroCount = llavero != null ? llavero.intValue() : 0;

                        // Crear un array con los nombres de los ítems
                        String[] items = {"camiseta", "gorra", "llavero"};
                        Random random = new Random();
                        int randomIndex = random.nextInt(items.length);
                        String selectedItem = items[randomIndex];

                        // Sumar una unidad al ítem seleccionado
                        Map<String, Object> updates = new HashMap<>();
                        if (selectedItem.equals("camiseta")) {
                            updates.put("camiseta", camisetaCount + 1);
                        } else if (selectedItem.equals("gorra")) {
                            updates.put("gorra", gorraCount + 1);
                        } else if (selectedItem.equals("llavero")) {
                            updates.put("llavero", llaveroCount + 1);
                        }

                        // Actualizar el documento en Firestore
                        docRef.update(updates)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG, "DocumentSnapshot successfully updated!");
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w(TAG, "Error updating document", e);
                                    }
                                });

                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

        //Puede elegir cualquiera de los 3 premios
        if (puntuacionFinal >= 3 && puntuacionFinal < 5) {

            Toast.makeText(getApplicationContext(), "Puedes elegir el llavero", Toast.LENGTH_SHORT).show();
            btnLlavero.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "Has elegido el llavero", Toast.LENGTH_SHORT).show();

                    // Obtener el documento
                    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    // El documento existe, ahora puedes acceder a los campos
                                    Long llavero = document.getLong("llavero");

                                    // Convertir Long a int
                                    int llaveroCount = llavero != null ? llavero.intValue() : 0;

                                    // Restar una unidad a la camiseta
                                    Map<String, Object> updates = new HashMap<>();
                                    updates.put("llavero", llaveroCount - 1);

                                    // Actualizar el documento en Firestore
                                    docRef.update(updates)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Log.d(TAG, "DocumentSnapshot successfully updated!");
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.w(TAG, "Error updating document", e);
                                                }
                                            });

                                    Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                } else {
                                    Log.d(TAG, "No such document");
                                }
                            } else {
                                Log.d(TAG, "get failed with ", task.getException());
                            }
                        }
                    });
                }
            });
        }

        if (puntuacionFinal >= 5 && puntuacionFinal < 7) {
            Toast.makeText(getApplicationContext(), "Puedes elegir la gorra", Toast.LENGTH_SHORT).show();
            btnGorra.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "Has elegido la gorra", Toast.LENGTH_SHORT).show();

                    // Obtener el documento
                    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    // El documento existe, ahora puedes acceder a los campos
                                    Long gorra = document.getLong("gorra");

                                    // Convertir Long a int
                                    int gorraCount = gorra != null ? gorra.intValue() : 0;

                                    // Restar una unidad a la camiseta
                                    Map<String, Object> updates = new HashMap<>();
                                    updates.put("gorra", gorraCount - 1);

                                    // Actualizar el documento en Firestore
                                    docRef.update(updates)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Log.d(TAG, "DocumentSnapshot successfully updated!");
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.w(TAG, "Error updating document", e);
                                                }
                                            });

                                    Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                } else {
                                    Log.d(TAG, "No such document");
                                }
                            } else {
                                Log.d(TAG, "get failed with ", task.getException());
                            }
                        }
                    });
                }
            });
        }

        if (puntuacionFinal >= 7) {
            Toast.makeText(getApplicationContext(), "Puedes elegir la camiseta", Toast.LENGTH_SHORT).show();
            btnCamiseta.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "Has elegido la camiseta", Toast.LENGTH_SHORT).show();

                    // Obtener el documento
                    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    // El documento existe, ahora puedes acceder a los campos
                                    Long camiseta = document.getLong("camiseta");

                                    // Convertir Long a int
                                    int camisetaCount = camiseta != null ? camiseta.intValue() : 0;

                                    // Restar una unidad a la camiseta
                                    Map<String, Object> updates = new HashMap<>();
                                    updates.put("camiseta", camisetaCount - 1);

                                    // Actualizar el documento en Firestore
                                    docRef.update(updates)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Log.d(TAG, "DocumentSnapshot successfully updated!");
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.w(TAG, "Error updating document", e);
                                                }
                                            });

                                    Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                } else {
                                    Log.d(TAG, "No such document");
                                }
                            } else {
                                Log.d(TAG, "get failed with ", task.getException());
                            }
                        }
                    });
                }
            });
        }

        if (puntuacionFinal < 3) {
            Toast.makeText(getApplicationContext(), "No puedes elegir ningún premio", Toast.LENGTH_SHORT).show();

        }

        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Premios.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}