package br.com.cv;

public class ByteImage {

    private int width;
    private int heigth;
    private byte[][] data;

    public ByteImage(int w, int h) {
        width = w;
        heigth = h;
        data = new byte[w][h];
    }

    public int getHeigth() {
        return heigth;
    }

    public int getWidth() {
        return width;
    }

    public int getPixel(int x, int y) {
        final byte pixel = data[x][y];
        return pixel < 0 ? (pixel & 0XFF) : pixel;
    }

    public void setPixel(int x, int y, int value) {
        data[x][y] = (byte) value;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < width; i++) {
            sb.append("[");
            for (int j = 0; j < heigth; j++) {
                sb.append(" " + data[i][j] + " ");
            }
            sb.append("]\n");
        }
        return sb.toString();
    }
}
