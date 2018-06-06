/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testDB;

/**
 *
 * @author EXTmsouto
 */
public class testDatabase{
    public static void main(String[] args){
        SQLiteManager sm = SQLiteManager.getInstance();
        sm.init();
                
    }
    
}
