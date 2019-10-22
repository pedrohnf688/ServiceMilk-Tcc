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
import com.pa2.milk.api.model.Fazenda;
import com.pa2.milk.api.model.Solicitacao;
import com.pa2.milk.api.model.Usuario;
import com.pa2.milk.api.model.enums.EnumTipoPerfilUsuario;
import com.pa2.milk.api.repository.ArquivoRepository;
import com.pa2.milk.api.service.AdministradorService;
import com.pa2.milk.api.service.ArquivoService;
import com.pa2.milk.api.service.BolsistaService;
import com.pa2.milk.api.service.ClienteService;
import com.pa2.milk.api.service.FazendaService;
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

	@Autowired
	private FazendaService fazendaService;

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

			dbFile.setFotoPerfil(cliente.get());
			this.arquivoRepository.save(dbFile);

		} 
//		else if (usuario.get().getCodigoTipoPerfilUsuario().equals(EnumTipoPerfilUsuario.ROLE_BOLSISTA)) {
//			Optional<Bolsista> bolsista = this.bolsistaService
//					.buscarPorTipoPerfilUsuarioandID(usuario.get().getCodigoTipoPerfilUsuario(), id);
//			//bolsista.get().setFotoPerfil(dbFile);
//			this.arquivoRepository.save(dbFile);
//			//this.bolsistaService.salvar(bolsista.get());
//
//		} else if (usuario.get().getCodigoTipoPerfilUsuario().equals(EnumTipoPerfilUsuario.ROLE_ADMINISTRADOR)) {
//			Optional<Administrador> administrador = this.administradorService
//					.buscarPorTipoPerfilUsuarioandID(usuario.get().getCodigoTipoPerfilUsuario(), id);
//			administrador.get().setFotoPerfil(dbFile);
//			//this.arquivoRepository.save(dbFile);
//			this.administradorService.salvar(administrador.get());
//			
//
//		}

		return dbFile;
	}

	@PutMapping("/uploadFileSolicitacao/{id}")
	public Arquivo uploadFileSolicitacao(@RequestParam("file") MultipartFile file, @PathVariable("id") Integer id)
			throws NotFoundException {
		Arquivo dbFile = arquivoService.storeFile(file);

		Optional<Solicitacao> solic = this.solicitacaoService.buscarSolicitacaoPorId(id);

		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/")
				.path(dbFile.getId()).toUriString();

		dbFile.setFileDownloadUri(fileDownloadUri);
		dbFile.setSize(file.getSize());

		solic.get().setFotoSolicitacao(dbFile);
		this.solicitacaoService.salvarSolicitacao(solic.get());


		return dbFile;
	}

	@PutMapping("/uploadFileComprovanteSolicitacao/{id}")
	public Arquivo uploadFileComprovanteSolicitacao(@RequestParam("file") MultipartFile file,
			@PathVariable("id") Integer id) throws NotFoundException {
		Arquivo dbFile = arquivoService.storeFile(file);

		Optional<Solicitacao> solic = this.solicitacaoService.buscarSolicitacaoPorId(id);

		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/")
				.path(dbFile.getId()).toUriString();

		dbFile.setFileDownloadUri(fileDownloadUri);
		dbFile.setSize(file.getSize());
		
		solic.get().setComprovanteSolicitacao(dbFile);
		this.solicitacaoService.salvarSolicitacao(solic.get());

		return dbFile;
	}

	@PutMapping("/uploadFileFazenda/{id}")
	public Arquivo uploadFileFazenda(@RequestParam("file") MultipartFile file, @PathVariable("id") Integer id)
			throws NotFoundException {
		Arquivo dbFile = arquivoService.storeFile(file);

		Optional<Fazenda> fazenda = this.fazendaService.buscarPorId(id);

		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/")
				.path(dbFile.getId()).toUriString();

		dbFile.setFileDownloadUri(fileDownloadUri);
		dbFile.setSize(file.getSize());

		fazenda.get().setFotoFazenda(dbFile);
		this.fazendaService.salvar(fazenda.get());

		return dbFile;
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
		
		
		Optional<Cliente> cliente = this.clienteService
				.buscarPorTipoPerfilUsuarioandID(EnumTipoPerfilUsuario.ROLE_CLIENTE, id);

		Arquivo arquivo = this.arquivoService.buscarCliente(cliente.get().getId());
		
		return arquivo.getFileDownloadUri().toString();
	}

	@GetMapping("/fileUrlFazenda/{id}")
	public String fileUrlFotoFazenda(@PathVariable("id") Integer id) {
		Optional<Fazenda> fazenda = this.fazendaService.buscarPorId(id);
		return fazenda.get().getFotoFazenda().getFileDownloadUri();
	}

	@GetMapping("/fileUrlComprovante/{id}")
	public String fileUrlComprovante(@PathVariable("id") Integer id) {
		Optional<Solicitacao> solicitacao = this.solicitacaoService.buscarSolicitacaoPorId(id);
		return solicitacao.get().getComprovanteSolicitacao().getFileDownloadUri();
	}

	@GetMapping("/fileUrlSolicitacao/{id}")
	public String fileUrlSolicitacao(@PathVariable("id") Integer id) {
		Optional<Solicitacao> solicitacao = this.solicitacaoService.buscarSolicitacaoPorId(id);
		return solicitacao.get().getFotoSolicitacao().getFileDownloadUri();
	}


	
	
}
