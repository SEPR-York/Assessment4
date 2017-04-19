package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.graphics.Color;

/**
 * @author Gandhi-Inc.
 * @version Assessment 3
 *          An executable version of the game can be found at: http://gandhi-inc.me/downloads/assessment3.jar
 *          Our website is: www.gandhi-inc.me
 * @since Assessment 3
 */
public class College {

  /**
   * The numeric representation of the college
   *
   * DERWENT: 1
   * LANGWITH: 2
   * VANBURGH: 3
   * JAMES: 4
   * WENTWORTH: 5
   * HALIFAX: 6
   * ALCUIN: 7
   * GOODRICKE: 8
   * CONSTANTINE: 9
   */
  private int ID;

  /**
   * The name of the College.
   */
  private String Name;

  /**
  * The color of the College
  */

  private Color color;

  /**
   * The symbol of the college
   */
  private Image logo;

  /**
   * The texture encoding the symbol of the college
   */
  private Texture logoTexture;

  /**
   * The constructor for the College class
   * This will assign a name and a logo to the College based on the ID provided
   *
   * @param college The Name of the college.
   */
  public College(String college){

      //Given the college string initialise some of the important variables for the game to display properly
    if (college == "Derwent")
    {
        this.Name = "Derwent";
        this.logoTexture = new Texture("image/Derwent.png");
        this.color = Color.BLUE;
    }
    else if (college == "Langwith")
    {
    	this.Name = "Langwith";
        this.logoTexture = new Texture("image/Langwith.png");
        this.color = Color.CHARTREUSE;
    }
    else if (college == "Vanburgh")
    {
    	this.Name = "Vanburgh";
        this.logoTexture = new Texture("image/Vanburgh.png");
        this.color = Color.TEAL;
    }
    else if (college == "James")
    {
    	this.Name = "James";
        this.logoTexture = new Texture("image/James.png");
        this.color = Color.CYAN;
    }
    else if (college == "Wentworth")
    {
    	this.Name = "Wentworth";
        this.logoTexture = new Texture("image/Wentworth.png");
        this.color = Color.MAROON;
    }
    else if (college == "Halifax")
    {
    	this.Name = "Halifax";
        this.logoTexture = new Texture("image/Halifax.png");
        this.color = Color.YELLOW;
    }
    else if (college == "Alcuin")
    {
    	this.Name = "Alcuin";
        this.logoTexture = new Texture("image/Alcuin.png");
        this.color = Color.RED;
    }
    else if (college == "Goodricke")
    {
    	this.Name = "Goodricke";
        this.logoTexture = new Texture("image/Goodricke.png");
        this.color = Color.GREEN;
    }
    else if (college == "Constantine")
    {
        this.Name = "Constantine";
        this.logoTexture = new Texture("image/Constantine.png");
        this.color = Color.PINK;
    }

    this.logo = new Image(logoTexture);
    //Map the college's associated logo texture to an image object

  }


  /**
   * Returns the college's assigned name
   *
   * @return String The college's name
   */
  public String getName() 
  {
    return this.Name;
  }

  /**
   * Returns the college's associated ID
   *
   * @return Integer The college's associated ID
   */
  public int getID() 
  {
    return this.ID;
  }

  /**
  * Returns the Color object for this college
  *
  * @return Color The color associated with the college
  */
  public Color getColor()
  {
      return color;
  }

  /**
   * Returns an Image object with the texture of the college's logo mapped to it
   *
   * @return Image Icon representing the college
   */
  public Image getLogo() 
  {
    return this.logo;
  }

  /**
   * Returns the texture file encoding the college's logo
   *
   * @return Texture The texture encoding the college's logo
   */
  public Texture getLogoTexture() 
  {
    return logoTexture;
  }
}
