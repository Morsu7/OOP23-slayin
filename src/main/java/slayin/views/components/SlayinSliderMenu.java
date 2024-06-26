package slayin.views.components;

import java.awt.BorderLayout;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class SlayinSliderMenu<X> extends JPanel {
    private List<X> items;
    private int currentItemIndex;

    private JLabel currentItemLabel;
    private JButton nextButton;
    private JButton prevButton;

    private Consumer<X> changeListener;
    private Function<X, String> itemToString;

    /**
     * Creates a new SlayinSliderMenu
     * 
     * @param curItem the current item of the list
     * @param items   the list of available items
     */
    public SlayinSliderMenu(X curItem, List<X> items, Function<X, String> itemToString) {
        this.itemToString = itemToString;
        this.currentItemIndex = items.indexOf(curItem);

        this.items = items;

        nextButton = new JButton(">");
        prevButton = new JButton("<");

        nextButton.addActionListener(e -> nextItem());
        prevButton.addActionListener(e -> prevItem());

        String itemName = itemToString != null ? itemToString.apply(items.get(currentItemIndex)) : items.get(currentItemIndex).toString();
        currentItemLabel = new JLabel(itemName, SwingConstants.CENTER);

        this.setLayout(new BorderLayout());

        this.add(prevButton, BorderLayout.WEST);
        this.add(currentItemLabel, BorderLayout.CENTER);
        this.add(nextButton, BorderLayout.EAST);
    }

    /**
     * Moves to the next item
     */
    private void nextItem() {
        currentItemIndex++;

        if (currentItemIndex >= items.size())
            currentItemIndex = 0;

        updateText();

        if (changeListener != null)
            changeListener.accept(this.items.get(currentItemIndex));
    }

    /**
     * Moves to the previous item
     */
    private void prevItem() {
        currentItemIndex--;

        if (currentItemIndex < 0)
            currentItemIndex = items.size() - 1;

        updateText();

        if (changeListener != null)
            changeListener.accept(this.items.get(currentItemIndex));
    }

    /**
     * Updates the text of the current item
     */
    private void updateText() {
        String itemName = itemToString != null ? itemToString.apply(items.get(currentItemIndex)) : items.get(currentItemIndex).toString();
        currentItemLabel.setText(itemName);
    }

    /**
     * Adds a listener that is called when the current item changes
     * 
     * @param listener the listener to add
     */
    public void addChangeListener(Consumer<X> listener) {
        this.changeListener = listener;
    }
}