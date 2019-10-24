package com.pa2.milk.api.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.pa2.milk.api.helper.FileStorageException;
import com.pa2.milk.api.helper.MyFileNotFoundException;
import com.pa2.milk.api.model.Arquivo;
import com.pa2.milk.api.repository.ArquivoRepository;

@Service
public class ArquivoService {

	@Autowired
	private ArquivoRepository arquivoRepository;

	public Arquivo storeFile(MultipartFile file) {

		String fileName = StringUtils.cleanPath(file.getOriginalFilename());

		try {

			if (fileName.contains("..")) {
				throw new FileStorageException(
						"Desculpa! Nome do arquivo contém sequência de caminho inválida " + fileName);
			}

			Arquivo arquivo = new Arquivo(fileName, file.getContentType(), file.getBytes(), null, 0, null, null, null,
					null);

			return arquivoRepository.save(arquivo);
		} catch (IOException ex) {
			throw new FileStorageException(
					"Não foi possível armazenar o arquivo " + fileName + ". Por favor, tente novamente!", ex);
		}
	}

	public Arquivo getFile(String fileId) {
		return arquivoRepository.findById(fileId)
				.orElseThrow(() -> new MyFileNotFoundException("Arquivo não encontrado com o id " + fileId));
	}

	public Optional<Arquivo> buscarCliente(Integer clienteId) {
		return Optional.ofNullable(this.arquivoRepository.findByFotoPerfilId(clienteId));
	}

	public List<Arquivo> buscarListarArquivos(Integer clienteId) {
		return this.arquivoRepository.findAllByFotoPerfilId(clienteId);
	}

	public Optional<Arquivo> buscarSolicitacao(Integer clienteId) {
		return Optional.ofNullable(this.arquivoRepository.findByFotoSolicitacaoId(clienteId));
	}

	public List<Arquivo> buscarListarArquivosSolicitacao(Integer clienteId) {
		return this.arquivoRepository.findAllByFotoSolicitacaoId(clienteId);
	}

	public Optional<Arquivo> buscarComprovanteSolicitacao(Integer clienteId) {
		return Optional.ofNullable(this.arquivoRepository.findByComprovanteSolicitacaoId(clienteId));
	}

	public List<Arquivo> buscarListarArquivosComprovanteSolicitacao(Integer clienteId) {
		return this.arquivoRepository.findAllByComprovanteSolicitacaoId(clienteId);
	}

	public Optional<Arquivo> buscarFazenda(Integer clienteId) {
		return Optional.ofNullable(this.arquivoRepository.findByFotoFazendaId(clienteId));
	}

	public List<Arquivo> buscarListarArquivosFazendas(Integer clienteId) {
		return this.arquivoRepository.findAllByFotoFazendaId(clienteId);
	}

}
