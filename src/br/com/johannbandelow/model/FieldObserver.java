package br.com.johannbandelow.model;

@FunctionalInterface
public interface FieldObserver {

    public void onEvent(Field field, FieldEvent event);

}
