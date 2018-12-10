package com.to52.taquin.Scenes;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.to52.taquin.TaquinGame;

public class FirstScreen implements Screen {
    private TaquinGame game;
    private OrthographicCamera camera;
    private StretchViewport gamePort;
    private Texture background;
    private TextButton jouer;
    private Stage stage;

    public FirstScreen(TaquinGame game){
        this.game = game;
        camera = new OrthographicCamera();
        gamePort = new StretchViewport(1280,720,camera);
        camera.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);
        background = new Texture(Gdx.files.internal("background.jpg"));
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        createButton();
    }

    private void createButton() {
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = new BitmapFont();
        jouer = new TextButton("JOUER", style);
        jouer.setPosition(gamePort.getWorldWidth()/2, gamePort.getWorldHeight()/2);
        jouer.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                game.setScreen(new PlayScreen(game));
                dispose();
            }
        });
        stage.addActor(jouer);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        stage.act();
        stage.getBatch().begin();
        stage.getBatch().draw(background,0,0);
        stage.getBatch().end();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height, true);
        stage.setViewport(gamePort);
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
        stage.dispose();
    }
}
