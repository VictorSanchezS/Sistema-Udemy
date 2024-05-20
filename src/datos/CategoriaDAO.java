/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import database.Conexion;
import datos.interfaces.CrudSimpleInterface;
import entidades.Categoria;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

/**
 *
 * @author USUARIO
 */
public class CategoriaDAO implements CrudSimpleInterface<Categoria>{
    
    private final Conexion CON;
    private PreparedStatement ps;
    private ResultSet rs;
    private boolean resp;
    private String query;
    
    public CategoriaDAO(){
        CON = Conexion.getInstancia();
    }

    @Override
    public List<Categoria> listar(String texto) {
        List<Categoria> registros = new ArrayList();
        try {
            query = "SELECT * FROM categoria where nombre LIKE ?";
            ps = CON.connectar().prepareStatement(query);
            ps.setString(1,"%"+texto+"%");
            rs = ps.executeQuery();
            while(rs.next()){
                registros.add(new Categoria(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getBoolean(4)));
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Error al listar categorias: "+e.getMessage());
        } finally{
            ps = null;
            rs = null;
            CON.desconectar();
        }
        
        return registros;
    }

    @Override
    public boolean insertar(Categoria obj) {
        resp = false;
        try {
            query = "INSERT INTO categoria (nombre, descripcion, activo) VALUES (?,?,1)";
            ps = CON.connectar().prepareStatement(query);
            ps.setString(1, obj.getNombre());
            ps.setString(2, obj.getDescripcion());
            if(ps.executeUpdate() > 0){
                resp = true;
            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Error al insertar categoria: "+e.getMessage());
        } finally{
            ps = null;
            CON.desconectar();
        }
        
        return resp;
    }

    @Override
    public boolean actualizar(Categoria obj) {
        resp = false;
        try {
            query = "UPDATE categoria SET nombre = ?, descripcion = ? WHERE id = ?";
            ps = CON.connectar().prepareStatement(query);
            ps.setString(1, obj.getNombre());
            ps.setString(2, obj.getDescripcion());
            ps.setInt(3, obj.getId());
            if(ps.executeUpdate() > 0){
                resp = true;
            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Error al actualizar categoria: "+e.getMessage());
        } finally{
            ps = null;
            CON.desconectar();
        }
        
        return resp;
    }

    @Override
    public boolean desactivar(int id) {
        resp = false;
        try {
            query = "UPDATE categoria SET activo = 0 WHERE id = ?";
            ps = CON.connectar().prepareStatement(query);
            ps.setInt(1, id);
            if(ps.executeUpdate() > 0){
                resp = true;
            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Error al desactivar categoria: "+e.getMessage());
        } finally{
            ps = null;
            CON.desconectar();
        }
        
        return resp;
    }

    @Override
    public boolean activar(int id) {
        resp = false;
        try {
            query = "UPDATE categoria SET activo = 1 WHERE id = ?";
            ps = CON.connectar().prepareStatement(query);
            ps.setInt(1, id);
            if(ps.executeUpdate() > 0){
                resp = true;
            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Error al activar categoria: "+e.getMessage());
        } finally{
            ps = null;
            CON.desconectar();
        }
        
        return resp;
    }

    @Override
    public int total() {
        int totalRegistros = 0;
        try {
            query = "SELECT COUNT(id) FROM categoria";
            ps = CON.connectar().prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {                
                totalRegistros = rs.getInt("COUNT(id)");
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Error al contar total de registros en  categoria: "+e.getMessage());
        } finally{
            ps = null;
            rs = null;
            CON.desconectar();
        }
        
        return totalRegistros;
    }

    @Override
    public boolean existe(String texto) {
        resp = false;
        try {
            query = "SELECT nombre FROM categoria WHERE nombre = ?";
            ps = CON.connectar().prepareStatement(query);
            ps.setString(1, texto);
            rs = ps.executeQuery();
            rs.last();
            if(rs.getRow() > 0){
                resp = true;
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Error al encontrar registro: "+e.getMessage());
        } finally{
            ps = null;
            rs = null;
            CON.desconectar();
        }
        
        return resp;
    }
    
}
