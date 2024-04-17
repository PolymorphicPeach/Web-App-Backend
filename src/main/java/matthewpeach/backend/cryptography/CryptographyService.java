package matthewpeach.backend.cryptography;

import matthewpeach.backend.cryptography.CaesarCiphertext;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CryptographyService {
    public CaesarCiphertext caesarEncrypt(String plaintext, int key){
        StringBuilder cipherText = new StringBuilder();
        Map<String, Integer> letterFrequency = new HashMap<>();

        for(int i = 0; i < plaintext.length(); ++i){
            char currentChar = plaintext.charAt(i);
            // Only encode latin characters
            if(isLatin(plaintext.charAt(i))){
                char lowerCaseChar = Character.toLowerCase(currentChar);
                char convertedChar = (char) (key + (int) lowerCaseChar % 127);

                if(!letterFrequency.containsKey(String.valueOf(convertedChar))){
                    letterFrequency.put(String.valueOf(convertedChar), 1);
                }
                else{
                    letterFrequency.put(
                            String.valueOf(convertedChar),
                            letterFrequency.get(String.valueOf(convertedChar)) + 1
                    );
                }
                cipherText.append(convertedChar);
            }
            else{
                cipherText.append(currentChar);
            }
        }

        return new CaesarCiphertext(cipherText.toString(), letterFrequency);
    }

    public String caesarDecrypt(String ciphertext, int key){
        StringBuilder plaintext = new StringBuilder();
        for(int i = 0; i < ciphertext.length(); ++i){
            char convertedChar = (char) ((int) ciphertext.charAt(i) - key % 127);
            plaintext.append(convertedChar);
        }
        return plaintext.toString();
    }

    private boolean isLatin(char character){
        return (character >= 'A' && character <= 'Z') || (character >= 'a' && character <= 'z');
    }
}
