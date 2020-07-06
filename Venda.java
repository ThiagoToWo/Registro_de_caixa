import java.util.Calendar;
import java.util.Date;
import java.io.Serializable;
import java.text.NumberFormat;

public class Venda implements Serializable {
	private String colegio;
	private double valor;
	private String rotulo;
	private String formaDePagamento;
	private Calendar data;
	private NumberFormat fmt = NumberFormat.getCurrencyInstance();
	
	public Venda(String colegio, double valor, String descrição, String formaDePagamento) {
		this.colegio = colegio;
		this.valor = valor;
		this.rotulo = descrição;
		this.formaDePagamento = formaDePagamento;
		this.data = data.getInstance();
	}
	

	public String getColegio() {
		return colegio;
	}
	
	protected double getValor() {
		return valor;
	}

	protected String getRotulo() {
		return rotulo;
	}

	protected String getFormaDePagamento() {
		return formaDePagamento;
	}

	protected String getData() {
		return data.get(Calendar.DAY_OF_MONTH) + "/" + (data.get(Calendar.MONTH) + 1) + "/" + data.get(Calendar.YEAR);
	}
	
	public String toString() {
		return getFormaDePagamento() + ": " + fmt.format(getValor()) + "  " + getColegio() + " - "
				+ getRotulo() + " [" + getData() + "]";
	}

}
