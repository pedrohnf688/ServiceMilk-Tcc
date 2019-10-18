package com.pa2.milk.api.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pa2.milk.api.model.Laudo;
import com.pa2.milk.api.model.LaudoMedia;
import com.pa2.milk.api.model.Solicitacao;
import com.pa2.milk.api.model.dto.CadastroClienteDto;
import com.pa2.milk.api.model.dto.LaudoMediaDto;
import com.pa2.milk.api.service.LaudoMediaService;
import com.pa2.milk.api.service.LaudoService;
import com.pa2.milk.api.service.SolicitacaoService;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;

@RestController
@RequestMapping("/laudoMedia")
@CrossOrigin(origins = "*")
public class LaudoMediaController {

//	private static final Logger log = (Logger) LoggerFactory.getLogger(LaudoMediaController.class);

	@Autowired
	private DataSource dataSource;

	@Autowired
	private LaudoService laudoService;

	@Autowired
	private SolicitacaoService solicitacaoService;

	@Autowired
	private LaudoMediaService laudoMediaService;

	@GetMapping("laudo/{solicitacaoId}")
	public List<LaudoMedia> listarLaudoMediaPorSolicitacao(@PathVariable("solicitacaoId") Integer solicitacaoId) {
		List<LaudoMedia> lm = this.laudoMediaService.laudoMediaPeloIddaSolicitacao(solicitacaoId);
		return lm;
	}

