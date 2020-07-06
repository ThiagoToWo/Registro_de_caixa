import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.DefaultListModel;

public class Caderno implements Serializable {
	private ArrayList<Venda> listaVendas = new ArrayList<Venda>();
	private NumberFormat fmt = NumberFormat.getCurrencyInstance();
	
	protected ArrayList<Venda> getListaVendas() {
		return listaVendas;
	}	

	protected void setListaVendas(ArrayList<Venda> listaVendas) {
		this.listaVendas = listaVendas;
	}

	public Caderno carregar(File arquivo) {
		Caderno agenda = null;
		try {
			ObjectInputStream oi = new ObjectInputStream(new FileInputStream(arquivo));
			agenda = (Caderno) oi.readObject();	
			oi.close();
		} catch (IOException e1) {
			System.out.println("Ocorreu um erro! " + e1.getMessage());
		} catch (ClassNotFoundException e1) {
			System.out.println("Ocorreu um erro! " + e1.getMessage());
		}		
		return agenda;
	}

	public void salvarObject(File selectedFile) {
		try {
			ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(selectedFile));
			os.writeObject(this);
			os.close();
		} catch (IOException e) {
			System.out.println("Ocorreu um erro! " + e.getMessage());
		}		
	}

	public void salvarTxt(File selectedFile) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(selectedFile));
			bw.write("RELATÓRIO DIÁRIO\n\n");
			for (Venda v : listaVendas) {
				if (v instanceof VendaDinheiro) {
					bw.write("$ " + v + "\n\n");
				}				
			}
			
			for (Venda v : listaVendas) {
				if (v instanceof VendaCheque) {
					bw.write("# " + v + "\n\n");
				}				
			}
			
			for (Venda v : listaVendas) {
				if (v instanceof VendaDebito) {
					bw.write("@ " + v + "\n\n");
				}				
			}
			
			for (Venda v : listaVendas) {
				if (v instanceof VendaCreditoAVista) {
					bw.write("& " + v + "\n\n");
				}				
			}
			
			for (Venda v : listaVendas) {
				if (v instanceof VendaCreditoParcelado) {
					bw.write("* " + v + "\n\n");
				}				
			}			
			
			bw.write("\n$ TOTAL EM DINHEIRO: " + fmt.format(getSomaDinheiro()) + "\n");
			bw.write("# TOTAL EM CHEQUE: " + fmt.format(getSomaCheque()) + "\n");
			bw.write("@ TOTAL EM DÉBITO: " + fmt.format(getSomaDebito()) + "\n");
			bw.write("& TOTAL EM CRÉDITO À VISTA: " + fmt.format(getSomaCreditoAVista()) + "\n");
			bw.write("* TOTAL EM CRÉDITO PARCELADO: " + fmt.format(getSomaCreditoParcelado()) + "\n\n");
			double totalDoDia = getSomaDinheiro() + getSomaCheque() + getSomaDebito() + getSomaCreditoAVista() +
					getSomaCreditoParcelado();
			bw.write("= ENTRADA TOTAL DO DIA: " + fmt.format(totalDoDia) + "\n");		
			bw.close();			
		} catch (IOException e2) {
			e2.printStackTrace();
		}		
	}
	
	public void excluirDaListaSelecionavel(int[] indices, DefaultListModel<Venda> listModel) {		
		ArrayList<Venda> listaRemovidos = new ArrayList<Venda>();
		for (int i : indices) {
			listaRemovidos.add(listaVendas.get(i));
		}
		listaVendas.removeAll(listaRemovidos);
		listarNaListaSelecionavel(listModel);
	}
	
	public void listarNaListaSelecionavel(DefaultListModel<Venda> listModel) {	
		listModel.removeAllElements();
		
		for (Venda v : listaVendas) {
			listModel.addElement(v);;
		}		
	}
	
	private double getSomaDinheiro() {
		double total = 0;
		for (Venda v : listaVendas) {
			if (v instanceof VendaDinheiro) {
				total += v.getValor();
			}
		}
		return total;
	}
	
	private double getSomaCheque() {
		double total = 0;
		for (Venda v : listaVendas) {
			if (v instanceof VendaCheque) {
				total += v.getValor();
			}
		}
		return total;
	}
	
	private double getSomaDebito() {
		double total = 0;
		for (Venda v : listaVendas) {
			if (v instanceof VendaDebito) {
				total += v.getValor();
			}
		}
		return total;
	}
	
	private double getSomaCreditoAVista() {
		double total = 0;
		for (Venda v : listaVendas) {
			if (v instanceof VendaCreditoAVista) {
				total += v.getValor();
			}
		}
		return total;
	}
	
	private double getSomaCreditoParcelado() {
		double total = 0;
		for (Venda v : listaVendas) {
			if (v instanceof VendaCreditoParcelado) {
				total += v.getValor();
			}
		}
		return total;
	}
}
