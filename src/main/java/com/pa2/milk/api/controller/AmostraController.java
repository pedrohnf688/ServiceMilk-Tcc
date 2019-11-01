package com.pa2.milk.api.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
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
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.pa2.milk.api.helper.Response;
import com.pa2.milk.api.model.Amostra;
import com.pa2.milk.api.model.Analise;
import com.pa2.milk.api.model.dto.AmostraDto;
import com.pa2.milk.api.model.dto.AmostrasDetalhes;
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

	@GetMapping(value = "buscarAmostra/{analiseId}/{identifAmostra}")
	public ResponseEntity<Response<AmostraDto>> buscarAmostra(@PathVariable("analiseId") Integer analiseId,
			@PathVariable("identifAmostra") String identifAmostra) {

		Response<AmostraDto> response = new Response<AmostraDto>();

		Optional<Amostra> amostra = this.amostraService.BuscarPorQrCodeAmostras(analiseId, identifAmostra);
		// Optional<Amostra> a =
		// this.amostraService.buscarIdentificadorAmostra(identifAmostra);

		AmostraDto am = new AmostraDto();

		if (amostra.isPresent()) {
			am.setDataColeta(amostra.get().getDataColeta());
			am.setIdentificadorAmostra(amostra.get().getIdentificadorAmostra());
			am.setNumeroAmostra(amostra.get().getNumeroAmostra());
			am.setQrCode(amostra.get().getQrCode());
			am.setEspecie(amostra.get().getAnalise().getEspecie());
			am.setOrigemLeite(amostra.get().getAnalise().getOrigemLeite());
			am.setProdutos(amostra.get().getAnalise().getProdutos());
			am.setObservacao(amostra.get().getObservacao());
			am.setAnalise(amostra.get().getAnalise());
		}

		response.setData(am);

		return ResponseEntity.ok(response);
	}

	@GetMapping(value = "buscarAmostraInfo/{identifAmostra}")
	public ResponseEntity<Response<AmostraDto>> buscarAmostra(@PathVariable("identifAmostra") String identifAmostra) {

		Response<AmostraDto> response = new Response<AmostraDto>();

		Optional<Amostra> amostra = this.amostraService.buscarIdentificadorAmostra(identifAmostra);

		AmostraDto am = new AmostraDto();

		if (amostra.isPresent()) {

			am.setDataColeta(amostra.get().getDataColeta());
			am.setIdentificadorAmostra(amostra.get().getIdentificadorAmostra());
			am.setNumeroAmostra(amostra.get().getNumeroAmostra());
			am.setQrCode(amostra.get().getQrCode());
			am.setEspecie(amostra.get().getAnalise().getEspecie());
			am.setOrigemLeite(amostra.get().getAnalise().getOrigemLeite());
			am.setProdutos(amostra.get().getAnalise().getProdutos());
			am.setObservacao(amostra.get().getObservacao());

			am.setNomeFazenda(amostra.get().getAnalise().getSolicitacao().getFazenda().getNomeFazenda());
			am.setAnalisesSolicitadas(amostra.get().getAnalise().getAnalisesSolicitadas());
			am.setNomeCliente(amostra.get().getAnalise().getSolicitacao().getCliente().getNome());

		}

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

//		if (amostra.getQrCode() != null) {
//			am.setQrCode(amostra.getQrCode());
//		} else {
//			am.setQrCode(am.getQrCode());
//		}

	}

	@PreAuthorize("hasAnyRole('ADMINISTRADOR','CLIENTE','BOLSISTA')")
	@GetMapping(value = "listaQrCode/{analiseId}")
	public List<Amostra> listarAmostrasPorAnalise(@PathVariable("analiseId") Integer analiseId) {

		List<Amostra> amostras = this.amostraRepositorio.findByAnaliseId(analiseId);

		return amostras;
	}

	@GetMapping(value = "/pdfreport/{analiseId}", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<InputStreamResource> listaQrcodeReport(@PathVariable("analiseId") Integer analiseId)
			throws MalformedURLException, IOException {

		List<Amostra> amostras = this.amostraRepositorio.findByAnaliseId(analiseId);

		ByteArrayInputStream bis = report(amostras);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment; filename=listaQrcodereport.pdf");

		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
				.body(new InputStreamResource(bis));
	}

	public ByteArrayInputStream report(List<Amostra> amostras) throws MalformedURLException, IOException {

		Document document = new Document();
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		try {

			PdfPTable table = new PdfPTable(8);
			table.setWidthPercentage(100);
			table.setWidths(new float[] { 1, 1, 1, 1, 1, 1, 1, 1 });

			for (Amostra amostra : amostras) {

				ByteArrayOutputStream bout = QRCode.from(amostra.getIdentificadorAmostra()).withSize(250, 250)
						.to(ImageType.PNG).stream();

				try {
					File file = new File("qr_code.png");
					OutputStream out2 = new FileOutputStream(file);
					bout.writeTo(out2);

					out.flush();
					out.close();

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

				byte[] pngData = bout.toByteArray();

				PdfPCell cell;

//				Image img = Image.getInstance("logo1.jpg");
//				img.scaleAbsolute(60f, 55f);
//
//				cell = new PdfPCell(img);
//				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//				cell.setPaddingRight(5);
//				cell.setFixedHeight(60);
//				table.addCell(cell);

//				cell = new PdfPCell(new Phrase(amostra.getId().toString()));
//				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//				cell.setFixedHeight(60);
//				table.addCell(cell);

//				cell = new PdfPCell(new Phrase(String.valueOf(amostra.getNumeroAmostra())));
//				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//				cell.setFixedHeight(60);
//				table.addCell(cell);

				// Locale.setDefault(new Locale("pt","Brazil"));

//				cell = new PdfPCell(new Phrase(amostra.getDataColeta().toGMTString()));
//				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//				cell.setPaddingRight(5);
//				cell.setFixedHeight(60);
//				table.addCell(cell);

				Image img2 = Image.getInstance(pngData);
				img2.scaleAbsolute(55f, 55f);

				cell = new PdfPCell(img2);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPaddingRight(5);
				cell.setFixedHeight(60);

//				Paragraph p = new Paragraph("ID: " + amostra.getId().toString());
//				p.setAlignment(Element.ALIGN_RIGHT);
//				cell.addElement(p);
//				Paragraph p2 = new Paragraph("SEQ: " + String.valueOf(amostra.getNumeroAmostra()));
//				p2.setAlignment(Element.ALIGN_CENTER);
//				cell.addElement(p2);

				table.addCell(cell);

//				cell = new PdfPCell(new Phrase(
//						"ID: " + amostra.getId().toString() + "\nSEQ: " + String.valueOf(amostra.getNumeroAmostra())));
//				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//				//cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//				cell.setFixedHeight(60);
//				table.addCell(cell);

				PdfWriter.getInstance(document, out);
				document.open();
				document.add(table);

			}

			document.close();

		} catch (DocumentException ex) {

			log.error("Error occurred: {0}", ex);
		}

		return new ByteArrayInputStream(out.toByteArray());
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

	@GetMapping(value = "/dadosAmostras/{analiseId}")
	public AmostrasDetalhes buscarDadosAmostrasPorAnaliseId(@PathVariable("analiseId") Integer analiseId) {

		log.info("Buscar Analise por Id:", analiseId);

		Optional<Analise> analise = this.analiseRepository.findById(analiseId);

		analise.get().getQuantidadeAmostras();// total de Amostras
		int amostrasColetadas = this.amostraService.amostrasColetas(); // amostras Coletas

		AmostrasDetalhes amostrasDetalhes = new AmostrasDetalhes();

		amostrasDetalhes.setAmostrasColetadas(amostrasColetadas);
		amostrasDetalhes.setTotalAmostras(analise.get().getQuantidadeAmostras());

		return amostrasDetalhes;

	}

}
