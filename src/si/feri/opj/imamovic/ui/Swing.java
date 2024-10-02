package si.feri.opj.imamovic.ui;

import si.feri.opj.imamovic.prvi.*;
import si.feri.opj.imamovic.drugi.*;
import si.feri.opj.imamovic.tretji.*;
import si.feri.opj.imamovic.listeners.*;
import si.feri.opj.imamovic.exceptions.*;
import si.feri.opj.imamovic.comparators.*;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.*;
import java.time.*;
import java.time.format.*;
import java.util.*;
import java.util.zip.*;

public class Swing extends Component {
    private JPanel window;
    private JPanel panelContainer;
    private JPanel home;
    private JPanel article;
    private JButton home_article;
    private JButton article_home;
    private JButton home_depot;
    private JButton article_depot;
    private JPanel depot;
    private JButton depot_home;
    private JButton depot_article;
    private JPanel shipment;
    private JButton ship_home;
    private JButton ship_article;
    private JButton ship_depot;
    private JButton depot_ship;
    private JButton article_ship;
    private JButton home_ship;

    private JPanel sidePanel_article;
    private JPanel mainPanel_article;
    private JLabel labelArticle;
    private JPanel dashboard_Article;
    private JTextField nameArticle;
    private JTextField heightArticle;
    private JTextField widthArticle;
    private JTextField lengthArticle;
    private JTextField weightArticle;
    private JTextField priceArticle;
    private JButton ustvariArtikel;
    private JButton izbrisiArtikel;
    private JPanel content;
    private JPanel panelArticle;
    private JPanel tablePanelArticle;
    private JButton spremeniArtikel;
    private JPanel sidePanel_shipment;
    private JCheckBox checkBoxShipment;
    private JComboBox oznakaZaboja;
    private JTextField nameShipment;
    private JTextField lengthShipment;
    private JTextField widthShipment;
    private JTextField heightShipment;
    private JTextField dateShipment;
    private JTextField priceShipment;
    private JPanel mainPanel_shipment;
    private JTable tableShipment;
    private JPanel tablePanelShipment;
    private JTable tableDepot;
    private JTextField nameDepot;
    private JTextField locationDepot;
    private JTextField priceDepot;
    private JCheckBox cameraChecked;
    private JPanel tablePanelDepot;
    private JPanel listArticle;
    private JButton dodajArtikelVPosiljko;
    private JTextArea textAreaDepot;
    private JPanel listDepot;
    private JButton dodajPosiljkoVDepo;
    private JButton odstraniPosiljkoIzDepoja;
    private JButton izbrisiArtikelIzPosiljke;
    private JButton ustvariPosiljko;
    private JButton spremeniPosiljko;
    private JButton izbrisiPosiljko;
    private JTable tableArticle;
    private JComboBox tipPosiljka;
    private JComboBox tipDepo;
    private JButton ustvariDepo;
    private JButton spremeniDepo;
    private JButton izbrisiDepo;
    private DefaultTableModel tableModelArticle;
    private DefaultTableModel tableModelShipment;
    private DefaultTableModel tableModelDepot;

    FileOutputStream fosArtikel;
    FileOutputStream fosPosiljka;
    FileOutputStream fosDepo;

    JFrame frame = new JFrame("Sistem za upravljanje zalog");

    CardLayout cl = new CardLayout();

    private ArrayList<Artikel> articleList = new ArrayList<>();
    private ArrayList<Posiljka> shipmentList = new ArrayList<>();
    private ArrayList<Depo> depoList = new ArrayList<>();


    // Panel POSILJKA
    private JList<Artikel> listArticleOnShip = new JList<>();
    private JList<Artikel> secondListArticleOnShip = new JList<>();

    private JList<Posiljka> listShipOnDepo = new JList<>();
    private JList<Posiljka> secondListShipOnDepo = new JList<>();

    private DefaultListModel<Artikel> modelArticleOnShip = new DefaultListModel<>();
    private DefaultListModel<Artikel> secondModelArticleOnShip = new DefaultListModel<>();

