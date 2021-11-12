package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.swing.JOptionPane;

public class SteamController implements SteamI {
	
	String ano;
	String mes;
	double valor;
	
	public SteamController(String ano, String mes, double valor) {
		this.ano = ano;
		this.mes = mes;
		this.valor = valor;
	}

	public SteamController() {
		super();
	}

    public void readFile(int ano, String mes, double media, String path, String name) throws IOException {
        File dir = new File(path);
        File arq = new File(path, name);      

        if ( arq.exists() && arq.isFile() ) {
            FileInputStream fluxo = new FileInputStream(arq);
            InputStreamReader leitor = new InputStreamReader(fluxo);
            BufferedReader buffer = new BufferedReader(leitor);
            String line = buffer.readLine();

            while (line != null) {
                String [] phrase;
                phrase = line.split(",");
                
                for (String word: phrase) {
                    if ( word.equalsIgnoreCase(mes) ) {
                        int anos = Integer.parseInt(phrase[1]);
                        double medias = Double.parseDouble(phrase[3]);
                        if ( anos == ano && medias >= media ) {
                            System.out.println(phrase[0]+" | "+phrase[3]);
                        }
                    }
                }
                line = buffer.readLine();
            }
            
            buffer.close();
            leitor.close();
            fluxo.close();
        } 
        else {
            throw new IOException("Arquivo inválido");
        }
    }
    
    public void createFile(int ano, String mes, String path, String name) throws IOException {
        File dir = new File(path);
        File arq = new File(path, name);
        if ( dir.exists() && dir.isDirectory() ) {
            boolean existe = false;
            if ( arq.exists() ) {
                existe = true;
            }
            String conteudo = geraTxt(ano, mes);
            FileWriter fileWriter = new FileWriter(arq, existe);
            PrintWriter print = new PrintWriter(fileWriter);
            print.write(conteudo);
            print.flush();
            print.close();
            fileWriter.close();
            JOptionPane.showMessageDialog(null,"Arquivo salvo em: "+ path);
        }
        else {
            throw new IOException("Diretorio invalido");
        }
    }

   
    private String geraTxt(int ano, String mes) throws IOException {
        String arquivo = "SteamCharts.csv";
        File arq = new File(arquivo); 
        StringBuffer buffer = new StringBuffer();

        if ( arq.exists() && arq.isFile() ) {
            FileInputStream fluxo = new FileInputStream(arq);
            InputStreamReader leitor = new InputStreamReader(fluxo);
            BufferedReader buffers = new BufferedReader(leitor);
            String line = buffers.readLine();

            while (line != null) {
                String [] phrase;
                phrase = line.split(",");
                
                for (String word: phrase) {
                    if ( word.equalsIgnoreCase(mes) ) {
                        int anos = Integer.parseInt(phrase[1]);
                        if ( anos == ano) {
                            buffer.append(phrase[0]+";"+phrase[3]+"\r\n");
                        }
                    }
                }
                line = buffers.readLine();
            }
            buffers.close();
            leitor.close();
            fluxo.close();
        }
        return buffer.toString();
    }
}