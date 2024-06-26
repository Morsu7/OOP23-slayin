package slayin.views;

import java.awt.Container;

import slayin.model.events.GameEventListener;
import slayin.model.events.menus.QuitGameEvent;
import slayin.model.events.menus.SimpleChangeSceneEvent;
import slayin.model.utility.SceneType;
import slayin.model.utility.assets.Asset;
import slayin.model.utility.assets.AssetsManager;
import slayin.views.components.SlayinButton;
import slayin.views.components.SlayinPanel;
import slayin.views.components.SlayinLabel;

import java.awt.Image;

public class MainMenuScene implements SimpleGameScene {
    private GameEventListener eventListener;

    public MainMenuScene(GameEventListener eventListener) {
        this.eventListener = eventListener;
    }

    @Override
    public Container getContent() {
        Image backgroundImage = AssetsManager.getInstance().getImageAsset(Asset.MAIN_MENU_BG);

        SlayinLabel title = new SlayinLabel("Slayin", true);
        SlayinButton playBtn = new SlayinButton("Gioca",
                () -> eventListener.addEvent(new SimpleChangeSceneEvent(SceneType.CHARACTER_SELECTION)));
        SlayinButton optionsBtn = new SlayinButton("Opzioni",
                () -> eventListener.addEvent(new SimpleChangeSceneEvent(SceneType.OPTION_MENU)));
        SlayinButton quitBtn = new SlayinButton("Esci", () -> eventListener.addEvent(new QuitGameEvent()));

        SlayinPanel container = new SlayinPanel(backgroundImage);
        container.addComponents(title, playBtn, optionsBtn, quitBtn);

        return container;
    }

    @Override
    public SceneType getSceneType() {
        return SceneType.MAIN_MENU;
    }

    @Override
    public boolean shouldRevalidate() {
        return false;
    }
}
