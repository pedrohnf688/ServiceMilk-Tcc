package com.pa2.milk.api.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.zxing.WriterException;
import com.pa2.milk.api.helper.Response;
import com.pa2.milk.api.model.Amostra;
import com.pa2.milk.api.model.Analise;
import com.pa2.milk.api.model.dto.AmostraDto;
import com.pa2.milk.api.repository.AmostraRepository;
import com.pa2.milk.api.repository.AnaliseRepository;
import com.pa2.milk.api.service.AmostraService;

import net.glxn.qrgen.core.image.ImageType;
import net.glxn.qrgen.javase.QRCode;

@RestController
@RequestMapping(value = "/amostra")
@CrossOrigin(origins = "*")
public class AmostraController {
//O crud de amostra envolve a busca pelo id da solicitação e o id de analise

	private static final Logger log = LoggerFactory.getLogger(AmostraController.class);

	@Autowired
	private AmostraRepository amostraRepositorio;

	@Autowired
	private AmostraService amostraService;

	@Autowired
	private AnaliseRepository analiseRepository;

	@GetMapping(value = "buscarAmostra/{identifAmostra}")
	public ResponseEntity<Response<AmostraDto>> buscarAmostra(@PathVariable("identifAmostra") String identifAmostra) {

		Response<AmostraDto> response = new Response<AmostraDto>();

		Optional<Amostra> a = this.amostraService.buscarIdentificadorAmostra(identifAmostra);

		AmostraDto am = new AmostraDto();

		am.setDataColeta(a.get().getDataColeta());
		am.setNumeroAmostra(a.get().getNumeroAmostra());
		am.setQrCode(a.get().getQrCode());
		am.setEspecie(a.get().getAnalise().getEspecie());
		am.setOrigemLeite(a.get().getAnalise().getOrigemLeite());
		am.setProdutos(a.get().getAnalise().getProdutos());
		am.setObservacao(a.get().getObservacao());

		response.setData(am);

		return ResponseEntity.ok(response);
	}

	@GetMapping(value = "qrCode/{amostraId}")
	public ResponseEntity<Resource> getQRCodeImage(@PathVariable("amostraId") Integer amostraId)
			throws WriterException, IOException {

		Optional<Amostra> a = this.amostraRepositorio.findById(amostraId);

		String texto = a.get().getIdentificadorAmostra();

		ByteArrayOutputStream bout = QRCode.from(texto).withSize(250, 250).to(ImageType.PNG).stream();

		try {
			File file = new File("qr_code.png");
			OutputStream out = new FileOutputStream(file);
			bout.writeTo(out);

			out.flush();
			out.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		byte[] pngData = bout.toByteArray();

		return ResponseEntity.ok().contentType(MediaType.parseMediaType("image/png"))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + "QRCODE1" + "\"")
				.body(new ByteArrayResource(pngData));

	}

