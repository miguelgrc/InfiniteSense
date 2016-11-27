package com.infinitesense.modelos;

import android.content.Context;
import android.graphics.Canvas;

import com.infinitesense.R;
import com.infinitesense.gestores.CargadorGraficos;
import com.infinitesense.graficos.Sprite;

import java.util.HashMap;

/**
 * Created by juanfa on 5/10/16.
 */

public class Jugador extends Modelo {
    public static final String PARADO_DERECHA = "Parado_derecha";
    public static final String PARADO_IZQUIERDA = "Parado_izquierda";

    public static final String CAMINANDO_DERECHA = "Caminando_derecha";
    public static final String CAMINANDO_IZQUIERDA = "Caminando_izquierda";

    public static final String SALTANDO_DERECHA = "saltando_derecha";
    public static final String SALTANDO_IZQUIERDA = "saltando_izquierda";

    public static final String GOLPEANDO_DERECHA = "disparando_derecha";
    public static final String GOLPEANDO_IZQUIERDA = "disparando_izquierda";

    public static final String GOLPEADO_DERECHA = "golpeado_derecha";
    public static final String GOLPEADO_IZQUIERDA = "golpeado_izquierda";

    public static final String AGACHADO_IZQUIERDA = "agachado_izquierda";
    public static final String AGACHADO_DERECHA = "agachado_derecha";

    public boolean estadoGolpeando;

    double velocidadX;
    float velocidadY; // actual
    float velcidadSalto = -14; // velocidad que le da el salto

    public boolean saltoPendiente; // tiene que saltar
    public boolean enElAire; // está en el aire

    private Sprite sprite;
    private HashMap<String, Sprite> sprites = new HashMap<String, Sprite>();

    double xInicial;
    double yInicial;

    public int orientacion;
    public static final int DERECHA = 1;
    public static final int IZQUIERDA = -1;
    public boolean golpeado = false;
    private boolean estadoAgachado;

    public Jugador(Context context, double xInicial, double yInicial) {
        super(context, 0, 0, 40, 40);

        this.xInicial = xInicial;
        this.yInicial = yInicial - altura / 2;

        this.x = this.xInicial;
        this.y = this.yInicial;

        orientacion = DERECHA;

        //colisiones
        cDerecha = ancho/2;
        cIzquierda = ancho/2;
        cArriba = altura/2;
        cAbajo = altura/2;

        inicializar();
    }

    public void inicializar() {


        Sprite paradoDerecha = new Sprite(
                CargadorGraficos.cargarDrawable(context, R.drawable.playeridleright),
                ancho, altura,
                4, 8, true);
        sprites.put(PARADO_DERECHA, paradoDerecha);

        Sprite paradoIzquierda = new Sprite(
                CargadorGraficos.cargarDrawable(context, R.drawable.playeridle),
                ancho, altura,
                4, 8, true);
        sprites.put(PARADO_IZQUIERDA, paradoIzquierda);

// animación actual

        Sprite caminandoDerecha = new Sprite(
                CargadorGraficos.cargarDrawable(context, R.drawable.playerrunright),
                ancho, altura,
                4, 8, true);
        sprites.put(CAMINANDO_DERECHA, caminandoDerecha);

        Sprite caminandoIzquierda = new Sprite(
                CargadorGraficos.cargarDrawable(context, R.drawable.playerrun),
                ancho, altura,
                4, 8, true);
        sprites.put(CAMINANDO_IZQUIERDA, caminandoIzquierda);

        Sprite saltandoDerecha = new Sprite(
                CargadorGraficos.cargarDrawable(context, R.drawable.playerjumpright),
                ancho, altura,
                4, 4, true);
        sprites.put(SALTANDO_DERECHA, saltandoDerecha);

        Sprite saltandoIzquierda = new Sprite(
                CargadorGraficos.cargarDrawable(context, R.drawable.playerjump),
                ancho, altura,
                4, 4, true);
        sprites.put(SALTANDO_IZQUIERDA, saltandoIzquierda);

        //Golpeando

        Sprite golpeandoDerecha = new Sprite(
                CargadorGraficos.cargarDrawable(context, R.drawable.playershootright),
                ancho, altura,
                4, 4, false);
        sprites.put(GOLPEANDO_DERECHA, golpeandoDerecha);

        Sprite golpeandoIzquierda = new Sprite(
                CargadorGraficos.cargarDrawable(context, R.drawable.playershoot),
                ancho, altura,
                4, 4, false);
        sprites.put(GOLPEANDO_IZQUIERDA, golpeandoIzquierda);

        //Agachado TODO: Cambiar los sprites por los de agachado

        Sprite agachadoDerecha = new Sprite(
                CargadorGraficos.cargarDrawable(context, R.drawable.playeridle),
                ancho, altura,
                4, 8, true);
        sprites.put(AGACHADO_DERECHA, agachadoDerecha);

        Sprite agachadoIzquierda = new Sprite(
                CargadorGraficos.cargarDrawable(context, R.drawable.playeridle),
                ancho, altura,
                4, 8, true);
        sprites.put(AGACHADO_IZQUIERDA, agachadoIzquierda);

        //golpeado

        Sprite golpeadoDerecha = new Sprite(
                CargadorGraficos.cargarDrawable(context, R.drawable.playerimpactright),
                ancho, altura,
                4, 4, false);
        sprites.put(GOLPEADO_DERECHA, golpeadoDerecha);

        Sprite golpeadoIzquierda = new Sprite(
                CargadorGraficos.cargarDrawable(context, R.drawable.playerimpact),
                ancho, altura,
                4, 4, false);
        sprites.put(GOLPEADO_IZQUIERDA, golpeadoIzquierda);

        sprite = paradoDerecha;

    }


