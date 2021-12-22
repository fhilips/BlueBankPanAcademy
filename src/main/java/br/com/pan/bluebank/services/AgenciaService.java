package br.com.pan.bluebank.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.pan.bluebank.dtos.AgenciaDTO;
import br.com.pan.bluebank.dtos.response.AgenciaResponseDTO;
import br.com.pan.bluebank.mappers.AgenciaMapper;
import br.com.pan.bluebank.models.Agencia;
import br.com.pan.bluebank.models.Gerente;
import br.com.pan.bluebank.repositories.AgenciaRepository;
import br.com.pan.bluebank.services.exceptions.ResourceNotFoundException;

@Service
public class AgenciaService {

	@Autowired
	private AgenciaRepository agenciaRepository;
	
	@Autowired
	private GerenteService gerenteService;
	
	public Agencia create(AgenciaDTO agenciaDTO) {
		Agencia createAgencia = verifyGerente(agenciaDTO);
		return agenciaRepository.save(createAgencia);	
	}
	
	public Agencia update(Long id, AgenciaDTO agenciaDTO) {
		Agencia updateAgencia = findById(id);
		updateAgencia = verifyGerente(agenciaDTO, updateAgencia);	
		return agenciaRepository.save(updateAgencia);
	}
	
	public Agencia findById(Long id) {	
		return agenciaRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Agencia não encontrada!"));
	}
	
	public AgenciaResponseDTO findByIdResponse(Long id) {
		return AgenciaMapper.toDTO(findById(id));
	}
	
	public List<AgenciaResponseDTO> findAll() {
		List<Agencia> listaAgencia = agenciaRepository.findAll();
		return listaAgencia.stream()
				.map(agencia -> AgenciaMapper.toDTO(agencia))
				.collect(Collectors.toList());
	}	
		
	public void delete(Long id) {
		Agencia agencia = findById(id);
		agenciaRepository.delete(agencia);	
	}
	
	private Agencia verifyGerente(AgenciaDTO agenciaDTO, Agencia agencia) {
		if (agenciaDTO.getIdGerente() != null) {
			Gerente gerente = gerenteService.findById(agenciaDTO.getIdGerente());
			agencia.setNumeroAgencia(agenciaDTO.getNumeroAgencia());
			agencia.setNomeAgencia(agenciaDTO.getNomeAgencia());
			agencia.setGerente(gerente);
			return agencia;
		} else {
			agencia.setNumeroAgencia(agenciaDTO.getNumeroAgencia());
			agencia.setNomeAgencia(agenciaDTO.getNomeAgencia());
			return agencia;
		}
	}
	
	private Agencia verifyGerente(AgenciaDTO agenciaDTO) {
		if (agenciaDTO.getIdGerente() != null) {
			Gerente gerente = gerenteService.findById(agenciaDTO.getIdGerente());			
			return new Agencia(agenciaDTO.getNumeroAgencia(), agenciaDTO.getNomeAgencia(), gerente);
		} else {			
			return new Agencia(agenciaDTO.getNumeroAgencia(), agenciaDTO.getNomeAgencia());
		}
	}
}
