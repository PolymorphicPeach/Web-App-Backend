package matthewpeach.backend.data_objects;

import java.util.List;

public class CaesarCiphertext {
    private String text;
    List<LetterFrequency> cipherFrequencies;

    // https://www3.nd.edu/~busiforc/handouts/cryptography/letterfrequencies.html

    public CaesarCiphertext(){
        // Empty required for Jackson library
    }

    public CaesarCiphertext(String text, List<LetterFrequency> cipherFrequencies) {
        this.text = text;
        this.cipherFrequencies = cipherFrequencies;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<LetterFrequency> getCipherFrequencies() {
        return cipherFrequencies;
    }

    public void setCipherFrequencies(List<LetterFrequency> cipherFrequencies) {
        this.cipherFrequencies = cipherFrequencies;
    }
}
