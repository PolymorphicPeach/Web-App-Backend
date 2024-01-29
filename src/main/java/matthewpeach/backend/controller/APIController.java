package matthewpeach.backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import matthewpeach.backend.configuration.KeyConfigurationProperties;
import matthewpeach.backend.repository.ProjectRepository;
import matthewpeach.backend.service.ByteReaderService;
import matthewpeach.backend.service.CryptographyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URL;
import java.util.Base64;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class APIController {
    private final RestTemplate restTemplate;
    private final CryptographyService cryptographyService;
    private final ByteReaderService byteReaderService;
    private final ProjectRepository projectRepository;
    private final KeyConfigurationProperties keys;

    @Autowired
    APIController(CryptographyService cryptographyService,
                  ByteReaderService byteReaderService,
                  ProjectRepository projectRepository,
                  KeyConfigurationProperties keys){
        this.cryptographyService = cryptographyService;
        this.byteReaderService = byteReaderService;
        this.projectRepository = projectRepository;
        this.restTemplate = new RestTemplate();
        this.keys = keys;
    }

    @GetMapping
    public String root(){
        return "api available";
    }

    @GetMapping("/skills")
    public ResponseEntity<String> getSkills(){
        try {
            return new ResponseEntity<>(projectRepository.getSkillsCount(), HttpStatus.OK);
        }
        catch (JsonProcessingException e) {
            return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/projects")
    public ResponseEntity<String> getProjects(){
        try{
            return new ResponseEntity<>(projectRepository.getProjectsJSON(), HttpStatus.OK);
        }
        catch(JsonProcessingException e){
            return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
        }
    }

    // ===========================================
    //        Aerial Classification Endpoints
    // ===========================================
    @PostMapping("/aerial")
    public ResponseEntity<String> aerialClassification(@RequestBody Map<String, String> request){
        String imageUrl = buildGoogleMapURL(request);
        //String microserviceUrl = "http://localhost:5000/aerial";
        String microserviceUrl = "http://mlservice:5000/aerial";

        HttpEntity<String> requestEntity = new HttpEntity<>(imageUrl);
        ResponseEntity<String> responseEntity = restTemplate.exchange(microserviceUrl, HttpMethod.POST, requestEntity, String.class);

        return new ResponseEntity<>(responseEntity.getBody(), HttpStatus.OK);
    }

    @PostMapping("/static-map-proxy")
    public ResponseEntity<byte[]> proxyStaticMap(@RequestBody Map<String, String> request){
        String googleMapAddress = buildGoogleMapURL(request);

        try {
            byte[] imageBytes = byteReaderService.getFromURL(new URL(googleMapAddress));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);
            return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ===========================================
    //           Cryptography Endpoints
    // ===========================================
    @PostMapping("/caesar")
    public ResponseEntity<String> caesarCipher(@RequestBody Map<String,String> request){
        String plaintext = request.get("plaintext");
        int privateKey = Integer.parseInt(request.get("key"));

        String json = null;
        try {
            json = cryptographyService.caesarEncrypt(plaintext, privateKey);
        }
        catch (JsonProcessingException e) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }

        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    // ===========================================
    //               Helpers
    // ===========================================
    private String buildGoogleMapURL(Map<String, String> request){
        StringBuilder googleMapAddress = new StringBuilder();
        googleMapAddress.append("https://maps.googleapis.com/maps/api/staticmap?center=");
        googleMapAddress.append(request.get("latitude"));
        googleMapAddress.append(",");
        googleMapAddress.append(request.get("longitude"));
        googleMapAddress.append("&zoom=");
        googleMapAddress.append(request.get("zoom"));
        googleMapAddress.append("&size=600x600&maptype=satellite&key=");
        googleMapAddress.append(keys.GOOGLE_MAP_KEY());
        return googleMapAddress.toString();
    }
}
