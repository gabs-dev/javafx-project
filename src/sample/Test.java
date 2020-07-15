package sample;

import dao.PersonDao;
import exception.DbException;
import jdbc.ConnectionFactory;
import model.Person;

import javax.swing.*;

public class Test {

    public static void main(String[] args) {

//        try {
//            ConnectionFactory cf = new ConnectionFactory();
//            cf.getConnection();
//        } catch (RuntimeException e) {
//            String message = "Falha ao conectar com o banco de dados!";
//            message += "\n" + e.getMessage();
//            JOptionPane.showMessageDialog(null, message, "Erro!", JOptionPane.ERROR_MESSAGE);
//            e.printStackTrace();
//        }

        Person p = new Person();
        p.setName("Rafael");
        p.setEmail("rafael@gmail.com");
        //p.setPassword("rafael");

        PersonDao dao = new PersonDao();
        try {
            dao.add(p);
            System.out.println("Usuário salvo com sucesso!");
        } catch (DbException e) {
            System.err.println("Erro ao salvar o usuário!");
            System.err.println(e.getMessage());
        }

    }
}
