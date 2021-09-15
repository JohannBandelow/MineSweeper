package br.com.johannbandelow.model;

public class ResultEvent {

	private final boolean ganhou;

	public ResultEvent(boolean ganhou) {
		this.ganhou = ganhou;
	}

	public boolean isGanhou() {
		return ganhou;
	}
}
