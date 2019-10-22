package com.pa2.milk.api.model;

import javax.persistence.Entity;

@Entity(name = "bolsista")
public class Bolsista extends Usuario {

	public Bolsista() {

	}

	public Bolsista(String email, String nome, String cpf, Integer codigoTipoPerfilUsuario, Arquivo fotoPerfil) {
		super(email, nome, cpf, codigoTipoPerfilUsuario, fotoPerfil);
	}

}
