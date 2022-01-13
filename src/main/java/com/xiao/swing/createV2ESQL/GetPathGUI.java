package com.xiao.swing.createV2ESQL;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class GetPathGUI extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    JButton doZip;
    JButton unZip;
    JButton browseInput;
    JTextField inputFile;
    JTextField message;
    private static String inputDir;

    public GetPathGUI() {

        setTitle("ExcelToESQL");
        setLocation(600, 300);
        setDefaultCloseOperation(3);
        this.doZip = new JButton("生成");
        this.doZip.setBackground(Color.orange);

        this.browseInput = new JButton("选择文件");
        this.browseInput.setBackground(Color.orange);

        this.inputFile = new JTextField(20);
        this.inputFile.setEditable(true);
        this.inputFile.setText("请选择文件");
        this.inputFile.selectAll();
        this.inputFile.setBackground(Color.pink);

        this.message = new JTextField(20);
        this.message.setEditable(false);
        this.message.setBackground(Color.gray);
        this.message.setForeground(Color.CYAN);
        getContentPane().setLayout(new FlowLayout());

        getContentPane().add(this.inputFile);
        getContentPane().add(this.browseInput);

        getContentPane().add(this.doZip);
        getContentPane().add(this.message);
        getContentPane().setBackground(Color.gray);

        this.doZip.setActionCommand("doZip");

        this.browseInput.setActionCommand("browseInput");

        this.doZip.addActionListener(this);

        this.browseInput.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("browseInput")) {
            JFileChooser fc = new JFileChooser();
            fc.setFileSelectionMode(2);
            fc.setCurrentDirectory(new File("D:/"));
            fc.showOpenDialog(this);
            this.inputFile.setText(fc.getSelectedFile().getName());
            inputDir = fc.getSelectedFile().getParentFile().getAbsolutePath();
        }
        else if (e.getActionCommand().equals("doZip"))
        {
            if (GetElePath.setPath(inputDir + this.inputFile.getText())){
                //System.out.println(this.inputFile.getText());
                this.message.setText("成功:" + inputDir + "\\" + this.inputFile.getText().replace(".xls", "_ESQL.txt").replace(".xlsx", "_ESQL.txt"));
            }
            else {
                this.message.setText("失败:请检查文档格式.");
            }

        }

        repaint();
    }

    public static void main(String[] args){

        GetPathGUI myZip = new GetPathGUI();
        myZip.setSize(300, 150);
        myZip.setVisible(true);
    }
}