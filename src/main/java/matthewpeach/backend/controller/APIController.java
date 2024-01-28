package matthewpeach.backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import matthewpeach.backend.repository.ProjectRepository;
import matthewpeach.backend.service.CryptographyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class APIController {
    private final RestTemplate restTemplate;
    private final CryptographyService cryptographyService;
    private final ProjectRepository projectRepository;

    @Autowired
    APIController(CryptographyService cryptographyService,
                  ProjectRepository projectRepository){
        this.cryptographyService = cryptographyService;
        this.projectRepository = projectRepository;
        this.restTemplate = new RestTemplate();
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
        System.out.println("In /projects, whhheeeee");

        try{
            return new ResponseEntity<>(projectRepository.getProjectsJSON(), HttpStatus.OK);
        }
        catch(JsonProcessingException e){
            return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/aerial")
    public ResponseEntity<String> aerialClassification(@RequestBody Map<String, String> request){
        String imageUrl = request.get("imageUrl");
        String microserviceUrl = "http://mlservice:5000/aerial";

        HttpEntity<String> requestEntity = new HttpEntity<>(imageUrl);
        ResponseEntity<String> responseEntity = restTemplate.exchange(microserviceUrl, HttpMethod.POST, requestEntity, String.class);

        return new ResponseEntity<>(responseEntity.getBody(), HttpStatus.OK);
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
            throw new RuntimeException(e);
        }

        return new ResponseEntity<>(json, HttpStatus.OK);
    }
}