    public void actualizar(long tiempo) {

        boolean finSprite = sprite.actualizar(tiempo);

        // Deja de estar golpeado, cuando lo estaba y se acaba el sprite
        if (golpeado && finSprite) {
            golpeado = false;
        }

        if (estadoGolpeando && finSprite) {
            estadoGolpeando = false;
        }
        // saltar
        if (saltoPendiente) {
            saltoPendiente = false;
            enElAire = true;
            velocidadY = velcidadSalto;
        }
        if (velocidadX > 0) {
            sprite = sprites.get(CAMINANDO_DERECHA);
            orientacion = DERECHA;
        }
        if (velocidadX < 0) {
            sprite = sprites.get(CAMINANDO_IZQUIERDA);
            orientacion = IZQUIERDA;
        }
        if (velocidadX == 0) {
            if (orientacion == DERECHA) {
                sprite = sprites.get(PARADO_DERECHA);
            } else if (orientacion == IZQUIERDA) {
                sprite = sprites.get(PARADO_IZQUIERDA);
            }
        }
        if (enElAire && orientacion == IZQUIERDA) {
            sprite = sprites.get(SALTANDO_IZQUIERDA);

        } else if (enElAire && orientacion == DERECHA) {
            sprite = sprites.get(SALTANDO_DERECHA);
        }

        //Estado agachado
        if (estadoAgachado){
            agachado();
        }
        //la útlima es la que mas prioridad tiene. En este caso el diparo.
        if (estadoGolpeando) {
            if (orientacion == DERECHA) {
                sprite = sprites.get(GOLPEANDO_DERECHA);
            } else if (orientacion == IZQUIERDA) {
                sprite = sprites.get(GOLPEANDO_IZQUIERDA);
            }
        }

        if (golpeado) {
            if (orientacion == DERECHA) {
                sprite = sprites.get(GOLPEADO_DERECHA);
            } else if (orientacion == IZQUIERDA) {
                sprite = sprites.get(GOLPEADO_IZQUIERDA);
            }

        }
    }

    private void agachado() {
        if (orientacion == IZQUIERDA){
            sprite= sprites.get(AGACHADO_IZQUIERDA);
            //TODO: Cambiar las colisiones para que solo sea para la mitad del tile.
            cDerecha = ancho/2;
            cIzquierda = ancho/2;
            cArriba = altura/2;
            cAbajo = altura/2;
        }
        else {
            sprite= sprites.get(AGACHADO_IZQUIERDA);
        }
    }

    public void dibujar(Canvas canvas) {
        sprite.dibujarSprite(canvas, (int) x - Nivel.scrollEjeX, (int) y - Nivel.scrollEjeY);
    }

    /**
     * Saltar hace que el jugador cambie su estado a saltoPendiente.
     * Agachar hace que el jugador se agache y pierda velocidad.
     * Golpear hace que el jugador cambie el estado de golpeando a true.
     * @param saltar
     * @param golpear
     * @param agachar
     */
    public void procesarOrdenes(boolean saltar, boolean golpear, boolean agachar) {
        if (saltar) {
            if (!enElAire) {
                saltoPendiente = true;
            }
        }
        if (agachar){
            velocidadX = 2;
            velocidadY += 2;
            estadoAgachado = true;
        }
        else{
            velocidadX = 5;
            estadoAgachado = false;
        }
        if (golpear) {
            estadoGolpeando = true;
            // preparar los sprites, no son bucles hay que reiniciarlos
            sprites.get(GOLPEANDO_DERECHA).setFrameActual(0);
            sprites.get(GOLPEANDO_IZQUIERDA).setFrameActual(0);
        }

    }

    public void restablecerPosicionInicial() {
        golpeado = false;

        this.x = xInicial;
        this.y = yInicial;
        orientacion = DERECHA;
    }

    public void golpeado() {
        golpeado = true;
        // Reiniciar animaciones que no son bucle
        sprites.get(GOLPEADO_IZQUIERDA).setFrameActual(0);
        sprites.get(GOLPEADO_DERECHA).setFrameActual(0);
    }

}