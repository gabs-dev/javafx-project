package sample;

import dao.PersonDao;
import jdbc.exception.DbException;
import model.Person;

import java.util.List;

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

        //Person p = new Person();
        //p.setId(6L);
//        p.setName("Pedro");
//        p.setEmail("pedro@gmail.com");
//        p.setPassword("12345678");

        PersonDao dao = new PersonDao();
        try {
            //dao.delete(p);
            //System.out.println("Usuário excluído com sucesso!");
            List<Person> list = dao.findAll();
            list.forEach(System.out::println);
        } catch (DbException e) {
            System.err.println("Erro exibir os usuários!");
            System.err.println(e.getMessage());
        }

    }
}
