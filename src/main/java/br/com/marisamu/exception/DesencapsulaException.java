/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.marisamu.exception;

import java.sql.SQLException;

/**
 *
 * @author renan
 */
public class DesencapsulaException {

    public static String desencapsula(Throwable ex) {
        Throwable ee = ex.getCause();
        Throwable th = ex;
        while ((ee != null) && ((ee = ee.getCause()) != null)) {
            th = ee;
        }
        String mensagem = "";
        try {
            SQLException e = (SQLException) th;
            mensagem = e.getNextException().getMessage();
            e.printStackTrace();
        } catch (Exception exc) {
            mensagem = th.getMessage();
            exc.printStackTrace();
        }
        return mensagem;
    }
}
