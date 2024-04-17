package matthewpeach.backend.cryptography;

import org.springframework.stereotype.Service;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class ByteReaderService {
    public byte[] getFromURL(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try(BufferedInputStream inputStream =
                    new BufferedInputStream(connection.getInputStream())){

            byte[] buffer = new byte[1024];
            int bytesRead = 0;
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            while((bytesRead = inputStream.read(buffer, 0, buffer.length)) != -1){
                outputStream.write(buffer, 0, bytesRead);
            }
            return outputStream.toByteArray();
        }
        finally{
            connection.disconnect();
        }
    }
}
