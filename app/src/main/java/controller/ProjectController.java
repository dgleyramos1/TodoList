/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Project;
import util.ConnectionFactory;

/**
 *
 * @author dgley
 */
public class ProjectController {
    
    
    public void save(Project project) throws SQLException{
        
        String sql = "INSERT INTO projects"
                + "(name, "
                + "description, "
                + "createdAt, "
                + "updatedAt) "
                + "VALUES (?, ?, ?, ?)";
        
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            //Criando conexão com banco de dados
            connection = ConnectionFactory.getConnection();
            
            //Prepara a query
            statement = connection.prepareStatement(sql);
            
            //Setando os valores
            statement.setString(1, project.getName());
            statement.setString(2, project.getDescription());
            statement.setDate(3, new Date(project.getCreatedAt().getTime()));
            statement.setDate(4, new Date(project.getUpdatedAt().getTime()));
            
            
            //executando query
            statement.execute();
            
        } catch(Exception ex){
            throw new RuntimeException("Erro ao inserir o projeto" + ex.getMessage(), ex);
        } finally {
            //fechando conexões(banco de dados e com statement)
            ConnectionFactory.closeConnection(connection, statement);
        }
    }
    
    public void update(Project project){
        
        String sql = "UPDATE projects SET "
                + "name = ?, "
                + "description = ?, "
                + "createdAt = ?, "
                + "updatedAt = ?, "
                + "WHERE id = ?";
        
        Connection connection = null;
        PreparedStatement statement = null;
        
        try{
            
            //estabelecenco conexão com o banco de dados
            connection = ConnectionFactory.getConnection();
            
            //preparando a qurey
            statement = connection.prepareStatement(sql);
            
            //setando os valores do statement
            statement.setString(1, project.getName());
            statement.setString(2, project.getDescription());
            statement.setDate(3, new Date(project.getCreatedAt().getTime()));
            statement.setDate(4, new Date(project.getUpdatedAt().getTime()));
            statement.setInt(5, project.getId());
            
            //executando query
            statement.execute();
            
        } catch (Exception ex){
            throw new RuntimeException("Erro ao atualizar projeto" + ex.getMessage(), ex);
        } finally {
            //fechando conexões(banco de dados e com statement)
            ConnectionFactory.closeConnection(connection, statement);
        }
    }
    
    public void removeById(int idProject){
        
        String sql = "DELETE FROM projects WHERE id = ?";
        
        Connection connection = null;
        PreparedStatement statement = null;
        
        try{
            
            //estabelecenco conexão com o banco de dados
            connection = ConnectionFactory.getConnection();
            
            //preparando a qurey
            statement = connection.prepareStatement(sql);
            
            //setando os valores do statement
            statement.setInt(1, idProject);
            
            //executando query
            statement.execute();
            
        } catch (Exception ex){
            throw new RuntimeException("Erro ao deletar o projeto" + ex.getMessage(), ex);
        } finally {
            //fechando conexões(banco de dados e com statement)
            ConnectionFactory.closeConnection(connection, statement);
        }
    }
    
    public List<Project> getAll(){
        
        String sql = "SELECT * FROM projects";
        
        List<Project> projects = new ArrayList<>();
        
        Connection connection = null;
        PreparedStatement statement = null;
        
        //Classe que vai recuperar os dados do banco de dados
        ResultSet resultSet = null;
        
        try{
            //estabelecenco conexão com o banco de dados
            connection = ConnectionFactory.getConnection();
            //preparando a qurey
            statement = connection.prepareStatement(sql);
            //executando query
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                
                Project project = new Project();
                
                project.setId(resultSet.getInt("id"));
                project.setName(resultSet.getString("name"));
                project.setDescription(resultSet.getString("description"));
                project.setCreatedAt(resultSet.getDate("cretedAt"));
                project.setUpdatedAt(resultSet.getDate("updatedAt"));
                
                //adicionando o projeto dentro da lista de projetos
                projects.add(project);

                
            }
            
        } catch (Exception ex){
            throw new RuntimeException("Erro ao buscar projetos" + ex.getMessage(), ex);
        } finally {
            //fechando conexões(banco de dados, statement e resultSet)
            ConnectionFactory.closeConnection(connection, statement, resultSet);
        }
        
        
        return projects;
    }
    
    
}
