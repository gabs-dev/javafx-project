package util;

public class CodeGenerator {

    // Gera codigos de verificacao com 6 digitos
    public static long generateCode() {
        long code = (long) (100000 + Math.random() * 899999l);
        return code;
    }

}
