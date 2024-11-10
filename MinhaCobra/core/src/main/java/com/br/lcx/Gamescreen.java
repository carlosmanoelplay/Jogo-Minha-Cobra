package com.br.lcx;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Random;

public class Gamescreen implements Screen,GestureDetector.GestureListener {

    private Game game;
    private Viewport viewport;
    private SpriteBatch batch;

    private Texture texCorpo;
    private Texture texFundo;
    private Texture texPonto;

    private boolean[][] corpo;

    private Array<Vector2> partes;

    private int direcao;   //1 para frente , 2 para direita, 3 para baixo , 4 pra esquerda.

    private float timeToMove;
    private Vector2 toque;
    private Array<Vector2> pontos;
    private float timeToNext;

    private Random rand;


    public Gamescreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        viewport = new FitViewport(100, 100);
        viewport.apply();

        batch = new SpriteBatch(); // Inicializa o batch
        gerarTextura();
        toque = new Vector2();
        rand = new Random();

        init();
        Gdx.input.setInputProcessor(new GestureDetector(this));
    }


    @Override
    public void render(float delta) {
        updete(delta);

        batch.setProjectionMatrix(viewport.getCamera().combined);

        Gdx.gl.glClearColor(0.29f, 0.894f, 0.303f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(texFundo, 0, 0, 100, 100);
        for (Vector2 pastes : partes) {
            batch.draw(texCorpo, pastes.x * 5, pastes.y * 5, 5, 5);
        }
        for (Vector2 ponto : pontos) {
            batch.draw(texPonto, ponto.x * 5, ponto.y * 5, 5, 5);
        }
        batch.end();


    }

    public void gerarTextura() {
        // Criação de texCorpo
        Pixmap pixmap = new Pixmap(64, 64, Pixmap.Format.RGBA8888);
        pixmap.setColor(1f, 1f, 1f, 1f);
        pixmap.fillRectangle(0, 0, 64, 64);
        texCorpo = new Texture(pixmap);
        pixmap.dispose();

        // Criação de texFundo
        Pixmap pixmap2 = new Pixmap(64, 64, Pixmap.Format.RGBA8888);
        pixmap2.setColor(0.29f, 0.784f, 0.373f, 0.5f);
        pixmap2.fillRectangle(0, 0, 64, 64);
        texFundo = new Texture(pixmap2); // Corrigido para usar pixmap2
        pixmap2.dispose();

        // Criação de texPonto
        Pixmap pixmap3 = new Pixmap(64, 64, Pixmap.Format.RGBA8888);
        pixmap3.setColor(1f, 1f, 1f, 1f);
        pixmap3.fillCircle(32, 32, 32);
        texPonto = new Texture(pixmap3); // Corrigido para usar pixmap3
        pixmap3.dispose();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }



    private void updete(float delta) {
        timeToMove -= delta;
        if (timeToMove <= 0) {
            timeToMove = 0.4f;
        }
        Gdx.app.log("log", "move");

        int x1, x2, y1, y2;

        x1 = (int) partes.get(0).x;
        y1 = (int) partes.get(0).y;
        corpo[x1][y1] = false;
        x2 = x1;
        y2 = y1;
        switch (direcao) {
            case 1:
                y1++;
                break;
            case 2:
                x1++;
                break;
            case 3:
                y1--;
                break;
            case 4:
                x1--;
                break;

        }
        if (x1 < 0 || y1 < 0 || x1 > 19 || y1 > 19 || corpo[x1][y1]) {
            // perdemos

            return;
        }
        partes.get(0).set(x1, y1);
        corpo[x1][y1] = true;

        for (int i = 1; i < partes.size; i++) {
            x1 = (int) partes.get(i).x;
            y1 = (int) partes.get(i).y;
            ;
            corpo[x1][y1] = false;

            partes.get(i).set(x2, y2);
            corpo[x2][y2] = true;
            x2 = x1;
            y2 = y1;
        }

        timeToNext -= delta;
        if (timeToNext <= 0) {
            int x = rand.nextInt(20);
            int y = rand.nextInt(20);
            if (!corpo[x][y]) {
                pontos.add(new Vector2(x, y));
                timeToNext = 5f;

            }
        }
    }
    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        viewport.unproject(toque.set(velocityX, velocityY));
        Gdx.app.log("log", velocityX + " " + velocityY + " " + toque.x + " " + toque.y);
        if (Math.abs(toque.x) > Math.abs(toque.y)) toque.y = 0;
        else toque.x = 0;
        if (toque.x > 50 && direcao != 4) {
            direcao = 2;
        } else if (toque.y > 50 && direcao != 3) {
            direcao = 1;
        } else if (toque.x < -50 && direcao != 2) {
            direcao = 4;
        } else if (toque.y < -50 && direcao != 1) {
            direcao = 3;
        }
        return true;
    }
    private boolean init() {
        corpo = new boolean[20][20];
        partes = new Array<Vector2>();
        partes.add(new Vector2(6, 5));
        corpo[6][5] = true;
        partes.add(new Vector2(5, 5));
        corpo[5][5] = true;
        direcao = 2;

        timeToMove = 0.4f;

        pontos = new Array<Vector2>();

        timeToNext = 3f;


        return false;
    }
    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }



    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {

    }


}
