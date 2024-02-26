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

public class ElementosBasico6 extends AppCompatActivity {

    private MediaPlayer textoVoz, segundoSonido, tercerSonido;
    private ImageView sonido, sonidoElemento;
    private boolean sonidoReproduciendose = false;
    private boolean sonidoReproduciendose2 = false;
    private boolean sonidoReproduciendose3 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elementos_basico6);

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
                Intent intent = new Intent(ElementosBasico6.this, MainActivity.class);
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
                Intent intent = new Intent(ElementosBasico6.this, ElementosBasico11.class);
                startActivity(intent);
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

        textoVoz = MediaPlayer.create(this, R.raw.sonido12);
        segundoSonido = MediaPlayer.create(this, R.raw.esp32);
        tercerSonido = MediaPlayer.create(this, R.raw.arduino);

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
        if (segundoSonido.isPlaying()) {
            segundoSonido.pause();
            //sonidoElemento.setImageResource(R.drawable.sonido);
            sonidoReproduciendose2 = false;
        }

        if (tercerSonido.isPlaying()) {
            tercerSonido.pause();
            //sonidoElemento.setImageResource(R.drawable.sonido);
            sonidoReproduciendose3 = false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        textoVoz.release();
        segundoSonido.release();
        tercerSonido.release();
    }


    private AlertDialog dialogoPersonalizado;
    private AlertDialog dialogoPersonalizado2;


    private void mostrarDialogoPersonalizado() {
        // Detener la reproducción del primer sonido
        if (textoVoz.isPlaying()) {
            textoVoz.pause();
            sonido.setImageResource(R.drawable.sonido);
            sonidoReproduciendose = false;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Obtener el layout inflado
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.esp32, null);

        // Obtener la referencia a la imagen en el diálogo
        ImageView sonidoElemento = dialogView.findViewById(R.id.sonido_elemento);

        // Configurar el contenido del diálogo
        builder.setView(dialogView)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss(); // Cierra el diálogo
                    }
                })
                .setNeutralButton("Arduino", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        mostrarDialogoPersonalizado2();
                    }
                });

        // Crear el diálogo
        dialogoPersonalizado = builder.create();

        // Configurar el OnClickListener para la imagen en el diálogo
        sonidoElemento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleReproduccionSonido2(sonidoElemento);
            }
        });

        // Agregar un OnDismissListener para detener la reproducción de audio cuando se cierre el diálogo
        dialogoPersonalizado.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (segundoSonido.isPlaying()) {
                    segundoSonido.pause();
                    segundoSonido.seekTo(0);
                    sonidoElemento.setImageResource(R.drawable.sonido);
                    sonidoReproduciendose2 = false;
                }
            }
        });

        // Mostrar el diálogo
        dialogoPersonalizado.show();
    }

    private void mostrarDialogoPersonalizado2() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Obtener el layout inflado
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.arduino, null);

        // Obtener la referencia a la imagen en el diálogo
        ImageView sonidoElemento = dialogView.findViewById(R.id.sonido_elemento);

        // Configurar el contenido del diálogo
        builder.setView(dialogView)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss(); // Cierra el diálogo
                    }
                });

        // Crear el diálogo
        dialogoPersonalizado2 = builder.create();

        // Configurar el OnClickListener para la imagen en el diálogo
        sonidoElemento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleReproduccionSonido3(sonidoElemento);
            }
        });

        // Agregar un OnDismissListener para detener la reproducción de audio cuando se cierre el diálogo
        dialogoPersonalizado2.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (tercerSonido.isPlaying()) {
                    tercerSonido.pause();
                    tercerSonido.seekTo(0);
                    sonidoElemento.setImageResource(R.drawable.sonido);
                    sonidoReproduciendose3 = false;
                }
            }
        });

        // Mostrar el diálogo
        dialogoPersonalizado2.show();
    }

    private void toggleReproduccionSonido2(ImageView sonidoElemento) {
        if (!sonidoReproduciendose2) {
            // Si el sonido no se está reproduciendo, iniciar reproducción
            if (!segundoSonido.isPlaying()) {
                segundoSonido.start();
            }
            sonidoElemento.setImageResource(R.drawable.parar_sonido);
            sonidoReproduciendose2 = true;

            // Agregar un OnCompletionListener al MediaPlayer
            segundoSonido.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    // Cuando la reproducción del sonido termine, cambiar la imagen al estado inicial
                    sonidoElemento.setImageResource(R.drawable.sonido);
                    sonidoReproduciendose2 = false;
                }
            });
        } else {
            // Si el sonido se está reproduciendo, detener reproducción
            if (segundoSonido.isPlaying()) {
                segundoSonido.pause(); // Pausa en lugar de stop para que pueda continuar desde donde se pausó
            }
            sonidoElemento.setImageResource(R.drawable.sonido);
            sonidoReproduciendose2 = false;
        }
    }

    private void toggleReproduccionSonido3(ImageView sonidoElemento) {
        if (!sonidoReproduciendose3) {
            // Si el sonido no se está reproduciendo, iniciar reproducción
            if (!tercerSonido.isPlaying()) {
                tercerSonido.start();
            }
            sonidoElemento.setImageResource(R.drawable.parar_sonido);
            sonidoReproduciendose3 = true;

            // Agregar un OnCompletionListener al MediaPlayer
            tercerSonido.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    // Cuando la reproducción del sonido termine, cambiar la imagen al estado inicial
                    sonidoElemento.setImageResource(R.drawable.sonido);
                    sonidoReproduciendose3 = false;
                }
            });
        } else {
            // Si el sonido se está reproduciendo, detener reproducción
            if (tercerSonido.isPlaying()) {
                tercerSonido.pause(); // Pausa en lugar de stop para que pueda continuar desde donde se pausó
            }
            sonidoElemento.setImageResource(R.drawable.sonido);
            sonidoReproduciendose3 = false;
        }
    }
}