	@PostMapping("/batchId/{solicitacaoId}")
	public LaudoMedia MedidaLaudos(@RequestBody LaudoMediaDto laudoMediaDto,
			@PathVariable("solicitacaoId") Integer solicitacaoId) {
		// return this.laudoMediaService.salvar(mediaAritmeticaLaudo(batchId,
		// solicitacaoId));
		// }
// @RequestParam("batchId") String batchId
		// public LaudoMedia mediaAritmeticaLaudo(String batchId, int solicitacaoId) {
		// log.info("Metodo para gerar a media dos atributos do laudo por
		// solicitação:");

		List<Laudo> laudos = this.laudoService.buscarPorBatchId(laudoMediaDto.getBatch().toString());
//		List<Laudo> laudos = this.laudoService.buscarPorBatchId("bovinocultura");

		Optional<Solicitacao> s = this.solicitacaoService.buscarSolicitacaoPorId(solicitacaoId);

		String regex = "[+-]?[0-9]+(\\.[0-9]+)?([Ee][+-]?[0-9]+)?";
		// compiling regex
		Pattern p = Pattern.compile(regex);

		// Creates a matcher that will match input1 against regex
		double media1 = 0;
		double media2 = 0;
		double media3 = 0;
		double media4 = 0;
		double media5 = 0;
		double media6 = 0;
		double media7 = 0;
		double media8 = 0;
		double media9 = 0;
		double media10 = 0;
		double media11 = 0;
		double media12 = 0;
		double media13 = 0;
		double media14 = 0;
		double media15 = 0;
		double media16 = 0;
		double media17 = 0;
		double media18 = 0;
		double media19 = 0;
		double media20 = 0;

		int cont = 0;
		DecimalFormat df = new DecimalFormat("0,00");

		for (int i = 0; i < laudos.size(); i++) {

			Matcher m1 = p.matcher(laudos.get(i).getCasein() != null ? laudos.get(i).getCasein() : "0");
			Matcher m2 = p.matcher(laudos.get(i).getCbt() != null ? laudos.get(i).getCbt() : "0");
			Matcher m3 = p.matcher(laudos.get(i).getCcs() != null ? laudos.get(i).getCcs() : "0");
			Matcher m4 = p.matcher(laudos.get(i).getCel() != null ? laudos.get(i).getCel() : "0");
			Matcher m5 = p.matcher(laudos.get(i).getCmt() != null ? laudos.get(i).getCmt() : "0");

			Matcher m6 = p.matcher(laudos.get(i).getDen() != null ? laudos.get(i).getDen() : "0");
			Matcher m7 = p.matcher(laudos.get(i).getFat() != null ? laudos.get(i).getFat() : "0");
			Matcher m8 = p.matcher(laudos.get(i).getFpd() != null ? laudos.get(i).getFpd() : "0");
			Matcher m9 = p.matcher(laudos.get(i).getPh() != null ? laudos.get(i).getPh() : "0");
			Matcher m10 = p.matcher(laudos.get(i).getRant() != null ? laudos.get(i).getRant() : "0");

			Matcher m11 = p.matcher(laudos.get(i).getSnf() != null ? laudos.get(i).getSnf() : "0");
			Matcher m12 = p.matcher(laudos.get(i).getSolids() != null ? laudos.get(i).getSolids() : "0");
			Matcher m13 = p.matcher(laudos.get(i).getTotpro() != null ? laudos.get(i).getTotpro() : "0");
			Matcher m14 = p.matcher(laudos.get(i).getTrupro() != null ? laudos.get(i).getTrupro() : "0");
			Matcher m15 = p.matcher(laudos.get(i).getUrea() != null ? laudos.get(i).getUrea() : "0");

			Matcher m16 = p.matcher(laudos.get(i).getGord() != null ? laudos.get(i).getGord() : "0");
			Matcher m17 = p.matcher(laudos.get(i).getProt() != null ? laudos.get(i).getProt() : "0");
			Matcher m18 = p.matcher(laudos.get(i).getLact() != null ? laudos.get(i).getLact() : "0");
			Matcher m19 = p.matcher(laudos.get(i).getEsd() != null ? laudos.get(i).getEsd() : "0");
			Matcher m20 = p.matcher(laudos.get(i).getPc() != null ? laudos.get(i).getPc() : "0");

			media1 += (m1.find() && m1.group().equals(laudos.get(i).getCasein()))
					? Double.parseDouble(laudos.get(i).getCasein())
					: 0;

			media2 += (m2.find() && m2.group().equals(laudos.get(i).getCbt()))
					? Double.parseDouble(laudos.get(i).getCbt())
					: 0;
			media3 += (m3.find() && m3.group().equals(laudos.get(i).getCcs()))
					? Double.parseDouble(laudos.get(i).getCcs())
					: 0;
			media4 += (m4.find() && m4.group().equals(laudos.get(i).getCel()))
					? Double.parseDouble(laudos.get(i).getCel())
					: 0;
			media5 += (m5.find() && m5.group().equals(laudos.get(i).getCmt()))
					? Double.parseDouble(laudos.get(i).getCmt())
					: 0;

			media6 += (m6.find() && m6.group().equals(laudos.get(i).getDen()))
					? Double.parseDouble(laudos.get(i).getDen())
					: 0;
			media7 += (m7.find() && m7.group().equals(laudos.get(i).getFat()))
					? Double.parseDouble(laudos.get(i).getFat())
					: 0;
			media8 += (m8.find() && m8.group().equals(laudos.get(i).getFpd()))
					? Double.parseDouble(laudos.get(i).getFpd())
					: 0;
			media9 += (m9.find() && m9.group().equals(laudos.get(i).getPh()))
					? Double.parseDouble(laudos.get(i).getPh())
					: 0;
			media10 += (m10.find() && m10.group().equals(laudos.get(i).getRant()))
					? Double.parseDouble(laudos.get(i).getRant())
					: 0;

			media11 += (m11.find() && m11.group().equals(laudos.get(i).getSnf()))
					? Double.parseDouble(laudos.get(i).getSnf())
					: 0;
			media12 += (m12.find() && m12.group().equals(laudos.get(i).getSolids()))
					? Double.parseDouble(laudos.get(i).getSolids())
					: 0;
			media13 += (m13.find() && m13.group().equals(laudos.get(i).getTotpro()))
					? Double.parseDouble(laudos.get(i).getTotpro())
					: 0;
			media14 += (m14.find() && m14.group().equals(laudos.get(i).getTrupro()))
					? Double.parseDouble(laudos.get(i).getTrupro())
					: 0;
			media15 += (m15.find() && m15.group().equals(laudos.get(i).getUrea()))
					? Double.parseDouble(laudos.get(i).getUrea())
					: 0;
			media16 += (m16.find() && m16.group().equals(laudos.get(i).getGord()))
					? Double.parseDouble(laudos.get(i).getGord())
					: 0;
			media17 += (m17.find() && m17.group().equals(laudos.get(i).getProt()))
					? Double.parseDouble(laudos.get(i).getProt())
					: 0;
			media18 += (m18.find() && m18.group().equals(laudos.get(i).getLact()))
					? Double.parseDouble(laudos.get(i).getLact())
					: 0;
			media19 += (m19.find() && m19.group().equals(laudos.get(i).getEsd()))
					? Double.parseDouble(laudos.get(i).getEsd())
					: 0;
			media20 += (m20.find() && m20.group().equals(laudos.get(i).getPc()))
					? Double.parseDouble(laudos.get(i).getPc())
					: 0;

			media1 = Double.parseDouble(String.valueOf((Math.round(media1 * 100) / 100.0)));
			media2 = Double.parseDouble(String.valueOf((Math.round(media2 * 100) / 100.0)));
			media3 = Double.parseDouble(String.valueOf((Math.round(media3 * 100) / 100.0)));
			media4 = Double.parseDouble(String.valueOf((Math.round(media4 * 100) / 100.0)));
			media5 = Double.parseDouble(String.valueOf((Math.round(media5 * 100) / 100.0)));
			media6 = Double.parseDouble(String.valueOf((Math.round(media6 * 100) / 100.0)));
			media7 = Double.parseDouble(String.valueOf((Math.round(media7 * 100) / 100.0)));
			media8 = Double.parseDouble(String.valueOf((Math.round(media8 * 100) / 100.0)));
			media9 = Double.parseDouble(String.valueOf((Math.round(media9 * 100) / 100.0)));
			media10 = Double.parseDouble(String.valueOf((Math.round(media10 * 100) / 100.0)));
			media11 = Double.parseDouble(String.valueOf((Math.round(media11 * 100) / 100.0)));
			media12 = Double.parseDouble(String.valueOf((Math.round(media12 * 100) / 100.0)));
			media13 = Double.parseDouble(String.valueOf((Math.round(media13 * 100) / 100.0)));
			media14 = Double.parseDouble(String.valueOf((Math.round(media14 * 100) / 100.0)));
			media15 = Double.parseDouble(String.valueOf((Math.round(media15 * 100) / 100.0)));
			media16 = Double.parseDouble(String.valueOf((Math.round(media16 * 100) / 100.0)));
			media17 = Double.parseDouble(String.valueOf((Math.round(media17 * 100) / 100.0)));
			media18 = Double.parseDouble(String.valueOf((Math.round(media18 * 100) / 100.0)));
			media19 = Double.parseDouble(String.valueOf((Math.round(media19 * 100) / 100.0)));
			media20 = Double.parseDouble(String.valueOf((Math.round(media20 * 100) / 100.0)));

			System.out.println("Media " + i + ":" + media1);
			cont++;
		}

		media1 /= cont;
		media2 /= cont;
		media3 /= cont;
		media4 /= cont;
		media5 /= cont;

		media6 /= cont;
		media7 /= cont;
		media8 /= cont;
		media9 /= cont;
		media10 /= cont;

		media11 /= cont;
		media12 /= cont;
		media13 /= cont;
		media14 /= cont;
		media15 /= cont;

		media16 /= cont;
		media17 /= cont;
		media18 /= cont;
		media19 /= cont;
		media20 /= cont;

		media1 = Double.parseDouble(String.valueOf((Math.round(media1 * 100) / 100.0)));
		media2 = Double.parseDouble(String.valueOf((Math.round(media2 * 100) / 100.0)));
		media3 = Double.parseDouble(String.valueOf((Math.round(media3 * 100) / 100.0)));
		media4 = Double.parseDouble(String.valueOf((Math.round(media4 * 100) / 100.0)));
		media5 = Double.parseDouble(String.valueOf((Math.round(media5 * 100) / 100.0)));
		media6 = Double.parseDouble(String.valueOf((Math.round(media6 * 100) / 100.0)));
		media7 = Double.parseDouble(String.valueOf((Math.round(media7 * 100) / 100.0)));
		media8 = Double.parseDouble(String.valueOf((Math.round(media8 * 100) / 100.0)));
		media9 = Double.parseDouble(String.valueOf((Math.round(media9 * 100) / 100.0)));
		media10 = Double.parseDouble(String.valueOf((Math.round(media10 * 100) / 100.0)));
		media11 = Double.parseDouble(String.valueOf((Math.round(media11 * 100) / 100.0)));
		media12 = Double.parseDouble(String.valueOf((Math.round(media12 * 100) / 100.0)));
		media13 = Double.parseDouble(String.valueOf((Math.round(media13 * 100) / 100.0)));
		media14 = Double.parseDouble(String.valueOf((Math.round(media14 * 100) / 100.0)));
		media15 = Double.parseDouble(String.valueOf((Math.round(media15 * 100) / 100.0)));
		media16 = Double.parseDouble(String.valueOf((Math.round(media16 * 100) / 100.0)));
		media17 = Double.parseDouble(String.valueOf((Math.round(media17 * 100) / 100.0)));
		media18 = Double.parseDouble(String.valueOf((Math.round(media18 * 100) / 100.0)));
		media19 = Double.parseDouble(String.valueOf((Math.round(media19 * 100) / 100.0)));
		media20 = Double.parseDouble(String.valueOf((Math.round(media20 * 100) / 100.0)));

		System.out.println("Media final:" + media1 + " Cont" + cont);

		LaudoMedia l = new LaudoMedia();

		l.setBatchIdMedia(laudoMediaDto.getBatch().toString());
		l.setCaseinMedia(String.valueOf(media1));
		l.setCbtMedia(String.valueOf(media2));
		l.setCcsMedia(String.valueOf(media3));
		l.setCelMedia(String.valueOf(media4));
		l.setCmtMedia(String.valueOf(media5));
		l.setDenMedia(String.valueOf(media6));
		l.setFatMedia(String.valueOf(media7));
		l.setFpdMedia(String.valueOf(media8));
		l.setPhMedia(String.valueOf(media9));
		l.setRantMedia(String.valueOf(media10));
		l.setSnfMedia(String.valueOf(media11));
		l.setSolidsMedia(String.valueOf(media12));
		l.setTotproMedia(String.valueOf(media13));
		l.setTruproMedia(String.valueOf(media14));
		l.setUreaMedia(String.valueOf(media15));
		l.setGordMedia(String.valueOf(media16));
		l.setProtMedia(String.valueOf(media17));
		l.setLactMedia(String.valueOf(media18));
		l.setEsdMedia(String.valueOf(media19));
		l.setPcMedia(String.valueOf(media20));

//		l.setBatchIdMedia(batchId);
//		l.setCaseinMedia(String.valueOf(Math.round(media1*100)/100.0));
//		l.setCbtMedia(String.valueOf(Math.round(media2*100)/100.0));
//		l.setCcsMedia(String.valueOf(Math.round(media3*100)/100.0));
//		l.setCelMedia(String.valueOf(Math.round(media4*100)/100.0));
//		l.setCmtMedia(String.valueOf(Math.round(media5*100)/100.0));
//		l.setDenMedia(String.valueOf(Math.round(media6*100)/100.0));
//		l.setFatMedia(String.valueOf(Math.round(media7*100)/100.0));
//		l.setFpdMedia(String.valueOf(Math.round(media8*100)/100.0));
//		l.setPhMedia(String.valueOf(Math.round(media9*100)/100.0));
//		l.setRantMedia(String.valueOf(Math.round(media10*100)/100.0));
//		l.setSnfMedia(String.valueOf(Math.round(media11*100)/100.0));
//		l.setSolidsMedia(String.valueOf(Math.round(media12*100)/100.0));
//		l.setTotproMedia(String.valueOf(Math.round(media13*100)/100.0));
//		l.setTruproMedia(String.valueOf(Math.round(media14*100)/100.0));
//		l.setUreaMedia(String.valueOf(Math.round(media15*100)/100.0));
//		l.setGordMedia(String.valueOf(Math.round(media16*100)/100.0));
//		l.setProtMedia(String.valueOf(Math.round(media17*100)/100.0));
//		l.setLactMedia(String.valueOf(Math.round(media18*100)/100.0));
//		l.setEsdMedia(String.valueOf(Math.round(media19*100)/100.0));
//		l.setPcMedia(String.valueOf(Math.round(media20*100)/100.0));

		l.setSolicitacao(s.get());

		l.setListaLaudos(laudos);
		this.laudoMediaService.salvar(l);

		// log.info("Contador:{}", cont);

		return l;
	}

