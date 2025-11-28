package edu.io;

import edu.io.token.PlayerToken;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class Controls {
    private final PlayerToken player;

    public Controls(JComponent component, PlayerToken player) {
        this.player = player;

        InputMap im = component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = component.getActionMap();

        im.put(KeyStroke.getKeyStroke("pressed W"), "moveUP");
        im.put(KeyStroke.getKeyStroke("pressed UP"), "moveUP");
        am.put("moveUP", new MoveAction(PlayerToken.Move.UP));

        im.put(KeyStroke.getKeyStroke("pressed S"), "moveDOWN");
        im.put(KeyStroke.getKeyStroke("pressed DOWN"), "moveDOWN");
        am.put("moveDOWN", new MoveAction(PlayerToken.Move.DOWN));

        im.put(KeyStroke.getKeyStroke("pressed A"), "moveLEFT");
        im.put(KeyStroke.getKeyStroke("pressed LEFT"), "moveLEFT");
        am.put("moveLEFT", new MoveAction(PlayerToken.Move.LEFT));

        im.put(KeyStroke.getKeyStroke("pressed D"), "moveRIGHT");
        im.put(KeyStroke.getKeyStroke("pressed RIGHT"), "moveRIGHT");
        am.put("moveRIGHT", new MoveAction(PlayerToken.Move.RIGHT));
    }

    private class MoveAction extends AbstractAction {
        private final PlayerToken.Move direction;

        public MoveAction(PlayerToken.Move direction) {
            this.direction = direction;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            player.move(direction);
        }
    }
}