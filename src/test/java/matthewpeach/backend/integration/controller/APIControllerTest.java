package matthewpeach.backend.integration.controller;

import matthewpeach.backend.configuration.KeyConfigurationProperties;
import matthewpeach.backend.controller.APIController;

import matthewpeach.backend.repository.ProjectRepository;
import matthewpeach.backend.service.CryptographyService;
import matthewpeach.backend.service.ByteReaderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(APIController.class)
public class APIControllerTest {
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
}
