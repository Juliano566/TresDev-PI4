package br.com.TresDevsPI4.model;

import java.io.Serializable;

public class imagem implements Serializable{

	private int id;
	
	private String caminho;
	
	private int idProduto;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCaminho() {
		return caminho;
	}

	public void setCaminho(String caminho) {
		this.caminho = caminho;
	}

	public int getIdProduto() {
		return idProduto;
	}

	public void setIdProduto(int idProduto) {
		this.idProduto = idProduto;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((caminho == null) ? 0 : caminho.hashCode());
		result = prime * result + id;
		result = prime * result + idProduto;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		imagem other = (imagem) obj;
		if (caminho == null) {
			if (other.caminho != null)
				return false;
		} else if (!caminho.equals(other.caminho))
			return false;
		if (id != other.id)
			return false;
		if (idProduto != other.idProduto)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "imagem [id=" + id + ", caminho=" + caminho + ", idProduto=" + idProduto + "]";
	}
	
	
}
