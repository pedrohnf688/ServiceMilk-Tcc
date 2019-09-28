package com.pa2.milk.api.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.pa2.milk.api.helper.UploadFileResponse;
import com.pa2.milk.api.model.Administrador;
import com.pa2.milk.api.model.Arquivo;
import com.pa2.milk.api.model.Bolsista;
import com.pa2.milk.api.model.Cliente;
import com.pa2.milk.api.model.Solicitacao;
import com.pa2.milk.api.model.Usuario;
import com.pa2.milk.api.model.enums.EnumTipoPerfilUsuario;
import com.pa2.milk.api.repository.ArquivoRepository;
import com.pa2.milk.api.service.AdministradorService;
import com.pa2.milk.api.service.ArquivoService;
import com.pa2.milk.api.service.BolsistaService;
import com.pa2.milk.api.service.ClienteService;
import com.pa2.milk.api.service.SolicitacaoService;
import com.pa2.milk.api.service.UsuarioService;

import javassist.NotFoundException;

@RestController
@CrossOrigin(origins = "*")
public class ArquivoController {

	private static final Logger logger = LoggerFactory.getLogger(ArquivoController.class);

	@Autowired
	private ArquivoService arquivoService;

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private BolsistaService bolsistaService;

	@Autowired
	private AdministradorService administradorService;

	@Autowired
	private SolicitacaoService solicitacaoService;

	@Autowired
	private ArquivoRepository arquivoRepository;
	
	@PutMapping("/uploadFileUsuario/{id}")
	public Arquivo uploadFileCliente(@RequestParam("file") MultipartFile file, @PathVariable("id") Integer id) {
		Arquivo dbFile = arquivoService.storeFile(file);

		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/")
				.path(dbFile.getId()).toUriString();

		dbFile.setFileDownloadUri(fileDownloadUri);
		dbFile.setSize(file.getSize());

		Optional<Usuario> usuario = this.usuarioService.buscarPorId(id);

		if (usuario.get().getCodigoTipoPerfilUsuario().equals(EnumTipoPerfilUsuario.ROLE_CLIENTE)) {
			Optional<Cliente> cliente = this.clienteService
					.buscarPorTipoPerfilUsuarioandID(usuario.get().getCodigoTipoPerfilUsuario(), id);
			cliente.get().setFotoPerfil(dbFile);
			this.clienteService.salvar(cliente.get());

		} else if (usuario.get().getCodigoTipoPerfilUsuario().equals(EnumTipoPerfilUsuario.ROLE_BOLSISTA)) {
			Optional<Bolsista> bolsista = this.bolsistaService
					.buscarPorTipoPerfilUsuarioandID(usuario.get().getCodigoTipoPerfilUsuario(), id);
			bolsista.get().setFotoPerfil(dbFile);
			this.bolsistaService.salvar(bolsista.get());

		} else if (usuario.get().getCodigoTipoPerfilUsuario().equals(EnumTipoPerfilUsuario.ROLE_ADMINISTRADOR)) {
			Optional<Administrador> administrador = this.administradorService
					.buscarPorTipoPerfilUsuarioandID(usuario.get().getCodigoTipoPerfilUsuario(), id);
			administrador.get().setFotoPerfil(dbFile);
			this.administradorService.salvar(administrador.get());

		}

		// return new UploadFileResponse(dbFile.getFileName(), fileDownloadUri,
		// file.getContentType(), file.getSize());
		return dbFile;
	}

	@PutMapping("/uploadFileSolicitacao/{id}")
	public UploadFileResponse uploadFileSolicitacao(@RequestParam("file") MultipartFile file,
			@PathVariable("id") Integer id) throws NotFoundException {
		Arquivo dbFile = arquivoService.storeFile(file);

		Optional<Solicitacao> solic = this.solicitacaoService.buscarSolicitacaoPorId(id);

		solic.get().setFotoSolicitacao(dbFile);
		this.solicitacaoService.salvarSolicitacao(solic.get());

		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/")
				.path(dbFile.getId()).toUriString();

		return new UploadFileResponse(dbFile.getFileName(), fileDownloadUri, file.getContentType(), file.getSize());
	}

	@PostMapping("/uploadFile")
	public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) {
		Arquivo dbFile = arquivoService.storeFile(file);

		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/")
				.path(dbFile.getId()).toUriString();

		return new UploadFileResponse(dbFile.getFileName(), fileDownloadUri, file.getContentType(), file.getSize());
	}

	@PostMapping("/uploadMultipleFiles")
	public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
		return Arrays.asList(files).stream().map(file -> uploadFile(file)).collect(Collectors.toList());
	}

	@GetMapping("/downloadFile/{fileId}")
	public ResponseEntity<Resource> downloadFile(@PathVariable String fileId) {

		Arquivo dbFile = arquivoService.getFile(fileId);

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(dbFile.getFileType()))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getFileName() + "\"")
				.body(new ByteArrayResource(dbFile.getData()));
	}

	@GetMapping("/fileUrl/{id}")
	public String fileUrlFoto(@PathVariable("id") Integer id) {
		Optional<Usuario> usuario = this.usuarioService.buscarPorId(id);
		
		return usuario.get().getFotoPerfil().getFileDownloadUri();
	}

}