	@GetMapping(value = "relatorio/pdf/{id}")
	public void gerarPdf(@PathVariable("id") Integer id, HttpServletResponse response)
			throws JRException, SQLException, IOException {
		// log.info("Gerando Relatorio do Laudo para Id: {}", id);

		Map<String, Object> parametros = new HashMap<>();

		parametros.put("Id_LaudoMedia", id);

		InputStream gto1 = this.getClass().getResourceAsStream("/relatorios/imgReport/image1.png");
		InputStream gto3 = this.getClass().getResourceAsStream("/relatorios/imgReport/image3.jpg");
		InputStream gto4 = this.getClass().getResourceAsStream("/relatorios/imgReport/image4.png");
		InputStream gto5 = this.getClass().getResourceAsStream("/relatorios/imgReport/image5.png");
		InputStream gto6 = this.getClass().getResourceAsStream("/relatorios/imgReport/image6.png");
		InputStream gto7 = this.getClass().getResourceAsStream("/relatorios/imgReport/image7.png");
		InputStream gto9 = this.getClass().getResourceAsStream("/relatorios/imgReport/image9.png");
		InputStream gto10 = this.getClass().getResourceAsStream("/relatorios/imgReport/image10.png");
		InputStream gto11 = this.getClass().getResourceAsStream("/relatorios/imgReport/image11.png");
		InputStream gto12 = this.getClass().getResourceAsStream("/relatorios/imgReport/image12.jpg");
		InputStream gto13 = this.getClass().getResourceAsStream("/relatorios/imgReport/legenda.jpg");

		parametros.put("imagem1", gto1);
		parametros.put("imagem3", gto3);
		parametros.put("imagem4", gto4);
		parametros.put("imagem5", gto5);
		parametros.put("imagem6", gto6);
//		parametros.put("imagem7", gto7); Erro
//		parametros.put("imagem9", gto9); Erro
		parametros.put("imagem10", gto10);
		parametros.put("imagem11", gto11);
		parametros.put("imagem12", gto12);
		parametros.put("imagem13", gto13);

		// Pega o arquivo .jasper localizado em resources
		InputStream jasperStream = this.getClass().getResourceAsStream("/relatorios/report2.jasper");

		// Cria o objeto JaperReport com o Stream do arquivo jasper
		JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
		// Passa para o JasperPrint o relatório, os parâmetros e a fonte dos dados, no
		// caso uma conexão ao banco de dados
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, dataSource.getConnection());

