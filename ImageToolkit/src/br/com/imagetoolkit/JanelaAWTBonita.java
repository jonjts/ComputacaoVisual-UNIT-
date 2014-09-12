/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.imagetoolkit;

import br.com.imagetoolkit.*;
import br.com.imagetoolkit.util.Desenho;
import java.awt.Canvas;
import java.awt.Frame;
import java.awt.HeadlessException;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.xml.ws.ServiceMode;

/**
 *
 * @author 2091140052
 */
public class JanelaAWTBonita extends Desenho {

    private java.awt.Menu editar;
    private java.awt.Menu loadFoto;
    private Menu redirencionar;
    private java.awt.MenuBar menuBar;
    private java.awt.MenuItem miCarregarImg;
    private java.awt.MenuItem miCinza;
    private java.awt.MenuItem miPretoBranco;
    private java.awt.MenuItem miEnlarguecerImg;
    private MenuItem miReduzirImg;
    private MenuItem miReflexao;
    private ImageCanvas canvas;
    private File file;
    private JTabbedPane tab;
    private ArrayList<ImageCanvas> tabItens = new ArrayList<ImageCanvas>();
    private int countImg = 1;

    public JanelaAWTBonita() {
        super();
        canvas = new ImageCanvas(null);
        initComponents();
        // setCanvas(canvas);
        add(canvas);
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                int retorno = JOptionPane.showConfirmDialog(null,
                        "File ja existe, sobreescrever?",
                        "Confirmação",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.NO_OPTION);
                if (retorno == JOptionPane.NO_OPTION) {
                    System.exit(0);
                }
                Util.salvarBufferedImage(canvas.getImg(), "C:\\Users\\Public\\Pictures\\Sample Pictures\\temp.png");
                System.exit(0);
            }
        });
        setSize(250, 100);
    }

    private void initComponents() {
        criarMenuBar();
        criarTab();
    }

    private int createDialogSlider() {
        String retorno = JOptionPane.showInputDialog(this, "Informe o fator");
        int intRetorno = 0;
        try {
            intRetorno = Integer.parseInt(retorno);
        } catch (NumberFormatException numberFormatException) {
            JOptionPane.showMessageDialog(null, "Valor invalido.");
        }
        return intRetorno;
    }

    public void setCanvas(ImageCanvas canvas) {
        if (this.canvas != null) {
            remove(this.canvas);
        }
        this.canvas = canvas;
        add(canvas);
        setVisible(true);
        setSize(canvas.getImg().getWidth(), canvas.getImg().getHeight());
    }

    public ImageCanvas getCanvas() {
        return canvas;
    }

    private void exitForm(java.awt.event.WindowEvent evt) {
        System.exit(0);
    }

    private ImageCanvas updateCanvas(BufferedImage bufferedImage) {
        ImageCanvas ic = new ImageCanvas(bufferedImage);
        setCanvas(ic);
        return ic;
    }

    private void criarTab() {
        tab = new JTabbedPane();
        add(tab);
    }

    private void addTabIten(BufferedImage bufferedImage){
        ImageCanvas imageCanvas = updateCanvas(bufferedImage);
        tab.add("Imagen "+countImg++, imageCanvas);
        tabItens.add(imageCanvas);
    }

    private void criarMenuBar() throws HeadlessException {
        menuBar = new java.awt.MenuBar();
        loadFoto = new java.awt.Menu();
        miCarregarImg = new java.awt.MenuItem();
        editar = new java.awt.Menu();
        redirencionar = new java.awt.Menu();
        miCinza = new java.awt.MenuItem();
        miPretoBranco = new java.awt.MenuItem();
        miEnlarguecerImg = new java.awt.MenuItem();
        miReduzirImg = new java.awt.MenuItem();
        miReflexao = new java.awt.MenuItem();

        addWindowListener(new java.awt.event.WindowAdapter() {

            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });
        loadFoto.setLabel("Carregar Image");
        miCarregarImg.setLabel("Carregar");
        loadFoto.add(miCarregarImg);
        miCarregarImg.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                file = Util.selectImage();
                addTabIten(Util.carregarBufferedImage(file));
                //updateCanvas(Util.carregarBufferedImage(file));
            }
        });

        editar.setLabel("Editar");
        miCinza.setLabel("Cinza");
        editar.add(miCinza);
        miCinza.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                final BufferedImage bf = Util.carregarBufferedImage(file);
                BufferedImage cinza = Transformacao.transformeNivelCinza(bf, 0);
                updateCanvas(cinza);
            }
        });
        miPretoBranco.setLabel("Preto e branco");
        editar.add(miPretoBranco);
        miPretoBranco.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                final BufferedImage bf = Util.carregarBufferedImage(file);
                BufferedImage cinza = Binarizacao.binarize(bf, 0, 100);
                updateCanvas(cinza);
            }
        });
        miEnlarguecerImg.setLabel("Enlarguecer Imagem");
        redirencionar.add(miEnlarguecerImg);
        miEnlarguecerImg.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(ActionEvent e) {
                int fator = createDialogSlider();
                if (fator > 0) {
                    BufferedImage bfEnlargecida = ManipulacaoGeometrica.enlarguecerImagem(Util.carregarBufferedImage(file), fator);
                    updateCanvas(bfEnlargecida);
                }
            }
        });

        redirencionar.setLabel("Redirecionar");

        miReduzirImg.setLabel("Reduzir Imagem");
        redirencionar.add(miReduzirImg);
        miReduzirImg.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(ActionEvent e) {
                int fator = createDialogSlider();
                if (fator > 0) {
                    BufferedImage bfReduzida = ManipulacaoGeometrica.reduzirImagem(Util.carregarBufferedImage(file), fator);
                    updateCanvas(bfReduzida);
                }
            }
        });



        miReflexao.setLabel("Reflexonar");
        redirencionar.add(miReflexao);
        miReflexao.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(ActionEvent e) {
                BufferedImage bfReflexao = ManipulacaoGeometrica.reflexaoXInPlace(Util.carregarBufferedImage(file));
                updateCanvas(bfReflexao);
            }
        });

        menuBar.add(loadFoto);
        menuBar.add(editar);
        menuBar.add(redirencionar);
        setMenuBar(menuBar);
        pack();
    }
}
