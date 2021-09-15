package br.com.johannbandelow.frontend;

import br.com.johannbandelow.model.Board;
import br.com.johannbandelow.model.Field;
import br.com.johannbandelow.model.FieldEvent;
import br.com.johannbandelow.model.FieldObserver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class FieldButton extends JButton implements FieldObserver, MouseListener {

    private final Color BG_DEFAULT = new Color(184, 184, 184);
    private final Color BG_MARKED = new Color(8, 179, 247);
    private final Color BG_EXPLODED = new Color(189, 66, 68);
    private final Color TEXT = new Color(0, 100, 0);

    private Field field;

    public FieldButton(Field field) {
        this.field = field;
        setBorder(BorderFactory.createBevelBorder(0));
        setBackground(BG_DEFAULT);

        addMouseListener(this);
        field.observerRegister(this);
    }

    @Override
    public void onEvent(Field field, FieldEvent event) {
        switch (event) {
            case OPEN -> applyOpenStyle();
            case MARK -> applyMarkedStyle();
            case EXPLODE -> applyExplodedStyle();
            default -> applyUnmarkedStyle();
        }
    }

    private void applyExplodedStyle() {
    	setBackground(BG_EXPLODED);
        setForeground(Color.WHITE);
        setText("X");
    }

    private void applyUnmarkedStyle() {
    	setBackground(BG_DEFAULT);
    	setBorder(BorderFactory.createBevelBorder(0));
    	setText("");
    }

    private void applyMarkedStyle() {
    	setBackground(BG_MARKED);
    	setForeground(Color.BLACK);
    	setText("!");
    }

    private void applyOpenStyle() {
    	
    	if(field.isBomber() && !field.isMarked()) {
    		setBackground(BG_EXPLODED);
            setForeground(Color.WHITE);
            setText("X");
    	}else if(field.isBomber() && field.isMarked()) {
    		setBackground(BG_MARKED);
            setForeground(Color.WHITE);
            setText("X");
    	} 
    	else {
    		setBackground(BG_DEFAULT);
            setBorder(BorderFactory.createLineBorder(Color.GRAY));
            
            switch (field.closeBombs()) {
            case 1:
                setForeground(TEXT);
                break;

            case 2:
                setForeground(Color.BLUE);
                break;

            case 3:
                setForeground(Color.YELLOW);
                break;

            case 4:
                setForeground(Color.RED);
                break;

            case 5:
            case 6:
                setForeground(Color.BLACK);
                break;
            default:
                setForeground(Color.CYAN);
        }

        String value = !field.safeNeighbourhood() ? field.closeBombs() + "" : "";
        setText(value);
    	}
    	
        

        
    }

    @Override
    public void mousePressed(MouseEvent e) {

        if(e.getButton() == 1) {
            field.open();
        } else {
            field.alternateMark();
        }

    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {

    }


}