		// Configura a respota para o tipo PDF
		response.setContentType("application/pdf");
		// Define que o arquivo pode ser visualizado no navegador e também nome final do
		// arquivo
		// para fazer download do relatório troque 'inline' por 'attachment'
		response.setHeader("Content-Disposition", "inline; filename=reportLaudoCliente.pdf");

		// Faz a exportação do relatório para o HttpServletResponse
		final OutputStream outStream = response.getOutputStream();
		JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
	}

	@GetMapping(value = "relatorio/excel/{id}")
	public void gerarExcel(@PathVariable("id") Integer id, HttpServletResponse response)
			throws JRException, SQLException, IOException {

		Map<String, Object> parametros = new HashMap<>();

		parametros.put("Id_LaudoMedia", id);

		InputStream jasperStream = this.getClass().getResourceAsStream("/relatorios/report2.jasper");

		JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, dataSource.getConnection());

		response.setContentType("application/x-xls");
		response.setHeader("Content-Disposition", "inline; filename=reportLaudoCliente.xls");

		final OutputStream outStream = response.getOutputStream();

		JRXlsExporter exportsXLS = new JRXlsExporter();

		exportsXLS.setExporterInput(new SimpleExporterInput(jasperPrint));
		exportsXLS.setExporterOutput(new SimpleOutputStreamExporterOutput(outStream));

		SimpleXlsReportConfiguration configuration = new SimpleXlsReportConfiguration();

		configuration.setOnePagePerSheet(true);
		configuration.setDetectCellType(true);
		configuration.setCollapseRowSpan(false);
		configuration.setWhitePageBackground(true);//
		configuration.setRemoveEmptySpaceBetweenRows(false);
		configuration.setAutoFitPageHeight(true);

		exportsXLS.setConfiguration(configuration);

		exportsXLS.exportReport();

	}

}
