package com.example.utcbot.robotica;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.VideoView;

import com.example.utcbot.R;

public class Impresion3D extends AppCompatActivity {

    private MediaPlayer textoVoz;
    private ImageView sonido;
    private boolean sonidoReproduciendose = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_impresion3_d);
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
                onBackPressed();
            }
        });

        View tarjeta1 = findViewById(R.id.targeta1);
        tarjeta1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.start();
                mostrarDialogoPersonalizado();
            }
        });


        View secondLayout = getLayoutInflater().inflate(R.layout.video_3d, null);

        textoVoz = MediaPlayer.create(this, R.raw.impresion3d);
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


    private void mostrarDialogoPersonalizado() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Obtener el layout inflado
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.video_3d, null);
        // Configurar el contenido del diálogo
        builder.setView(dialogView);

        // Obtener una referencia al VideoView dentro del diálogo
        VideoView videoView = dialogView.findViewById(R.id.videoView2);

        // Ruta del video en recursos raw
        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.video_3d;
        Uri uri = Uri.parse(videoPath);

        // Configurar el video en el VideoView
        videoView.setVideoURI(uri);
        // Iniciar la reproducción del video
        videoView.start();

        // Crear y mostrar el diálogo
        AlertDialog dialog = builder.create();
        dialog.show();
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