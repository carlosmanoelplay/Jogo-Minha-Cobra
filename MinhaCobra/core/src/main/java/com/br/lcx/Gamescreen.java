package com.br.lcx;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Gamescreen implements Screen {

    private Game game;
    private Viewport viewport;
    private SpriteBatch batch;

    private Texture texCorpo;
    private Texture texFundo;
    private Texture texPonto;

    private boolean [][] corpo;

    private Array<Vector2> partes;

    private int direcao;   //1 para frente , 2 para direita, 3 para esquerda , 4 pra baixo.

    public Gamescreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        viewport = new FitViewport(100, 100);
        viewport.apply();

        batch = new SpriteBatch(); // Inicializa o batch
        gerarTextura();

        init();
    }

    @Override
    public void render(float delta) {
        batch.setProjectionMatrix(viewport.getCamera().combined);

        Gdx.gl.glClearColor(0.29f, 0.894f, 0.303f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(texFundo, 0, 0, 100, 100);
        for (Vector2 pastes:partes){
            batch.draw(texCorpo, pastes.x*5, pastes.y*5, 5, 5);
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
    private void init(){
        corpo = new boolean[20][20];
        partes = new Array<Vector2>();
        partes.add(new Vector2(6,5));
        corpo[6][5] = true;
        partes.add(new Vector2(5, 5));
        corpo[5][5] = true;
        direcao = 2;
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        if (batch != null) batch.dispose();
        if (texCorpo != null) texCorpo.dispose();
        if (texFundo != null) texFundo.dispose();
        if (texPonto != null) texPonto.dispose();
    }
}
