package com.example.utcbot.robotica.elementosBasicos;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.utcbot.MainActivity;
import com.example.utcbot.R;

public class ElementosBasico12 extends AppCompatActivity {

    private MediaPlayer textoVoz;
    private ImageView sonido;
    private boolean sonidoReproduciendose = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elementos_basico12);

        //Codigo para poner la pantalla horizontal
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        //codigo para ocultar la barra de estado del celular
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Ocultar la barra de navegación y los botones de navegación
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);

        final MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.clic);

        ImageView cerrar = findViewById(R.id.cerrar);
        cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                Intent intent = new Intent(ElementosBasico12.this, MainActivity.class);
                startActivity(intent);
            }
        });

        ImageView regresar = findViewById(R.id.regresar);
        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                onBackPressed();
            }
        });

        ImageView siguiente = findViewById(R.id.siguiente);
        siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aquí puedes realizar acciones cuando se hace clic en la imagen
                mediaPlayer.start();
                Intent intent = new Intent(ElementosBasico12.this, MainActivity.class);
                startActivity(intent);
            }
        });

        textoVoz = MediaPlayer.create(this, R.raw.sonido14);
        sonido = findViewById(R.id.sonido);

        textoVoz.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                // Cuando la reproducción del sonido haya terminado, cambiar la imagen a la original
                sonido.setImageResource(R.drawable.sonido);
                sonidoReproduciendose = false;
            }
        });

        sonido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleReproduccionSonido();
            }
        });
    }

    private void toggleReproduccionSonido() {
        if (!sonidoReproduciendose) {
            // Si el sonido no se está reproduciendo, iniciar reproducción
            textoVoz.start();
            sonido.setImageResource(R.drawable.parar_sonido);
            sonidoReproduciendose = true;
        } else {
            // Si el sonido se está reproduciendo, detener reproducción
            textoVoz.pause(); // Pausa en lugar de stop para que pueda continuar desde donde se pausó
            sonido.setImageResource(R.drawable.sonido);
            sonidoReproduciendose = false;
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (textoVoz.isPlaying()) {
            textoVoz.pause();
            sonido.setImageResource(R.drawable.sonido);
            sonidoReproduciendose = false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        textoVoz.release();
    }
}