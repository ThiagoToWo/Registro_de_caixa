
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.text.NumberFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

// classe responsável pela janela do aplicativo
public class Caixa extends JFrame implements Serializable {	
	
	private Caderno caderno ;
	private DefaultListModel<Venda> listModel;	
	private JList<Venda> areaDeTexto;	
	private JScrollPane barraDeRolagem;
	private String autor = "Autor: Thiago de Oliveira Alves\ntowo497@gmail.com";
	private String versao = "Versão: 1.0 \n 05-07-2020\n\n";	
	NumberFormat fmt = NumberFormat.getCurrencyInstance();
	Font fonteTxt = new Font(getName(), Font.CENTER_BASELINE, 16);
	Font fonteRotBot = new Font(getName(), Font.CENTER_BASELINE, 18);
	Pattern pat;
	Matcher mat;
	
	// constrói a janela do aplicativo
	public void construir() {
		super.setTitle("Caixa");
		
		caderno = new Caderno();
		// cria e configura barra de menu
		JMenuBar barraDeMenu = new JMenuBar();
		JMenu menuSobre = new JMenu("Informações");
		JMenuItem autoria = new JMenuItem("Autor");
		autoria.addActionListener(new AutorListener());
		JMenuItem versao = new JMenuItem("Sobre o aplicativo");
		versao.addActionListener(new VersaoListener());
		menuSobre.add(autoria);
		menuSobre.add(versao);
		barraDeMenu.add(menuSobre);
		setJMenuBar(barraDeMenu);	
		
		JPanel painelNorte = new JPanel();
		painelNorte.setLayout(new BoxLayout(painelNorte, BoxLayout.X_AXIS));
		painelNorte.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
		JLabel rotColegio = new JLabel("Colégio ");
		rotColegio.setFont(fonteRotBot);
		JTextField txtColegio = new JTextField(10);
		txtColegio.setFont(fonteTxt);
		JLabel rotValor = new JLabel(" Valor ");
		rotValor.setFont(fonteRotBot);
		JTextField txtValor = new JTextField(5);
		txtValor.setFont(fonteTxt);
		JLabel rotRotulo = new JLabel(" Descrição ");
		rotRotulo.setFont(fonteRotBot);
		JTextField txtRotulo = new JTextField(45);
		txtRotulo.setFont(fonteTxt);
		JRadioButton botDinheiro = new JRadioButton("Dinheiro", true);
		botDinheiro.setFont(fonteRotBot);
		JRadioButton botCheque = new JRadioButton("Cheque");
		botCheque.setFont(fonteRotBot);
		JRadioButton botDebito = new JRadioButton("Débito");
		botDebito.setFont(fonteRotBot);
		JRadioButton botCreditoAVista = new JRadioButton("Crédito à vista");
		botCreditoAVista.setFont(fonteRotBot);
		JRadioButton botCreditoParcelado = new JRadioButton("Crédito parcelado");
		botCreditoParcelado.setFont(fonteRotBot);
		
		ButtonGroup bg = new ButtonGroup();
		bg.add(botDinheiro);
		bg.add(botCheque);
		bg.add(botDebito);
		bg.add(botCreditoAVista);
		bg.add(botCreditoParcelado);
		painelNorte.add(rotColegio);
		painelNorte.add(txtColegio);
		painelNorte.add(rotValor);
		painelNorte.add(txtValor);
		painelNorte.add(rotRotulo);
		painelNorte.add(txtRotulo);
		painelNorte.add(botDinheiro);
		painelNorte.add(botCheque);
		painelNorte.add(botDebito);
		painelNorte.add(botCreditoAVista);
		painelNorte.add(botCreditoParcelado);
		getContentPane().add(BorderLayout.NORTH, painelNorte);
		
		JPanel background = new JPanel();
		listModel = new DefaultListModel<Venda>();
		// cria um JList de tarefas com a listModel
		areaDeTexto = new JList<Venda>(listModel);
		// configura a apresentação da JList
		areaDeTexto.setVisibleRowCount(50);
		areaDeTexto.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		areaDeTexto.setFont(new Font(null, Font.BOLD, 16));	
		barraDeRolagem = new JScrollPane(areaDeTexto);
		background.add(barraDeRolagem);
		background.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));		
		background.setLayout(new BoxLayout(background, BoxLayout.Y_AXIS));
		
		//adiciona os componentes no frame		
		add(background);
		
		JPanel painelSul = new JPanel();		
		painelSul.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
		JButton botIncluir = new JButton("Incluir");
		botIncluir.setFont(fonteRotBot);
		botIncluir.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {				
				pat = Pattern.compile("\\d+[.]\\d{2}");
				mat = pat.matcher(txtValor.getText());
				if (!mat.find()) {
					JOptionPane.showMessageDialog(null,
							"Soluções: (1) Separe os centavos usando '.' (ponto) ao invés de ',' (vírgula).\n"
									+ "(2) É obrigatório colocar, no mínimo, dois dígitos decimais.\n" +
									"Exemplo: 1234.56",	"Erro na entrada do preço médio", JOptionPane.ERROR_MESSAGE);
				} else {
					if (botDinheiro.isSelected()) {
						double valor = Double.parseDouble( txtValor.getText());
						Venda vendaDinheiro = new VendaDinheiro(txtColegio.getText(), valor, txtRotulo.getText(), "DINHEIRO");
						caderno.getListaVendas().add(vendaDinheiro);
						listModel.addElement(vendaDinheiro);
						
						txtColegio.setText("");
						txtValor.setText("");
						txtRotulo.setText("");									
						txtColegio.requestFocus();
					} else if (botCheque.isSelected()) {
						double valor = Double.parseDouble(txtValor.getText());
						Venda vendaCheque = new VendaCheque(txtColegio.getText(), valor, txtRotulo.getText(), "CHEQUE");
						caderno.getListaVendas().add(vendaCheque);
						listModel.addElement(vendaCheque);

						txtColegio.setText("");
						txtValor.setText("");
						txtRotulo.setText("");									
						txtColegio.requestFocus();
					} else if (botDebito.isSelected()) {
						double valor = Double.parseDouble( txtValor.getText());
						Venda vendaDebito = new VendaDebito(txtColegio.getText(), valor, txtRotulo.getText(), "DÉBITO");
						caderno.getListaVendas().add(vendaDebito);
						listModel.addElement(vendaDebito);						
						
						txtColegio.setText("");
						txtValor.setText("");
						txtRotulo.setText("");									
						txtColegio.requestFocus();
					} else if (botCreditoAVista.isSelected()) {
						double valor = Double.parseDouble( txtValor.getText());
						Venda vendaCreditoAVista = new VendaCreditoAVista(txtColegio.getText(), valor, txtRotulo.getText(), "CRÉDITO À VISTA");
						caderno.getListaVendas().add(vendaCreditoAVista);
						listModel.addElement(vendaCreditoAVista);
						
						txtColegio.setText("");
						txtValor.setText("");
						txtRotulo.setText("");									
						txtColegio.requestFocus();
					} else if (botCreditoParcelado.isSelected()) {
						double valor = Double.parseDouble( txtValor.getText());
						Venda vendaCreditoParcelado = new VendaCreditoParcelado(txtColegio.getText(), valor, txtRotulo.getText(), "CRÉDITO PARCELADO");
						caderno.getListaVendas().add(vendaCreditoParcelado);
						listModel.addElement(vendaCreditoParcelado);
						
						txtColegio.setText("");
						txtValor.setText("");
						txtRotulo.setText("");									
						txtColegio.requestFocus();
					}
				}			
			}
		});
		JButton botDeletar = new JButton("Deletar");
		botDeletar.setFont(fonteRotBot);
		botDeletar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				caderno.excluirDaListaSelecionavel(areaDeTexto.getSelectedIndices(), listModel);				
			}
		});
		JButton botSalvar = new JButton("Salvar");
		botSalvar.setFont(fonteRotBot);
		botSalvar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser save = new JFileChooser();
				save.showSaveDialog(getParent());			
				caderno.salvarObject(save.getSelectedFile());										
			}
		});
		
		JButton botCarregar = new JButton("Carregar");
		botCarregar.setFont(fonteRotBot);
		botCarregar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
											
				JFileChooser load = new JFileChooser();
				load.showOpenDialog(getParent());
				caderno = caderno.carregar(load.getSelectedFile());
				caderno.listarNaListaSelecionavel(listModel);
				setTitle(load.getSelectedFile().getName());
				
			}
		});
		JButton botExportar = new JButton("Exportar arquivo de texto");
		botExportar.setFont(fonteRotBot);
		botExportar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser save = new JFileChooser();
				save.showSaveDialog(getParent());			
				caderno.salvarTxt(save.getSelectedFile());										
			}
		});
		painelSul.add(botIncluir);
		painelSul.add(botDeletar);
		painelSul.add(botSalvar);
		painelSul.add(botCarregar);
		painelSul.add(botExportar);
		getContentPane().add(BorderLayout.SOUTH, painelSul);
		
		// configura o frame
		//setSize(1700, 900);
		pack();
		setLocation(100, 50);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}		
	

	public class AutorListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {			
			JOptionPane.showMessageDialog(null, autor, "Sobre mim", JOptionPane.INFORMATION_MESSAGE);

		}

	}
	
	public class VersaoListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JOptionPane.showMessageDialog(null, versao, "Sobre este", JOptionPane.INFORMATION_MESSAGE);

		}

	}
}
	