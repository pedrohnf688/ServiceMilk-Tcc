package com.pa2.milk.api.model;

import javax.persistence.Entity;

@Entity(name = "administrador")
public class Administrador extends Usuario {

	public Administrador() {

	}

	public Administrador(String email, String nome, String cpf, Integer codigoTipoPerfilUsuario) {
		super(email, nome, cpf, codigoTipoPerfilUsuario);
	}

}
