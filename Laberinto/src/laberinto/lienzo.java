/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package laberinto;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import javax.imageio.ImageIO;

import javax.swing.JOptionPane;

/**
 *
 * @author david
 */
public class lienzo extends javax.swing.JPanel implements Runnable {

    int[][] matrizGeneral;
    int f = 0;
    int radiobotones;

    public lienzo() {
        initComponents();
        reinicio();
    }

    Thread hilo;
    static int e = 3, s = 4, filas = 10, columnas = 10, comp = 1;
    int fil_entrada = 3, col_entrada = 0, filsalida = 8, colsalida = 9;
    int x = 0, y = 0, termino = 0;
    BufferedImage personaje, puerta_salida, muro;//atributode tipo bufferedImagen para cargar en memoria la imagen o las imagenes  
    //Class targeClass = getClass();

    URL pers = getClass().getResource("imagenes/robot.png");
    URL dor = getClass().getResource("imagenes/puerta.png");
    URL mu = getClass().getResource("imagenes/paredNueva.png");

    public void reinicio() {
        int[][] copia = {{0, 1, 0, 0, 1, 0, 0, 0, 0, 0},
        {0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
        {1, 1, 0, 1, 1, 0, 1, 0, 0, 0},
        {e, 0, 0, 0, 1, 0, 1, 0, 0, 0},
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {1, 0, 1, 0, 1, 0, 1, 0, 0, 0},
        {1, 0, 1, 1, 0, 0, 1, 0, 1, 0},
        {1, 0, 0, 0, 0, 1, 1, 0, 1, 1},
        {1, 0, 1, 0, 0, 1, 0, 0, 0, s},
        {1, 1, 1, 1, 1, 1, 0, 1, 1, 1}};

        try {
            personaje = ImageIO.read(pers);
            puerta_salida = ImageIO.read(dor);
            muro = ImageIO.read(mu);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "No se cargo imagen por" + e.getMessage());
        }

        matrizGeneral = copia;
        hilo = new Thread(this);
        fil_entrada = 3;
        col_entrada = 0;
        filsalida = 8;
        colsalida = 9;
        f = 1;
        termino = 0;
        repaint();
    }

    public void Aleatorio() {
        System.out.println("Aleatorio");
        for (int i = 0; i < matrizGeneral.length; i++) {
            for (int j = 0; j < matrizGeneral.length; j++) {
                int dato = (int) (Math.random() * 1.99);
                if (i == fil_entrada && j == col_entrada) {
                    matrizGeneral[i][j] = e;
                } else if (i == filsalida && j == colsalida) {
                    matrizGeneral[i][j] = s;
                } else {
                    matrizGeneral[i][j] = dato;
                }
            }
        }

        //generando camino aleatorio
        int k = fil_entrada, l = col_entrada;
        while (k < filsalida || l < colsalida) {
            int camino = (int) (Math.random() * 1.9);

            if (camino == 0 && colsalida > 0) {
                matrizGeneral[k][++l] = 0;
            } else if (filsalida > k) {
                matrizGeneral[++k][l] = 0;
            } else {
                matrizGeneral[k][++l] = 0;
            }
        }
        matrizGeneral[filsalida][colsalida] = s;

        try {
            personaje = ImageIO.read(pers);
            puerta_salida = ImageIO.read(dor);
            muro = ImageIO.read(mu);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "No se cargo imagen por" + e.getMessage());
        }

        //matrizGeneral=copia;
        hilo = new Thread(this);
        fil_entrada = 3;
        col_entrada = 0;
        filsalida = 8;
        colsalida = 9;
        f = 1;
        termino = 0;

