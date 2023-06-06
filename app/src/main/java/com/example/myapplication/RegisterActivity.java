package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.HashMap;
import java.util.Map;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentReference;



public class RegisterActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener los valores ingresados por el usuario
                EditText nameEditText = findViewById(R.id.nameEditText);
                EditText addressEditText = findViewById(R.id.addressEditText);
                EditText passwordEditText = findViewById(R.id.passwordEditText);
                String name = nameEditText.getText().toString();
                String address = addressEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                // Guardar los datos en Firebase Firestore
                saveUserData(name, address, password);

            }
            private void saveUserData(String name, String address, String password) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();

                // Crear un nuevo documento en la colección "users" con los datos del perfil
                Map<String, Object> userData = new HashMap<>();
                userData.put("name", name);
                userData.put("address", address);
                userData.put("password", password);

                db.collection("users")
                        .add(userData)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                // El usuario se guardó exitosamente en Firestore
                                Toast.makeText(RegisterActivity.this, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Error al guardar el usuario en Firestore
                                Toast.makeText(RegisterActivity.this, "Error al registrar usuario", Toast.LENGTH_SHORT).show();
                            }
                        });
            }


        });



    }
}