package br.com.johannbandelow.model;


import java.util.ArrayList;
import java.util.List;

public class Field {

    private final int line;
    private final int column;

    private List<Field> proximity = new ArrayList<>();
    private List<FieldObserver> observers = new ArrayList<>();

    private boolean opened  = false;
    private boolean marked = false;
    private boolean bomber = false;

    public Field(int line, int column) {
        this.line = line;
        this.column = column;
    }

    public void observerRegister(FieldObserver observer) {
        observers.add(observer);
    }

    private void notifyObserver(FieldEvent event) {
        observers.stream().forEach(o -> o.onEvent(this, event));
    }

    public boolean addNeighbour(Field neighbour) {
        boolean difLine = line != neighbour.line;
        boolean difColumn = column != neighbour.column;
        boolean diagonal = difColumn && difLine;

        int lineDelta = Math.abs(line - neighbour.line);
        int columnDelta = Math.abs(column - neighbour.column);
        int genDelta = columnDelta + lineDelta;

        if (genDelta == 1 && !diagonal) {
            proximity.add(neighbour);
            return true;
        } else if(genDelta == 2 && diagonal) {
            proximity.add(neighbour);
            return true;
        } else {
            return false;
        }
    }

    public void alternateMark() {
        if(!opened) {
            marked = !marked;

            if(marked) {
                notifyObserver(FieldEvent.MARK);

            } else {
                notifyObserver(FieldEvent.UNMARK);
            }

        }
    }

    public boolean open() {
        if(!opened && !marked) {
            if(bomber) {
                notifyObserver(FieldEvent.EXPLODE);
                return true;
            }

            setOpened(true);

            if(safeNeighbourhood()) {
                proximity.forEach(Field::open);
            }
        return true;
        }
        return false;
    }

    public void toBomb() {
        bomber = true;
    }

    public boolean safeNeighbourhood() {
        return proximity.stream().noneMatch(v -> v.bomber);
    }

    public boolean achievedObj() {
        boolean disbraved = !bomber && opened;
        boolean flagged = bomber && marked;

        return disbraved || flagged;
    }

    public int closeBombs() {
        return  (int)proximity.stream().filter(v -> v.bomber).count();
    }

    void restart() {
       opened = false;
       bomber = false;
       marked = false;
       
       notifyObserver(FieldEvent.RESTART);
    }


    public boolean isMarked() {
        return marked;
    }

    public void setOpened(boolean opened) {
        this.opened = opened;

        if(opened && bomber) {
            notifyObserver(FieldEvent.OPEN);
        } else if (opened) {
        	notifyObserver(FieldEvent.OPEN);
        }

    }

    public boolean isOpened() {
        return opened;
    }

    public boolean isBomber() {
        return bomber;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }



}
