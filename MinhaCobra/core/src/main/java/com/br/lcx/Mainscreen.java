package com.br.lcx;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Mainscreen implements Screen {

    private Game game;

    private Viewport viewport;
    private SpriteBatch batch;
    private Texture[] fund;

    private float tempo;

    private boolean segurando;
    public Mainscreen(Game game){
        this.game = game;
    }
    @Override
    public void show() {
        batch = new SpriteBatch();
        viewport = new FillViewport(1000, 1500);
        viewport.apply();
        fund = new Texture[2];
        fund[0] = new Texture("fund0.png");
        fund[1] = new Texture("fund1.png");
        tempo = 0f;
        segurando = false;
        Gdx.input.setInputProcessor(null);


    }

    @Override
    public void render(float delta) {
        tempo += delta;

        input();

        batch.setProjectionMatrix(viewport.getCamera().combined);

        Gdx.gl.glClearColor(0.29f, 0.894f, 0.303f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        batch.draw(fund[(int)tempo%2 ], 0, 0, 1000, 1500);

        batch.end();

    }
    private void input (){
        if(Gdx.input.isTouched()){
            segurando = true;
        } else if (!Gdx.input.isTouched() && segurando) {
            segurando = false;
            game.setScreen(new Gamescreen(game));

        }
    }

    @Override
    public void resize(int width, int height) {
       viewport.update(width, height, true);

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

}