	@PreAuthorize("hasAnyRole('ADMINISTRADOR','BOLSISTA','CLIENTE')")
	@PutMapping(value = "{identifAmostra}")
	public ResponseEntity<Response<Amostra>> atualizarAmostra(@PathVariable("identifAmostra") String identifAmostra,
			@Valid @RequestBody Amostra amostra, BindingResult result) {

		log.info("Atualizando a Amostra:{}", amostra.toString());

		Response<Amostra> response = new Response<Amostra>();

		Optional<Amostra> am = this.amostraService.buscarIdentificadorAmostra(identifAmostra);

		if (!am.isPresent()) {
			log.info("Amostra não encontrada");
			response.getErros().add("Amostra não encontrada");
			ResponseEntity.badRequest().body(response);
		}

		this.atualizarDadosAmostra(am.get(), amostra, result);

		if (result.hasErrors()) {
			log.error("Erro validando lancamento:{}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErros().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		response.setData(am.get());
		this.amostraService.salvar(am.get());

		return ResponseEntity.ok(response);

	}

	private void atualizarDadosAmostra(Amostra am, Amostra amostra, BindingResult result) {

		am.setDataColeta(new Date());
		am.setFinalizada(true);

		if (amostra.getObservacao() != null) {
			am.setObservacao(amostra.getObservacao());
		} else {
			am.setObservacao(am.getObservacao());
		}

		if (amostra.getQrCode() != null) {
			am.setQrCode(amostra.getQrCode());
		} else {
			am.setQrCode(am.getQrCode());
		}

	}

	@PreAuthorize("hasAnyRole('ADMINISTRADOR','CLIENTE','BOLSISTA')")
	@GetMapping(value = "{analiseId}/ListaQrCode")
	public List<Amostra> listarAmostrasPorAnalise(@PathVariable("analiseId") Integer analiseId) {

		List<Amostra> amostras = this.amostraRepositorio.findByAnaliseId(analiseId);

		return amostras;
	}

	@PreAuthorize("hasAnyRole('ADMINISTRADOR','CLIENTE','BOLSISTA')")
	@PostMapping
	public ResponseEntity<Response<Amostra>> cadastrarAmostra(@Valid @RequestBody Amostra amostra,
			@PathVariable("analiseId") Integer analiseId, BindingResult result) throws NoSuchAlgorithmException {

		log.info("Cadastrando Amostra:{}", amostra.toString());

		Response<Amostra> response = new Response<Amostra>();

		Optional<Analise> analise = this.analiseRepository.findById(analiseId);

		amostra.setAnalise(analise.get());
		amostraService.salvar(amostra);

		response.setData(amostra);

		return ResponseEntity.ok(response);

	}

	@PreAuthorize("hasAnyRole('ADMINISTRADOR','BOLSISTA')")
	@GetMapping
	public List<Amostra> listarTodasAmostras() {
		List<Amostra> amostras = this.amostraService.listarAmostras();
		return amostras;
	}

	@PreAuthorize("hasAnyRole('ADMINISTRADOR','BOLSISTA')")
	@GetMapping(value = "{id}")
	public ResponseEntity<Response<Amostra>> buscarAmostraPorId(@PathVariable("id") Integer id) {

		log.info("Buscar Amostra por Id");

		Response<Amostra> response = new Response<Amostra>();

		Optional<Amostra> amostra = this.amostraService.buscarPorId(id);

		if (!amostra.isPresent()) {
			log.info("Amostra não encontrada: {}", amostra.get());
			response.getErros().add("Amostra não encontrado");
			return ResponseEntity.badRequest().body(response);
		}

		response.setData(amostra.get());

		return ResponseEntity.ok(response);
	}

	@PreAuthorize("hasAnyRole('ADMINISTRADOR','BOLSISTA','CLIENTE')")
	@DeleteMapping(value = "{id}")
	public ResponseEntity<Response<Amostra>> deletarAmostraPorId(@PathVariable("id") Integer id) {

		log.info("Removendo Amostra por Id: {}", id);

		Response<Amostra> response = new Response<Amostra>();

		Optional<Amostra> amostra = this.amostraService.buscarPorId(id);

		if (!amostra.isPresent()) {
			log.info("Amostra não encontrada");
			response.getErros().add("Amostra não encontrada");
			ResponseEntity.badRequest().body(response);
		}

		response.setData(amostra.get());
		this.amostraService.remover(id);

		return ResponseEntity.ok(response);
	}

	@PreAuthorize("hasAnyRole('ADMINISTRADOR','BOLSISTA','CLIENTE')")
	@DeleteMapping(value = "/analise/{id}")
	public List<Amostra> deletarAmostraPorAnalise(@PathVariable("id") Integer id) {
		log.info("Removendo Amostra: {}", id);

		List<Amostra> amostras = this.amostraService.deletarAmostrasPorAnalise(id);

		return amostras;
	}

}
