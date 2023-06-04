package com.PersonaDAO;

import Model.Conexion;
import Model.Persona;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAO {

    private Conexion factoryConexion;

    public DAO(){
        this.factoryConexion = new Conexion();
    }
    public boolean register(Persona persona){
        try {
            String SQL = "INSERT INTO salones(nombre,semestre,asignatura,sede,salon,hora,fecha)"
                    + "values(?,?,?,?,?,?,?)";

            Connection connection = this.factoryConexion.getConnection();

            PreparedStatement sentence = connection.prepareStatement(SQL);

            sentence.setString(1, persona.getName() );
            sentence.setString(2, persona.getSemestre() );
            sentence.setString(3, persona.getAsignatura());
            sentence.setString(4, persona.getSede() );
            sentence.setString(5, persona.getSalon() );
            sentence.setString(6, persona.getHora() );
            sentence.setString(7, persona.getFecha() );

            sentence.executeUpdate();
            sentence.close();

            return true;

        }catch (Exception e){

            System.err.println("Error not Registered :(");
            System.err.println("Message Error:"+ e.getMessage());
            System.err.println("Detail:");
            e.printStackTrace();

            return false;

        }
    }//hh

    public boolean edit(Persona persona){

        try {
            String SQL = "UPDATE `salones` SET `id`='?' ,`nombre`='?',`semestre`='?',`asignatura`='?',`sede`='?',`salon`='?',`hora`='?',`fecha`='?' WHERE name=?";
            Connection connection = this.factoryConexion.getConnection();

            PreparedStatement sentence = connection.prepareStatement(SQL);

            sentence.setString(1, persona.getName() );
            sentence.setString(2, persona.getSemestre() );
            sentence.setString(3, persona.getAsignatura());
            sentence.setString(4, persona.getSede() );
            sentence.setString(5, persona.getSalon() );
            sentence.setString(6, persona.getHora() );
            sentence.setString(7, persona.getFecha() );

            sentence.executeUpdate();
            sentence.close();

            return true;

        }catch (Exception e){

            System.err.println("Error not Edit :(");
            System.err.println("Message Error:"+ e.getMessage());
            System.err.println("Detail:");
            e.printStackTrace();

            return false;
        }
    }

   public List<Persona> list()   {

        List<Persona> personaList = new ArrayList<>();

        try {

            String SQL = "SELECT * FROM salones";
            Connection connection = this.factoryConexion.getConnection();

            PreparedStatement sentence = connection.prepareStatement(SQL);
            ResultSet data = sentence.executeQuery();
            while(data.next()){

                Persona persona = new Persona();

                persona.setId(data.getInt(1));
                persona.setName(data.getString(2));
                persona.setSemestre(data.getString(3));
                persona.setAsignatura(data.getString(4));
                persona.setSede(data.getString(5));
                persona.setSalon(data.getString(6));
                persona.setHora(data.getString(7));
                persona.setFecha(data.getString(8));

                personaList.add(persona);
            }

            data.close();
            sentence.close();

        }catch (Exception e){

            System.err.println("Error not List :(");
            System.err.println("Message Error:"+ e.getMessage());
            System.err.println("Detail:");
            e.printStackTrace();

        }

        return personaList;
    }

    public boolean delete(int id){

        try {
            String SQL = "DELETE FROM salones WHERE id = ?";

            Connection connection = this.factoryConexion.getConnection();
            PreparedStatement sentence = connection.prepareStatement(SQL);
            sentence.setInt(1,id);
            sentence.executeUpdate();
            sentence.close();
            return true;

        }catch (Exception e){
            System.err.println("Error not Delete :(");
            System.err.println("Message Error:"+ e.getMessage());
            System.err.println("Detail:");
            e.printStackTrace();
            return false;

        }
    }
}
