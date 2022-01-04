import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.ListIterator;
import javax.swing.*;



public class LinkedListGUI{
	public static void main(String[] args) {
		Finestra f = new Finestra();
		f.setVisible(true);
	}
}
class Finestra extends JFrame {
	private static final long serialVersionUID = 1L;
	private File Salvataggio = null;
	private JMenuItem nuova,apri , salva, salvaConNome, esci, info, aggiungi, aggiungiInizio, aggiungiFine,
					  rimuovi,rimuoviPrimo,rimuoviUltimo,ordina,svuota,numeroOggetti,trova,getFirst,getLast,
					  hasNext,hasPrevious,addLIT,removeLIT,set,isEmpty,isFull,inizio,LIT;
	private Font f = new Font("Helvetica",Font.BOLD,20);
	private Color col = new Color(0,255,0), azzurro = new Color(0,255,255);
	boolean start = false;
	private String titolo = "LinkedList GUI";
	private LinkedList<Integer> LL = new LinkedList<>();
	boolean verificato = false;
	private JMenu agg,remove,domande;
	private JTextArea text,textLIT;
	private JButton next,previous;
	private int cont = 0;
	private enum Move{FORWARD,BACKWARD,UNKNOWN};
	private Move lastMove;
	
	
	public Finestra() {
		setTitle(titolo);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		 addWindowListener( new WindowAdapter() {
		        public void windowClosing(WindowEvent e){
		       	 if( accettoUscita() ) System.exit(0);
		        }
		     } );
		 AscoltatoreEventiAzione listener = new AscoltatoreEventiAzione();
		 
		 JMenuBar menuBar = new JMenuBar();
		 this.setJMenuBar(menuBar);
		 // creazione file menu
		 JMenu menuFile = new JMenu("File");
		 menuBar.add(menuFile);
		 nuova= new JMenuItem("Nuova");
		 nuova.addActionListener(listener);
		 menuFile.add(nuova);
		 menuFile.addSeparator();
		 
		 apri =new JMenuItem("Apri");
		 apri.addActionListener(listener);
		 menuFile.add(apri);
		 salva = new JMenuItem("Salva");
		 salva.addActionListener(listener);
		 menuFile.add(salva);
		 salvaConNome = new JMenuItem( "Salva con nome");
		 salvaConNome.addActionListener(listener);
		 menuFile.add(salvaConNome);
		 esci = new JMenuItem("Esci");
		 esci.addActionListener(listener);
		 menuFile.addSeparator();
		 menuFile.add(esci);
		 
		 //creazione del menu comandi
		 JMenu comandiMenu = new JMenu("Comandi");
		 menuBar.add(comandiMenu);
		 agg = new JMenu("Aggiungi");
		 comandiMenu.add(agg);
		 aggiungi = new JMenuItem("Aggiungi");
		 aggiungi.addActionListener(listener);
		 agg.add(aggiungi);
		 aggiungiInizio = new JMenuItem("Aggiungi all'inizio");
		 aggiungiInizio.addActionListener(listener);
		 agg.add(aggiungiInizio);
		 aggiungiFine = new JMenuItem("Aggiungi alla fine");
		 aggiungiFine.addActionListener(listener);
		 agg.add(aggiungiFine);
		 comandiMenu.addSeparator();
		 remove = new JMenu("Rimuovi");
		 comandiMenu.add(remove);
		 rimuovi = new JMenuItem("Rimuovi");
		 rimuovi.addActionListener(listener);
		 remove.add(rimuovi);
		 rimuoviPrimo = new JMenuItem("Rimuovi il Primo");
		 rimuoviPrimo.addActionListener(listener);
		 remove.add(rimuoviPrimo);
		 rimuoviUltimo = new JMenuItem("Rimuovi l'ultimo");
		 rimuoviUltimo.addActionListener(listener);
		 remove.add(rimuoviUltimo);
		 comandiMenu.addSeparator();
		 domande = new JMenu("Domande");
		 comandiMenu.add(domande);
		 isEmpty = new JMenuItem("E' vuota?");
		 isEmpty.addActionListener(listener);
		 domande.add(isEmpty);
		 isFull = new JMenuItem("E' piena?");
		 isFull.addActionListener(listener);
		 domande.add(isFull);
		 trova = new JMenuItem("Il numero esiste?");
		 trova.addActionListener(listener);
		 domande.add(trova);
		 ordina = new JMenuItem("Ordina");
		 ordina.addActionListener(listener);
		 comandiMenu.add(ordina);
		 svuota = new JMenuItem("Svuota");
		 svuota.addActionListener(listener);
		 comandiMenu.add(svuota);
		 getFirst = new JMenuItem("Primo elemento");
		 getFirst.addActionListener(listener);
		 comandiMenu.add(getFirst);
		 getLast = new JMenuItem("Ultimo elemento");
		 getLast.addActionListener(listener);
		 comandiMenu.add(getLast);
		 comandiMenu.addSeparator();
		 numeroOggetti = new JMenuItem("Size");
		 numeroOggetti.addActionListener(listener);
		 comandiMenu.add(numeroOggetti);
		 LIT = new JMenuItem("ListIterator");
		 LIT.addActionListener(listener);
		 comandiMenu.add(LIT);
		 
		 
		 
		 
		 // creazione menu Supporto
		 JMenu supportoMenu = new JMenu("Help");
		 menuBar.add(supportoMenu);
		 info = new JMenuItem("About");
		 info.addActionListener(listener);
		 supportoMenu.add(info);
		 text = new JTextArea(100,500);
		 text.setFont(f);
		 text.setBackground(col);
		 text.setEditable(false);
		 add(text);
		 menuInizio();
		 pack();
		 
		 
		 setLocation(200,200);
		 setSize(500,400);
	}
	
	

	
	private class FrameAggiungi extends JFrame implements ActionListener{
		private static final long serialVersionUID = 1L;
		private JTextField num;
		private JButton ok;
		public FrameAggiungi() {
			setTitle("Aggiungi il numero");
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			JPanel p = new JPanel();
			p.setLayout(new FlowLayout());
			p.add(new JLabel("Numero",JLabel.CENTER));
			p.add(num = new JTextField("",14));
			p.add(ok = new JButton("OK"));
			add(p);
			num.addActionListener(this);
			ok.addActionListener(this);
			setLocation(250,340);
			setSize(500,200);
		}
		
