package com.example.utcbot;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ListadoProyectos extends AppCompatActivity {

    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_proyectos);

        //Codigo para poner la pantalla horizontal
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        //codigo para ocultar la barra de estado del celular
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Ocultar la barra de navegación y los botones de navegación
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);


        // Crear tarjetas dinámicamente y agregarlas al contenedor
        GridLayout cardsContainer = findViewById(R.id.cardsContainer);

        dbHelper = new DatabaseHelper(this);
        mostrarTarjetasDesdeDB(cardsContainer);

    }

    // Método para cargar tarjetas desde la base de datos
    private void mostrarTarjetasDesdeDB(GridLayout cardsContainer) {
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
                    projectImage.setImageResource(R.drawable.proyecto); // Cambia esto según tus necesidades
                    projectName.setText(nombre);

                    cardView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Lanzar la nueva actividad cuando se hace clic en la tarjeta
                            Intent intent = new Intent(ListadoProyectos.this, MainActivity2.class);
                            intent.putExtra("id", id);
                            intent.putExtra("nombre", nombre);
                            intent.putExtra("contenido", contenido);
                            startActivity(intent);
                        }
                    });

                    // Fuera del método onLongClick en MainActivity
                    final AlertDialog[] alertDialog = {null};

                    // Dentro del método onLongClick en MainActivity
                    cardView.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View view) {
                            // Crear un cuadro de diálogo personalizado
                            AlertDialog.Builder builder = new AlertDialog.Builder(ListadoProyectos.this);
                            View dialogView = LayoutInflater.from(ListadoProyectos.this).inflate(R.layout.custom_dialog, null);
                            builder.setView(dialogView);

                            // Obtener referencias a los elementos en el cuadro de diálogo personalizado
                            TextView dialogMessage = dialogView.findViewById(R.id.dialog_message);
                            Button btnEliminar = dialogView.findViewById(R.id.btnEliminar);
                            Button btnCancelar = dialogView.findViewById(R.id.btnCancelar);

                            // Configurar el mensaje del cuadro de diálogo
                            dialogMessage.setText("¿Estás seguro de eliminar este proyecto?");

                            // Configurar los botones del cuadro de diálogo
                            btnEliminar.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    // Eliminar la tarjeta de la base de datos
                                    eliminarTarjeta(id);
                                    // Cerrar el cuadro de diálogo
                                    alertDialog[0].dismiss();
                                }
                            });

                            btnCancelar.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    // No hacer nada si se selecciona Cancelar
                                    // Cerrar el cuadro de diálogo
                                    alertDialog[0].dismiss();
                                }
                            });

                            // Asignar el cuadro de diálogo creado al array alertDialog
                            alertDialog[0] = builder.create();

                            // Mostrar el cuadro de diálogo
                            alertDialog[0].show();

                            return true;
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
        GridLayout cardsContainer = findViewById(R.id.cardsContainer);

        // Llama a la función para mostrar las tarjetas desde la base de datos
        mostrarTarjetasDesdeDB(cardsContainer);
    }

    private void eliminarTarjeta(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        int rowsAffected = db.delete(
                DatabaseHelper.TABLE_NAME,
                DatabaseHelper.COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)}
        );

        if (rowsAffected > 0) {
            Toast.makeText(this, "Tarjeta eliminada correctamente", Toast.LENGTH_SHORT).show();
            // Actualizar la vista después de eliminar la tarjeta
            GridLayout cardsContainer = findViewById(R.id.cardsContainer);
            mostrarTarjetasDesdeDB(cardsContainer);
        } else {
            Toast.makeText(this, "Error al eliminar tarjeta", Toast.LENGTH_SHORT).show();
        }

        db.close();
    }
}