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
    
    public String actualizar (int id, String nombre, String nombreAnt, String descripcion){
        if(nombre.equals(nombreAnt)){
            obj.setId(id);
            obj.setNombre(nombre);
            obj.setDescripcion(descripcion);
            if(DATOS.actualizar(obj)){
                return "OK";
            }
            else{
                return "Error en la actuzalicacion";
            }
        }else{
            if(DATOS.existe(nombre)){
                return "El registro ya existe";
            }else{
                obj.setNombre(nombre);
                obj.setDescripcion(descripcion);
                if(DATOS.actualizar(obj)){
                    return "OK";
                }else{
                    return "Error en la actualizacion";
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
            return "Ok";
        }else{
            return "No se puede desactivar";
        }
    }
    
    public int total(){
        return DATOS.total();
    }
    
    public int totalMostrado(){
        return this.registrosMostrados;
    }
}
