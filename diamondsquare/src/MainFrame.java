import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MainFrame extends JFrame{
	
	private double[][] tabValeurs;
	private int sizeGrid;
	private int N=5;
	private int sizeSquare = 2;
	
	private JPanel conteneur;
	
	private final double SEED = 1000.00;
	private final double RAND = 2000.00;
	
	
	public static void main(String args[]){
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});	
	}
	
	public MainFrame(){		
		
		conteneur = new JPanel();
		conteneur.setLayout(new BorderLayout());
		setContentPane(conteneur);
		
		init();
	}
	private void init(){
		sizeGrid = (int)(Math.pow(2, N)+1);
		tabValeurs = new double[sizeGrid][sizeGrid];
		genererValeurs();		
		
		Grille grille = new Grille(tabValeurs, sizeSquare);
		grille.setPreferredSize(new Dimension(sizeGrid*sizeSquare+1, sizeGrid*sizeSquare+1));
		
		JPanel conteneurGrille = new JPanel();
		conteneurGrille.setBorder(new EmptyBorder(10, 10, 10, 10));
		conteneurGrille.add(grille);
		
		conteneur.add(conteneurGrille, BorderLayout.WEST);
		conteneur.add(paneauOptions(), BorderLayout.EAST);
		
		pack();
	}
	private void nouvelleMap(){
		conteneur.removeAll();
		conteneur.repaint();
		init();
	}
	private JPanel paneauOptions(){
		
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(7, 1));
		
		//bouton New
		JButton b = new JButton("New");
		b.addActionListener(new ActionListener(){
			
		  public void actionPerformed(ActionEvent e){
			  nouvelleMap();
		  }
		});
		
		p.add(b);
		p.add(sliderN());
		p.add(sliderS());
		
		
		
		p.setBorder(new EmptyBorder(10, 10, 10, 10));
		return p;		
	}
	private JPanel sliderE(){
		JPanel p = new JPanel();
		
		JSlider s = new JSlider(JSlider.HORIZONTAL, 1, 10, 5);
		s.setMajorTickSpacing(1);
		s.setPaintTicks(true);
		s.setSnapToTicks(false);
		
		s.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e){
				sizeSquare = s.getValue();
			}
		});
		//label S
		JLabel l = new JLabel("Quantité d'eau");
		
		p.add(l);
		p.add(s);	
		
		return p;
		
	}
	
	
	private JPanel sliderS(){
		JPanel p = new JPanel();
		
		JSlider sS = new JSlider(JSlider.HORIZONTAL, 1, 20, sizeSquare);
		sS.setMajorTickSpacing(5);
		sS.setMinorTickSpacing(1);
		sS.setPaintTicks(true);
		sS.setSnapToTicks(true);
		
		sS.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e){
				sizeSquare = sS.getValue();
			}
		});
		//label S
		JLabel lS = new JLabel("largeur d'une case");
		
		p.add(lS);
		p.add(sS);	
		
		return p;
	}
	private JPanel sliderN(){

		JPanel p = new JPanel();
		
		JSlider sN = new JSlider(JSlider.HORIZONTAL, 1, 10, N);
		
		sN.setMajorTickSpacing(1);
		sN.setPaintTicks(true);
		sN.setPaintLabels(true);
		sN.setSnapToTicks(true);
		
		sN.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e){
				N = sN.getValue();
			}
		});
		//label N
		JLabel lN = new JLabel("largeur de la map");
		
		p.add(lN);
		p.add(sN);
		
		return p;
	}
	
	private void genererValeurs(){
		
		
		double valCoins = SEED/2;
		
		tabValeurs[0][0] = valCoins;
		tabValeurs[0][sizeGrid-1] = valCoins;
		tabValeurs[sizeGrid-1][sizeGrid-1] = valCoins;
		tabValeurs[sizeGrid-1][0] = valCoins;
		/*------------------------*/
		int sizeSquare = sizeGrid;
		
		
		tabValeurs[sizeSquare/2][sizeSquare/2] = SEED + ((Math.random()*RAND/2));
		
		for(int i = 1; i<=N; i++){
		
			
			//diamond
			for(int x=0; x<tabValeurs.length; x++){
				for(int y=0; y<tabValeurs.length; y++){
					
					double coord = tabValeurs[x][y];
					if(coord==0){
						int nbDivs = (int)Math.pow(2,(double)i); 
						int displacement = sizeGrid/nbDivs;
						
						boolean isX = false;
						boolean isY = false;
							
						
						for(int k = 0; k<=nbDivs; k++){
							if(x==displacement*k){
								isX = true;								
							}
							if(y==displacement*k){
								isY = true;								
							}
						}	
						if(isX&&isY){
							
							double moyenne = 0;
							int nbMoyenne = 0;
							
							if(x!=0){
								moyenne+=tabValeurs[x-displacement][y];
								nbMoyenne++;
							}
							if(x!=sizeGrid-1){
								moyenne+=tabValeurs[x+displacement][y];
								nbMoyenne++;
							}
							if(y!=0){
								moyenne+=tabValeurs[x][y-displacement];
								nbMoyenne++;
							}
							if(y!=(sizeGrid-1)){
								moyenne+=tabValeurs[x][y+displacement];
								nbMoyenne++;
							}
							moyenne = moyenne/nbMoyenne;
							
							double valeur = moyenne + ((Math.random()*RAND/(i*2))-(RAND/(i*4)));
							if(nbMoyenne<4){
								valeur = moyenne - (Math.random()*RAND/(RAND/(i*4)));
							}
							if(i==N){
								valeur = moyenne;
							}
							tabValeurs[x][y] = valeur;
						}
					
					}
				}
			}
			//square
			for(int x=0; x<tabValeurs.length; x++){
				for(int y=0; y<tabValeurs.length; y++){
				
					double coord = tabValeurs[x][y];
					if(coord==0){
						int nbDivs = (int)Math.pow(2,(double)i); 
						int displacement = sizeGrid/(2*nbDivs);
						
						boolean isX = false;
						boolean isY = false;
						
						for(int k = 0; k<=nbDivs; k++){
							if(x==displacement+(k*(sizeGrid/nbDivs))){
								isX = true;								
							}
							if(y==displacement+(k*(sizeGrid/nbDivs))){
								isY = true;								
							}
						}	
						if(isX&&isY){
							double moyenne = 0;
							int nbMoyenne = 4;
							
							moyenne+=tabValeurs[x-displacement][y-displacement];
							moyenne+=tabValeurs[x-displacement][y+displacement];
							moyenne+=tabValeurs[x+displacement][y-displacement];
							moyenne+=tabValeurs[x+displacement][y+displacement];
							
							
							moyenne = moyenne/nbMoyenne;
							double valeur = moyenne + ((Math.random()*RAND/(i*2))-(RAND/(i*4)));
							if(i==N-1){
								valeur = moyenne;
							}
							tabValeurs[x][y] = valeur;
						}
					}
				}
			}
			
		}
		
		
		
	}
	
}
