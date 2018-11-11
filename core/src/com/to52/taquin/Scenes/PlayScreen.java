package com.to52.taquin.Scenes;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.sun.prism.image.ViewPort;
import com.to52.taquin.TaquinGame;


public class PlayScreen implements Screen {
    private TaquinGame game;
    private OrthographicCamera camera;
    private ScreenViewport gamePort;
    private Texture testTexture;

    public PlayScreen(TaquinGame game){
        this.game = game;
        camera = new OrthographicCamera();
        gamePort = new ScreenViewport(camera);
        camera.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);
        TextureLoader.TextureParameter param = new TextureLoader.TextureParameter();
        testTexture = new Texture(Gdx.files.internal("E:\\Dev\\Workspaces\\Taquin\\images\\puma.jpg"));
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        game.batch.draw(testTexture, 0,0);
        game.batch.end();
}

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
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
