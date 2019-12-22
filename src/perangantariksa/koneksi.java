/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package perangantariksa;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author NanK
 */
class koneksi {
     public static Connection conn=null;
    
    public void konek() throws ClassNotFoundException, SQLException{
       Class.forName("org.gjt.mm.mysql.Driver"); 
       String url="jdbc:mysql://localhost/tubes";
       String user="root";
       String pass="";
       conn = DriverManager.getConnection(url,user,pass);
    }
    
}