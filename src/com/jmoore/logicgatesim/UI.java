package com.jmoore.logicgatesim;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;

public class UI extends JFrame {

    private int[][] grid;
    private int tool = -1; //-1 = mouse; 0 = wire; 1 = not; 2 = and; 3 = nand; 4 = or; 5 = nor; 6 = xor; 7 = xnor;

    private JPanel inventoryP;
    private JPanel gridP;

    private JLabel[] buttons = new JLabel[8];

    public UI() {
        super("Logic Gate Sim - Created by Joshua Moore");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new FlowLayout(FlowLayout.LEADING));
        setLocationRelativeTo(null);
        setResizable(true);
        setSize(1100,1000);
        setMinimumSize(new Dimension(1100,1000));
        setVisible(false);
        setEnabled(true);

        create_inventory_panel();
        create_grid_panel();

        setVisible(true);
    }

    private void create_inventory_panel() {
        /* Setup for Panel */
        inventoryP = new JPanel(new GridBagLayout());
        GridBagConstraints inventoryGBC = new GridBagConstraints();
        inventoryGBC.insets = new Insets(10,10,10,10);

        /* Creating all the Labels */
        buttons[0] = new JLabel(new ImageIcon(getClass().getResource("wire.png")));//wire
        buttons[1] = new JLabel(new ImageIcon(getClass().getResource("not.png")));//not
        buttons[2] = new JLabel(new ImageIcon(getClass().getResource("not.png")));//and
        buttons[3] = new JLabel(new ImageIcon(getClass().getResource("not.png")));//nand
        buttons[4] = new JLabel(new ImageIcon(getClass().getResource("not.png")));//or
        buttons[5] = new JLabel(new ImageIcon(getClass().getResource("not.png")));//nor
        buttons[6] = new JLabel(new ImageIcon(getClass().getResource("not.png")));//xor
        buttons[7] = new JLabel(new ImageIcon(getClass().getResource("not.png")));//xnor

        /* Add the labels to the Panel */
        inventoryGBC.gridx = 0; inventoryGBC.gridy = 0;
        inventoryP.add(buttons[0],inventoryGBC);

        inventoryGBC.gridx = 0; inventoryGBC.gridy = 1;
        inventoryP.add(buttons[1],inventoryGBC);

        inventoryGBC.gridx = 0; inventoryGBC.gridy = 2;
        inventoryP.add(buttons[2],inventoryGBC);

        inventoryGBC.gridx = 0; inventoryGBC.gridy = 3;
        inventoryP.add(buttons[3],inventoryGBC);

        inventoryGBC.gridx = 1; inventoryGBC.gridy = 0;
        inventoryP.add(buttons[4],inventoryGBC);

        inventoryGBC.gridx = 1; inventoryGBC.gridy = 1;
        inventoryP.add(buttons[5],inventoryGBC);

        inventoryGBC.gridx = 1; inventoryGBC.gridy = 2;
        inventoryP.add(buttons[6],inventoryGBC);

        inventoryGBC.gridx = 1; inventoryGBC.gridy = 3;
        inventoryP.add(buttons[7],inventoryGBC);

        /* Decorate the panel */
        inventoryP.setBorder(new LineBorder(new Color(1,1,1)));

        /* Create clickable buttons */
        for(JLabel label : buttons) {
            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    onInventoryMouseClicked(e);
                }
            });
        }

        add(inventoryP);
    }

    private void create_grid_panel() {
        /* Setup for panel */
        gridP = new JPanel(new GridLayout(Core.GRID_SIZE,Core.GRID_SIZE));

        for(int i = 0; i < Core.GRID_SIZE * Core.GRID_SIZE; i++) {
            gridP.add(new JLabel(new ImageIcon(getClass().getResource("blank2.png"))));
        }

        for(int i = 0; i < gridP.getComponentCount(); i++) {
            gridP.getComponent(i).addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    onGridMouseClicked(e);
                }
            });
        }

        add(gridP);
    }

    private void onInventoryMouseClicked(MouseEvent e) {
        for(int i = 0; i < 8; i++) {
            if(e.getSource() == buttons[i]) {
                Toolkit toolkit = Toolkit.getDefaultToolkit();
                Image image = null;
                switch(i) {
                    case 0:
                        image = toolkit.getImage(getClass().getResource("wire.png"));
                        tool = 0;
                        break;
                    case 1:
                        image = toolkit.getImage(getClass().getResource("not.png"));
                        tool = 1;
                        break;
                    case 2:
                        image = toolkit.getImage(getClass().getResource("and.png"));
                        tool = 2;
                        break;
                    case 3:
                        image = toolkit.getImage(getClass().getResource("nand.png"));
                        tool = 3;
                        break;
                    case 4:
                        image = toolkit.getImage(getClass().getResource("or.png"));
                        tool = 4;
                        break;
                    case 5:
                        image = toolkit.getImage(getClass().getResource("xor.png"));
                        tool = 5;
                        break;
                    case 6:
                        image = toolkit.getImage(getClass().getResource("xor.png"));
                        tool = 6;
                        break;
                    case 7:
                        image = toolkit.getImage(getClass().getResource("xnor.png"));
                        tool = 7;
                        break;
                }

                Cursor c = toolkit.createCustomCursor(image, new Point(0, 0), "img");
                setCursor(c);
            }
        }
    }

    private void onGridMouseClicked(MouseEvent e) {
        GridSearchLoop:
        for(int i = 0; i < gridP.getComponentCount(); i++) {
            if(e.getSource() == gridP.getComponent(i)) {
                int x = i % Core.GRID_SIZE;
                int y = i / Core.GRID_SIZE;

                for(int a = x; a < x + 3; a++) {
                    for(int b = y; b < y  +3; b++) {
                        if(grid[a][b] == 2) break GridSearchLoop;
                    }
                }

                BufferedImage original = null;
                try {
                    switch(tool) {
                        case -1:

                            break;
                        case 0:
                            original = ImageIO.read(getClass().getResource("wire.png"));
                            break;
                    }
                } catch(Exception ex) {
                    ex.printStackTrace();
                }

                BufferedImage im_1 = original.getSubimage(0,0,11,11);
                BufferedImage im_2 = original.getSubimage(11,0,11,11);
                BufferedImage im_3 = original.getSubimage(22,0,10,11);
                BufferedImage im_4 = original.getSubimage(0,11,11,11);
                BufferedImage im_5 = original.getSubimage(11,11,11,11);
                BufferedImage im_6 = original.getSubimage(22,11,10,11);
                BufferedImage im_7 = original.getSubimage(0,22,11,10);
                BufferedImage im_8 = original.getSubimage(11,22,11,10);
                BufferedImage im_9 = original.getSubimage(22,22,10,10);

                ((JLabel)gridP.getComponent(i)).setIcon( new ImageIcon(im_1));
                ((JLabel)gridP.getComponent(i + 1)).setIcon( new ImageIcon(im_2));
                ((JLabel)gridP.getComponent(i + 2)).setIcon( new ImageIcon(im_3));
                ((JLabel)gridP.getComponent(i + Core.GRID_SIZE)).setIcon( new ImageIcon(im_4));
                ((JLabel)gridP.getComponent(i + Core.GRID_SIZE + 1)).setIcon( new ImageIcon(im_5));
                ((JLabel)gridP.getComponent(i + Core.GRID_SIZE + 2)).setIcon( new ImageIcon(im_6));
                ((JLabel)gridP.getComponent(i + Core.GRID_SIZE * 2)).setIcon( new ImageIcon(im_7));
                ((JLabel)gridP.getComponent(i + Core.GRID_SIZE * 2 + 1)).setIcon( new ImageIcon(im_8));
                ((JLabel)gridP.getComponent(i + Core.GRID_SIZE * 2 + 2)).setIcon( new ImageIcon(im_9));

                for(int a = x; a < x + 3; a++) {
                    for(int b = y; b < y  +3; b++) {
                        grid[a][b] = 2;
                    }
                }

                Core.add_gate(tool, x, y, 1);

                gridP.repaint();
                setCursor(Cursor.getDefaultCursor());
                tool = -1;
                System.out.println("INFO.UI: Updated Coordinate (" + x + "," + y + ")");
            }
        }
    }

    int check_coords(int x, int y) {
        System.out.println("INFO.UI: Checking coordinates (" + x + "," + y + ")");
        if(grid[x][y] == 1) return 1;
        else if (grid[x][y] == 0) return 0;
        else return -1;
    }

    void update_grid(int x, int y, int value) {
        System.out.println("INFO.UI: Updating coordinate (" + x + "," + y + ") with value " + value);
        grid[x][y] = value;
    }

    void set_grid(int width, int height) {
        try {
            if(grid.length < 1) grid = new int[width][height];
        } catch(NullPointerException ex) {
            grid = new int[width][height];
        }
    }
}
