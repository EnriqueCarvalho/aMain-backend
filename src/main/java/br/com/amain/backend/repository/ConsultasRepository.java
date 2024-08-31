package br.com.amain.backend.repository;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.amain.backend.model.Consultas;
import br.com.amain.backend.model.Paciente;
import br.com.amain.backend.model.Psicologo;

public interface  ConsultasRepository extends JpaRepository<Consultas, Long>{
    
    @Query("select c from Consultas c where c.paciente = :paciente")
    public List<Consultas> findByPaciente(Paciente paciente);

    @Query("select c from Consultas c where c.psicologo = :psicologo")
    public List<Consultas> findByPsicologo(Psicologo psicologo);

    public Consultas findByDataAndHoraAndPsicologo(Date data, Time time, Psicologo psicologo);
}
