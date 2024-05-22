/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio;

import datos.CategoriaDAO;
import entidades.Categoria;
import java.util.List;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author USUARIO
 */
public class CategoriaControl {
    private final CategoriaDAO DATOS;
    private Categoria obj;
    private DefaultTableModel modeloTabla;
    public int registrosMostrados;
    
    public CategoriaControl(){
        this.DATOS = new CategoriaDAO();
        this.obj = new Categoria();
        this.registrosMostrados = 0;
    }
    
    public DefaultTableModel listar (String texto){
        List<Categoria> lista = new ArrayList();
        lista.addAll(DATOS.listar(texto));
        
        String[] titulos = {"Id","Nombre","Descripcion","Estado"};
        this.modeloTabla = new DefaultTableModel(null,titulos);
        
        String estado;
        String[] registro = new String[4];
        
        this.registrosMostrados = 0;
        
        for(Categoria item:lista){
            if(item.isActivo()){
                estado = "Activo";
            }else{
                estado = "Inactivo";
            }
            registro[0] = Integer.toString(item.getId());
            registro[1] = item.getNombre();
            registro[2] = item.getDescripcion();
            registro[3] = estado;
            this.modeloTabla.addRow(registro);
            this.registrosMostrados = this.registrosMostrados + 1;
        }
        return this.modeloTabla;
    }
    
    public String insertar (String nombre, String descripcion){
        if(DATOS.existe(nombre)){
            return "El registro ya existe";
        }else{
            obj.setNombre(nombre);
            obj.setDescripcion(descripcion);
            if(DATOS.insertar(obj)){
                return "OK";
            }else{
                return "Error en la insercion del registro";
            }
            
        }
    }
    
    public String actualizar(int id, String nombre, String nombreAnt, String descripcion) {
    // Asegurar que los parámetros básicos sean asignados al objeto antes de la lógica condicional
    obj.setId(id);
    obj.setNombre(nombre);
    obj.setDescripcion(descripcion);

    // Si el nombre no ha cambiado, simplemente intentamos actualizar
    if (nombre.equals(nombreAnt)) {
        if (DATOS.actualizar(obj)) {
            return "OK";
        } else {
            return "Error en la actualización";
        }
    } else {
        // Si el nombre ha cambiado, verificamos que no exista ya en la base de datos
        if (DATOS.existe(nombre)) {
            return "El registro ya existe";
        } else {
            if (DATOS.actualizar(obj)) {
                return "OK";
            } else {
                return "Error en la actualización";
            }
        }
    }
}

    
    public String desactivar(int id){
        if (DATOS.desactivar(id)){
            return "OK";
        }else{
            return "El registro no se puede desactivar";
        }
    }
    
    public String activar(int id){
        if(DATOS.activar(id)){
            return "OK";
        }else{
            return "El registro no se puede activar";
        }
    }
    
    public int total(){
        return DATOS.total();
    }
    
    public int totalMostrado(){
        return this.registrosMostrados;
    }
}
