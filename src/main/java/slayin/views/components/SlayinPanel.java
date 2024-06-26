package slayin.views.components;

import java.awt.Graphics;
import java.awt.Image;


import slayin.model.utility.Globals;

public class SlayinPanel extends SlayinCenteredPanel {
    private Image backgroundImage;

    /**
     * Creates a new centered panel with the given background image.
     * 
     * @param backgroundImage The background image to display.
     */
    public SlayinPanel(Image backgroundImage) {
        super();

        this.backgroundImage = backgroundImage;
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, Globals.RESOLUTION.getWidth(), Globals.RESOLUTION.getHeight(), this);
        }
        
        super.paintComponent(g);
    }
}
