package br.com.johannbandelow.frontend;

import br.com.johannbandelow.model.Board;

import javax.swing.*;
import java.awt.*;

public class TelaPrincipal extends JFrame {

    public TelaPrincipal()  {

        Board board = new Board(16, 30, 50);

        add(new BoardPanel(board));

        setTitle("MineSweeper");
        setSize(690, 438);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

    }

    public static void main(String[] args) {
        new TelaPrincipal();
    }


}
