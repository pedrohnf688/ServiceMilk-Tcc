package com.pa2.milk.api.controller;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pa2.milk.api.helper.Response;
import com.pa2.milk.api.model.Amostra;
import com.pa2.milk.api.model.Analise;
import com.pa2.milk.api.model.Fazenda;
import com.pa2.milk.api.model.OrdemServico;
import com.pa2.milk.api.model.Solicitacao;
import com.pa2.milk.api.model.dto.SolicitacaoDetalhesDto;
import com.pa2.milk.api.model.dto.SolicitacaoDto;
import com.pa2.milk.api.model.dto.SolicitacaoGetDto;
import com.pa2.milk.api.model.dto.StatusSolicitacaoDTO;
import com.pa2.milk.api.model.enums.EnumStatusSolicitacao;
import com.pa2.milk.api.repository.AmostraRepository;
import com.pa2.milk.api.repository.AnaliseRepository;
import com.pa2.milk.api.repository.BolsistaRepository;
import com.pa2.milk.api.repository.OrdemServicoRepository;
import com.pa2.milk.api.service.AmostraService;
import com.pa2.milk.api.service.AnaliseService;
import com.pa2.milk.api.service.BolsistaService;
import com.pa2.milk.api.service.FazendaService;
import com.pa2.milk.api.service.SolicitacaoService;

import javassist.NotFoundException;

@RestController
@RequestMapping(value = "/solicitacao")
@CrossOrigin(origins = "*")
public class SolicitacaoController {

	private static final Logger log = LoggerFactory.getLogger(FazendaController.class);

	@Autowired
	private SolicitacaoService solicitacaoService;

	@Autowired
	private FazendaService fazendaSerice;

	@Autowired
	private AmostraService amostraService;

	@Autowired
	private AnaliseRepository analiseRepository;

	@Autowired
	private AnaliseService analiseService;

	@Autowired
	private AmostraRepository amostraRepository;

	@Autowired
	private OrdemServicoRepository ordemServicoRepository;

	@Autowired
	private BolsistaService bolsistaService;

	@Autowired
	private BolsistaRepository bolsistaRepository;

	@GetMapping
	public List<Solicitacao> listarSolicitacoes() {
		log.info("Listando Solicitações");
		return this.solicitacaoService.listarTodasSolicitacoes();
	}

	@GetMapping(value = "ordemServico/{id}")
	public OrdemServico ordemServicoSolicitacao(@PathVariable("id") Integer id) {
		OrdemServico os = this.ordemServicoRepository.findBySolicitacaoId(id);
		return os;
	}

