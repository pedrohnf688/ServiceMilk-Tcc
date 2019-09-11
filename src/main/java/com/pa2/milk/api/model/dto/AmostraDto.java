package com.pa2.milk.api.model.dto;

import com.pa2.milk.api.model.Amostra;
import com.pa2.milk.api.model.Analise;

public class AmostraDto {

	private Amostra amostra;
	private Analise analise;

	public AmostraDto() {
		super();
	}

	public Amostra getAmostra() {
		return amostra;
	}

	public void setAmostra(Amostra amostra) {
		this.amostra = amostra;
	}

	public Analise getAnalise() {
		return analise;
	}

	public void setAnalise(Analise analise) {
		this.analise = analise;
	}

	@Override
	public String toString() {
		return "AmostraDto [amostra=" + amostra + ", analise=" + analise + "]";
	}

}
