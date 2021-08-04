package poo.progetto_espressioni;

import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import poo.util.Stack;
import poo.util.StackConcatenato;

public class ValutatoreEspressioni
{

	public static void main(String[] args) 
	{	
		JFrame f = new Finestra();
		f.setVisible(true);

	}

}

@SuppressWarnings("serial")
class Finestra extends JFrame implements ActionListener
{
	private JTextField espressione, risultato;
	private JButton bottone;
	private JMenuItem help;

	
	public Finestra()
	{	setTitle("Valutatore Espressioni");
		setSize(400, 160);
		setLocation(550, 300);
		JPanel p1 = new JPanel();
		p1.add(new JLabel("Espressione:", JLabel.LEFT));
		p1.add(espressione = new JTextField("", 25));
		add(p1, BorderLayout.NORTH);
		JPanel p2 = new JPanel();
		p2.add(new JLabel("Risultato:      ", JLabel.LEFT));
		p2.add(risultato = new JTextField("", 25));
		risultato.setEditable(false);
		add(p2, BorderLayout.CENTER);
		JPanel p3 = new JPanel();
		p3.add(bottone = new JButton("Valuta"));
		add(p3, BorderLayout.SOUTH);
		bottone.addActionListener(this);
		JMenuBar bar = new JMenuBar();
		this.setJMenuBar(bar);
		help = new JMenuItem("Help");
		help.addActionListener(this);
		bar.add(help);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}


	public void actionPerformed(ActionEvent e)
	{	if(e.getSource() == bottone)
		{	String expr = espressione.getText();
			StringTokenizer st = new StringTokenizer(expr, "^/*%+-() ", true);
			int contaAperta = 0;
			int contaChiusa = 0;
			boolean flag = true;
			while(st.hasMoreTokens())
			{	String t = st.nextToken();
				if(t.equals("("))
					contaAperta++;
				else if(t.equals(")"))
					contaChiusa++;
				else if(!t.matches("\\d+") && !t.matches("[\\^\\*%/\\+\\-]"))
				{	JOptionPane.showMessageDialog(bottone.getParent(), "Espressione malformata, ripetere...", "Errore", 0);
					flag = false;
				}
			}
			if(contaAperta != contaChiusa)
				JOptionPane.showMessageDialog(bottone.getParent(), "Espressione malformata, ripetere...", "Errore", 0);
			else if(flag)
				try
				{	risultato.setText(""+Valuta.valutaEspressione(expr));}
				catch(IllegalArgumentException iae)
				{	JOptionPane.showMessageDialog(bottone.getParent(), "Espressione malformata, ripetere...", "Errore", 0);}
		}
		if(e.getSource() == help)
			JOptionPane.showMessageDialog(help.getParent(), "Questa applicazione fornisce il risultato di un'espressione aritmetica.\nGli operandi ammessi sono interi senza segno.\nGli operatori ammessi sono: ^, *, /, %, +, -.\nE' possibile utilizzare le parentesi tonde per alterare le priorità intrinseche.", "Informazioni", 1);
	}

}

final class Valuta
{
	private static String max = "\\^";
	private static String middle = "[\\*/%]";
	private static String min = "[\\+\\-]";
	private static String tot = "[\\^\\*/%\\+\\-]";
	private static String num = "\\d+";
	
	public Valuta() {}
	
	private static boolean ePiuPrioritario(char op1, char op2)
	{	String oper1 = ""+op1;
		String oper2 = ""+op2;
		if(!oper1.matches(tot) || !oper2.matches(tot))
			throw new IllegalArgumentException();
		if( (oper1.matches(max)) || ((oper1.matches(middle)) && (oper2.matches(min))) )
			return true;
		if((oper1.matches(min)))
			return false;
		return false;//never performed
	}
	
	public static int valutaEspressione(String s)
	{	StringTokenizer st = new StringTokenizer(s, tot+"( )", true);
		Stack<Integer> operandi = new StackConcatenato<>();
		Stack<Character> operatori = new StackConcatenato<>();
		int index = 0;
		while(st.hasMoreTokens())
		{	String e = st.nextToken();
			index++;
			if(e.equals(")"))
				break;
			if(e.equals("("))
			{	operandi.push(valutaEspressione(s.substring(index)));
				int c = 1;
				while(!(c == 0))
				{	String p = st.nextToken();
					index++;
					if(p.equals("("))
						c++;
					if(p.equals(")"))
						c--;
				}
			}
			if(e.matches(num))
			{	operandi.push(Integer.parseInt(e));
				for(int k = 0; k < e.length()-1; k++)
					index++;
			}
			else if(e.matches(tot))
			{	char opc = e.charAt(0);
				for(;;)
				{	if(operatori.isEmpty() || ePiuPrioritario(opc, operatori.peek()))
					{	operatori.push(opc);
						break;
					}
					else
					{	char op = operatori.pop();
						int operando2;
						int operando1;
						try
						{	operando2 = operandi.pop();
							operando1 = operandi.pop();}
						catch(NoSuchElementException ex)
						{	throw new IllegalArgumentException("Espressione Malformata");}
						int ris;
						switch(op)
						{	case '^':
								ris = (int)Math.pow(operando1, operando2);
								break;
							case '*':
								ris = operando1 * operando2;
								break;
							case '/':
								ris = operando1 / operando2;
								break;
							case '%':
								ris = operando1 % operando2;
								break;
							case '+':
								ris = operando1 + operando2;
								break;
							default:
								ris = operando1 - operando2;
						}
						operandi.push(ris);
					}
				}	
			}
		}
		while(!operatori.isEmpty())
		{	char $op = operatori.pop();
			int $operando2;
			int $operando1;
			try
			{	$operando2 = operandi.pop();
				$operando1 = operandi.pop();}
			catch(NoSuchElementException ex)
			{	throw new IllegalArgumentException("Espressione Malformata");}
			int $ris;
			switch($op)
			{	case '^':
					$ris = (int)Math.pow($operando1, $operando2);
					break;
				case '*':
					$ris = $operando1 * $operando2;
					break;
				case '/':
					$ris = $operando1 / $operando2;
					break;
				case '%':
					$ris = $operando1 % $operando2;
					break;
				case '+':
					$ris = $operando1 + $operando2;
					break;
				default:
					$ris = $operando1 - $operando2;
			}
			operandi.push($ris);
		}
		if(operandi.size()!= 1)
			throw new IllegalArgumentException("Espressione Malformata");
		return operandi.peek();
	}
}