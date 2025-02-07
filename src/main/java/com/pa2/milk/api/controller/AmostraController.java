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
import java.text.DateFormat;
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
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.pa2.milk.api.helper.Response;
import com.pa2.milk.api.model.Amostra;
import com.pa2.milk.api.model.Analise;
import com.pa2.milk.api.model.Solicitacao;
import com.pa2.milk.api.model.dto.AmostraDto;
import com.pa2.milk.api.model.dto.AmostrasDetalhes;
import com.pa2.milk.api.repository.AmostraRepository;
import com.pa2.milk.api.repository.AnaliseRepository;
import com.pa2.milk.api.repository.SolicitacaoRepository;
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

	@Autowired
	private SolicitacaoRepository solicitacaoRepository;

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

		Optional<Analise> analise = this.analiseRepository.findById(am.get().getAnalise().getId());

		if (analise.isPresent()) {

			Optional<Solicitacao> solicitacao = this.solicitacaoRepository
					.findById(analise.get().getSolicitacao().getId());

			if (solicitacao.isPresent()) {

				int coletadas = 0;
				int total = 0;

				for (int j = 0; j < solicitacao.get().getListaAnalise().size(); j++) {
					Optional<Analise> a = this.analiseRepository
							.findById(solicitacao.get().getListaAnalise().get(j).getId());

					coletadas += this.amostraService.amostrasColetas(a.get().getId()).size();
					total += coletadas;

					if (total == 1) {
						solicitacao.get().setInicioColeta(DateFormat.getDateInstance().format(new Date()));
						this.solicitacaoRepository.save(solicitacao.get());
					}
				}
			}
		}

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

		// PdfDocument pdfDoc = new PdfDocument();
		Document document = new Document(PageSize.LETTER);
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		if (amostras.size() == 1) {
			// OK
			try {

				PdfPTable table = new PdfPTable(1);
				table.setWidthPercentage(15);
				table.setWidths(new float[] { 1 });

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

					Image img2 = Image.getInstance(pngData);
					img2.scaleAbsolute(55f, 55f);

					cell = new PdfPCell(img2);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setPaddingRight(5);
					cell.setFixedHeight(60);
					table.addCell(cell);

					PdfWriter.getInstance(document, out);
					document.open();
					document.add(table);

				}

				document.close();

			} catch (DocumentException ex) {

				log.error("Error occurred: {0}", ex);
			}

		} else if (amostras.size() == 2) {
			// OK
			try {

				PdfPTable table = new PdfPTable(2);
				table.setWidthPercentage(25);
				table.setWidths(new float[] { 1, 1 });

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

					Image img2 = Image.getInstance(pngData);
					img2.scaleAbsolute(55f, 55f);

					cell = new PdfPCell(img2);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setPaddingRight(5);
					cell.setFixedHeight(60);
					table.addCell(cell);

					PdfWriter.getInstance(document, out);
					document.open();
					document.add(table);

				}

				document.close();

			} catch (DocumentException ex) {

				log.error("Error occurred: {0}", ex);
			}

		} else if (amostras.size() == 3) {
			// OK
			try {

				PdfPTable table = new PdfPTable(3);
				table.setWidthPercentage(40);
				table.setWidths(new float[] { 1, 1, 1 });

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

					Image img2 = Image.getInstance(pngData);
					img2.scaleAbsolute(55f, 55f);

					cell = new PdfPCell(img2);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setPaddingRight(5);
					cell.setFixedHeight(60);
					table.addCell(cell);

					PdfWriter.getInstance(document, out);
					document.open();
					document.add(table);

				}

				document.close();

			} catch (DocumentException ex) {

				log.error("Error occurred: {0}", ex);
			}

		} else if (amostras.size() == 4) {
			// OK
			try {

				PdfPTable table = new PdfPTable(4);
				table.setWidthPercentage(50);
				table.setWidths(new float[] { 1, 1, 1, 1 });

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

					Image img2 = Image.getInstance(pngData);
					img2.scaleAbsolute(55f, 55f);

					cell = new PdfPCell(img2);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setPaddingRight(5);
					cell.setFixedHeight(60);
					table.addCell(cell);

					PdfWriter.getInstance(document, out);
					document.open();
					document.add(table);

				}

				document.close();

			} catch (DocumentException ex) {

				log.error("Error occurred: {0}", ex);
			}

		} else if (amostras.size() == 5) {
			// OK
			try {

				PdfPTable table = new PdfPTable(5);
				table.setWidthPercentage(60);
				table.setWidths(new float[] { 1, 1, 1, 1, 1, });

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

					Image img2 = Image.getInstance(pngData);
					img2.scaleAbsolute(55f, 55f);

					cell = new PdfPCell(img2);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setPaddingRight(5);
					cell.setFixedHeight(60);
					table.addCell(cell);

					PdfWriter.getInstance(document, out);
					document.open();
					document.add(table);

				}

				document.close();

			} catch (DocumentException ex) {

				log.error("Error occurred: {0}", ex);
			}

		} else if (amostras.size() == 6) {
			// OK
			try {

				PdfPTable table = new PdfPTable(6);
				table.setWidthPercentage(70);
				table.setWidths(new float[] { 1, 1, 1, 1, 1, 1 });

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

					Image img2 = Image.getInstance(pngData);
					img2.scaleAbsolute(55f, 55f);

					cell = new PdfPCell(img2);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setPaddingRight(5);
					cell.setFixedHeight(60);
					table.addCell(cell);

					PdfWriter.getInstance(document, out);
					document.open();
					document.add(table);

				}

				document.close();

			} catch (DocumentException ex) {

				log.error("Error occurred: {0}", ex);
			}

		} else if (amostras.size() == 7) {

			try {

				PdfPTable table = new PdfPTable(7);
				table.setWidthPercentage(85);
				table.setWidths(new float[] { 1, 1, 1, 1, 1, 1, 1 });

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

					Image img2 = Image.getInstance(pngData);
					img2.scaleAbsolute(55f, 55f);

					cell = new PdfPCell(img2);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setPaddingRight(5);
					cell.setFixedHeight(60);
					table.addCell(cell);

					PdfWriter.getInstance(document, out);
					document.open();
					document.add(table);

				}

				document.close();

			} catch (DocumentException ex) {

				log.error("Error occurred: {0}", ex);
			}

		} else if (amostras.size() >= 8) {

			try {

				PdfWriter.getInstance(document, out);
				document.open();

				PdfPTable table = new PdfPTable(8);
				table.setWidthPercentage(100);
				table.setWidths(new float[] { 1, 1, 1, 1, 1, 1, 1, 1 });
				table.setSplitRows(false);
				table.setComplete(false);

				for (int i = 0; i < amostras.size(); i++) {

					ByteArrayOutputStream bout = QRCode.from(amostras.get(i).getIdentificadorAmostra())
							.withSize(250, 250).to(ImageType.PNG).stream();

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

					if (i % 8 == 0) {
						document.add(table);
					}

					Image img2 = Image.getInstance(pngData);
					img2.scaleAbsolute(55f, 55f);

					cell = new PdfPCell(img2);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setPaddingRight(5);
					cell.setFixedHeight(60);
					table.addCell(cell);

					System.out.println("amostra:" + amostras.get(i).getId());

				}
				table.setComplete(true);
				document.add(table);

				PdfPTable tableRestante = null;
				if (amostras.size() % 8 != 0) {
					int resto = amostras.size() % 8;
					int ate = amostras.size() - resto;
					tableRestante = new PdfPTable(resto);
					table.setSplitRows(false);
					table.setComplete(false);

					if (resto == 1) {
						tableRestante.setWidthPercentage(15);
						tableRestante.setWidths(new float[] { 1 });
					} else if (resto == 2) {
						tableRestante.setWidthPercentage(25);
						tableRestante.setWidths(new float[] { 1, 1 });
					} else if (resto == 3) {
						tableRestante.setWidthPercentage(40);
						tableRestante.setWidths(new float[] { 1, 1, 1 });
					} else if (resto == 4) {
						tableRestante.setWidthPercentage(50);
						tableRestante.setWidths(new float[] { 1, 1, 1, 1 });
					} else if (resto == 5) {
						tableRestante.setWidthPercentage(60);
						tableRestante.setWidths(new float[] { 1, 1, 1, 1, 1 });
					} else if (resto == 6) {
						tableRestante.setWidthPercentage(70);
						tableRestante.setWidths(new float[] { 1, 1, 1, 1, 1, 1 });
					} else if (resto == 7) {
						tableRestante.setWidthPercentage(85);
						tableRestante.setWidths(new float[] { 1, 1, 1, 1, 1, 1, 1 });
					}

					for (int j = ate; j < amostras.size(); j++) {

						ByteArrayOutputStream bout2 = QRCode.from(amostras.get(j).getIdentificadorAmostra())
								.withSize(250, 250).to(ImageType.PNG).stream();

						try {
							File file2 = new File("qr_code.png");
							OutputStream out3 = new FileOutputStream(file2);
							bout2.writeTo(out3);

							out.flush();
							out.close();

						} catch (FileNotFoundException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}

						byte[] pngData2 = bout2.toByteArray();

						PdfPCell cell3;

						if (j % resto == 0) {
							document.add(tableRestante);
						}

						Image img3 = Image.getInstance(pngData2);
						img3.scaleAbsolute(55f, 55f);

						cell3 = new PdfPCell(img3);
						cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell3.setPaddingRight(5);
						cell3.setFixedHeight(60);
						tableRestante.addCell(cell3);

						System.out.println("amostra deu certo:" + amostras.get(j).getId());
					}

					tableRestante.setComplete(true);
					document.add(tableRestante);
				}

				document.close();

			} catch (DocumentException ex) {

				log.error("Error occurred: {0}", ex);
			}

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
		int amostrasColetadas = this.amostraService.amostrasColetas(analise.get().getId()).size(); // amostras Coletas

		AmostrasDetalhes amostrasDetalhes = new AmostrasDetalhes();

		amostrasDetalhes.setAmostrasColetadas(amostrasColetadas);
		amostrasDetalhes.setTotalAmostras(analise.get().getQuantidadeAmostras());

		return amostrasDetalhes;

	}

}
