/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package yogi_bear;
import java.awt.*;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
//import yogi_bear
/**
 *
 * @author Abdullah Othman
 */
public class Main {
     public static void main(String[] args) {
        try {
            new YogiBearGui();
        } catch (HeadlessException ex) {
            Logger.getLogger(YogiBearGui.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(YogiBearGui.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
