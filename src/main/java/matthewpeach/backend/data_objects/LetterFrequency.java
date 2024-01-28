package matthewpeach.backend.data_objects;

public class LetterFrequency {
    private char letter;
    private int frequency;

    public LetterFrequency(){

    }

    public LetterFrequency(char letter, int frequency) {
        this.letter = letter;
        this.frequency = frequency;
    }

    public char getLetter() {
        return letter;
    }

    public void setLetter(char letter) {
        this.letter = letter;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }
}
