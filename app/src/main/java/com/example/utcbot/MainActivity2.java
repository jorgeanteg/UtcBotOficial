package com.example.utcbot;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity2 extends AppCompatActivity {

    private static final String KEY_WEB_VIEW_URL = "web_view_url";

    private WebView wv1;
    private DatabaseHelper dbHelper;
    private int id;
    private String nombre;
    private String contenido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //codigo para ocultar la barra de estado del celular
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Ocultar la barra de navegación y los botones de navegación
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);


        // Obtener datos pasados desde MainActivity
        id = getIntent().getIntExtra("id", -1);
        nombre = getIntent().getStringExtra("nombre");
        contenido = getIntent().getStringExtra("contenido");

        wv1 = findViewById(R.id.wv1);
        WebSettings webSettings = wv1.getSettings();
        webSettings.setBuiltInZoomControls(true);
        webSettings.setJavaScriptEnabled(true);

        wv1.addJavascriptInterface(this, "Android");

        wv1.setWebViewClient(new Callback());

        dbHelper = new DatabaseHelper(this);

        if (savedInstanceState != null) {
            // Restaurar el estado de la URL del WebView si está presente
            String savedUrl = savedInstanceState.getString(KEY_WEB_VIEW_URL);
            if (savedUrl != null) {
                wv1.loadUrl(savedUrl);
            } else {
                // Cargar una URL predeterminada si no hay ninguna URL guardada
                wv1.loadUrl("file:///android_asset/index.html");
            }
        } else {
            // Cargar la URL predeterminada cuando la actividad se crea por primera vez
            wv1.loadUrl("file:///android_asset/index.html");
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        // Guardar el estado, en este caso, la URL actual del WebView
        outState.putString(KEY_WEB_VIEW_URL, wv1.getUrl());
        super.onSaveInstanceState(outState);
    }

    private class Callback extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
        }
    }

    @Override
    public void onBackPressed() {
        if (wv1 != null && wv1.canGoBack()) {
            wv1.goBack();
        } else {
            // Mostrar el modal al presionar el botón de atrás
            wv1.post(new Runnable() {
                @Override
                public void run() {
                    wv1.evaluateJavascript("abrirModalGuardar();", null);
                }
            });
        }
    }




    // Método para insertar datos en la base de datos
    private void insertarDatosEnDB(String nombre, String contenido) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_NOMBRE, nombre);
        values.put(DatabaseHelper.COLUMN_CONTENIDO, contenido);

        long newRowId = db.insert(DatabaseHelper.TABLE_NAME, null, values);

        if (newRowId != -1) {
            Toast.makeText(this, "Datos insertados correctamente", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error al insertar datos", Toast.LENGTH_SHORT).show();
        }

        db.close();
    }

    private void editarDatosEnDB(int id, String nombre, String contenido) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_NOMBRE, nombre);
        values.put(DatabaseHelper.COLUMN_CONTENIDO, contenido);

        int rowsAffected = db.update(
                DatabaseHelper.TABLE_NAME,
                values,
                DatabaseHelper.COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)}
        );

        if (rowsAffected > 0) {
            Toast.makeText(this, "Datos actualizados correctamente", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error al actualizar datos", Toast.LENGTH_SHORT).show();
        }

        db.close();
    }


    // Método para manejar la llamada de JavaScript desde el formulario
    @JavascriptInterface
    public void insertarDatos(String nombre, String contenido) {
        insertarDatosEnDB(nombre, contenido);
    }

    @JavascriptInterface
    public String obtenerDatos() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", id);
            jsonObject.put("nombre", nombre);
            jsonObject.put("contenido", contenido);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }


    // Llamada desde JavaScript para editar datos
    @JavascriptInterface
    public void editarDatos(int id, String nombre, String contenido) {
        editarDatosEnDB(id, nombre, contenido);
    }

    @JavascriptInterface
    public void regresarAPantallaInicial() {
        finish(); // Cierra la actividad actual y regresa a la actividad anterior (MainActivity)
    }
}
