package br.com.pan.bluebank.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.pan.bluebank.models.Agencia;

public interface AgenciaRepository extends JpaRepository<Agencia, Long>{
    
}