        repaint();

    }

    public void paint(Graphics g) {
        if (f >= 1) {
            g.setColor(getBackground());
            g.fillRect(0, 0, getWidth(), getHeight());
            for (int i = 0; i < matrizGeneral.length; i++) {
                for (int j = 0; j < matrizGeneral.length; j++) {
                    g.setColor(Color.BLACK);
                    if (matrizGeneral[i][j] == 0 || matrizGeneral[i][j] == 8) {
                        g.drawRect(j * 40, i * 40, 40, 40);
                    } else if (matrizGeneral[i][j] == 1) {
                        g.drawImage(muro, j * 40, i * 40, 40, 40, this);
                    } else if (matrizGeneral[i][j] == e) {
                        g.drawImage(personaje, j * 40, i * 40, 40, 40, this);
                    } else if (matrizGeneral[i][j] == s) {
                        g.drawImage(puerta_salida, j * 40, i * 40, 40, 40, this);
                    } else if (matrizGeneral[i][j] == 5) {
                        g.setColor(Color.BLACK);
                        g.fillRect(j * 40, i * 40, 40, 40);
                        g.setColor(Color.WHITE);
                        g.fillRect(j * 40, i * 40, 40, 40);
                    }

                }

            }
        }

    }

    public boolean existeCamino(int fila, int columna) {

        //si esta fuera de la matriz 
        if (fila < 0 || fila >= filas || columna < 0 || columna >= columnas) {
            return false;
        }

        // se encuentra con un obstaculo en este caso 5 que puede ser el reemplazo de la entrada  3 por el 5 
        if (matrizGeneral[fila][columna] == 1 || matrizGeneral[fila][columna] == 5) {
            return false;

        }

        return true;
    }

    //CODIGO para resolver el laberinto
    //fila_entrada y columna entrada 
    public boolean resolver(int fil, int col) {
        boolean salida = false;
        int abajo = 0, derecha = 0, izquierda = 0, arriba = 0;

        try {
            Thread.sleep(200);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }

        matrizGeneral[fil][col] = 5;

//si encontro la salida entonces retorne solo true  
        if (fil == filsalida && col == colsalida) {
            System.out.println("--return true---");
            //salida=true;
            return true;
        }

        //abajo    
        if (!salida && existeCamino(fil + 1, col)) {

            System.out.println("abajo");
            System.out.println((fil + 1) + " " + col);
            matrizGeneral[fil + 1][col] = e;
            repaint();
            //salida=(resolver(fil+1, col)==false)?false : true;
            salida = resolver(fil + 1, col);//falso v verdadero
            System.out.println("salida" + salida);

        }

//derecha        
        if (!salida && existeCamino(fil, col + 1)) {
            System.out.println("derecha");
            System.out.println(fil + " " + (col + 1));
            matrizGeneral[fil][col + 1] = e;
            repaint();
            salida = resolver(fil, col + 1);
            System.out.println("salida" + salida);
        }

        //izquierda
        if (!salida && existeCamino(fil, col - 1)) {
            System.out.println("izquierda");
            matrizGeneral[fil][col - 1] = e;
            repaint();
            salida = resolver(fil, col - 1);
            System.out.println("salida" + salida);
        }
//arriba        

        if (!salida && existeCamino(fil - 1, col)) {

            System.out.println("arriba+");
            matrizGeneral[fil - 1][col] = e;
            repaint();
            salida = resolver(fil - 1, col);
            System.out.println("salida" + salida);
        }

        return salida;

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });
        setLayout(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        //JOptionPane.showMessageDialog(this, "boton presionado");
        x = evt.getX() / 40;
        y = evt.getY() / 40;

        if (matrizGeneral[y][x] != e && matrizGeneral[y][x] != s && f == 1) {

            if (evt.getButton() == evt.BUTTON1 && radiobotones == 3) {
                matrizGeneral[y][x] = 1;
            } else if (evt.getButton() == evt.BUTTON3 && radiobotones == 3) {
                matrizGeneral[y][x] = 0;
            } else if (evt.getButton() == evt.BUTTON1 && radiobotones == 1) {

                matrizGeneral[fil_entrada][col_entrada] = 0;
                matrizGeneral[y][x] = e;
                fil_entrada = y;
                col_entrada = x;
            }
        }
        if (evt.getButton() == evt.BUTTON1 && radiobotones == 2) {
            System.out.println("x:" + x + "y" + y);
            matrizGeneral[filsalida][colsalida] = 0;
            matrizGeneral[y][x] = s;
            filsalida = y;
            colsalida = x;

        }
        repaint();
    }//GEN-LAST:event_formMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    @Override
    public void run() {
        termino = 1;
        if (resolver(fil_entrada, col_entrada)) {
            JOptionPane.showMessageDialog(this, "Felicitacione lo lograste");
        } else {
            termino = 1;
            JOptionPane.showMessageDialog(this, "No hay salida");
            //JOptionPane.showMessageDialog(this, "Felicitacione lo lograste");

        }
    }

}
