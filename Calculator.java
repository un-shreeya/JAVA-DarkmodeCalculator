import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Calculator extends JFrame implements ActionListener{
    private JTextField display;
    private String currentInput="";
    private double acc=0;
    private String op="";
    private double memory=0;
    private JButton selOp=null;
    private final Color OP_NORMAL = new Color(60, 60, 60);
    private final Color OP_HIGHLIGHT = new Color(100, 100, 180);
    private boolean s=false;

    public Calculator(){
        setTitle("CALCULATOR");
        setSize(400,600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        display=new JTextField();
        display.setBackground(new Color(40,40,40));
        display.setForeground(Color.WHITE);
        display.setFont(new Font("Arial", Font.BOLD, 30));
        display.setEditable(false);
        display.setHorizontalAlignment(SwingConstants.RIGHT);
        display.setText("0");
        add(display,BorderLayout.NORTH);

        JPanel bPanel= new JPanel();
        bPanel.setLayout(new GridLayout(4,4,5,5));
        String[] buttons = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", ".", "=", "+"
        };
        for (String b : buttons){
            JButton btn= new JButton(b);
            btn.setBackground(new Color(60, 60, 60));  
            btn.setForeground(Color.WHITE);            
            btn.setFocusPainted(false);                

            btn.setFont(new Font("Arial",Font.BOLD,30));
            btn.addActionListener(this);
            bPanel.add(btn);
        }

        JPanel memPanel= new JPanel();
        memPanel.setLayout(new GridLayout(1,4,5,5));
        String[] mButtons = {"MC", "MR", "M+","C"};
        for (String m : mButtons){
            JButton mbtn= new JButton(m);
            mbtn.setBackground(new Color(90, 90, 90));
            mbtn.setForeground(Color.WHITE);

            mbtn.setFont(new Font("Arial",Font.BOLD,30));
            mbtn.addActionListener(this);
            memPanel.add(mbtn);
        }
        
        add(bPanel,BorderLayout.CENTER);
        add(memPanel,BorderLayout.SOUTH);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e){
        String cmd =  e.getActionCommand();
        if (cmd.equals("MC")){
            memory=0;
            return;
        }
        else if(cmd.equals("MR")){
            currentInput=String.valueOf(memory);
            display.setText(currentInput);
            return;
        }
        else if(cmd.equals("M+")){
            double val= currentInput.isEmpty()? acc : Double.parseDouble(currentInput);
            memory+=val;
            return;
        }
        else if(cmd.equals("C")){
            currentInput="";
            acc=0;
            op="";
            s=false;
            display.setText("0");

            if (selOp != null) {
                selOp.setBackground(OP_NORMAL);
                selOp = null;
            }
            return;
        }
        if (cmd.matches("[0-9]")){
            if (s){
                display.setText("");
                currentInput="";
                s=false;
            }
            if (selOp != null) {
                currentInput="";
                display.setText("");
                selOp.setBackground(OP_NORMAL);
                selOp = null;
            }
            currentInput+=cmd;
            display.setText(currentInput);
            return;
        }
        else if(cmd.equals(".")){
            if(!currentInput.contains(".")){
                currentInput+=cmd;
                display.setText(currentInput);
            }
        }
        else if(cmd.equals("=")){
            calculate();
            op="";
            s=true;
            if (selOp!=null){
                selOp.setBackground(OP_NORMAL);
                selOp=null;
            }
            return;
        }
        else{
            if(!currentInput.isEmpty()){
                if (!op.isEmpty()){
                    calculate();
                }
                else{
                    acc=Double.parseDouble(currentInput);
                }
            }
            high((JButton)e.getSource());
            op=cmd;
            currentInput="";  
        }
    }

    private void high(JButton btn){
        if (selOp!=null){
            selOp.setBackground(OP_NORMAL);
        }
        btn.setBackground(OP_HIGHLIGHT);
        selOp=btn;
    }

    private void calculate(){
        if (currentInput.isEmpty()) return;

        double num= Double.parseDouble(currentInput);

        switch(op){
            case "+":
                acc+=num;
                break;
            case "-":
                acc-=num;
                break;
            case "*":
                acc*=num;
                break;
            case "/":
                if (num==0){
                    display.setText("Cannot Divide by Zero");
                    currentInput="";
                    op="";
                    return;
                }
                else{
                    acc/=num;
                }
                break;
            default:
                acc=num;
        }
        display.setText(String.valueOf(acc));
        currentInput="";
    }
    public static void main(String[] args){
        new Calculator();
    }

}
