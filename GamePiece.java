/**
 * Authors:    Willy Choi, Andrew Marone, Dervent Wright
 * Date:       March 17, 2014
 * Purpose:    This class mainly adds images to simulate game pieces
               and initializes game in default state.            
 */

import javax.swing.*;
import java.util.*;
import java.text.*;
import java.awt.*;
import java.awt.event.*;

public class GamePiece extends JButton
{
    //Global variables
    String pieceState;
    boolean highlighted = false;
    int x;
    int y;  

    ImageIcon whitePawn = new ImageIcon("Chess-Pawn-icon-white.png");
    ImageIcon blackPawn = new ImageIcon("Chess-Pawn-icon-black.png");
    ImageIcon whitePawnH = new ImageIcon("Chess-Pawn-icon-white-highlighted.png");
    ImageIcon blackPawnH = new ImageIcon("Chess-Pawn-icon-black-highlighted.png");
    ImageIcon blankH = new ImageIcon("Chess-Pawn-icon-blank-highlighted.png");

    public GamePiece(int xValue,int yValue)
    {

        x = xValue;
        y = yValue;
    }
    
    //Mutators
    
    /**
       Sets up a new game with pieces in orignal positions
    */
    public void setup()
    {
        //set up player 1 pieces
        if(y==1 ||y == 2)
        {
            pieceState = BoardGame.P1;
            this.setIcon(whitePawn);
        }
        //set up player 2 pieces
        else if(y == 7|| y== 8)
        {
            pieceState = BoardGame.P2;
            this.setIcon(blackPawn);
        }

        //set up blank spaces
        else
        {
            pieceState = "blank";
            this.setIcon(null);
        }

    }

    /**
       Removes highlighting of positions
     */
    public void clearHighlight()
    {
        if(highlighted)
        {
            highlighted = false;
            if( pieceState.equals(BoardGame.P1))
            {
                this.setIcon(whitePawn);
            }
            else if(pieceState.equals(BoardGame.P2))
            {
                this.setIcon(blackPawn);
            }
            else
            {
                this.setIcon(null);
            }
        }

    }
    
    /**
       Sets board x,y position to empty when a piece has been captured
     */
    public void setEmpty()
    {  
        pieceState = "blank";
        this.setIcon(null);
    }
    
    /**
       Sets turn to player 1
     */
    public void setPlayer1()
    {
        pieceState = BoardGame.P1;
    }
    
    /**
       Sets turn to player 2
     */
    public void setPlayer2()
    {
        pieceState = BoardGame.P2;
    }
   
    /**
       Activates pieces depending on player's turn
     */
    public void highlight()
    {
        highlighted = true;
        if( pieceState.equals(BoardGame.P1))
        {
            this.setIcon(whitePawnH);
        }
        else if(pieceState.equals(BoardGame.P2))
        {
            this.setIcon(blackPawnH);
        }
        else
        {
            this.setIcon(blankH);
        }  
    }

    
    //Accessors
    
    /**
       Gets state of a game piece
       @return    game piece state
     */
    public String getPieceState()
    {
        return pieceState;
    }
    
    /**
       Gets x coordinates
       @return    x coordinates
     */
    public int getXCoordinates()
    {
        return x;
    }
    
    /**
       Gets y coordinates 
       @return    y coordinates
     */
    public int getYCoordinates()
    {
        return y;
    }
    
    /**
       Checks if piece is highlighted and returns it
       @return    highlighted piece
     */
    public boolean getHighlighted()
    {
        return highlighted;

    }

   
}