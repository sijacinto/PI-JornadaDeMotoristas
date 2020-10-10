package model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Query;
import javax.persistence.Table;
import javax.swing.JOptionPane;

import view.Viagens;

@Entity
@Table(name="viagens")
public class Viagem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;
	private String inicio;
	private String fim;
	private String total;
	private String destino;
	private String carga;
	private String situacao;

	@ManyToOne
	@JoinColumn(name = "motorista", nullable = false, foreignKey = @ForeignKey(name = "fk_motoristas_cpf"))
	private Motorista motorista = new Motorista();
	
	@ManyToOne
	@JoinColumn(name = "veiculo", nullable = false, foreignKey = @ForeignKey(name = "fk_veiculos_placa"))
	private Veiculo veiculo = new Veiculo();
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getInicio() {
		return inicio;
	}
	public void setInicio(String inicio) {
		this.inicio = inicio;
	}
	public String getFim() {
		return fim;
	}
	public void setFim(String fim) {
		this.fim = fim;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getDestino() {
		return destino;
	}
	public void setDestino(String destino) {
		this.destino = destino;
	}
	public void setVeiculo(Veiculo veiculo) {
		this.veiculo = veiculo;
	}
	public Veiculo getVeiculo() {
		return veiculo;
	}
	
	public Motorista getMotorista() {
		return motorista;
	}
	public void setMotorista(Motorista motorista) {
		this.motorista = motorista;
	}
	public String getCarga() {
		return carga;
	}
	public void setCarga(String carga) {
		this.carga = carga;
	}
	public String getSituacao() {
		return situacao;
	}
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	
	public void cadastrarViagem(String fim, String destino, String cpfFuncionario, String placaVeiculo, String carga) {

		EntityManager con = new ConnectionFactory().getConnection();
		
		this.setDestino(destino);
		this.setCarga(carga);
		this.setFim(fim);
		
		motorista.setCpf(cpfFuncionario);
		this.setMotorista(motorista);
		
		veiculo.setPlaca(placaVeiculo);
		this.setVeiculo(veiculo);
		
		this.setInicio("N�o iniciado");
		this.setSituacao("N�o iniciado");
		this.setTotal("00:00:00");
		
		try {
			con.getTransaction().begin();
			con.persist(this);
			con.getTransaction().commit();
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Ocorreu um problema ao cadastrar a viagem. Tente novamente.\nErro: "+ e, "Erro", JOptionPane.ERROR_MESSAGE);
			con.getTransaction().rollback();
		}
		finally {
			con.close();
		}
		
	}
	

	public void alterarDadosViagem(String novoPrazo, String novoDestino, String novoCpfFuncionario, String novoVeiculo, String novaCarga, int idViagem) {

		EntityManager con = new ConnectionFactory().getConnection();
		
		this.setDestino(novoDestino);
		this.setCarga(novaCarga);
		this.setFim(novoPrazo);
		
		motorista.setCpf(novoCpfFuncionario);
		this.setMotorista(motorista);
		
		veiculo.setPlaca(novoVeiculo);
		this.setVeiculo(veiculo);
		
		try {
			con.getTransaction().begin();
			
			
			Query query = con.createQuery("update Viagem set fim = :novoPrazo, carga = :novaCarga, destino = :novoDestino, motorista = :novoFuncionario, veiculo = :novoVeiculo where id = :idViagem");
			query.setParameter("novoPrazo", novoPrazo);
			query.setParameter("novaCarga", novaCarga);
			query.setParameter("novoDestino", novoDestino);
			query.setParameter("novoFuncionario", this.getMotorista());
			query.setParameter("novoVeiculo", this.getVeiculo());
			query.setParameter("idViagem", idViagem);
			query.executeUpdate();
			
			con.getTransaction().commit();
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao atualizar os dados da viagem: "+ e, "Erro", JOptionPane.ERROR_MESSAGE);
			System.err.println(e);
			con.getTransaction().rollback();
		}
		finally {
			con.close();
		}
		
	}
	
	public void excluirViagem(Integer id) {
		EntityManager con = new ConnectionFactory().getConnection();
		
		Viagem viagem = null;
		
		try {
			viagem = con.find(model.Viagem.class, id);		
			
			con.getTransaction().begin();
			con.remove(viagem);
			con.getTransaction().commit();
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro: "+ e, "Erro", JOptionPane.ERROR_MESSAGE);
			con.getTransaction().rollback();
		}
		finally {
			con.close();
		}
		
	}
	
	public List<Viagem> consultarTodasViagens(){
		EntityManager con = new ConnectionFactory().getConnection();
		List<Viagem> viagens = null;
		try {
			viagens = con.createQuery("from Viagem v").getResultList();
		}
		catch (Exception e) {
			System.err.println(e);
		}
		finally {
			con.close();
		}

		return viagens;
	}
	
	public List<Viagens> listarViagens(){	
		List<Viagens> lista = new ArrayList<>();
		
		for (Viagem v: this.consultarTodasViagens()) {
			Viagens viagens = new Viagens(v.getId(), v.getDestino(), v.getMotorista().getNome());
		
			
			lista.add(viagens);
		}
		
		return lista;
		
	}
	
	public Viagem encontrarViagem(int id){
		
		EntityManager con = new ConnectionFactory().getConnection();

		Viagem viagem = null;

		try {
			viagem = con.find(model.Viagem.class, id);
		}
		catch (Exception e) {
			System.err.println(e);
			con.getTransaction().rollback();
		}
		finally {
			con.close();
		}

		return viagem;
	}
	

}
