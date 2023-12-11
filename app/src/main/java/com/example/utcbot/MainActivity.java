package com.example.utcbot;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        // Crear tarjetas dinámicamente y agregarlas al contenedor
        LinearLayout cardsContainer = findViewById(R.id.cardsContainer);

        // Ejemplo de datos de proyectos (puedes reemplazar esto con tus propios datos)
        String[] projectNames = {"Proyecto 1", "Proyecto 2", "Proyecto 3", "Proyecto 4","Proyecto 5","Proyecto 6","Proyecto 7","Proyecto 8","Proyecto 9", "Proyecto 10"};
        int[] projectImages = {R.drawable.icono1, R.drawable.icono1, R.drawable.icono1,R.drawable.icono1,R.drawable.icono1,R.drawable.icono1,R.drawable.icono1,R.drawable.icono1,R.drawable.icono1,R.drawable.icono1,};


        for (int i = 0; i < projectNames.length; i++) {
            View cardView = LayoutInflater.from(this).inflate(R.layout.card_item, cardsContainer, false);

            // Configurar imagen y nombre del proyecto
            ImageView projectImage = cardView.findViewById(R.id.projectImage);
            TextView projectName = cardView.findViewById(R.id.projectName);

            // Establecer la imagen y el nombre del proyecto según los datos
            projectImage.setImageResource(projectImages[i]);
            projectName.setText(projectNames[i]);

            cardsContainer.addView(cardView);
        }

        // Obtén una referencia al botón btn3
        ImageButton btn3 = findViewById(R.id.btn3);

        // Agrega un controlador de eventos al botón btn3
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lanzar la nueva actividad cuando se hace clic en btn3
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(intent);
            }
        });
    }
}