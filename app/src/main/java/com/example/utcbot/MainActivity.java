package com.example.utcbot;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        // Crear tarjetas dinámicamente y agregarlas al contenedor
        LinearLayout cardsContainer = findViewById(R.id.cardsContainer);

        dbHelper = new DatabaseHelper(this);
        mostrarTarjetasDesdeDB(cardsContainer);

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

    // Método para cargar tarjetas desde la base de datos
    private void mostrarTarjetasDesdeDB(LinearLayout cardsContainer) {
        cardsContainer.removeAllViews(); // Limpiar las tarjetas existentes

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {DatabaseHelper.COLUMN_ID, DatabaseHelper.COLUMN_NOMBRE, DatabaseHelper.COLUMN_CONTENIDO};
        Cursor cursor = db.query(DatabaseHelper.TABLE_NAME, projection, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int idIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_ID);
                int nombreIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_NOMBRE);
                int contenidoIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_CONTENIDO);

                // Verificar que las columnas existan en el cursor
                if (idIndex != -1 && nombreIndex != -1 && contenidoIndex != -1) {
                    int id = cursor.getInt(idIndex);
                    String nombre = cursor.getString(nombreIndex);
                    String contenido = cursor.getString(contenidoIndex);

                    View cardView = LayoutInflater.from(this).inflate(R.layout.card_item, cardsContainer, false);

                    // Configurar imagen y nombre del proyecto
                    ImageView projectImage = cardView.findViewById(R.id.projectImage);
                    TextView projectName = cardView.findViewById(R.id.projectName);

                    // Establecer la imagen y el nombre del proyecto según los datos
                    projectImage.setImageResource(R.drawable.icono1); // Cambia esto según tus necesidades
                    projectName.setText(nombre);

                    cardView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Lanzar la nueva actividad cuando se hace clic en la tarjeta
                            Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                            intent.putExtra("nombre", nombre);
                            intent.putExtra("contenido", contenido);
                            startActivity(intent);
                        }
                    });

                    cardsContainer.addView(cardView);
                } else {
                    // Manejar el caso en que las columnas no existan en el cursor
                    // Puedes agregar un mensaje de registro o mostrar un Toast
                    Toast.makeText(this, "Error: Columnas no encontradas en el cursor", Toast.LENGTH_SHORT).show();
                }
            } while (cursor.moveToNext());

            cursor.close();
        } else {
            // Manejar el caso en que el cursor esté vacío
            // Puedes agregar un mensaje de registro o mostrar un Toast
            Toast.makeText(this, "Error: Cursor vacío", Toast.LENGTH_SHORT).show();
        }

        db.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Obtén una referencia al contenedor de tarjetas
        LinearLayout cardsContainer = findViewById(R.id.cardsContainer);

        // Llama a la función para mostrar las tarjetas desde la base de datos
        mostrarTarjetasDesdeDB(cardsContainer);
    }
}

