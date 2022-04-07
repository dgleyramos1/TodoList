/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Task;
import util.ConnectionFactory;

/**
 *
 * @author dgley
 */
public class TaskController {

    public void save(Task task) {

        String sql = "INSERT INTO tasks (idProject,"
                + "name,"
                + "description,"
                + "completed,"
                + "notes,"
                + "deadline,"
                + "createdAt,"
                + "updatedAt) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            
            //estabelecenco conexão com o banco de dados
            connection = ConnectionFactory.getConnection();
            
            //preparando a qurey
            statement = connection.prepareStatement(sql);
            
            //setando os valores do statement
            statement.setInt(1, task.getIdProject());
            statement.setString(2, task.getName());
            statement.setString(3, task.getDescription());
            statement.setBoolean(4, task.isIsCompleted());
            statement.setString(5, task.getNotes());
            statement.setDate(6, new Date(task.getDeadline().getTime()));
            statement.setDate(7, new Date(task.getCreatedAt().getTime()));
            statement.setDate(8, new Date(task.getUpdatedAt().getTime()));
            
            //executando a query
            statement.execute();
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao salvar a tarefa "
                    + ex.getMessage(), ex);
        } finally {
            //Fechando conexões (banco de dados e statement)
            ConnectionFactory.closeConnection(connection, statement);
        }

    }

    public void update(Task task) {

        String sql = "UPDATE tasks SET "
                + "idProject = ?, "
                + "name = ?, "
                + "description = ?, "
                + "notes = ?, "
                + "completed = ?, "
                + "deadline = ?, "
                + "createdAt = ?, "
                + "updateAt = ?, "
                + "WHERE id = ?";

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            //estabelecenco conexão com o banco de dados
            connection = ConnectionFactory.getConnection();
            
            //preparando a qurey
            statement = connection.prepareStatement(sql);
            
            //setando os valores do statement
            statement.setInt(1, task.getIdProject());
            statement.setString(2, task.getName());
            statement.setString(3, task.getDescription());
            statement.setString(4, task.getNotes());
            statement.setBoolean(5, task.isIsCompleted());
            statement.setDate(6, new Date(task.getDeadline().getTime()));
            statement.setDate(7, new Date(task.getCreatedAt().getTime()));
            statement.setDate(8, new Date(task.getUpdatedAt().getTime()));
            statement.setInt(9, task.getId());
            
            //executando a query
            statement.execute();
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao atualizar a tarefa"
                    + ex.getMessage(), ex);
        } finally {
            //Fechando conexões (banco de dados e statement)
            ConnectionFactory.closeConnection(connection, statement);
        }

    }

    public void removeById(int taskId) {

        String sql = "DELETE FROM tasks WHERE id = ?";

        Connection connection = null;
        PreparedStatement statement = null;

        try {

            //estabelecenco conexão com o banco de dados
            connection = ConnectionFactory.getConnection();
            
            //preparando a qurey
            statement = connection.prepareStatement(sql);
            
            //setando os valores do statement
            statement.setInt(1, taskId);
            
            //executando a query
            statement.execute();
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao deletar a tarefa"
                    + ex.getMessage(), ex);
        } finally {
            //Fechando conexões (banco de dados e statement)
            ConnectionFactory.closeConnection(connection, statement);
        }

    }

    public List<Task> getAll(int idProject) {
        
        String sql = "SELECT * FROM tasks WHERE idProject = ?";
        
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        // Lista de tarefas que será devolvida quando a chamada do método acontecer
        List<Task> tasks = new ArrayList<>();
        
        
        try{
            //estabelecenco conexão com o banco de dados
            connection = ConnectionFactory.getConnection();
            
            //preparando a qurey
            statement = connection.prepareStatement(sql);
            
            //setando os valores do statement
            statement.setInt(1, idProject);
            
            //Salvando a resposta do banco de dados
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                
                //Criando uma nova tarefa
                Task task = new Task();
                
                //Setando os valores na  tarefa
                task.setId(resultSet.getInt("id"));
                task.setIdProject(resultSet.getInt("idProject"));
                task.setName(resultSet.getString("name"));
                task.setDescription(resultSet.getString("description"));
                task.setNotes(resultSet.getString("notes"));
                task.setIsCompleted(resultSet.getBoolean("completed"));
                task.setDeadline(resultSet.getDate("deadline"));
                task.setCreatedAt(resultSet.getDate("createdAt"));
                task.setUpdatedAt(resultSet.getDate("updatedAt"));
                
                
                //Adicionando a tarefa dentro da lista de tarefas
                tasks.add(task);
                
                
            }
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao buscar tarefas"
                    + ex.getMessage(), ex);
        } finally {
            
            //Fechando conexões (banco de dados, statement e  resultSet)
            ConnectionFactory.closeConnection(connection, statement, resultSet);
        }
        
        
        //Lista de tarefas que foi criada e carregada com o banco de dados
        return tasks;
    }
}
