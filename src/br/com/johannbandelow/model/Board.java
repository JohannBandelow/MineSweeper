package br.com.johannbandelow.model;



import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Board implements FieldObserver{
    private int lines;
    private int columns;
    private int bombs;

    private final List<Field> fields = new ArrayList<>();
    private final List<Consumer<ResultEvent>> observers = new ArrayList<>();

    public Board(int lines, int columns, int bombs) {
        this.lines = lines;
        this.columns = columns;
        this.bombs = bombs;

        generateFields();
        associateNeighbours();
        randomizeBombs();
    }
    
    public void eachButton(Consumer<Field> function) {
       fields.forEach(function);
    }


    public void open(int line, int column) {
            fields
                    .parallelStream()
                    .filter(c -> c.getLine() == line && c.getColumn() == column)
                    .findFirst()
                    .ifPresent(f -> f.open());
    }

    public void showBombs() {
        fields.stream().filter( f -> f.isBomber()).forEach(b -> b.setOpened(true));
    }

    public void resgisterObserver (Consumer<ResultEvent> observer) {
        observers.add(observer);
    }

    public void notifyObserver(boolean result) {
        observers.stream().forEach(o -> o.accept(new ResultEvent(result)));
    }


    public void mark(int line, int column) {
        fields
                .parallelStream()
                .filter(c -> c.getLine() == line && c.getColumn() == column)
                .findFirst()
                .ifPresent(f -> f.alternateMark());
    }

    private void randomizeBombs() {
        long armedBomb = 0;

        do {
            int aleatory = (int) (Math.random() * fields.size());
            fields.get(aleatory).toBomb();
            armedBomb = fields.stream().filter(c -> c.isBomber()).count();
        } while (armedBomb < bombs);
    }

    private void associateNeighbours() {
        for (Field c1: fields){
            for (Field c2: fields){
                c1.addNeighbour(c2);
            }
        }
    }

    public boolean targetAcquired() {
        return fields.stream().allMatch(Field::achievedObj);
    }

    private void generateFields() {
        for (int i = 0; i < lines; i++) {
            for (int j = 0; j < columns; j++){
            	Field field = new Field(i, j);
            	field.observerRegister(this);
                fields.add(field);
            }
        }
    }

    public void restart() {
        fields.forEach(Field::restart);
        randomizeBombs();
    }

    @Override
    public void onEvent(Field field, FieldEvent event) {
        if (event == FieldEvent.EXPLODE) {
        	showBombs();
            notifyObserver(false);
        } else if (targetAcquired()) {
            notifyObserver(true);
            System.out.println("Ganhou");
        }
    }

    public int getLines() {
        return lines;
    }

    public int getColumns() {
        return columns;
    }



}
