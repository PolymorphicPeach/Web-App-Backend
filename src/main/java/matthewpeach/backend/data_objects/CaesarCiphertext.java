package matthewpeach.backend.data_objects;

import java.util.Map;

public class CaesarCiphertext {
    private String text;
    Map<String, Integer> letterFrequency;

    // https://www3.nd.edu/~busiforc/handouts/cryptography/letterfrequencies.html

    public CaesarCiphertext(){
        // Empty required for Jackson library
    }

    public CaesarCiphertext(String text, Map<String, Integer> letterFrequency) {
        this.text = text;
        this.letterFrequency = letterFrequency;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Map<String, Integer> getLetterFrequencies() {
        return letterFrequency;
    }

    public void setLetterFrequencies(Map<String, Integer> letterFrequencies) {
        this.letterFrequency = letterFrequencies;
    }
}
