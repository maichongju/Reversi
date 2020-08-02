package reversi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ComponentGraphic extends JPanel {
    private final int WIDTH = 200, HEIGHT = 800;
    protected final int STATUSWIDTH = WIDTH, STATUSHEIGHT = 600;
    protected final int CURRENTPIECEWIDTH = WIDTH, CURRENTPIECEHEIGHT = HEIGHT - STATUSHEIGHT;
    private StatusGraphic sg = new StatusGraphic();
    private CurrentPieceGraphic cpg = new CurrentPieceGraphic();
    private final Color background = new Color(0xF4F5C1);
    private Grid grid;
    public ComponentGraphic(Grid grid) {
        setSize(WIDTH,HEIGHT);
        this.grid = grid;
        this.setLayout(new BorderLayout());
        add(sg,BorderLayout.CENTER);
        add(cpg,BorderLayout.PAGE_END);
    }


    public void updateCount(final GridInfo info){
        sg.updateCount(info);
    }
    /**
     * function will pass the color to the CurrentPieceGraphic to update the current color piece
     * @param color java.awt
     */
    public void updateCurrentPiece(Color color){
        cpg.updateCurrentPiece(color);
    }

    /**
     * Force the size
     * @return Dimension of the size
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(WIDTH,HEIGHT);
    }

    class StatusGraphic extends JPanel{

        protected JTextField gridSizeTextField = new JTextField(10);
        private JButton newButton = new JButton("New Game");
        private JLabel blackCount = new JLabel("Black: ");
        private JLabel whiteCount = new JLabel("White:");


        public StatusGraphic(){
            setAlignmentX(Component.CENTER_ALIGNMENT);
            setBackground(new Color(0xF4F5C1));
            setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
            add(Box.createVerticalStrut(200));
            add(blackCount);
            add(whiteCount);
            add(Box.createVerticalStrut(30));
            add(gridSizeTextField);
            add(Box.createVerticalStrut(10));
            add(newButton);
            add(Box.createVerticalStrut(300));
            blackCount.setAlignmentX(3.0f);
            whiteCount.setAlignmentX(3.0f);
            newButton.addActionListener(new buttonListener());
        }

        /**
         * Update the count numebr of the game
         * @param info
         */
        public void updateCount(final GridInfo info){
            blackCount.setText("Black: " + info.Black);
            whiteCount.setText("White: " + info.White);
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(STATUSWIDTH,STATUSHEIGHT);
        }

        /**
         * Action listener for All the button
         */
        class buttonListener implements ActionListener{

            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    int size = Integer.valueOf(gridSizeTextField.getText());
                    if (size >= 4 && size % 2 ==0 && size <=30){
                        grid.init(size);
                    }else{
                        throw new Exception();
                    }
                }catch (Exception e0){
                    System.err.println("Not a valid number");
                    JOptionPane.showMessageDialog(null,"Not a valid number, must be even number and >=4, <=30"
                            ,"Error",JOptionPane.ERROR_MESSAGE,null);
                }
            }
        }
    }



    class CurrentPieceGraphic extends JComponent{
        Color pieceColor = Color.BLACK;
        private final String currentString = "Current Player";
        public void updateCurrentPiece(Color color){
            pieceColor = color;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(background);
            g.fillRect(0,0,CURRENTPIECEWIDTH,CURRENTPIECEHEIGHT);

            g.setColor(Color.BLACK);
            g.setFont(g.getFont().deriveFont(Font.BOLD));
            g.drawString(currentString,60,20);

            g.setColor(pieceColor);
            double x, y, piecewidth;
            x = 0.125 * CURRENTPIECEWIDTH;
            y = 0.175 * CURRENTPIECEWIDTH;
            piecewidth = CURRENTPIECEWIDTH - 0.25 * CURRENTPIECEWIDTH;
            g.fillOval((int) x, (int) y, (int) piecewidth, (int) piecewidth);
            g.setColor(Color.BLACK);
            g.drawOval((int) x, (int) y, (int) piecewidth, (int) piecewidth);

        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(CURRENTPIECEWIDTH,CURRENTPIECEHEIGHT);
        }
    }

}
