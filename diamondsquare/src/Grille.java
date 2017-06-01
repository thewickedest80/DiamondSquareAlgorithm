import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Grille extends JPanel{

	private int sizeSquare;
	private int sizeGrid;
	private double[][] data;
	
	private Color cTerre = new Color(65,170,60);
	private Color cSable = new Color(120,190,115);
	private Color cNeige = new Color(255,255,255);
	private Color cEau = new Color(0,80,160);
	private Color cPlage = new Color(40, 130, 185);
	private Color cMontagne = new Color(80, 90, 50);
	
	public Grille(double[][] data, int sizeSquare){
		this.data = data;
		this.sizeGrid= data.length;
		this.sizeSquare=sizeSquare;
	}
	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		dessiner(g);	
	}
	private void dessiner(Graphics g){
		
		double highVal = 0;
		double lowVal = 10000;
		for(int i = 0; i<sizeGrid; i++){
			for(int j = 0; j<sizeGrid; j++){
				if(data[j][i]>highVal){
					highVal=data[j][i];
				}
				if(data[j][i]<lowVal){
					lowVal=data[j][i];
				}
			}
		}
		
		double terre = lowVal + ((highVal-lowVal)*0.5);
		double montagne = highVal*0.84;
		double neige = highVal*0.925;
		double sable = terre + (terre*0.05);
		double plage = terre - (terre*0.05);
		
		
		
		for(int i = 0; i<sizeGrid; i++){
			for(int j = 0; j<sizeGrid; j++){
				
				Dimension pos = new Dimension(j*sizeSquare, i*sizeSquare);
				Dimension dim= new Dimension(sizeSquare, sizeSquare);
				double valeur = data[j][i];
				if(valeur>=terre){
					if(valeur<=sable){
						g.setColor(cSable);
					}else if(valeur>=montagne){
						if(valeur>neige){
							g.setColor(cNeige);
						}else{
							g.setColor(cMontagne);
						}
					}else{
						g.setColor(cTerre);
					}
				}else{
					if(valeur>=plage){
						g.setColor(cPlage);
					}else{
						g.setColor(cEau);
					}
				}
				g.fillRect(pos.width, pos.height, dim.width, dim.height);
			}
		}
		
		for(int i = 0; i<sizeGrid; i++){
			for(int j = 0; j<sizeGrid; j++){
				
	
			}
		}
		
	}
	
	
	
	
}
