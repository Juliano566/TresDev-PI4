package br.com.TresDevsPI4.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity

public class ItensCompra implements Serializable {

	@Override
	public String toString() {
		return "ItensCompra [id=" + id + ", produto=" + produto + ", compra=" + compra + ", quantidade=" + quantidade
				+ ", valorUnitario=" + valorUnitario + ", valorTotal=" + valorTotal + ", getValorTotal()="
				+ getValorTotal() + ", getId()=" + getId() + ", getProduto()=" + getProduto() + ", getCompra()="
				+ getCompra() + ", getQuantidade()=" + getQuantidade() + ", getValorUnitario()=" + getValorUnitario()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}

	public ItensCompra() {
		super();
	}

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	private Produto produto;

	@ManyToOne
	private Compra compra;

	private Integer quantidade;

	private Double valorUnitario = 0.;

	private Double valorTotal = 0.;

	private Integer id_compra;

	@JoinColumn(name = "id", nullable = false)
	private Produto produto2;

	public Double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Compra getCompra() {
		return compra;
	}

	public void setCompra(Compra compra) {
		this.compra = compra;
	}

	public Integer getQuantidade() {
		if (quantidade == null) {
			quantidade = 0;
		}
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Double getValorUnitario() {
		return valorUnitario;
	}

	public void setValorUnitario(Double valorUnitario) {
		this.valorUnitario = valorUnitario;
	}

	public Integer getId_compra() {
		return id_compra;
	}

	public void setId_compra(Integer id_compra) {
		this.id_compra = id_compra;
	}

}
