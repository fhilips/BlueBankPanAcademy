package br.com.pan.bluebank.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "tb_clientes")
public class Cliente implements Serializable {

 static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_cliente")
	@ApiModelProperty(value = "Código do cliente")
	private Long id;
	
	@NotBlank(message = "Nome é um atributo obrigatório")
	@Pattern(regexp = "^\\p{L}+[\\p{L}\\p{Z}\\p{P}]{0,}", message = "Nome inválido")
	@Column(name = "nome")
	@ApiModelProperty(value = "Nome do cliente")
	private String nome;
	
	@NotBlank(message = "CPF é um atributo obrigatório")
	@CPF
	@Column(name = "cpf", unique = true)
	@ApiModelProperty(value = "CPF do cliente")
	private String cpf;
	
	@NotNull(message = "Data de Nascimento é um atributo obrigatório")
	@Past(message = "Data de nascimento inválida")
	@Column(name = "dataNascimento")
	@ApiModelProperty(value = "Data de nascimento do cliente", dataType = "java.sql.LocalDate")
    @JsonFormat(pattern="dd-MM-yyyy")
	private LocalDate dataNascimento;
	
	@NotBlank(message = "E-mail é um atributo obrigatório")
	@Email
	@Column(name = "email", unique = true)
	@ApiModelProperty(value = "Email do cliente")
	private String email;
	
	@NotBlank(message = "Telefone é um atributo obrigatório") 
	@Column(name = "telefone", unique = true)
	@ApiModelProperty(value = "Telefone do cliente")
	private String telefone;
	
	@NotNull(message = "Endereço é um atributo obrigatório")
	@OneToOne()
	@JoinColumn(name = "id_endereco")
	@ApiModelProperty(value = "Endereço do cliente")
	private Endereco endereco;
	
	public Cliente() {		
	}
	
	public Cliente(
			@NotBlank(message = "Nome é um atributo obrigatório") @Pattern(regexp = "^\\p{L}+[\\p{L}\\p{Z}\\p{P}]{0,}", message = "Nome inválido") String nome,
			@NotBlank(message = "CPF é um atributo obrigatório") @CPF String cpf,
			@NotBlank(message = "Data de Nascimento é um atributo obrigatório") @Size(min = 10, max = 10) @Past(message = "Data de nascimento inválida") LocalDate dataNascimento,
			@NotBlank(message = "E-mail é um atributo obrigatório") @Email String email,
			@NotBlank(message = "Telefone é um atributo obrigatório") String telefone,
			@NotBlank(message = "Endereço é um atributo obrigatório") Endereco endereco) {
		this.nome = nome;
		this.cpf = cpf;
		this.dataNascimento = dataNascimento;
		this.email = email;
		this.telefone = telefone;
		this.endereco = endereco;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Long getId() {
		return id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		return Objects.equals(id, other.id);
	}
}
