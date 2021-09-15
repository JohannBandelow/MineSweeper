package br.com.johannbandelow.frontend;

import br.com.johannbandelow.model.Board;

import javax.swing.*;
import java.awt.*;

public class BoardPanel extends JPanel {

    public BoardPanel(Board board) {

        setLayout(new GridLayout(board.getLines(),board.getColumns()));

        board.eachButton(f -> add(new FieldButton(f)));
        board.resgisterObserver(e -> {
        	SwingUtilities.invokeLater(() -> {
        	  if(e.isGanhou()) {
        		JOptionPane.showMessageDialog(this, "Ganhou");
        	} else {
        		JOptionPane.showMessageDialog(this, "Perdeu");
        	}
        	  board.restart();
        	});
        });
    }
}
