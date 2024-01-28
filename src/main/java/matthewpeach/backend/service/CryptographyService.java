package matthewpeach.backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import matthewpeach.backend.data_objects.CaesarCiphertext;
import matthewpeach.backend.data_objects.LetterFrequency;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CryptographyService {
    public String caesarEncrypt(String plaintext, int key) throws JsonProcessingException {
        StringBuilder cipherText = new StringBuilder();
        Map<String, Integer> frequenciesMap = new HashMap<>();
        List<LetterFrequency> frequencies = new ArrayList<>();

        for(int i = 0; i < plaintext.length(); ++i){
            char currentChar = plaintext.charAt(i);
            // Only encode latin characters
            if(isLatin(plaintext.charAt(i))){
                char lowerCaseChar = Character.toLowerCase(currentChar);
                char convertedChar = (char) (key + (int) lowerCaseChar % 127);

                if(!frequenciesMap.containsKey(String.valueOf(convertedChar))){
                    frequenciesMap.put(String.valueOf(convertedChar), 1);
                }
                else{
                    frequenciesMap.put(
                            String.valueOf(convertedChar),
                            frequenciesMap.get(String.valueOf(convertedChar)) + 1
                    );
                }
                cipherText.append(convertedChar);
            }
            else{
                cipherText.append(currentChar);
            }
        }

        for(Map.Entry<String, Integer> entry : frequenciesMap.entrySet()){
            frequencies.add(new LetterFrequency(entry.getKey().charAt(0), entry.getValue()));
        }

        ObjectMapper objectMapper = new ObjectMapper();
        CaesarCiphertext data = new CaesarCiphertext(cipherText.toString(), frequencies);
        return objectMapper.writeValueAsString(data);
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
