/**
 * Authors:    Willy Choi, Andrew Marone, Dervent Wright
 * Date:       March 17, 2014
 * Purpose:    This program allows two users to play a simple boardgame, Breakthrough. 
 */

import javax.swing.*;
import java.util.*;
import java.text.*;
import java.awt.*;
import java.awt.event.*;

public class BoardGame extends JFrame
{
    String turn;
    String txtTurn;
    boolean highlighted = false;
    int activePiece;
    ArrayList<GamePiece> pieceArray = new ArrayList<GamePiece>();
    int piecesTakenP1;
    int piecesTakenP2;
    int winsP1 = 0;
    int winsP2 = 0;
    public static final String P1 = "It is Player1's turn";
    public static final String P2 = "It is Player2's turn";
    public static final String gameOver = "Game Over!";
    JTextField txtP1Wins = new JTextField(2);
    JTextField txtP1PiecesTaken = new JTextField();
    JTextField txtP2Wins = new JTextField();
    JTextField txtP2PiecesTaken = new JTextField();
    JPanel pnlBoard;
    JMenuItem newAction;
    JMenuItem quitAction;
    JMenuItem aboutAction;
    JMenuItem rulesAction;
    int[] winningP1Array = new int[]{7,15,23,31,39,47,55,63};
    int[] winningP2Array = new int[]{0,8,16,24,32,40,48,56};
    JLabel lblTurn = new JLabel("rgdg");
    
    /**
       Default constructor which only calls the initialize method
    */
    public BoardGame()
    {
        initialize();
    }
    
    /**
       Boardgame execution begins here by creating an instance of BoardGame class
    */
    public static void main(String [] args)
    {
        new BoardGame();
    }
    
