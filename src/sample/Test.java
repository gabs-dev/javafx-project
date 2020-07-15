package sample;

import dao.PersonDao;
import jdbc.exception.DbException;
import model.Person;

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
        p.setId(6L);
        p.setName("Pedro");
        p.setEmail("pedro@gmail.com");
        p.setPassword("12345678");

        PersonDao dao = new PersonDao();
        try {
            dao.update(p);
            System.out.println("Usuário atualizado com sucesso!");
        } catch (DbException e) {
            System.err.println("Erro ao atualizar o usuário!");
            System.err.println(e.getMessage());
        }

    }
}
