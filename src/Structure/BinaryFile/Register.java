package Structure.BinaryFile;

import java.io.IOException;
import java.io.RandomAccessFile;

public class Register {
    public final int tf = 1022;
    private int number; // 4 bytes
    private char trash[] = new char[tf]; // 2044 bytes

    public Register(int numero) {
        this.number = numero;
        for (int i = 0; i < tf; i++)
            trash[i] = 'X';
    }

    public void writeOnFile(RandomAccessFile arquivo) {
        try {
            arquivo.writeInt(number);
            for (int i = 0; i < tf; i++)
                arquivo.writeChar(trash[i]);
        } catch (IOException e) {
        }
    }

    public void readFile(RandomAccessFile arquivo) {
        try {
            number = arquivo.readInt();
            for (int i = 0; i < tf; i++)
                trash[i] = arquivo.readChar();
        } catch (IOException e) {
        }
    }

    public void showRegister() {
        System.out.print("number....." + this.number);
    }

    static int length() {
        return (2048);
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
