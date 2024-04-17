package matthewpeach.backend;

import matthewpeach.backend.configuration.KeyConfigurationProperties;
import matthewpeach.backend.controller.APIController;

import matthewpeach.backend.projects.SkillCount;
import matthewpeach.backend.projects.ProjectRepository;
import matthewpeach.backend.cryptography.CryptographyService;
import matthewpeach.backend.cryptography.ByteReaderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;

@WebMvcTest(APIController.class)
public class APIControllerTest {
    // Mock Beans for every bean required by controller
    @MockBean
    private CryptographyService cryptographyService;
    @MockBean
    private ByteReaderService byteReaderService;
    @MockBean
    private ProjectRepository projectRepository;
    @MockBean
    private KeyConfigurationProperties keyConfigurationProperties;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void isAvailable() throws Exception{
        mockMvc
                .perform(get("/api"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }

    @Test
    public void getSkills_Endpoint() throws Exception {
        List<SkillCount> testList = new ArrayList<>();
        testList.add(new SkillCount("Testing", 999));
        testList.add(new SkillCount("Controller", 10));

        when(projectRepository.getSkillsCount()).thenReturn(testList);

        mockMvc
                .perform(get("/api/skills"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"skill\":\"Testing\",\"count\":999},{\"skill\":\"Controller\",\"count\":10}]"));
    }
}