	@PreAuthorize("hasAnyRole('ADMINISTRADOR','BOLSISTA','CLIENTE')")
	@PostMapping
	public ResponseEntity<Response<Solicitacao>> cadastrarSolicitacao(@RequestBody SolicitacaoDto solicitacaoDTO,
			BindingResult result) throws NoSuchAlgorithmException, NotFoundException {
		log.info("Cadastrando solicitacao: {}", solicitacaoDTO.toString(), "cpfcnj");
		Response<Solicitacao> response = new Response<Solicitacao>();

		Optional<Fazenda> fazenda = fazendaSerice.buscarPorCpfCnpj(solicitacaoDTO.getCpfcnpj());

		if (!fazenda.isPresent()) {
			log.info("Não existe fazenda cadastrada com tais dados: {}", solicitacaoDTO.getCpfcnpj());
			response.getErros().add("Fazenda não encontrada");
			return ResponseEntity.badRequest().body(response);
			// result.addError(new ObjectError("solicitacao", "Fazenda não encontrada."));
		}
		Solicitacao solicitacao = gerarSolicitacao(solicitacaoDTO, fazenda.get(), result);
		if (result.hasErrors()) {
			log.info("Erro no cadastro da Solicitacao: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErros().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		this.solicitacaoService.salvarSolicitacao(solicitacao);

		Calendar hoje = Calendar.getInstance();
		int mes = hoje.get(Calendar.MONTH) + 1;
		int ano = hoje.get(Calendar.YEAR);
		// mes/idSolicitacao/ano
		OrdemServico os = new OrdemServico();
		os.setDataHora(new Date());
		solicitacaoService.salvarSolicitacao(solicitacao);
		os.setOrdem(mes + "/" + "A" + "/" + ano);
		os.setSolicitacao(solicitacao);
		this.ordemServicoRepository.save(os);

		response.setData(solicitacao);

		return ResponseEntity.ok(response);
	}

	private Solicitacao gerarSolicitacao(SolicitacaoDto solicitacaoDTO, Fazenda fazenda, BindingResult result)
			throws NoSuchAlgorithmException {
		List<Analise> analises = solicitacaoDTO.transformarParaListaAnalise();
		List<Amostra> amostras = new ArrayList<Amostra>();

		if (analises.isEmpty()) {
			result.addError(new ObjectError("analises", "Lista de análises vazia"));
		} else {
			for (int i = 0; i < analises.size(); i++) {
				int cont = 1;
				for (int j = 0; j < analises.get(i).getQuantidadeAmostras(); j++) {
					// Cadastrar Amostra

					Amostra a = new Amostra();
					a.setAnalise(analises.get(i));
					a.setDataColeta(new Date());
					a.setFinalizada(false);
					a.setNumeroAmostra(cont);
					amostras.add(a);
					analises.get(i).setAmostras(amostras);
					// this.amostraService.salvar(a);
					cont++;
					
				}

			}
		}

		Solicitacao solicitacao = solicitacaoDTO.transformarParaSolicitacao();
		solicitacao.setFazenda(fazenda);

		solicitacao.setCliente(fazenda.getCliente());
		// solicitacao.setDataCriada(Calendar.getInstance(TimeZone.getTimeZone("GMT-03:00")).getTime());
		solicitacao.setDataCriada(new Date());
		analises.stream().forEach(objAnalise -> solicitacao.addAnalise(objAnalise));

		solicitacao.setStatus(EnumStatusSolicitacao.PENDENTE);
		return solicitacao;
	}

	@PreAuthorize("hasAnyRole('ADMINISTRADOR','BOLSISTA','CLIENTE')")
	@GetMapping(value = "{id}")
	public ResponseEntity<Response<SolicitacaoGetDto>> buscarSolicitacaoPorID(@PathVariable("id") Integer id) {
		log.info("Buscar Solicitacao por Id");

		Response<SolicitacaoGetDto> response = new Response<SolicitacaoGetDto>();

		SolicitacaoGetDto sgt = new SolicitacaoGetDto();
		Optional<Solicitacao> solicitacao = solicitacaoService.buscarSolicitacaoPorId(id);

		if (!solicitacao.isPresent()) {
			log.info("Solicitacao não encontrada: {}", solicitacao.get());
			return ResponseEntity.badRequest().body(response);
		}

		this.converterSparaSdto(solicitacao.get(), sgt);

//		List<Amostra> amostras = new ArrayList<Amostra>();
//
//		for (int i = 0; i < solicitacao.get().getListaAnalise().size(); i++) {
//			for (int j = 0; j < solicitacao.get().getListaAnalise().get(i).getAmostras().size(); j++) {
//				amostras.add(solicitacao.get().getListaAnalise().get(i).getAmostras().get(j));
//			}
//		}
//		

		response.setData(sgt);

		return ResponseEntity.ok(response);
	}

	private void converterSparaSdto(Solicitacao sol, SolicitacaoGetDto sgt) {
		List<Amostra> amostras = new ArrayList<Amostra>();

		sgt.setId(sol.getId());
		sgt.setListaAnalise(sol.getListaAnalise());

		for (int i = 0; i < sol.getListaAnalise().size(); i++) {
			for (int j = 0; j < sol.getListaAnalise().get(i).getAmostras().size(); j++) {
				amostras.add(sol.getListaAnalise().get(i).getAmostras().get(j));
			}
		}
		for (int i = 0; i < sol.getListaAnalise().size(); i++) {
			sgt.getListaAnalise().get(i).setAmostras(amostras);
		}
		sgt.setAmostra(amostras);

		sgt.setCliente(sol.getCliente());
		sgt.setDataCriada(sol.getDataCriada());
		sgt.setFazenda(sol.getFazenda());
		sgt.setListaLaudoMedia(sol.getListaLaudoMedia());
		sgt.setObservacao(sol.getObservacao());
		sgt.setStatus(sol.getStatus());
		sgt.setTemperatura(sol.getTemperatura());
		sgt.setInicioColeta(sol.getInicioColeta());

	}

	@GetMapping(value = "/status/{id}")
	public ResponseEntity<Response<SolicitacaoDetalhesDto>> statusSolicitacao(@PathVariable("id") Integer id) {
		log.info("Status e Observação da Solicitação");

		Response<SolicitacaoDetalhesDto> response = new Response<SolicitacaoDetalhesDto>();

		Optional<Solicitacao> solicitacao = solicitacaoService.buscarSolicitacaoPorId(id);

		if (!solicitacao.isPresent()) {
			log.info("Solicitação não encontrada: {}", solicitacao.get());
			return ResponseEntity.badRequest().body(response);
		}

		SolicitacaoDetalhesDto s = new SolicitacaoDetalhesDto();
		s.setStatus(solicitacao.get().getStatus());
		s.setObservacao(solicitacao.get().getObservacao());
		s.setTemperatura(solicitacao.get().getTemperatura());

		response.setData(s);

		return ResponseEntity.ok(response);
	}

	@PreAuthorize("hasAnyRole('ADMINISTRADOR','BOLSISTA','CLIENTE')")
	@DeleteMapping(value = "{id}")
	public ResponseEntity<Response<Solicitacao>> deletarSolicitacao(@PathVariable("id") Integer id) {
		log.info("Removendo Solicitação: {}", id);

		Response<Solicitacao> response = new Response<Solicitacao>();

		Optional<Solicitacao> solicitacao = this.solicitacaoService.buscarSolicitacaoPorId(id);

		if (!solicitacao.isPresent()) {
			log.info("Solicitacao não encontrada");
			response.getErros().add("Solicitacao não encontrada");
			ResponseEntity.badRequest().body(response);
		}

		List<Analise> analises = this.analiseService.listarAnalisesPorSolicitacaoId(solicitacao.get().getId());

		response.setData(solicitacao.get());

		this.solicitacaoService.deletarSolicitacaoPorId(id);
		this.analiseRepository.deleteAll(analises);
		// this.amostraRepository.deleteAll(amostras);

		return ResponseEntity.ok(response);
	}

	// TODO: Atualizar solicitação

	@PreAuthorize("hasAnyRole('ADMINISTRADOR','BOLSISTA','CLIENTE')")
	@PostMapping(value = "/status")
	public ResponseEntity<Response<Solicitacao>> atualizarStatus(@RequestBody StatusSolicitacaoDTO statusSolicitacaoDTO)
			throws NotFoundException {
		log.info("Atualizando Status de Solicitação: {}", statusSolicitacaoDTO.getSolicitacaoId());

		Response<Solicitacao> response = new Response<Solicitacao>();

		Optional<Solicitacao> solicitacao = solicitacaoService
				.buscarSolicitacaoPorId(statusSolicitacaoDTO.getSolicitacaoId());

		if (!solicitacao.isPresent()) {
			log.info("Solicitacao não encontrada");
			response.getErros().add("Solicitacao não encontrada");
			ResponseEntity.badRequest().body(response);
		}

		solicitacao.get().setStatus(statusSolicitacaoDTO.getStatus());
		solicitacao.get().setObservacao(statusSolicitacaoDTO.getObservacao() == null ? solicitacao.get().getObservacao()
				: statusSolicitacaoDTO.getObservacao());
		solicitacao.get().setTemperatura(statusSolicitacaoDTO.getTemperatura() == 0 ? solicitacao.get().getTemperatura()
				: statusSolicitacaoDTO.getTemperatura());

		Optional<OrdemServico> os = this.ordemServicoRepository.findById(solicitacao.get().getId());

		os.get().setEmissaoLaudo(statusSolicitacaoDTO.getEmissaoLaudo() == null ? os.get().getEmissaoLaudo() : statusSolicitacaoDTO.getEmissaoLaudo());
		os.get().setAnaliseLaboratorial(statusSolicitacaoDTO.getAnaliseLaboratorial() == null ? os.get().getAnaliseLaboratorial() : statusSolicitacaoDTO.getAnaliseLaboratorial());
		os.get().setEntregaAmostras(statusSolicitacaoDTO.getEntregaAmostras() == null ? os.get().getEntregaAmostras() : statusSolicitacaoDTO.getEntregaAmostras());

		
		
		os.get().setValorPreco(statusSolicitacaoDTO.getValorPreco() == 0 ? os.get().getValorPreco()
				: statusSolicitacaoDTO.getValorPreco());

		if (os.get().getOrdem().contains("A")) {
			os.get().setOrdem(os.get().getOrdem().replace("A", String.valueOf(solicitacao.get().getId())));
		}

		
		os.get().setDataAnalise(statusSolicitacaoDTO.getDataAnalise() == null ? os.get().getDataAnalise()
				: statusSolicitacaoDTO.getDataAnalise());
		os.get().setDataProcessamento(
				statusSolicitacaoDTO.getDataProcessamento() == null ? os.get().getDataProcessamento()
						: statusSolicitacaoDTO.getDataProcessamento());
		os.get().setDataRecebimento(statusSolicitacaoDTO.getDataRecebimento() == null ? os.get().getDataRecebimento()
				: statusSolicitacaoDTO.getDataRecebimento());
		os.get().setAmostrasNaoAnalisadas(
				statusSolicitacaoDTO.getAmostrasNaoAnalisadas() == 0 ? os.get().getAmostrasNaoAnalisadas()
						: statusSolicitacaoDTO.getAmostrasNaoAnalisadas());
		os.get().setAmostrasRecebidas(
				(statusSolicitacaoDTO.getAmostrasRecebidas() == 0 ? os.get().getAmostrasRecebidas()
						: statusSolicitacaoDTO.getAmostrasRecebidas()));

		int soma = 0;
		int coletadas = 0;

		for (int j = 0; j < solicitacao.get().getListaAnalise().size(); j++) {
			Optional<Analise> analise = this.analiseRepository
					.findById(solicitacao.get().getListaAnalise().get(j).getId());
			soma += analise.get().getQuantidadeAmostras();
			coletadas += this.amostraService.amostrasColetas(analise.get().getId()).size();
		}

		os.get().setAmostrasNaoColetadas(soma - coletadas);

		os.get().setSolicitacao(solicitacao.get());
		this.ordemServicoRepository.save(os.get());

		solicitacaoService.salvarSolicitacao(solicitacao.get());

		response.setData(solicitacao.get());

		return ResponseEntity.ok(response);
	}

//	@PutMapping(value = "{id}")
//	public ResponseEntity<Response<SolicitacaoDto>> atualizarSolicitacao(@PathVariable("id") Integer id,
//			@Valid @RequestBody SolicitacaoDto solicitacaoDto, BindingResult result) throws NoSuchAlgorithmException {
//
//		log.info("Atualizando o Solicitacao:{}", solicitacaoDto.toString());
//
//		Response<SolicitacaoDto> response = new Response<SolicitacaoDto>();
//
//		Optional<Solicitacao> solicitacao = this.solicitacaoService.buscarSolicitacaoPorId(id);
//
//		if (!solicitacao.isPresent()) {
//			log.info("Solicitacao não encontrada");
//			response.getErros().add("Solicitacao não encontrada");
//			ResponseEntity.badRequest().body(response);
//		}
//
//		this.atualizarDadosSolicitacao(solicitacao.get(), solicitacaoDto, result);
//
//		return ResponseEntity.ok(response);
//
//	}
//
//	private void atualizarDadosSolicitacao(Solicitacao solicitacao, SolicitacaoDto solicitacaoDto, BindingResult result)
//			throws NoSuchAlgorithmException {
//
//	}

}