    /**
       This method sets up the major GUI components to create actual
       visual representation of the boardgame.
    */
    public void initialize()
    {
        this.setSize(800,800);
        this.setLayout(new BorderLayout());

        JPanel pnlScoreBoard = new JPanel();
        pnlScoreBoard.setLayout(new BorderLayout());

        //Menu
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        newAction = new JMenuItem("New Game");
        quitAction = new JMenuItem("Quit");
        fileMenu.add(newAction);
        fileMenu.add(quitAction);
        menuBar.add(fileMenu);

        JMenu aboutMenu = new JMenu("About");
        aboutAction = new JMenuItem("About");
        rulesAction = new JMenuItem("Rules");
        aboutMenu.add(aboutAction);
        aboutMenu.add(rulesAction);
        menuBar.add(aboutMenu);

        //Creates Listeners For JMenuItems
        new pieceListener();

        //player 1 score
        JPanel pnlP1 = new JPanel();
        pnlP1.setLayout(new GridLayout(2,2));

        JLabel lblP1Wins = new JLabel("Player 1 Wins: ");
        txtP1Wins.setEditable(false);
        pnlP1.add(lblP1Wins);
        pnlP1.add(txtP1Wins);

        JLabel lblP1PiecesTaken = new JLabel("Player 1 Pieces Taken: ");
        txtP1PiecesTaken.setEditable(false);
        pnlP1.add(lblP1PiecesTaken);
        pnlP1.add(txtP1PiecesTaken);
        pnlScoreBoard.add(pnlP1,BorderLayout.WEST);

        //Player 2 score
        JPanel pnlP2 = new JPanel();
        pnlP2.setLayout(new GridLayout(2,2));

        JLabel lblP2Wins = new JLabel("Player 2 Wins: ");
        txtP2Wins.setEditable(false);
        pnlP2.add(lblP2Wins);
        pnlP2.add(txtP2Wins);

        JLabel lblP2PiecesTaken = new JLabel("Player 2 Pieces Taken: ");
        txtP2PiecesTaken.setEditable(false);
        pnlP2.add(lblP2PiecesTaken);
        pnlP2.add(txtP2PiecesTaken);

        pnlScoreBoard.add(pnlP2,BorderLayout.EAST);

        //Center panel for turn
        JPanel pnlTurn = new JPanel();
        lblTurn = new JLabel(turn);
        pnlTurn.add(lblTurn);
        pnlScoreBoard.add(pnlTurn,BorderLayout.CENTER);

        this.add(pnlScoreBoard,BorderLayout.NORTH);

        //create game board
        pnlBoard = new JPanel();
        pnlBoard.setLayout(new GridLayout(8,8));
        this.add(pnlBoard,BorderLayout.CENTER);
        addPieces();
        startGame();

        //Create Game Board Numbering
        //Create X Labels
        JPanel pnlXNumbers = new JPanel();
        pnlXNumbers.setLayout(new GridLayout(8,0));
        JLabel lblX1 = new JLabel("X1",SwingConstants.CENTER);
        JLabel lblX2 = new JLabel("X2",SwingConstants.CENTER);
        JLabel lblX3 = new JLabel("X3",SwingConstants.CENTER);
        JLabel lblX4 = new JLabel("X4",SwingConstants.CENTER);
        JLabel lblX5 = new JLabel("X5",SwingConstants.CENTER);
        JLabel lblX6 = new JLabel("X6",SwingConstants.CENTER);
        JLabel lblX7 = new JLabel("X7",SwingConstants.CENTER);
        JLabel lblX8 = new JLabel("X8",SwingConstants.CENTER);
        pnlXNumbers.add(lblX1);
        pnlXNumbers.add(lblX2);
        pnlXNumbers.add(lblX3);
        pnlXNumbers.add(lblX4);
        pnlXNumbers.add(lblX5);
        pnlXNumbers.add(lblX6);
        pnlXNumbers.add(lblX7);
        pnlXNumbers.add(lblX8);
        this.add(pnlXNumbers,BorderLayout.WEST);

        //Create Y Labels
        JPanel pnlYNumbers = new JPanel();
        pnlYNumbers.setLayout(new GridLayout(0,8));
        JLabel lblY1 = new JLabel("Y1",SwingConstants.CENTER);
        JLabel lblY2 = new JLabel("Y2",SwingConstants.CENTER);
        JLabel lblY3 = new JLabel("Y3",SwingConstants.CENTER);
        JLabel lblY4 = new JLabel("Y4",SwingConstants.CENTER);
        JLabel lblY5 = new JLabel("Y5",SwingConstants.CENTER);
        JLabel lblY6 = new JLabel("Y6",SwingConstants.CENTER);
        JLabel lblY7 = new JLabel("Y7",SwingConstants.CENTER);
        JLabel lblY8 = new JLabel("Y8",SwingConstants.CENTER);
        pnlYNumbers.add(lblY1);
        pnlYNumbers.add(lblY2);
        pnlYNumbers.add(lblY3);
        pnlYNumbers.add(lblY4);
        pnlYNumbers.add(lblY5);
        pnlYNumbers.add(lblY6);
        pnlYNumbers.add(lblY7);
        pnlYNumbers.add(lblY8);
        this.add(pnlYNumbers,BorderLayout.SOUTH);

        //final setup
        this.setJMenuBar(menuBar);
        this.pack();
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
    
    /**
       This method creates the initial set up of the boardgame
       with all counters cleared, pieces in default positions
       and sets turn to player 1.
    */
    public void startGame()
    {

        piecesTakenP1 = 0;
        piecesTakenP2 = 0;
        for(int i = 0;i <pieceArray.size();i++)
        {
            pieceArray.get(i).setEnabled(true);
            pieceArray.get(i).setup();
        }
        turn = P1;
        lblTurn.setText("rfgd");
        highlighted = false;
        updateCounters();
    }
    
    /**
       Method adds graphical pieces to board.
    */
    public void addPieces()
    {
        int pieceCount = 0;
        for(int x = 1;x <9;x++)
        {
            for(int y = 1; y<9;y++)
            {
                pieceArray.add( new GamePiece(x,y));
                pieceArray.get(pieceCount).addActionListener(new pieceListener(pieceCount,x,y));
                pnlBoard.add(pieceArray.get(pieceCount));
                pieceCount++;
            }
        }
    }
   
    /**
       Method clears highlighted available moves. 
    */
    public void clearHighlights()
    {
        for(int i = 0;i <pieceArray.size();i++)
        {
            pieceArray.get(i).clearHighlight();
        }  
    }
    
    /**
       Method switches turn automatically after a player has
       already made a move.
    */
    public void changeTurn()
    {
        if(turn.equals(P1))
        {
            turn = P2;
            updateCounters();

        }
        else if(turn.equals(P2))
        {
            turn = P1;
            updateCounters();
        }
    }
    
    /**
       Method sets the number of wins and pieces taken
       by both players. 
    */
    public void updateCounters()
    {
        String strWin1 = String.valueOf(winsP1);
        String strWin2 = String.valueOf(winsP2);
        String strTake1 = String.valueOf(piecesTakenP1);
        String strTake2 = String.valueOf(piecesTakenP2);
        lblTurn.setText("<html><h1>"+turn+"</h1></html>");
        txtP1Wins.setText(strWin1);
        txtP1PiecesTaken.setText(strTake1);
        txtP2Wins.setText(strWin2);
        txtP2PiecesTaken.setText(strTake2);

    }

    /**
        Private inner class which creates action listeners
        for game pieces and adds listeners to menu items.
    */
    private class pieceListener implements ActionListener
    {
        int xLocation;
        int yLocation;
        int pieceId;
        public pieceListener(int id,int x, int y)
        {
            xLocation = x;
            yLocation = y;
            pieceId = id;
        }
        
        //create listeners for JMenuItems
        public pieceListener()
        {
            newAction.addActionListener(this);
            quitAction.addActionListener(this);
            aboutAction.addActionListener(this);
            rulesAction.addActionListener(this);
        }

        /**
           Method moves active game piece into space clicked,
           empties active piece space and clears highlight.
           @param    moving   space piece is moved from   
           @param    landing  space piece is moved into  
        */
        public void pieceMove(int moving,int landing)
        {
            if( pieceArray.get(moving).getPieceState().equals(P1))
            {
                pieceArray.get(moving).setEmpty();
                pieceArray.get(landing).setPlayer1();
            }
            else if( pieceArray.get(moving).getPieceState().equals(P2))
            {
                pieceArray.get(moving).setEmpty();
                pieceArray.get(landing).setPlayer2();
            }
            highlighted = false;
        }

        /**
           Method that determines action taken when a player clicks a space.
           It carries out the following conditions:
           1. Checks which player's turn it is.
           2. If valid spaces/captures are available for player,
              the spaces/pieces are highlighted and the player can make move as appropriate.
        */
        public void firstClick()
        {
            //player1 turn
            if(turn.equals(P1))
            {
                //player1 clicks on own piece
                if(pieceArray.get(pieceId).getPieceState().equals(P1))
                {
                    activePiece = pieceId;
                    highlighted = true;
                    //highlight left diagonal space if possible 
                    if(pieceId-7 > 0 && !pieceArray.get(pieceId-7).getPieceState().equals(P1))
                    {
                        pieceArray.get(pieceId-7).highlight();
                    }
                    //highlight front space if possible
                    if(pieceArray.get(pieceId+1).getPieceState().equals("blank"))
                    {
                        pieceArray.get(pieceId+1).highlight();
                    }
                    //highlight right diagonal space if possible
                    if(pieceId+9 < 64 && !pieceArray.get(pieceId+9).getPieceState().equals(P1))
                    {
                        pieceArray.get(pieceId+9).highlight();
                    }
                }
                //if player1 clicks on player2 piece
                else if(pieceArray.get(pieceId).getPieceState().equals(P2))
                {
                    String message = String.format("Not your turn! Clicked on player 2 piece on [X%d,Y%d]",xLocation,yLocation);
                    System.err.println(message);
                    JOptionPane.showMessageDialog(null,message,"Error",JOptionPane.ERROR_MESSAGE);
                }

                //if player clicks on empty space
                else if(pieceArray.get(pieceId).getPieceState().equals("blank"))
                {
                    String message = String.format("Oops! There is no piece on [X%d,Y%d]",xLocation,yLocation);
                    System.err.println(message);
                    JOptionPane.showMessageDialog(null,message,"Error",JOptionPane.ERROR_MESSAGE);
                }
            }//end player1 click

            else if (turn.equals(P2))
            {
                //player2 clicks on own piece
                if(pieceArray.get(pieceId).getPieceState().equals(P2))
                {
                    activePiece = pieceId;
                    highlighted = true;
                    //highlight left diagonal space if possible 
                    if(pieceId+7 < 64 && !pieceArray.get(pieceId+7).getPieceState().equals(P2))
                    {
                        pieceArray.get(pieceId+7).highlight();
                    }
                    //highlight front space if possible
                    if(pieceArray.get(pieceId-1).getPieceState().equals("blank"))
                    {
                        pieceArray.get(pieceId-1).highlight();
                    }
                    //highlight right diagonal space if possible
                    if(pieceId-9 >= 0 && !pieceArray.get(pieceId-9).getPieceState().equals(P2))
                    {
                        pieceArray.get(pieceId-9).highlight();
                    }
                }
                //if player2 clicks on player1 piece
                else if(pieceArray.get(pieceId).getPieceState().equals(P1))
                {
                    String message = String.format("Not your turn! Clicked on player1 piece on [X%d,Y%d]",xLocation,yLocation);
                    System.err.println(message);
                    JOptionPane.showMessageDialog(null,message,"Error",JOptionPane.ERROR_MESSAGE);
                }

                //if player clicks on empty space
                else if(pieceArray.get(pieceId).getPieceState().equals("blank"))
                {
                    String message = String.format("Oops! There is no piece on [X%d,Y%d]",xLocation,yLocation);
                    System.err.println(message);
                    JOptionPane.showMessageDialog(null,message,"Error",JOptionPane.ERROR_MESSAGE);
                }
            }//end player2 click
        } 
        
        /**
           Method which creates action listeners for GUI features
           such as menu items and entire board    
        */
        public void actionPerformed(ActionEvent e)
        {
            if(e.getSource().equals(newAction))
            {
                String message = String.format("This Will Start A New Game %n Are You Sure?");
                int reply = JOptionPane.showConfirmDialog(null,message,"New Game",JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION)
                {
                    startGame();
                    System.out.println("New Game Started");
                }
            }
            else if(e.getSource().equals(quitAction))
            {
                System.exit(0);
            }
            else if(e.getSource().equals(aboutAction))
            {
                String message = String.format("Created By:%nAndrew Marone%nDervent Wright%nWilly Choi");
                JOptionPane.showMessageDialog(null,message,"About",JOptionPane.INFORMATION_MESSAGE);
            }
            else if(e.getSource().equals(rulesAction))
            {
                String message = String.format("Win by moving one piece to the opposite side. Pieces move one space " +
                        "%n forward or diagonally forward, and capture diagonally forward.");
                JOptionPane.showMessageDialog(null,message,"Rules",JOptionPane.PLAIN_MESSAGE);
            }
            //Start of turn phase
            else if(!highlighted)
            {
                firstClick();     
            }//end !highlighted

            else if(highlighted)
            {
                //if the space clicked is a highlighted space
                if(pieceArray.get(pieceId).getHighlighted())
                {
                    if(turn.equals(P1))
                    {
                        if(pieceArray.get(pieceId).getPieceState().equals(P2))
                        {
                            piecesTakenP1++;
                            if(piecesTakenP1 == 16)
                            {
                                winsP1++;
                                String message = "Player 1 wins!";
                                JOptionPane.showMessageDialog(null,message,"Winner!",JOptionPane.PLAIN_MESSAGE);
                                System.out.println("win");
                                for(int b = 0;b <pieceArray.size();b++)
                                {
                                    pieceArray.get(b).setEnabled(false);
                                }  

                            }
                        }
                        for (int i = 0; i < winningP1Array.length; i++) 
                        {
                            if (winningP1Array[i] == pieceId) 
                            {
                                winsP1++;
                                String message = "Player 1 wins!";
                                JOptionPane.showMessageDialog(null,message,"Winner!",JOptionPane.PLAIN_MESSAGE);
                                System.out.println("win");
                                for(int b = 0;b <pieceArray.size();b++)
                                {
                                    pieceArray.get(b).setEnabled(false);
                                }  
                            }
                        }
                    }
                    else if(turn.equals(P2))
                    {
                        if(pieceArray.get(pieceId).getPieceState().equals(P1))
                        {
                            piecesTakenP2++;
                            if(piecesTakenP2==16)
                            {
                                winsP2++;
                                String message = "Player 2 wins!";
                                JOptionPane.showMessageDialog(null,message,"Winner!",JOptionPane.PLAIN_MESSAGE);
                                System.out.println("win");
                                for(int b = 0;b <pieceArray.size();b++)
                                {
                                    pieceArray.get(b).setEnabled(false);
                                } 
                            }
                        }
                        for (int i = 0; i < winningP2Array.length; i++) 
                        {
                            if (winningP2Array[i] == pieceId) 
                            {
                                winsP2++;
                                String message = "Player 2 wins!";
                                JOptionPane.showMessageDialog(null,message,"Winner!",JOptionPane.PLAIN_MESSAGE);
                                System.out.println("win");
                                for(int b = 0;b <pieceArray.size();b++)
                                {
                                    pieceArray.get(b).setEnabled(false);
                                }  
                            }
                        }
                    }
                    pieceMove(activePiece,pieceId);
                    clearHighlights();
                    activePiece = 0;
                    changeTurn(); 
                }
                //if button clicked during highlighted phase is not highlighted
                else
                {
                    //if not highlighted but current player's piece - treat as phase one click
                    if(pieceId == activePiece)
                    {}
                    else if(pieceArray.get(pieceId).getPieceState()== turn)
                    {  
                        highlighted = false;
                        clearHighlights();
                        firstClick();
                    }
                    else if(turn == P1 && pieceId == activePiece+1)
                    {
                        String message = String.format("Oops! Cannot move piece from [X%d,Y%d] to [X%d,Y%d] because there is already a piece there",xLocation,yLocation,pieceArray.get(activePiece).getXCoordinates(),pieceArray.get(activePiece).getYCoordinates());
                        System.err.println(message);
                        JOptionPane.showMessageDialog(null,message,"Error",JOptionPane.ERROR_MESSAGE);
                    }
                    else if(turn == P2 && pieceId == activePiece-1)
                    {
                        String message = String.format("Oops! Cannot move piece from [X%d,Y%d] to [X%d,Y%d] because there is already a piece there",xLocation,yLocation,pieceArray.get(activePiece).getXCoordinates(),pieceArray.get(activePiece).getYCoordinates());
                        System.err.println(message);
                        JOptionPane.showMessageDialog(null,message,"Error",JOptionPane.ERROR_MESSAGE);
                    }

                    //if not own piece - send error
                    else
                    {
                        String message = String.format("Oops! A piece at [X%d,Y%d] cannot move to [X%d,Y%d]",pieceArray.get(activePiece).getXCoordinates(),pieceArray.get(activePiece).getYCoordinates(),xLocation,yLocation);
                        System.err.println(message);
                        JOptionPane.showMessageDialog(null,message,"Error",JOptionPane.ERROR_MESSAGE);
                    }

                }

            }

        }//end of action listener method
    } 
}//end of main BoardGame class