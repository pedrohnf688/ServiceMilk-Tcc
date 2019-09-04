package com.pa2.milk.api.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.pa2.milk.api.helper.CsvUtils;
import com.pa2.milk.api.helper.Response;
import com.pa2.milk.api.model.Laudo;
import com.pa2.milk.api.repository.LaudoRepositorio;
import com.pa2.milk.api.service.LaudoService;

@RestController
@RequestMapping("/laudo")
@CrossOrigin(origins = "*")
public class LaudoController {

	private static final Logger log = LoggerFactory.getLogger(LaudoController.class);

	@Autowired
	private LaudoService laudoService;

	@Autowired
	private LaudoRepositorio laudoRepositorio;

	@GetMapping
	public List<Laudo> listarLaudos() {
		List<Laudo> laudos = this.laudoService.listarLaudos();
		return laudos;
	}

//  OK
//	@PostMapping(value = "/upload", consumes = "text/csv")
//	public void uploadSimple(@RequestBody InputStream body) throws IOException {
//		laudoRepositorio.saveAll(CsvUtils.read(Laudo.class, body));
//	}

//  OK
	@PostMapping(value = "/upload", consumes = "multipart/form-data")
	public void uploadMultipart(@RequestParam("file") MultipartFile file) throws IOException {
		log.info("Fazendo Upload do Arquivo Csv do Laudo");

		try {

			laudoRepositorio.saveAll(CsvUtils.read(Laudo.class, file.getInputStream()));

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

//  OK	
	@PostMapping
	public ResponseEntity<Response<Laudo>> cadastrarLaudo(@Valid @RequestBody Laudo laudo, BindingResult result)
			throws NoSuchAlgorithmException {
		log.info("Cadastrando Laudo:{}", laudo.toString());

		Response<Laudo> response = new Response<Laudo>();

		validarDadosExistentes(laudo, result);

		if (result.hasErrors()) {
			log.error("Erro Validando Dados do Cadastro do Laudo: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErros().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		this.laudoService.salvar(laudo);
		response.setData(laudo);

		return ResponseEntity.ok(response);
	}

//  OK	
	@GetMapping(value = "{id}")
	public ResponseEntity<Response<Laudo>> buscarLaudoPorId(@PathVariable("id") Integer id) {
		log.info("Buscar Laudo por Id");

		Response<Laudo> response = new Response<Laudo>();

		Optional<Laudo> laudo = this.laudoService.buscarPorId(id);

		if (!laudo.isPresent()) {
			log.info("Laudo não encontrado");
			response.getErros().add("Laudo não encontrado");
			ResponseEntity.badRequest().body(response);
		}

		response.setData(laudo.get());

		return ResponseEntity.ok(response);

	}
//  OK
	@GetMapping(value = "/batch")
	public List<Laudo> buscarLaudoPorBatchId(@RequestParam("batchId") String batchId) {
		log.info("Buscar Laudo por BatchId");

		List<Laudo> laudos = this.laudoService.buscarPorBatchId(batchId);
		return laudos;
	}

//  OK
	@GetMapping(value = "/dataSolicitada")
	public List<Laudo> buscarLaudoPorData(@RequestParam("dataSolicitada") String dataSolicitada) throws ParseException {
		log.info("Buscar Laudo por BatchId");

		List<Laudo> laudos = this.laudoService.buscarPorData(dataSolicitada);
		return laudos;
	}

//  OK	
	@PutMapping(value = "{id}")
	public ResponseEntity<Response<Laudo>> atualizarLaudo(@PathVariable("id") Integer id,
			@Valid @RequestBody Laudo laudo, BindingResult result) throws NoSuchAlgorithmException {
		log.info("Atualizando o Laudo:{}", laudo.toString());

		Response<Laudo> response = new Response<Laudo>();

		Optional<Laudo> laudoId = this.laudoService.buscarPorId(id);

		if (!laudoId.isPresent()) {
			log.info("Laudo não encontrado");
			response.getErros().add("Laudo não encontrado");
			ResponseEntity.badRequest().body(response);
		}

		this.atualizarDadosLaudo(laudoId.get(), laudo, result);

		if (result.hasErrors()) {
			log.error("Erro validando Laudo:{}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErros().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		this.laudoService.salvar(laudoId.get());
		response.setData(laudoId.get());

		return ResponseEntity.ok(response);

	}

//  OK	
	@DeleteMapping
	public void deletarTodoLaudo() {
		this.laudoService.deletarTodoLaudo();
	}

//  OK 	
	@DeleteMapping(value = "{id}")
	public ResponseEntity<Response<Laudo>> deletarCliente(@PathVariable("id") Integer id) {
		log.info("Removendo Laudo por Id: {}", id);

		Response<Laudo> response = new Response<Laudo>();

		Optional<Laudo> laudo = this.laudoService.buscarPorId(id);

		if (!laudo.isPresent()) {
			log.info("Laudo não encontrado");
			response.getErros().add("Laudo não encontrado");
			ResponseEntity.badRequest().body(response);
		}

		response.setData(laudo.get());

		this.laudoService.deletaLaudoPorId(id);

		return ResponseEntity.ok(response);
	}

	private void atualizarDadosLaudo(Laudo laudoId, Laudo laudo, BindingResult result) throws NoSuchAlgorithmException {

		laudoId.setCbt(laudo.getCbt());
		laudoId.setCcs(laudo.getCcs());
		laudoId.setCel(laudo.getCel());
		laudoId.setCmt(laudo.getCmt());
		laudoId.setDen(laudo.getDen());
		laudoId.setPh(laudo.getPh());
		laudoId.setRant(laudo.getRant());
		laudoId.setGord(laudo.getGord());
		laudoId.setProt(laudo.getProt());
		laudoId.setLact(laudo.getLact());
	    laudoId.setEsd(laudo.getEsd());
		laudoId.setPc(laudo.getPc());

		laudoId = laudo;

	}

	private void validarDadosExistentes(Laudo l, BindingResult result) {

	}

	@GetMapping(value = "/filtro")
	public ResponseEntity<Response<List<Laudo>>> filtrarDadosLaudo(@RequestParam("batchId") String batchId)
			throws NoSuchAlgorithmException {
		log.info("Atualizando o Laudo do Batch:{}", batchId);

		Response<List<Laudo>> response = new Response<List<Laudo>>();

		List<Laudo> laudoNovo = new ArrayList<Laudo>();
		List<Laudo> laudo = this.laudoService.buscarPorBatchId(batchId);

		this.atualizarFiltraDadosLaudo(laudo, laudoNovo);

//		this.laudoService.salvar(laudoId.get());
		response.setData(laudoNovo);

		return ResponseEntity.ok(response);

	}

	private void atualizarFiltraDadosLaudo(List<Laudo> laudo, List<Laudo> laudoNovo) {

		Laudo la = new Laudo();
		int contRepetidos[] = new int[laudo.size()];

		float somaCasein[] = new float[laudo.size()];
		float somaCbt[] = new float[laudo.size()];
		float somaCcs[] = new float[laudo.size()];
		float somaCel[] = new float[laudo.size()];
		float somaCmt[] = new float[laudo.size()];

		float somaDen[] = new float[laudo.size()];
		float somaFat[] = new float[laudo.size()];
		float somaFpd[] = new float[laudo.size()];
		float somaPh[] = new float[laudo.size()];
		float somaRant[] = new float[laudo.size()];

		float somaSnf[] = new float[laudo.size()];
		float somaSolids[] = new float[laudo.size()];
		float somaTotpro[] = new float[laudo.size()];
		float somaTrupro[] = new float[laudo.size()];
		float somaUrea[] = new float[laudo.size()];
				
		String regex = "[+-]?[0-9]+(\\.[0-9]+)?([Ee][+-]?[0-9]+)?";
		// compiling regex
		Pattern p = Pattern.compile(regex);

		for (int i = 0; i < laudo.size(); i++) {
			for (int j = 0; j < laudo.size(); j++) {
				if (laudo.get(i).getSequence().equals(laudo.get(j).getSequence())) {
					contRepetidos[i]++;
				}
			}
			System.out
					.println("\n Repeticões numero " + laudo.get(i).getSequence() + ": " + contRepetidos[i] + " vezes");
		}

		for (int y = 0; y < contRepetidos.length; y++) {
			for (int z = 0; z < contRepetidos.length; z++) {
				if (contRepetidos[y] == contRepetidos[z] && laudo.get(y).getSequence() == laudo.get(z).getSequence()) {

					Matcher m1 = p.matcher(laudo.get(z).getCasein() != null ? laudo.get(z).getCasein() : "0");
					Matcher m2 = p.matcher(laudo.get(z).getCbt() != null ? laudo.get(z).getCbt() : "0");
					Matcher m3 = p.matcher(laudo.get(z).getCcs() != null ? laudo.get(z).getCcs() : "0");
					Matcher m4 = p.matcher(laudo.get(z).getCel() != null ? laudo.get(z).getCel() : "0");
					Matcher m5 = p.matcher(laudo.get(z).getCmt() != null ? laudo.get(z).getCmt() : "0");

					Matcher m6 = p.matcher(laudo.get(z).getDen() != null ? laudo.get(z).getDen() : "0");
					Matcher m7 = p.matcher(laudo.get(z).getFat() != null ? laudo.get(z).getFat() : "0");
					Matcher m8 = p.matcher(laudo.get(z).getFpd() != null ? laudo.get(z).getFpd() : "0");
					Matcher m9 = p.matcher(laudo.get(z).getPh() != null ? laudo.get(z).getPh() : "0");
					Matcher m10 = p.matcher(laudo.get(z).getRant() != null ? laudo.get(z).getRant() : "0");

					Matcher m11 = p.matcher(laudo.get(z).getSnf() != null ? laudo.get(z).getSnf() : "0");
					Matcher m12 = p.matcher(laudo.get(z).getSolids() != null ? laudo.get(z).getSolids() : "0");
					Matcher m13 = p.matcher(laudo.get(z).getTotpro() != null ? laudo.get(z).getTotpro() : "0");
					Matcher m14 = p.matcher(laudo.get(z).getTrupro() != null ? laudo.get(z).getTrupro() : "0");
					Matcher m15 = p.matcher(laudo.get(z).getUrea() != null ? laudo.get(z).getUrea() : "0");
	
					
					somaCasein[z] += (m1.find() && m1.group().equals(laudo.get(z).getCasein()))
							? Float.parseFloat(laudo.get(z).getCasein())
							: 0;
						
					System.out.println("SomaCasein:"+somaCasein[y]+"\n");		
					somaCbt[z] += (m2.find() && m2.group().equals(laudo.get(z).getCbt()))
							? Float.parseFloat(laudo.get(z).getCbt())
							: 0;
					somaCcs[z] += (m3.find() && m3.group().equals(laudo.get(z).getCcs()))
							? Float.parseFloat(laudo.get(z).getCcs())
							: 0;
					somaCel[z] += (m4.find() && m4.group().equals(laudo.get(z).getCel()))
							? Float.parseFloat(laudo.get(z).getCel())
							: 0;
					somaCmt[z] += (m5.find() && m5.group().equals(laudo.get(z).getCmt()))
							? Float.parseFloat(laudo.get(z).getCmt())
							: 0;

					somaDen[z] += (m6.find() && m6.group().equals(laudo.get(z).getDen()))
							? Float.parseFloat(laudo.get(z).getDen())
							: 0;
					somaFat[z] += (m7.find() && m7.group().equals(laudo.get(z).getFat()))
							? Float.parseFloat(laudo.get(z).getFat())
							: 0;
					somaFpd[z] += (m8.find() && m8.group().equals(laudo.get(z).getFpd()))
							? Float.parseFloat(laudo.get(z).getFpd())
							: 0;
					somaPh[z] += (m9.find() && m9.group().equals(laudo.get(z).getPh()))
							? Float.parseFloat(laudo.get(z).getPh())
							: 0;
					somaRant[z] += (m10.find() && m10.group().equals(laudo.get(z).getRant()))
							? Float.parseFloat(laudo.get(z).getRant())
							: 0;

					somaSnf[z] += (m11.find() && m11.group().equals(laudo.get(z).getSnf()))
							? Float.parseFloat(laudo.get(z).getSnf())
							: 0;
					somaSolids[z] += (m12.find() && m12.group().equals(laudo.get(z).getSolids()))
							? Float.parseFloat(laudo.get(z).getSolids())
							: 0;
					somaTotpro[z] += (m13.find() && m13.group().equals(laudo.get(z).getTotpro()))
							? Float.parseFloat(laudo.get(z).getTotpro())
							: 0;
					somaTrupro[z] += (m14.find() && m14.group().equals(laudo.get(z).getTrupro()))
							? Float.parseFloat(laudo.get(z).getTrupro())
							: 0;
					somaUrea[z] += (m15.find() && m15.group().equals(laudo.get(z).getUrea()))
							? Float.parseFloat(laudo.get(z).getUrea())
							: 0;
				
			}
				
			
	      }

			//System.out.println("Vetor de Casein Soma:" + somaCasein[y] + "\nCont:" + contRepetidos[y]);

		}
		
///OBS: DESCOBRIR COMO SOMAR		
		for (int i = 0; i < somaCasein.length; i++) {
			System.out.println("Ordem:"+i+" -->" +somaCasein[i]);
		
		}
		

		for (int s = 0; s < contRepetidos.length; s++) {
			
			System.out.println("Contador" + contRepetidos[s]);
			
			somaCasein[s] /= contRepetidos[s];
			somaCbt[s] /= contRepetidos[s];
			somaCcs[s] /= contRepetidos[s];
			somaCel[s] /= contRepetidos[s];
			somaCmt[s] /= contRepetidos[s];

			somaDen[s] /= contRepetidos[s];
			somaFat[s] /= contRepetidos[s];
			somaFpd[s] /= contRepetidos[s];
			somaPh[s] /= contRepetidos[s];
			somaRant[s] /= contRepetidos[s];

			somaSnf[s] /= contRepetidos[s];
			somaSolids[s] /= contRepetidos[s];
			somaTotpro[s] /= contRepetidos[s];
			somaTrupro[s] /= contRepetidos[s];
			somaUrea[s] /= contRepetidos[s];


		}

		for (int a = 0; a < laudo.size(); a++) {
			for (int b = 0; b < laudo.size(); b++) {

				if (laudo.get(a).getSequence() == laudo.get(b).getSequence() && a > 1) {

					la.setBatchId(laudo.get(a).getBatchId());
					la.setCasein(String.valueOf(somaCasein[a - 1]));
					la.setCbt(String.valueOf(somaCbt[a - 1]));
					la.setCcs(String.valueOf(somaCcs[a - 1]));
					la.setCel(String.valueOf(somaCel[a - 1]));
					la.setCmt(String.valueOf(somaCmt[a - 1]));
					la.setDate(laudo.get(a).getDate());
					la.setDen(String.valueOf(somaDen[a - 1]));
					la.setFat(String.valueOf(somaFat[a - 1]));
					la.setFpd(String.valueOf(somaFpd[a - 1]));
					la.setPh(String.valueOf(somaPh[a - 1]));
					la.setRant(String.valueOf(somaRant[a - 1]));
					la.setSampleid(laudo.get(a).getSampleid());
					la.setSequence(laudo.get(a).getSequence());
					la.setSnf(String.valueOf(somaSnf[a - 1]));
					la.setSolids(String.valueOf(somaSolids[a - 1]));
					la.setTotpro(String.valueOf(somaTotpro[a - 1]));
					la.setTrupro(String.valueOf(somaTrupro[a - 1]));
					la.setUrea(String.valueOf(somaUrea[a - 1]));

				} else {

					la.setBatchId(laudo.get(a).getBatchId());
					la.setCasein(String.valueOf(somaCasein[a]));
					la.setCbt(String.valueOf(somaCbt[a]));
					la.setCcs(String.valueOf(somaCcs[a]));
					la.setCel(String.valueOf(somaCel[a]));
					la.setCmt(String.valueOf(somaCmt[a]));
					la.setDate(laudo.get(a).getDate());
					la.setDen(String.valueOf(somaDen[a]));
					la.setFat(String.valueOf(somaFat[a]));
					la.setFpd(String.valueOf(somaFpd[a]));
					la.setPh(String.valueOf(somaPh[a]));
					la.setRant(String.valueOf(somaRant[a]));
					la.setSampleid(laudo.get(a).getSampleid());
					la.setSequence(laudo.get(a).getSequence());
					la.setSnf(String.valueOf(somaSnf[a]));
					la.setSolids(String.valueOf(somaSolids[a]));
					la.setTotpro(String.valueOf(somaTotpro[a]));
					la.setTrupro(String.valueOf(somaTrupro[a]));
					la.setUrea(String.valueOf(somaUrea[a]));

				}

				laudoNovo.add(la);
			}

			System.out.println(laudoNovo.get(a));

		}

	}

////	  public static void main( String[ ] args ) {
////	        int[ ] original = { 1 , 8 , 5 , 7 , 3 , 5 , 3 };
////
////	        // remover repetidos
////	        int[ ] unicos = new int[ original.length ];
////	        int qtd = 0;
////	        for( int i = 0 ; i < original.length ; i++ ) {
////	            boolean existe = false;
////	            for( int j = 0 ; j < qtd ; j++ ) {
////	                if( unicos[ j ] == original[ i ] ) {
////	                    existe = true;
////	                    break;
////	                }
////	            }
////	            if( !existe ) {
////	                unicos[ qtd++ ] = original[ i ];
////	            }
////	        }
////
////	        // ajuste do tamanho do vetor resultante
////	        unicos = Arrays.copyOf( unicos , qtd );
////
////	        // imprime resultado
////	        for( int i = 0 ; i < unicos.length ; i++ ) {
////	            System.out.println( "" + i + " = " + unicos[ i ] );
////	        }
////
////	    }

}