		public void actionPerformed(ActionEvent e) {
			int a = 0;
			if(e.getSource() == num) {
				verificato = true;
			}
			if(verificato) {
				a = Integer.parseInt(num.getText());
				LL.add(a);
				text.setText(LL.toString());
				this.dispose();
				this.setVisible(false);
				num.setText("");
			}
			else if(e.getSource() == ok) {
				a = Integer.parseInt(num.getText());
				LL.add(a);
				text.setText(LL.toString());
				this.dispose();
				this.setVisible(false);
				num.setText("");
			}
			
		}//actionPerformed
		
	}//FrameAggiungi;
	
	
	private class FrameAddFirst extends JFrame implements ActionListener{
		private static final long serialVersionUID = 1L;
		private JTextField num;
		private JButton ok;
		public FrameAddFirst() {
			setTitle("Intero da aggiungere all'inizio");
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			JPanel p = new JPanel();
			p.add(new JLabel("Numero: ",JLabel.RIGHT));
			p.add(num = new JTextField(14));
			p.add(ok = new JButton("OK"));
			add(p);
			num.addActionListener(this);
			ok.addActionListener(this);
			setLocation(250,340);
			setSize(500,200);
		}
		
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == num) {
				verificato = true;
			}
			if(verificato) {
				LL.addFirst(Integer.parseInt(num.getText()));
				text.setText(LL.toString());
				this.dispose();
				this.setVisible(false);
				num.setText("");
			}
			else if(e.getSource() == ok) {
				LL.addFirst(Integer.parseInt(num.getText()));
				text.setText(LL.toString());
				this.dispose();
				this.setVisible(false);
				num.setText("");
			}
			
		}
	
	}
	
	private class FrameRimuovi extends JFrame implements ActionListener{
		private static final long serialVersionUID = 1L;
		private JTextField num;
		private JButton ok;
		public FrameRimuovi() {
			setTitle("Rimozione numero");
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

			JPanel p = new JPanel();
			p.setLayout(new FlowLayout() );
			p.add(new JLabel("Numero",JLabel.CENTER));
			p.add( num = new JTextField("",14));
			p.add(ok = new JButton("OK"));
			add(p);
			num.addActionListener(this);
			ok.addActionListener(this);
			setLocation(250,340);
			setSize(500,200);
		}
		
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==num) {
				verificato = true;
			}
			if(verificato) {
				LL.remove(Integer.parseInt(num.getText()));
				text.setText(LL.toString());
				this.dispose();
				this.setVisible(false);
				num.setText("");
			}
			else if(e.getSource()== ok) {
				LL.remove(Integer.parseInt(num.getText()));
				text.setText(LL.toString());
				this.dispose();
				this.setVisible(false);
				num.setText("");
			}	
		}//actionPerformed
	}//FrameRimuovi
	
	private class FrameRicercaNumero extends JFrame implements ActionListener{
		private static final long serialVersionUID = 1L;
		private JTextField num;
		private JButton ok;
		public FrameRicercaNumero() {
			setTitle("Ricerca di un numero");
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			JPanel p = new JPanel();
			p.setLayout(new FlowLayout());
			p.add(new JLabel("Numero",JLabel.CENTER));
			p.add(num = new JTextField("",14));
			p.add(ok = new JButton("OK"));
			add(p);
			num.addActionListener(this);
			ok.addActionListener(this);
			setLocation(250,340);
			setSize(500,200);
		}
		
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == num) {
				verificato = true;
			}
			if(verificato) {
				if(LL.size()== 0) {
					JOptionPane.showMessageDialog(null, "Non puoi trovare numeri in una lista vuota");
					this.setVisible(false);
					num.setText("");
				}
				else {
					if(LL.contains(Integer.parseInt(num.getText()))) JOptionPane.showMessageDialog(null,"Il numero esiste");
					else JOptionPane.showMessageDialog(null, "Numero inesistente");
				this.setVisible(false);
				num.setText("");
				}
			}
			else if(e.getSource()== ok) {
				if(LL.size()== 0) {
					JOptionPane.showMessageDialog(null, "Non puoi trovare numeri in una lista vuota");
					this.setVisible(false);
					num.setText("");
				}
				else {
					if(LL.contains(Integer.parseInt(num.getText()))) JOptionPane.showMessageDialog(null,"Il numero esiste");
					else JOptionPane.showMessageDialog(null, "Numero inesistente");
				this.setVisible(false);
				num.setText("");
				}
			}
			

		}
	}
	
	private class FrameLIT extends JFrame implements ActionListener{
		private static final long serialVersionUID = 1L;
		private ListIterator<Integer>lit;
	
		public FrameLIT() {
			
			setTitle("ListIterator");
			textLIT = new JTextArea(100,500);
			add(textLIT);
			next = new JButton("Next");
			next.addActionListener(this);
			previous = new JButton("Previous");
			previous.addActionListener(this);
			textLIT = new JTextArea(100,500);
			textLIT.setFont(f);
			JMenuBar j = new JMenuBar();
			textLIT.add(j);
			JMenu comandi = new JMenu("Comandi");
			j.add(comandi);
			inizio = new JMenuItem("Inizio");
			inizio.addActionListener(this);
			comandi.add(inizio);
			hasNext = new JMenuItem("HasNext");
			hasNext.addActionListener(this);
			hasPrevious = new JMenuItem("HasPrevious");
			hasPrevious.addActionListener(this);
			comandi.add(hasNext);
			comandi.add(hasPrevious);
			addLIT = new JMenuItem("Aggiungi");
			addLIT.addActionListener(this);
			comandi.add(addLIT);
			removeLIT = new JMenuItem("Rimuovi");
			removeLIT.addActionListener(this);
			comandi.add(removeLIT);
			set = new JMenuItem("Set");
			set.addActionListener(this);
			comandi.add(set);
			addLIT.setEnabled(false);
			removeLIT.setEnabled(false);
			hasNext.setEnabled(false);
			hasPrevious.setEnabled(false);
			set.setEnabled(false);
			next.setEnabled(false);
			previous.setEnabled(false);
			JPanel p = new JPanel();
			p.add(previous);p.add(next);
			add(p,BorderLayout.SOUTH);
			
			JPanel a = new JPanel();
			a.add(j);
			add(a,BorderLayout.PAGE_START);
					
			setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			add(textLIT);
			textLIT.setEditable(false);
			addWindowListener( new WindowAdapter() {
		        public void windowClosing(WindowEvent e){
		       	 if( accettoUscita() ) {  menuAvviato(); start = false; text.setText(LL.toString());dispose();}
		        }
		     } );
			
			textLIT.setBackground(azzurro);
			setSize(500,400);
			setLocation(250,340);
			
		}
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()== inizio) {
				String dmd = JOptionPane.showInputDialog("Inserisci la posizione da cui partire");
				if(dmd!= null) {
					try {
						lit = LL.listIterator(Integer.valueOf(dmd));
						cont = Integer.valueOf(dmd);
						toStringLIT();
						menuAvviatoLIT();
						lastMove = Move.UNKNOWN;
						
					}catch(Exception ex) {
						JOptionPane.showMessageDialog(null,"Posizione errata");
					}
				}
			}
			else if(e.getSource() == next) {
				try {
					lit.next();cont++;toStringLIT();lastMove = Move.FORWARD;
				}catch(Exception ex) {
					JOptionPane.showMessageDialog(null,"Non esiste il successivo");
				}
			}
			else if(e.getSource() == previous) {
				try {
				lit.previous(); cont--; toStringLIT(); lastMove = Move.BACKWARD;
				} catch(Exception ec){
					JOptionPane.showMessageDialog(null,"Non esiste il precedente");
				}
			}
			else if(e.getSource() == addLIT) {
				String dmd = JOptionPane.showInputDialog("Inserisci il numero: ");
				if(dmd!= null) {
					lit.add(Integer.valueOf(dmd));
					cont++; lastMove = Move.UNKNOWN;
					toStringLIT();
				}
			}
			else if(e.getSource() == removeLIT) {
				try {
					lit.remove(); toStringLIT(); lastMove = Move.UNKNOWN;
				}catch(IllegalStateException ec) {
					JOptionPane.showMessageDialog(null,"Impossibile rimuovere l'elemento");
				}
	
			}
			else if(e.getSource()== hasNext) {
				if(lit.hasNext()) JOptionPane.showMessageDialog(null,"C'� un prossimo elemento");
				else JOptionPane.showMessageDialog(null,"Non c'� un prossimo elemento");
			}
			else if(e.getSource()== hasPrevious) {
				if(lit.hasPrevious()) JOptionPane.showMessageDialog(null,"C'� un elemento precedente");
				else JOptionPane.showMessageDialog(null,"Non c'� un elemento precedente");
			}
			else if(e.getSource() == set) {
				String dmd =JOptionPane.showInputDialog(null,"Inserisci l'elemento da sostituire");
				if(dmd != null) {
					try {
						lit.set(Integer.valueOf(dmd)); toStringLIT();
					}catch(IllegalArgumentException ex) {
						JOptionPane.showMessageDialog(null, "Formato errato");
					}catch(IllegalStateException ec) {
						JOptionPane.showMessageDialog(null, "Impossibile sostiture l'elemento");
					}
				}
			}
			
		}
		
	}
	
	 
	

	private class AscoltatoreEventiAzione implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()== esci) {
				if(accettoUscita())
					System.exit(0);
			}
			else if(e.getSource() == nuova) {
				text.setText(LL.toString());
				menuAvviato();
			}
			else if(e.getSource() == salva) {
				JFileChooser chooser = new JFileChooser();
				try {
					if(Salvataggio != null) {
						int dmd = JOptionPane.showConfirmDialog(null,"Sovrascrivere "+Salvataggio.getAbsolutePath()+" ?");
						if(dmd == 0) //yes
							LL.salva(Salvataggio.getAbsolutePath());
						else
							JOptionPane.showMessageDialog(null,"Nessun salvataggio!");
						return;
					}
					if(chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
						Salvataggio = chooser.getSelectedFile();
						Finestra.this.setTitle(titolo+" - "+ Salvataggio.getName());
					}
					if(Salvataggio!= null) {
						LL.salva(Salvataggio.getAbsolutePath());
					}
					else
						JOptionPane.showMessageDialog(null,"Nessun Salvataggio");
				}catch(Exception ec) {
					ec.printStackTrace();
				}
				
			}
			else if(e.getSource() == salvaConNome) {
				JFileChooser chooser = new JFileChooser();
				try {
					if(chooser.showSaveDialog(null)== JFileChooser.APPROVE_OPTION) {
						Salvataggio = chooser.getSelectedFile();
						Finestra.this.setTitle(titolo+" - "+ Salvataggio.getName());
					}
					if(Salvataggio!= null) {
						LL.salva(Salvataggio.getAbsolutePath());
					}
					else
						JOptionPane.showMessageDialog(null,"Nessun salvataggio");
				}catch(Exception ec) {
					ec.printStackTrace();
				}
			}
			else if(e.getSource()==apri) {
				JFileChooser chooser = new JFileChooser();
				try {
					if(chooser.showOpenDialog(null)==JFileChooser.APPROVE_OPTION) {
						if(!chooser.getSelectedFile().exists()) {
							JOptionPane.showMessageDialog(null,"File inesistente");
							menuInizio();
						}
						else {
							Salvataggio = chooser.getSelectedFile();
							Finestra.this.setTitle(titolo+"- "+Salvataggio.getName());
							menuAvviato();
							try {
								LL.ripristina(Salvataggio.getAbsolutePath());
							}catch(IOException o) {
								JOptionPane.showMessageDialog(null,"Fallimento nell'apertura. File malformato");
								menuInizio();
							}
						}
						text.append("Ecco la tua lista: "+ LL.toString());
						menuAvviato();
					}
					else { JOptionPane.showMessageDialog(null,"Nessuna apertura"); menuInizio();}
				}catch(Exception ec) {
					ec.printStackTrace();
				}
				
				
			}
			else if(e.getSource() == numeroOggetti) {
				if (LL.size() == 1) {
					JOptionPane.showMessageDialog( null,
		  					   "In questa lista c'� " +LL.size()+" numero",
		  						"Size", JOptionPane.PLAIN_MESSAGE );
				}
				else {
				JOptionPane.showMessageDialog( null,
	  					   "In questa lista ci sono " +LL.size()+" numeri",
	  						"Size", JOptionPane.PLAIN_MESSAGE );
				}
			}
			else if(e.getSource() == aggiungi) {
				FrameAggiungi agg = new FrameAggiungi();
				agg.setVisible(true);
			}
			else if(e.getSource() == aggiungiInizio) {
				FrameAddFirst first = new FrameAddFirst();
				first.setVisible(true);
			}
			else if(e.getSource() == aggiungiFine) {
				FrameAggiungi agg = new FrameAggiungi();
				agg.setVisible(true);
			}
			else if(e.getSource() == getFirst) {
				JOptionPane.showMessageDialog( null,
	  					   "Il primo elemento � " +LL.getFirst(),"Primo elemento",
	  					   JOptionPane.PLAIN_MESSAGE );
			}
			else if(e.getSource() == getLast) {
				JOptionPane.showMessageDialog( null,
	  					   "L'ultimo elemento � " +LL.getLast(),"Ultimo elemento",
	  					   JOptionPane.PLAIN_MESSAGE );
			}
			
			else if(e.getSource() == rimuovi) {
				FrameRimuovi rimuovi = new FrameRimuovi();
				rimuovi.setVisible(true);
			}
			else if(e.getSource() == rimuoviPrimo) {
				LL.removeFirst();
				text.setText(LL.toString());
			}
			else if(e.getSource() == rimuoviUltimo) {
				LL.removeLast();
				text.setText(LL.toString());
				
			}
			else if(e.getSource()  == svuota) {
				LL.clear();
				text.setText(LL.toString());
			}
			else if(e.getSource() == isEmpty) {
				boolean ver = false;
				if(LL.isEmpty())
					ver = true;
				JOptionPane.showMessageDialog( null,
	  					   ver,"La Lista � vuota ?", JOptionPane.PLAIN_MESSAGE );
	  			}	
			else if(e.getSource() == isFull) {
				JOptionPane.showMessageDialog( null,
	  					   "FALSE","La lista � piena ?", JOptionPane.PLAIN_MESSAGE );
				}
			else if(e.getSource() == ordina) {
				class MioComparatore implements Comparator<Integer>{
					public int compare(Integer a,Integer b) {
						return a.compareTo(b);
					} 
				}
				List.sort(LL,new MioComparatore());
				text.setText(LL.toString());
			} else if(e.getSource() == LIT) {
				FrameLIT f  = new FrameLIT();
				f.setVisible(true);
			}
			
			
			else if(e.getSource() == trova) {
				FrameRicercaNumero f = new FrameRicercaNumero();
				f.setVisible(true);
				
			}
			else if(e.getSource() == info) {
				JOptionPane.showMessageDialog( null,
							"Programma che ha la finalit� della creazione di una lista di numeri interi",
		  					"Info", JOptionPane.PLAIN_MESSAGE );
			}
		
		}
	}
	
	private boolean accettoUscita() {
		int opzione = JOptionPane.showConfirmDialog(
				   null, "Continuare? Uscendo si perderanno tutti i dati!","Uscita",JOptionPane.YES_NO_OPTION);
		return opzione == JOptionPane.YES_OPTION;
	}
	private void menuInizio() {
		salva.setEnabled(false);
		salvaConNome.setEnabled(false);
		agg.setEnabled(false);
		remove.setEnabled(false);
		ordina.setEnabled(false);
		svuota.setEnabled(false);
		numeroOggetti.setEnabled(false);
		getFirst.setEnabled(false);
		getLast.setEnabled(false);
		domande.setEnabled(false);
		LIT.setEnabled(false);
		
	}
	
	private void menuAvviato() {
		apri.setEnabled(false);
		nuova.setEnabled(false);
		salva.setEnabled(true);
		salvaConNome.setEnabled(true);
		agg.setEnabled(true);
		remove.setEnabled(true);
		ordina.setEnabled(true);
		svuota.setEnabled(true);
		numeroOggetti.setEnabled(true);
		getFirst.setEnabled(true);
		getLast.setEnabled(true);
		domande.setEnabled(true);
		LIT.setEnabled(true);
		
	}
	
	
	private void menuAvviatoLIT() {
		agg.setEnabled(false);
		remove.setEnabled(false);
		ordina.setEnabled(false);
		svuota.setEnabled(false);
		LIT.setEnabled(false);
		hasNext.setEnabled(true);
		hasPrevious.setEnabled(true);
		addLIT.setEnabled(true);
		removeLIT.setEnabled(true);
		set.setEnabled(true);
		inizio.setEnabled(false);
		next.setEnabled(true);
		previous.setEnabled(true);
	}
	
	private void toStringLIT() {
		StringBuilder sb = new StringBuilder(500);
		ListIterator<Integer> it = LL.listIterator();
		int c =0;
		sb.append('[');
		while(it.hasNext()) {
			int elemento = it.next();
			if(c == cont)
				sb.append('^');
			else if(c==0)
					sb.append(" ");
			sb.append("  ");
			sb.append(elemento);
			
			c++;
		}//while
		if(cont == LL.size())
			sb.append('^');
		sb.append(']');
		textLIT.setText(sb.toString());
	}
}
