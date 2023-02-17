import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;
import java.lang.*;
public class Calculator1 extends JFrame implements ActionListener
{
    JPanel p1,p2;
    JButton b1,b2,b3,b4,b5,b6,b7,b8,b9,b10,b11,b12,b13,b14,b15,b16,b17,b18,b19,b20,b21,b22,b23,b24,b25,b26,b27,b28,b29,b30,b31,b32,b33,b34,b35;
    JTextField t1,t2;
    int op=0;
   double a,b,res;
    public Calculator1()
    {
        setVisible(true);                                              
        setSize(520,600);                                               
        setLayout(null);
        setLocation(650,250); 
        setTitle("SG Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        Font f1=new Font("Consolas",Font.BOLD,30);
        Font f2=new Font("Consolas",Font.BOLD,25);
        Color c=new Color(49, 55, 59);
        Color c1=new Color( 111, 171, 115);
        Color c2=new Color(183, 187, 201);

        p1=new JPanel();
        p2=new JPanel();
        p1.setBounds(0,10,500,80);
        p1.setLayout(null);
        p2.setBounds(0,100,500,420);
        p2.setLayout(new GridLayout(7,5,7,7));
        p1.setBackground(c);
        p2.setBackground(c);
        t1=new JTextField();
        t1.setText("");
        t1.setBounds(0,10,500,60);
        t1.setForeground(Color.black);
        t1.setHorizontalAlignment(JTextField.RIGHT);
        t2=new JTextField();
        t2.setText(""+0);
        t1.setBackground(c1);
        t1.setFont(f1);

        b1=new JButton("x^y");
        b2=new JButton("TT");
        b3=new JButton("e");
        b4=new JButton("C");
        b5=new JButton("<-");
        b6=new JButton("x^2");
        b7=new JButton("1/x");
        b8=new JButton("|x|");
        b9=new JButton("exp");
        b10=new JButton("mod");
        b11=new JButton("2root(x)");
        b12=new JButton("(");
        b13=new JButton(")");
        b14=new JButton("n!");
        b15=new JButton("/");
        b16=new JButton("yroot(x)");
        b17=new JButton("9");
        b18=new JButton("8");
        b19=new JButton("7");
        b20=new JButton("*");
        b21=new JButton("10^x");
        b22=new JButton("6");
        b23=new JButton("5");
        b24=new JButton("4");
        b25=new JButton("-");
        b26=new JButton("log");
        b27=new JButton("3");
        b28=new JButton("2");
        b29=new JButton("1");
        b30=new JButton("+");
        b31=new JButton("In");        
        b32=new JButton("+/-");        
        b33=new JButton("0");        
        b34=new JButton(".");        
        b35=new JButton("=");           
        
        b1.setFont(f2);
        b2.setFont(f2);
        b3.setFont(f2);
        b4.setFont(f2);
        b5.setFont(f2);
        b6.setFont(f2);
        b7.setFont(f2);
        b8.setFont(f2);
        b9.setFont(f2);
        b10.setFont(f2);
        //b11.setFont(f2);
        b12.setFont(f2);
        b13.setFont(f2);
        b14.setFont(f2);
        b15.setFont(f2);
        //b16.setFont(f2);
        b17.setFont(f2);
        b18.setFont(f2);
        b19.setFont(f2);
        b20.setFont(f2);
        b21.setFont(f2);
        b22.setFont(f2);
        b23.setFont(f2);
        b24.setFont(f2);
        b25.setFont(f2);
        b26.setFont(f2);
        b27.setFont(f2);
        b28.setFont(f2);
        b29.setFont(f2);
        b30.setFont(f2);
        b31.setFont(f2);
        b32.setFont(f2);
        b33.setFont(f2);
        b34.setFont(f2);
        b35.setFont(f2);

        b1.setBackground(c2);b2.setBackground(c2);b3.setBackground(c2);b4.setBackground(c2);b5.setBackground(c2);b6.setBackground(c2);b7.setBackground(c2);b8.setBackground(c2);b9.setBackground(c2);b10.setBackground(c2);b11.setBackground(c2);b12.setBackground(c2);b13.setBackground(c2);b14.setBackground(c2);b15.setBackground(c2);b16.setBackground(c2);b17.setBackground(c2);b18.setBackground(c2);b19.setBackground(c2);b20.setBackground(c2);b21.setBackground(c2);b22.setBackground(c2);b23.setBackground(c2);b24.setBackground(c2);b25.setBackground(c2);b26.setBackground(c2);b27.setBackground(c2);b28.setBackground(c2);b29.setBackground(c2);b30.setBackground(c2);b31.setBackground(c2);b32.setBackground(c2);b33.setBackground(c2);b34.setBackground(c2);b35.setBackground(c2);




        p1.add(t1);
        add(p1);
        add(p2);
        p2.add(b1);
        p2.add(b2);
        p2.add(b3);
        p2.add(b4);
        p2.add(b5);
        p2.add(b6);
        p2.add(b7);
        p2.add(b8);
        p2.add(b9);
        p2.add(b10);
        p2.add(b11);
        p2.add(b12);
        p2.add(b13);
        p2.add(b14);
        p2.add(b15);
        p2.add(b16);
        p2.add(b17);
        p2.add(b18);
        p2.add(b19);
        p2.add(b20);
        p2.add(b21);
        p2.add(b22);
        p2.add(b23);
        p2.add(b24);
        p2.add(b25);
        p2.add(b26);
        p2.add(b27);
        p2.add(b28);
        p2.add(b29);
        p2.add(b30);
        p2.add(b31);
        p2.add(b32);
        p2.add(b33);
        p2.add(b34);
        p2.add(b35);
        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        b4.addActionListener(this);
        b5.addActionListener(this);
        b6.addActionListener(this);
        b7.addActionListener(this);
        b8.addActionListener(this);
        b9.addActionListener(this);
        b10.addActionListener(this);
        b11.addActionListener(this);
        b12.addActionListener(this);
        b13.addActionListener(this);
        b14.addActionListener(this);
        b15.addActionListener(this);
        b16.addActionListener(this);
        b17.addActionListener(this);
        b18.addActionListener(this);
        b19.addActionListener(this);
        b20.addActionListener(this);
        b21.addActionListener(this);
        b22.addActionListener(this);
        b23.addActionListener(this);
        b24.addActionListener(this);
        b25.addActionListener(this);
        b26.addActionListener(this);
        b27.addActionListener(this);
        b28.addActionListener(this);
        b29.addActionListener(this);
        b30.addActionListener(this);
        b31.addActionListener(this);
        b32.addActionListener(this);
        b33.addActionListener(this);
        b34.addActionListener(this);
        b35.addActionListener(this);

    }
    public void actionPerformed(ActionEvent ae)
    {
            String s=ae.getActionCommand();
            if(s.equals("C"))
            {
                    t1.setText("");
                    t2.setText("");
                    a=0.0;
                    b=0.0;
                    res=0.0;
                    op=0;              
            }
            if(s.equals("<-"))
            {
                    String back=t1.getText();  
                    String back1=t2.getText(); 
                    back = back.substring(0, back.length() - 1);
                    back1 = back1.substring(0, back1.length() - 1);
                    t1.setText(back);          
                    t2.setText(back1);       
            }
            if(s.equals("1"))
            {
                    t1.setText(t1.getText()+""+1);              
                    t2.setText(t2.getText()+""+1);              
            }
            if(s.equals("2"))
            {
                    t1.setText(t1.getText()+""+2);  
                    t2.setText(t2.getText()+""+2);            
            }
            if(s.equals("3"))
            {
                    t1.setText(t1.getText()+""+3);              
                    t2.setText(t2.getText()+""+3);              
            }
            if(s.equals("4"))
            {
                    t1.setText(t1.getText()+""+4);              
                    t2.setText(t2.getText()+""+4);              
            }
            if(s.equals("5"))
            {
                    t1.setText(t1.getText()+""+5);              
                    t2.setText(t2.getText()+""+5);              
            }
            if(s.equals("6"))
            {
                    t1.setText(t1.getText()+""+6);              
                    t2.setText(t2.getText()+""+6);              
            }
            if(s.equals("7"))
            {
                    t1.setText(t1.getText()+""+7);              
                    t2.setText(t2.getText()+""+7);              
            }
            if(s.equals("8"))
            {
                    t1.setText(t1.getText()+""+8);              
                    t2.setText(t2.getText()+""+8);              
            }
            if(s.equals("9"))
            {
                    t1.setText(t1.getText()+""+9);              
                    t2.setText(t2.getText()+""+9);              
            }
            if(s.equals("0"))
            {
                    t1.setText(t1.getText()+""+0);              
                    t2.setText(t2.getText()+""+0);              
            }
            if(s.equals("("))
            {
                    t1.setText(t1.getText()+"(");                   
            } 
            if(s.equals(")"))
            {
                    t1.setText(t1.getText()+")");                      
            }
            if(s.equals("."))
            {
                    t1.setText(t1.getText()+".");              
                    t2.setText(t2.getText()+".");  
            }
            if(ae.getSource()==b30)     //add
            {
                    a=Double.parseDouble(t2.getText());
                    t1.setText(t1.getText()+"+");         
                    op=1;
                    t2.setText("");
            }
            if(ae.getSource()==b25)     //sub
            {
                    a=Double.parseDouble(t2.getText());
                    t1.setText(t1.getText()+"-");    
                    op=2;
                    t2.setText("");
            }
            if(ae.getSource()==b20)     //mul
            {
                    a=Double.parseDouble(t2.getText());
                    t1.setText(t1.getText()+"*");    
                    op=3;
                    t2.setText("");
            }
            if(ae.getSource()==b15)     //div
            {
                    a=Double.parseDouble(t2.getText());
                    t1.setText(t1.getText()+"/");    
                    op=4;
                    t2.setText("");
            }
            if(ae.getSource()==b10)     //mod
            {
                    a=Double.parseDouble(t2.getText());
                    t1.setText(t1.getText()+"%");    
                    op=5;
                    t2.setText("");
            }
            
            if(ae.getSource()==b8)     //abs
            {
                    a=Double.parseDouble(t2.getText());
                    op=6;
                    t2.setText("");
            }
            if(ae.getSource()==b6)     //square of a no
            {
                    a=Double.parseDouble(t2.getText());
                    t1.setText(t1.getText()+"^2");      
                    op=7;
                    t2.setText("");
            }
            if(ae.getSource()==b1)     //x^y
            {
                    a=Double.parseDouble(t2.getText());
                    t1.setText(t1.getText()+"^");    
                    op=8;
                    t2.setText("");
            }
            if(ae.getSource()==b26)     //log
            {
                    a=Double.parseDouble(t2.getText());
                    op=9;
                    t2.setText("");
            }
            if(ae.getSource()==b11)     //square root
            {
                    a=Double.parseDouble(t2.getText());
                    t1.setText("sqroot("+a+")");    
                    op=10;
                    t2.setText("");
            }
            if(ae.getSource()==b2)     //Pi
            {
                    a=Double.parseDouble(t2.getText());
                    t1.setText(t1.getText()+"*3.14159");      
                    op=11;
                    t2.setText("");
            }
            if(ae.getSource()==b21)     //x^10
            {
                    a=Double.parseDouble(t2.getText());
                    t1.setText("10^"+t1.getText());    
                    op=12;
                    t2.setText("");
            }
            if(ae.getSource()==b35)
            {
                if(op!=6 && op!=7 && op!=9 && op!=10 && op!=11 && op!=12)
                    b=Double.parseDouble(t2.getText());
                    switch(op)
                    {
                            case 0:
                                        res=0;
                                        t1.setText("0");
                                        t2.setText("0");
                                        break;
                            case 1:
                                        res=a+b;
                                        break;
                            case 2:
                                        res=a-b;
                                        break;
                            case 3:
                                        res=a*b;
                                        break;
                             case 4:
                                        res=a/b;
                                        break;
                             case 5:
                                        res=a%b;
                                        break;
                             case 6:
                                        res=Math.abs(a);
                                        break;
                             case 7:
                                        res=a*a;
                                        break;
                             case 8:
                                        res=1;
                                        for(int i=0;i<b;i++)
                                        res=res*a;
                                        break;
                             case 9:
                                        res=Math.log(a);
                                        break;
                             case 10:
                                        res=Math.sqrt(a);
                                        break;
                             case 11:
                                        res=Math.PI*a;
                                        break;
                             case 12:
                                        res=1;
                                        for(int i=0;i<a;i++)
                                        res=res*10;
                                        break;
                            default:
                                        res=Double.parseDouble(t2.getText());
                             
                    }
                    if(op!=0)
                        t1.setText(""+res);
                        t2.setText(""+res);
        
                    op=0;
                    a=Double.parseDouble(t2.getText());

            }
    }
    public static void main(String a[])
    {
        Calculator1 c=new Calculator1();
    }
}