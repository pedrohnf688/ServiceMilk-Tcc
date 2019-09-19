package com.pa2.milk.api.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pa2.milk.api.model.Usuario;
import com.pa2.milk.api.model.enums.EnumTipoPerfilUsuario;
import com.pa2.milk.api.repository.UsuarioRepository;

@Service
public class UsuarioService {

	private static final Logger log = LoggerFactory.getLogger(UsuarioService.class);

	@Autowired
	private UsuarioRepository usuarioRepositorio;

	public Optional<Usuario> buscarPorEmail(String email) {
		log.info("Buscando usuario pelo email: {}", email);
		return Optional.ofNullable(this.usuarioRepositorio.findByEmail(email));
	}
	
	public Optional<Usuario> buscarPorTipoPerfilUsuario(EnumTipoPerfilUsuario tipoPerfilUsuario) {
		log.info("Buscando usuario pelo tipoPerfilUsuario: {}", tipoPerfilUsuario);
		return Optional.ofNullable(this.usuarioRepositorio.findByCodigoTipoPerfilUsuarioAndAtivoTrue(tipoPerfilUsuario.getCodigo()));
	}


	public Optional<Usuario> buscarPorTipoPerfilUsuarioandID(EnumTipoPerfilUsuario tipoPerfilUsuario, Integer id) {
		log.info("Buscando usuario pelo tipoPerfilUsuario: {}", tipoPerfilUsuario);
		return Optional.ofNullable(this.usuarioRepositorio.findByCodigoTipoPerfilUsuarioAndIdAndAtivoTrue(tipoPerfilUsuario.getCodigo(), id));
	}

	public Optional<Usuario> buscarPorId(Integer id) {
		log.info("Buscando Cliente por ID ");
		return this.usuarioRepositorio.findById(id);
	}

	public EnumTipoPerfilUsuario buscarPerfilpeloId(Integer id) {
		log.info("Buscando Perfil do Usuario por ID:{}",id);
		Optional<Usuario> usuario = this.usuarioRepositorio.findById(id);
		return usuario.get().getCodigoTipoPerfilUsuario();
	}

	
}
