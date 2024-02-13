package matthewpeach.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import matthewpeach.backend.configuration.KeyConfigurationProperties;
import matthewpeach.backend.data_objects.Project;
import matthewpeach.backend.repository.ProjectRepository;
import matthewpeach.backend.service.CryptographyService;
import matthewpeach.backend.service.ByteReaderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.Arrays;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(APIController.class)
public class APIControllerTests {
    @Autowired
    private MockMvc mockMvc;

    // Mock Beans for every bean required by controller
    @MockBean
    private CryptographyService cryptographyService;
    @MockBean
    private ByteReaderService byteReaderService;
    @MockBean
    private ProjectRepository projectRepository;
    @MockBean
    private KeyConfigurationProperties keyConfigurationProperties;

    @Test
    public void isAvailable() throws Exception{
        mockMvc
                .perform(
                        get("/api")
                )
                .andExpect(
                        status().isOk()
                );
    }

    @Test
    public void getSkills() throws Exception{
        // Will set the output of the .getSkillsCount() method.
        given(projectRepository.getSkillsCount())
                .willReturn("Testing String");

        mockMvc
                .perform(
                        get("/api/skills")
                )
                .andDo(
                        MockMvcResultHandlers.print()
                )
                .andExpect(
                        status().isOk()
                );
    }

    @Test
    public void getProjects() throws Exception{
        Project testProj1 = new Project(
                "Gauss-Jordan Elimination",
                "C++",
                "https://github.com/PolymorphicPeach/Gauss-Jordan-Elimination"
        );

        Project testProj2 = new Project(
                "Object Localization of Faces",
                "Python, Tensorflow, DeepLearning, TransferLearning",
                "https://github.com/PolymorphicPeach/Object-Localization-of-Faces"
        );

        String jsonTestString = asJsonString(Arrays.asList(testProj1, testProj2));

        // Realizing I did this a bit weird... Maybe the controller should handle
        //   mapping to JSON, not the classes...?
        given(projectRepository.getProjectsJSON())
                .willReturn(jsonTestString);

        mockMvc
                .perform(
                        get("/api/projects")
                )
                .andExpect(
                        status().isOk()
                )
                .andDo(
                        MockMvcResultHandlers.print()
                );
    }

    // Utility method for converting Objects to JSON strings
    private static String asJsonString(final Object obj){
        try{
            final ObjectMapper mapper = new ObjectMapper();
            // Returns String, obviously
            return mapper.writeValueAsString(obj);
        }
        catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}