    private DefaultListModel<Posiljka> modelShipOnDepo = new DefaultListModel<>();
    private DefaultListModel<Posiljka> secondModelShipOnDepo = new DefaultListModel<>();

    public Swing() {

        try {


            panelContainer.setLayout(cl);

            panelContainer.add(home, "home");
            panelContainer.add(article, "article");
            panelContainer.add(depot, "depot");
            panelContainer.add(shipment, "shipment");

            cl.show(panelContainer, "home");

            HomeListener homeListener = new HomeListener(cl, panelContainer);
            ArticleListener articleListener = new ArticleListener(cl, panelContainer);
            DepoListener depoListener = new DepoListener(cl, panelContainer);
            ShipListener shipListener = new ShipListener(cl, panelContainer);

            home_article.addActionListener(articleListener);
            home_depot.addActionListener(depoListener);
            home_ship.addActionListener(shipListener);

            article_home.addActionListener(homeListener);
            article_depot.addActionListener(depoListener);
            article_ship.addActionListener(shipListener);

            depot_home.addActionListener(homeListener);
            depot_article.addActionListener(articleListener);
            depot_ship.addActionListener(shipListener);

            ship_home.addActionListener(homeListener);
            ship_article.addActionListener(articleListener);
            ship_depot.addActionListener(depoListener);

            // Panel POSILJKA - add Article to Shipment
            listArticle.add(listArticleOnShip, BorderLayout.CENTER);
            listArticleOnShip.setModel(modelArticleOnShip);
            secondListArticleOnShip.setModel(secondModelArticleOnShip);

            // Panel DEPOT - add Shipment to Depot
            listDepot.add(listShipOnDepo, BorderLayout.CENTER);
            listShipOnDepo.setModel(modelShipOnDepo);
            secondListShipOnDepo.setModel(secondModelShipOnDepo);

            // TIP POŠILJKE - sort by Slovenian alphabet
            ArrayList<String> tipiPosiljeIzbira = new ArrayList<>();
            tipiPosiljeIzbira.add("ZABOJ");
            tipiPosiljeIzbira.add("PAKET");

            Collections.sort(tipiPosiljeIzbira, Collator.getInstance(new Locale("sl", "SI")));

            for (String tip : tipiPosiljeIzbira)
                tipPosiljka.addItem(tip);

            oznakaZaboja.setEnabled(false);

            tipPosiljka.addActionListener(e -> oznakaZaboja.setEnabled(tipPosiljka.getSelectedItem() != "PAKET") );


            // TIP DEPOJA - sort by Slovenian alphabet
            ArrayList<String> tipiDepoja = new ArrayList<>();
            tipiDepoja.add("NAVADNO SKLADIŠČE");
            tipiDepoja.add("PREMIUM SKLADIŠČE");

            Collections.sort(tipiDepoja, Collator.getInstance(new Locale("si", "SL")));

            for (String s : tipiDepoja)
                tipDepo.addItem(s);


            // OZNAKA ZABOJA - sort by Slovenian alphabet
            ArrayList<OznakaZaboja> oznake = new ArrayList<>();
            oznake.add(OznakaZaboja.LOMLJIVO);
            oznake.add(OznakaZaboja.VNETLJIVO);
            oznake.add(OznakaZaboja.BIOLOSKI);

            Collections.sort(oznake, new OznakaZabojaComparator());

            System.out.println("---------------------------\n oznake" + oznake);
            for (OznakaZaboja oznaka : oznake) {
                System.out.println(oznaka);
                oznakaZaboja.addItem(oznaka);
            };

            tipDepo.addActionListener(e -> {
                if (tipDepo.getSelectedIndex() == 0) {
                    cameraChecked.setEnabled(false);
                } else {
                    cameraChecked.setEnabled(true);
                }
            });

            cameraChecked.setEnabled(tipDepo.getSelectedIndex() != 0);

            initializeTableArticle();
            initializeTableShipment();
            initializeTableDepot();

            ustvariArtikel.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    createArticle();
                    modelArticleUpdate();
                }
            });

            spremeniArtikel.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    updateArticle();
                    modelArticleUpdate();
                }
            });
            izbrisiArtikel.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    deleteArticle();
                    modelArticleUpdate();
                }
            });

            ustvariPosiljko.addActionListener(new CreateShipment() );
            spremeniPosiljko.addActionListener(new UpdateShipment() );
            izbrisiPosiljko.addActionListener(new DeleteShipment() );

            dodajArtikelVPosiljko.addActionListener(e -> addArticleToShip() );
            izbrisiArtikelIzPosiljke.addActionListener(e -> removeArticleFromShip() );

            ustvariDepo.addActionListener(e -> createDepo());
            spremeniDepo.addActionListener(e -> updateDepo());
            izbrisiDepo.addActionListener(e -> deleteDepo());

            dodajPosiljkoVDepo.addActionListener(new AddShipmentToDepo() );
            odstraniPosiljkoIzDepoja.addActionListener(new DeleteShipmentFromDepo());

            try {

                ObjectInputStream oisArtikel = new ObjectInputStream(new GZIPInputStream(new FileInputStream("artikli.ser")));
                articleList = (ArrayList<Artikel>) oisArtikel.readObject();

                ObjectInputStream oisPosiljka = new ObjectInputStream(new GZIPInputStream(new FileInputStream("posiljke.ser")));
                shipmentList = (ArrayList<Posiljka>) oisPosiljka.readObject();

                ObjectInputStream oisDepo = new ObjectInputStream(new GZIPInputStream(new FileInputStream("depoji.ser")));
                depoList = (ArrayList<Depo>) oisDepo.readObject();

                refreshTableArticle();
                refreshTableShipment();
                refreshTableDepo();

                modelArticleUpdate();
                modelShipmentUpdate();

            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(this, e, "File Not Found Exception", JOptionPane.ERROR_MESSAGE);
                writeErrors(ErrorLogTypes.OPOZORILO, String.valueOf(e));
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, e, "??????????????????? IO Exception", JOptionPane.ERROR_MESSAGE);
                writeErrors(ErrorLogTypes.OPOZORILO, String.valueOf(e));
            } catch (ClassNotFoundException e) {
                JOptionPane.showMessageDialog(this, e, "Class Not Found Exception", JOptionPane.ERROR_MESSAGE);
                writeErrors(ErrorLogTypes.OPOZORILO, String.valueOf(e));
            }

            frame.add(panelContainer);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setSize(1600, 900);
            frame.setVisible(true);

        } catch (Error e) {
            JOptionPane.showMessageDialog(this, e, "ERROR", JOptionPane.ERROR_MESSAGE);
            writeErrors(ErrorLogTypes.KRITIČNO, String.valueOf(e));
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this, e, "RUNTIME EXCEPTION", JOptionPane.ERROR_MESSAGE);
            writeErrors(ErrorLogTypes.NAPAKA, String.valueOf(e));
        }
    }

    private class CreateShipment implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            createShipment();
            modelShipmentUpdate();
        }
    }

    private class UpdateShipment implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            updateShipment();
            modelShipmentUpdate();
        }
    }

    private class DeleteShipment implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            deleteShipment();
            modelShipmentUpdate();
        }
    }

    private class AddShipmentToDepo extends Component implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                addShipToDepo();
            } catch (SkladiscenjeException ex) {
                JOptionPane.showMessageDialog(this, ex, "Depot Error", JOptionPane.ERROR_MESSAGE);
                writeErrors(ErrorLogTypes.OPOZORILO, String.valueOf(e));
            } catch (OznakaZabojaException ex) {
                JOptionPane.showMessageDialog(this, ex, "\"Oznaka zaboja\" Error", JOptionPane.ERROR_MESSAGE);
                writeErrors(ErrorLogTypes.OPOZORILO, String.valueOf(e));
            }
        }
    }

    private class DeleteShipmentFromDepo implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            removeShipFromDepo();
        }
    }

    private void initializeTableArticle() {

        tableModelArticle = new DefaultTableModel();

        tableModelArticle.addColumn("NAZIV");
        tableModelArticle.addColumn("VIŠINA");
        tableModelArticle.addColumn("ŠIRINA");
        tableModelArticle.addColumn("DOLŽINA");
        tableModelArticle.addColumn("TEŽA");
        tableModelArticle.addColumn("CENA");

        tableArticle.setModel(tableModelArticle);
        tablePanelArticle.add(new JScrollPane(tableArticle));
    }

    private void initializeTableShipment() {

        tableModelShipment = new DefaultTableModel();

        tableModelShipment.addColumn("TIP POŠILJKE");
        tableModelShipment.addColumn("NAZIV");
        tableModelShipment.addColumn("VIŠINA");
        tableModelShipment.addColumn("ŠIRINA");
        tableModelShipment.addColumn("DOLŽINA");
        tableModelShipment.addColumn("DATUM ODPOSLANJA");
        tableModelShipment.addColumn("CENA");
        tableModelShipment.addColumn("DRAGOCENOST");
        tableModelShipment.addColumn("OZNAKA (zaboj)");
        tableModelShipment.addColumn("ARTIKLI");

        tableShipment.setModel(tableModelShipment);
        tablePanelShipment.add(new JScrollPane(tableShipment));
    }

    private void initializeTableDepot() {

        tableModelDepot = new DefaultTableModel();

        tableModelDepot.addColumn("TIP SKLADIŠČA");
        tableModelDepot.addColumn("NAZIV");
        tableModelDepot.addColumn("LOKACIJA");
        tableModelDepot.addColumn("CENA");
        tableModelDepot.addColumn("KAMERA (premium)");
        tableModelDepot.addColumn("POŠILJKE");

        tableDepot.setModel(tableModelDepot);
        tablePanelDepot.add(new JScrollPane(tableDepot));
    }


    public void createArticle() {
        try {
            String naziv = nameArticle.getText();
            Dimenzije dimenzije = (new Dimenzije(Double.parseDouble(heightArticle.getText()), Double.parseDouble(widthArticle.getText()), Double.parseDouble(lengthArticle.getText())));
            double teza = Double.parseDouble(weightArticle.getText());
            double cena = Double.parseDouble(priceArticle.getText());

            Artikel artikel = new Artikel(naziv, dimenzije, teza, cena);
            articleList.add(artikel);
            addToArticleTable(artikel);

            fileArtikel();

            writeErrors(ErrorLogTypes.ZAZNAMEK, "Dodan artikel.");

            System.out.println("-----\nARTIKEL LIST: \n" + articleList);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, e, "Number Format Exception", JOptionPane.ERROR_MESSAGE);
            writeErrors(ErrorLogTypes.OPOZORILO, String.valueOf(e));
        }
    }

    private void createShipment() {
        try {
            Posiljka posiljka;
            String naziv = nameShipment.getText();
            Dimenzije dimenzije = (new Dimenzije(Double.parseDouble(heightShipment.getText()), Double.parseDouble(widthShipment.getText()), Double.parseDouble(lengthShipment.getText())));
            LocalDate datum = LocalDate.parse(dateShipment.getText());
            double cena = Double.parseDouble(priceShipment.getText());
            boolean dragocenost = checkBoxShipment.isSelected();

            OznakaZaboja oznaka = (OznakaZaboja) oznakaZaboja.getSelectedItem();

            if (tipPosiljka.getSelectedItem() == "ZABOJ") {
                posiljka = new Zaboj(naziv, dimenzije, datum, oznaka, dragocenost, cena);
                posiljka.setTip("ZABOJ");
            } else {
                posiljka = new Paket(naziv, dimenzije, datum, dragocenost, cena);
                posiljka.setTip("PAKET");
            }

            shipmentList.add(posiljka);
            addToShipmentTable(posiljka);

            filePosiljka();

            writeErrors(ErrorLogTypes.ZAZNAMEK, "Dodana pošiljka.");

            System.out.println("-----\nPOSILJKA LIST: \n" + shipmentList);

        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "Prosim, vnesite datum v obliki: yyyy-mm-dd", "Date Format Error", JOptionPane.ERROR_MESSAGE);
            writeErrors(ErrorLogTypes.OPOZORILO, String.valueOf(e));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, e, "Number Format Error", JOptionPane.ERROR_MESSAGE);
            writeErrors(ErrorLogTypes.OPOZORILO, String.valueOf(e));
        }
    }

    public void createDepo() {
        try {
            String naziv = nameDepot.getText();
            String lokacija = locationDepot.getText();
            double cena = Double.parseDouble(priceDepot.getText());
            boolean kamera = cameraChecked.isSelected();

            Depo depo;

            if (tipDepo.getSelectedItem() == "NAVADNO SKLADIŠČE") {
                depo = new Skladisce(naziv, lokacija, cena);
            } else {
                depo = new PremiumSkladisce(naziv, lokacija, cena, kamera);
            }

            depoList.add(depo);
            addToDepoTable(depo);

            fileDepo();

            writeErrors(ErrorLogTypes.ZAZNAMEK, "Dodan depo.");

            System.out.println("-----\nDEPO LIST: \n" + depoList);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, e, "Number Format Error", JOptionPane.ERROR_MESSAGE);
            writeErrors(ErrorLogTypes.OPOZORILO, String.valueOf(e));
        }
    }

    private void updateArticle() {
        try {
            int selectedRow = tableArticle.getSelectedRow();
            if (selectedRow != -1) {
                Artikel artikel = articleList.get(selectedRow);
                artikel.setNaziv(nameArticle.getText());
                artikel.setDimenzije(new Dimenzije(
                        Double.parseDouble(heightArticle.getText()),
                        Double.parseDouble(widthArticle.getText()),
                        Double.parseDouble(lengthArticle.getText())
                ));
                artikel.setTeza(Double.parseDouble(weightArticle.getText()));
                artikel.setCena(Double.parseDouble(priceArticle.getText()));

                refreshTableArticle();
            } else {
                JOptionPane.showMessageDialog(null, "Izberite artikel za posodobitev.");
            }

            fileArtikel();
            writeErrors(ErrorLogTypes.ZAZNAMEK, "Spremljen artikel.");

            System.out.println(articleList);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, e, "Number Format Error", JOptionPane.ERROR_MESSAGE);
            writeErrors(ErrorLogTypes.OPOZORILO, String.valueOf(e));
        }
    }

    private void updateShipment() {
        try {
            int selectedRow = tableShipment.getSelectedRow();
            if (selectedRow != -1) {
                Posiljka posiljka = shipmentList.get(selectedRow);
                posiljka.setTip(tipPosiljka.getSelectedItem().toString());
                posiljka.setNaziv(nameShipment.getText());
                posiljka.setDimenzije(new Dimenzije(
                        Double.parseDouble(heightShipment.getText()),
                        Double.parseDouble(widthShipment.getText()),
                        Double.parseDouble(lengthShipment.getText())
                ));
                posiljka.setLocalDate(LocalDate.parse(dateShipment.getText()));
                posiljka.setCena(Double.parseDouble(priceShipment.getText()));
                posiljka.setDragocenost(checkBoxShipment.isSelected());

                if (posiljka instanceof Zaboj)
                    posiljka.setOznakaZaboja(OznakaZaboja.valueOf(oznakaZaboja.getSelectedItem().toString()));

                refreshTableShipment();
            } else {
                JOptionPane.showMessageDialog(null, "Izberite pošiljko za posodobitev.");
            }

            filePosiljka();
            writeErrors(ErrorLogTypes.ZAZNAMEK, "Spremljena pošiljka.");

            System.out.println(shipmentList);
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "Prosim, vnesite datum v obliki: yyyy-mm-dd", "Date Format Error", JOptionPane.ERROR_MESSAGE);
            writeErrors(ErrorLogTypes.OPOZORILO, String.valueOf(e));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, e, "Number Format Error", JOptionPane.ERROR_MESSAGE);
            writeErrors(ErrorLogTypes.OPOZORILO, String.valueOf(e));
        }
    }

    private void updateDepo() {
        try {
            int selectedRow = tableDepot.getSelectedRow();
            if (selectedRow != -1) {
                Depo depo = depoList.get(selectedRow);
                depo.setTipDepo(tipDepo.getSelectedItem().toString());
                depo.setNaziv(nameDepot.getText());
                depo.setLokacija(locationDepot.getText());
                depo.setCena(Double.parseDouble(priceDepot.getText()));

                if (depo instanceof PremiumSkladisce && tipDepo.getSelectedIndex() == 1) {
                    PremiumSkladisce ps = (PremiumSkladisce) depo;
                    ps.setKamera(cameraChecked.isSelected());
                } else if (depo instanceof Skladisce) cameraChecked.setSelected(false);

                refreshTableDepo();

            } else {
                JOptionPane.showMessageDialog(null, "Izberite depo za posodobitev.");
            }

            fileDepo();
            writeErrors(ErrorLogTypes.ZAZNAMEK, "Spremljen depo.");

            System.out.println(depoList);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, e, "Number Format Error", JOptionPane.ERROR_MESSAGE);
            writeErrors(ErrorLogTypes.OPOZORILO, String.valueOf(e));
        }
    }

    private void deleteArticle() {
        int selectedRow = tableArticle.getSelectedRow();
        if (selectedRow != -1) {
            articleList.remove(selectedRow);
            refreshTableArticle();
        } else {
            JOptionPane.showMessageDialog(null, "Izberite artikel za brisanje.");
        }

        fileArtikel();
        writeErrors(ErrorLogTypes.ZAZNAMEK, "Izbrisan artikel.");

        System.out.println(articleList);
    }

    private void deleteShipment() {
        int selectedRow = tableShipment.getSelectedRow();
        if (selectedRow != -1) {
            shipmentList.remove(selectedRow);
            refreshTableShipment();
        } else {
            JOptionPane.showMessageDialog(null, "Izberite pošiljko za brisanje.");
        }

        filePosiljka();
        writeErrors(ErrorLogTypes.ZAZNAMEK, "Izbrisana pošiljka.");

        System.out.println(shipmentList);
    }

    private void deleteDepo() {
        int selectedRow = tableDepot.getSelectedRow();
        if (selectedRow != -1) {
            depoList.remove(selectedRow);
            refreshTableDepo();
        } else {
            JOptionPane.showMessageDialog(null, "Izberite depo za brisanje.");
        }

        fileDepo();
        writeErrors(ErrorLogTypes.ZAZNAMEK, "Izbrisana pošiljka.");

        System.out.println(depoList);
    }

    private void fileArtikel() {
        ObjectOutputStream oosArtikel = null;
        try {
            oosArtikel = new ObjectOutputStream(new GZIPOutputStream(new FileOutputStream("artikli.ser")));
            oosArtikel.writeObject(articleList);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, e, "File Not Found Exception", JOptionPane.ERROR_MESSAGE);
            writeErrors(ErrorLogTypes.OPOZORILO, String.valueOf(e));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, e, "IO Exception", JOptionPane.ERROR_MESSAGE);
            writeErrors(ErrorLogTypes.OPOZORILO, String.valueOf(e));
        } finally {
            if (oosArtikel != null) {
                try {
                    oosArtikel.flush();
                    oosArtikel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    writeErrors(ErrorLogTypes.OPOZORILO, String.valueOf(e));
                }
            }
        }
    }

    private void filePosiljka() {
        ObjectOutputStream oosPosiljka = null;
        try {
            oosPosiljka = new ObjectOutputStream(new GZIPOutputStream(new FileOutputStream("posiljke.ser")));
            oosPosiljka.writeObject(shipmentList);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, e, "File Not Found Exception : Posiljka", JOptionPane.ERROR_MESSAGE);
            writeErrors(ErrorLogTypes.OPOZORILO, String.valueOf(e));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, e, "IO Exception : Posiljka", JOptionPane.ERROR_MESSAGE);
            writeErrors(ErrorLogTypes.OPOZORILO, String.valueOf(e));
        } finally {
            if (oosPosiljka != null) {
                try {
                    oosPosiljka.flush();
                    oosPosiljka.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    writeErrors(ErrorLogTypes.OPOZORILO, String.valueOf(e));
                }
            }
        }
    }

    private void fileDepo() {
        ObjectOutputStream oosDepo = null;
        try {
            oosDepo = new ObjectOutputStream(new GZIPOutputStream(new FileOutputStream("depoji.ser")));
            oosDepo.writeObject(depoList);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, e, "File Not Found Exception", JOptionPane.ERROR_MESSAGE);
            writeErrors(ErrorLogTypes.OPOZORILO, String.valueOf(e));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, e, "IO Exception", JOptionPane.ERROR_MESSAGE);
            writeErrors(ErrorLogTypes.OPOZORILO, String.valueOf(e));
        } finally {
            if (oosDepo != null) {
                try {
                    oosDepo.flush();
                    oosDepo.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    writeErrors(ErrorLogTypes.OPOZORILO, String.valueOf(e));
                }
            }
        }
    }

    private void addToArticleTable(Artikel artikel) {
        Object[] rowData = {
                artikel.getNaziv(),
                artikel.getDimenzije().getVisina(),
                artikel.getDimenzije().getSirina(),
                artikel.getDimenzije().getDolzina(),
                artikel.getTeza(),
                artikel.getCena()
        };
        tableModelArticle.addRow(rowData);
    }

    private void addToShipmentTable(Posiljka posiljka) {

        Object[] rowData = {
                posiljka.getTip(),
                posiljka.getNaziv(),
                posiljka.getDimenzije().getVisina(),
                posiljka.getDimenzije().getSirina(),
                posiljka.getDimenzije().getDolzina(),
                posiljka.getLocalDate(),
                posiljka.getCena(),
                posiljka.getDragocenost(),
                ((posiljka instanceof Zaboj) ? posiljka.getOznakaZaboja() : "X"),
                posiljka.prikaziArtikle()
        };
        tableModelShipment.addRow(rowData);
    }

    private void addToDepoTable(Depo depo) {

        Object[] rowData = {
                depo.getTipDepo(),
                depo.getNaziv(),
                depo.getLokacija(),
                depo.getCena(),
                (depo instanceof PremiumSkladisce ? ((PremiumSkladisce) depo).getKamera() : "X"),
                depo.prikaziPosiljke()
        };
        tableModelDepot.addRow(rowData);
    }

    private void refreshTableArticle() {
        tableModelArticle.setRowCount(0);
        for (Artikel artikel : articleList) {
            addToArticleTable(artikel);
        }
    }

    private void refreshTableShipment() {
        tableModelShipment.setRowCount(0);
        for (Posiljka posiljka : shipmentList) {
            addToShipmentTable(posiljka);
        }
    }

    private void refreshTableDepo() {
        tableModelDepot.setRowCount(0);
        for (Depo depo : depoList) {
            addToDepoTable(depo);
        }
    }

    private void addArticleToShip() {
        int selectedIndex = listArticleOnShip.getSelectedIndex();
        if (selectedIndex != -1) {
            int selectedRow = tableShipment.getSelectedRow();
            Artikel selectedArticle = listArticleOnShip.getSelectedValue();
            if (selectedRow != -1) {
                Posiljka posiljka = shipmentList.get(selectedRow);
                posiljka.dodajArtikel(selectedArticle);
                refreshTableShipment();
            } else {
                JOptionPane.showMessageDialog(null, "Izberite pošiljko.");
            }

            filePosiljka();

            writeErrors(ErrorLogTypes.ZAZNAMEK, "Dodan artikel v pošiljko.");

            System.out.println(shipmentList);

        } else {
            JOptionPane.showMessageDialog(null, "Izberite artikel, ki ga želite dodati pošiljki.");
        }
    }

    private void addShipToDepo() throws SkladiscenjeException, OznakaZabojaException {
        int selectedIndex = listShipOnDepo.getSelectedIndex();
        if (selectedIndex != -1) {
            int selectedRow = tableDepot.getSelectedRow();
            Posiljka selectedShip = listShipOnDepo.getSelectedValue();
            if (selectedRow != -1) {
                Depo depo = depoList.get(selectedRow);
                depo.skladisciPosiljko(selectedShip);
                refreshTableDepo();
            } else {
                JOptionPane.showMessageDialog(null, "Izberite depo.");
            }

            fileDepo();
            writeErrors(ErrorLogTypes.ZAZNAMEK, "Dodana pošiljka v depo.");

            System.out.println(depoList);

        } else {
            JOptionPane.showMessageDialog(null, "Izberite pošiljko, ki jo želite dodati depoju.");
        }
    }

    private void removeArticleFromShip() {
        int selectedIndex = listArticleOnShip.getSelectedIndex();
        if (selectedIndex != -1) {
            int selectedRow = tableShipment.getSelectedRow();
            Artikel selectedArticle = listArticleOnShip.getSelectedValue();
            if (selectedRow != -1) {
                Posiljka posiljka = shipmentList.get(selectedRow);
                posiljka.odstraniArtikel(selectedArticle);
                refreshTableShipment();
            } else {
                JOptionPane.showMessageDialog(null, "Izberite pošiljko.");
            }

            filePosiljka();
            writeErrors(ErrorLogTypes.ZAZNAMEK, "Odstranjen artikel iz pošiljke.");

            System.out.println(shipmentList);

        } else {
            JOptionPane.showMessageDialog(null, "Izberite artikel, ki ga želite odstraniti iz pošiljke.");
        }

    }

    private void removeShipFromDepo() {
        int selectedIndex = listShipOnDepo.getSelectedIndex();
        if (selectedIndex != -1) {
            int selectedRow = tableDepot.getSelectedRow();
            Posiljka selectedShip = listShipOnDepo.getSelectedValue();
            if (selectedRow != -1) {
                Depo depo = depoList.get(selectedRow);
                depo.odstraniPosiljko(selectedShip);
                refreshTableDepo();
            } else {
                JOptionPane.showMessageDialog(null, "Izberite depo.");
            }

            fileDepo();
            writeErrors(ErrorLogTypes.ZAZNAMEK, "Odstranjena pošiljka iz depoja.");

            System.out.println(depoList);

        } else {
            JOptionPane.showMessageDialog(null, "Izberite pošiljko, ki jo želite odstraniti iz depoja.");
        }

    }

    private void modelArticleUpdate() {
        modelArticleOnShip.removeAllElements();
        for (Artikel artikel : articleList)
            modelArticleOnShip.addElement(artikel);
    }

    private void modelShipmentUpdate() {
        modelShipOnDepo.removeAllElements();
        for (Posiljka posiljka : shipmentList)
            modelShipOnDepo.addElement(posiljka);
    }

    private void writeErrors(ErrorLogTypes elt, String s) {

        try (FileWriter errorLogFile = new FileWriter("error_log.txt", true)) {
            String formattedTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));

            switch (elt) {
                case NAPAKA:
                    s = "   " + s;
                    break;
                case ZAZNAMEK:
                    s = " " + s;
                    break;
                case KRITIČNO:
                    s = " " + s;
                    break;
                case OPOZORILO:
                    break;
                default:
                    break;
            }

            errorLogFile.write(LocalDate.now() + "  " + formattedTime + "  [" + elt + "]  " + s + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}