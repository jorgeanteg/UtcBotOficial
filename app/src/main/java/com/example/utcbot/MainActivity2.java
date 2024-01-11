package com.example.utcbot;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

public class MainActivity2 extends AppCompatActivity {

    //Inicio codigo para bluethoo
    private static final String TAG = "MainActivity2";
    private static final UUID BT_MODULE_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private static final int REQUEST_ENABLE_BT = 1;
    private static final int REQUEST_BLUETOOTH_CONNECT_PERMISSION = 3;
    private static final int REQUEST_FINE_LOCATION_PERMISSION = 2;
    private BluetoothAdapter mBtAdapter;
    private BluetoothSocket btSocket;
    private BluetoothDevice DispositivoSeleccionado;
    private ConnectedThread MyConexionBT;
    private ArrayList<String> mNameDevices = new ArrayList<>();
    private ArrayAdapter<String> deviceAdapter;
    Button IdBtnBuscar,IdBtnConectar,IdBtnLuz1on,IdBtnLuz1off,IdBtnLuz2on,IdBtnLuz2off,IdBtnDesconectar;
    Spinner IdDisEncontrados;

    //Fin codigo para bluethoo









    private static final String KEY_WEB_VIEW_URL = "web_view_url";

    private WebView wv1;
    private DatabaseHelper dbHelper;
    private int id;
    private String nombre;
    private String contenido;

    ConstraintLayout container;
    View background;


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


        container = findViewById(R.id.container);
        background = findViewById(R.id.background);


        // Configura el clic en el fondo para ocultar el contenedor y el fondo
        background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                container.setVisibility(View.GONE); // Oculta el contenedor
                background.setVisibility(View.GONE); // Oculta el fondo
            }
        });

        // Configura el clic en el contenedor para no hacer nada
        container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // No hagas nada al hacer clic en el contenedor
            }
        });



        //Inicio Codigo Bluethoo

        requestBluetoothConnectPermission();
        requestLocationPermission();

        IdBtnConectar = findViewById(R.id.IdBtnConectar);
        IdBtnConectar = findViewById(R.id.IdBtnConectar);
        IdDisEncontrados = findViewById(R.id.IdDisEncontrados);

        deviceAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mNameDevices);
        deviceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        IdDisEncontrados.setAdapter(deviceAdapter);




        IdBtnConectar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // CODIGO A EJECUTAR DE ESTE EVENTO PARA ESTE BOTON
                ConectarDispBT();
            }
        });








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


    @JavascriptInterface
    public void activarBluethoo() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                container.setVisibility(View.VISIBLE); // Oculta el contenedor
                background.setVisibility(View.VISIBLE);
                DispositivosVinculados();
            }
        });
    }



    //Inicio de Codigo Bluethoo
    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                    if (result.getResultCode() == MainActivity2.REQUEST_ENABLE_BT) {
                        Log.d(TAG, "ACTIVIDAD REGISTRADA");
                        //Toast.makeText(getBaseContext(), "ACTIVIDAD REGISTRADA", Toast.LENGTH_SHORT).show();
                    }
                }
            });

    public void DispositivosVinculados() {
        mBtAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBtAdapter == null) {
            showToast("Bluetooth no disponible en este dispositivo.");
            finish();
            return;
        }

        // Verificar si se tiene el permiso BLUETOOTH_CONNECT
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            // Si no se tiene el permiso, solicitarlo
            requestBluetoothConnectPermission();
            return;
        }

        if (!mBtAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            someActivityResultLauncher.launch(enableBtIntent);
        }

        IdDisEncontrados.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                DispositivoSeleccionado = getBluetoothDeviceByName(mNameDevices.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                DispositivoSeleccionado = null;
            }
        });

        // Limpiar la lista antes de agregar nuevos dispositivos
        mNameDevices.clear();

        Set<BluetoothDevice> pairedDevices = mBtAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                mNameDevices.add(device.getName());
            }
            // Notificar al adaptador que los datos han cambiado
            deviceAdapter.notifyDataSetChanged();
        } else {
            showToast("No hay dispositivos Bluetooth emparejados.");
        }
    }

    // Agrega este método para solicitar el permiso
    private void requestLocationPermission() {
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_FINE_LOCATION_PERMISSION);
    }

    // Agrega este método para solicitar el permiso
    private void requestBluetoothConnectPermission() {
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.BLUETOOTH_CONNECT}, REQUEST_BLUETOOTH_CONNECT_PERMISSION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_BLUETOOTH_CONNECT_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "Permiso concedido, ahora puedes utilizar funciones de Bluetooth que requieran BLUETOOTH_CONNECT");
            } else {
                Log.d(TAG, "Permiso denegado, debes manejar este caso según tus necesidades");
            }
        }
    }

    private BluetoothDevice getBluetoothDeviceByName(String name) {

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, " ----->>>>> ActivityCompat.checkSelfPermission");
        }
        Set<BluetoothDevice> pairedDevices = mBtAdapter.getBondedDevices();
        for (BluetoothDevice device : pairedDevices) {
            if (device.getName().equals(name)) {
                return device;
            }
        }
        return null;
    }
    private void ConectarDispBT() {
        if (DispositivoSeleccionado == null) {
            showToast("Selecciona un dispositivo Bluetooth.");
            return;
        }

        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            btSocket = DispositivoSeleccionado.createRfcommSocketToServiceRecord(BT_MODULE_UUID);
            btSocket.connect();
            MyConexionBT = new ConnectedThread(btSocket);
            MyConexionBT.start();
            showToast("Conexión exitosa.");
        } catch (IOException e) {
            showToast("Error al conectar con el dispositivo.");
        }
    }

    private class ConnectedThread extends Thread {
        private final OutputStream mmOutStream;
        ConnectedThread(BluetoothSocket socket) {
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
                showToast("Error al crear el flujo de datos.");
            }

            mmOutStream = tmpOut;
        }
        public void write(String message) {
            try {
                mmOutStream.write(message.getBytes());
            } catch (IOException e) {
                showToast("La conexión falló");
                finish();
            }
        }

    }
    private void showToast(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }


    //Enviar datos Bluethoo

    @JavascriptInterface
    public void enviarDatosBlue(String contenido) {
        //enviarDatosBlue(contenido);
        MyConexionBT.write(contenido);
    }


}
