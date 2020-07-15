package sample;

import jdbc.ConnectionFactory;

public class Test {

    public static void main(String[] args) {

        ConnectionFactory cf = new ConnectionFactory();
        cf.getConnection();

    }

}
