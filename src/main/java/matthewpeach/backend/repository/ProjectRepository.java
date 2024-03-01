package matthewpeach.backend.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import matthewpeach.backend.data_objects.Project;
import matthewpeach.backend.data_objects.SkillCount;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ProjectRepository {
    final private List<Project> projects = Arrays.asList(
            new Project(
                    "This Website",
                    "SpringBoot, AWS, RESTful-services, Java, JavaScript, HTML, Vue, Three.js, Chart.js",
                    "TBA"),
            new Project(
                    "Browser Game",
                    "SpringBoot, RESTful-services, Java, JavaScript, HTML",
                    "https://github.com/PolymorphicPeach/Personal-Website/tree/main/main"
            ),
            new Project(
                    "Aerial Picture Classification (CNN)",
                    "Tensorflow, MachineLearning, DeepLearning, Python, Java",
                    "https://github.com/PolymorphicPeach/CNN_Aerial_Images/blob/main/CNN_Aerial_Images.ipynb"
            ),
            new Project(
                    "Hiring Forecaster (RNN)",
                    "Tensorflow, MachineLearning, DeepLearning, Python",
                    "https://github.com/PolymorphicPeach/RNN_Hiring_Forecast/blob/main/RNN_Hiring_MatthewPeach.ipynb"

            ),
            new Project(
                    "Podcast Host Partisanship Prediction",
                    "MachineLearning, Python",
                    "https://github.com/PolymorphicPeach/Podcast-Host-Partisanship-Prediction/blob/main/Podcast_Host_Partisanship.ipynb"
            ),
            new Project(
                    "UFO Sighting Shape Prediction",
                    "MachineLearning, Python",
                    "https://github.com/PolymorphicPeach/UFO-Sighting-Shape-Predictor/blob/main/UFO_Predictor.ipynb"
            ),
            new Project(
                    "Creating a Fractal",
                    "Racket",
                    "https://github.com/PolymorphicPeach/Racket-Fractal"
            ),
            new Project(
                    "Advent Code 2022",
                    "C++",
                    "https://github.com/PolymorphicPeach/AdventCode2022"
            ),
            new Project(
                    "Huffman Character Encoding",
                    "C++",
                    "https://github.com/PolymorphicPeach/Huffman-Coding"
            ),
            new Project(
                    "NASA L'Space Program 2022",
                    "CAD-Modeling, UnrealEngine, Python",
                    "https://github.com/PolymorphicPeach/NASA-LSpace-Program-2022"
            ),
            new Project(
                    "Binary Tree Implementation",
                    "C++",
                    "https://github.com/PolymorphicPeach/Binary-Tree"
            ),
            new Project(
                    "Gauss-Jordan Elimination",
                    "C++",
                    "https://github.com/PolymorphicPeach/Gauss-Jordan-Elimination"
            ),
            new Project(
                    "Object Localization of Faces",
                    "Python, Tensorflow, DeepLearning, TransferLearning",
                    "https://github.com/PolymorphicPeach/Object-Localization-of-Faces"
            )
    );

    public List<Project> getProjects(){
        return projects;
    }

    public List<SkillCount> getSkillsCount() throws JsonProcessingException {
        List<String> skills = new ArrayList<>();

        for(Project project : projects){
            String[] currentSkills = project.getSkills().split("[ ,]+");
            skills.addAll(Arrays.asList(currentSkills));
        }

        Map<String, Long> skillCountMap = skills
                .stream()
                .collect(
                        Collectors.groupingBy(
                                Function.identity(),
                                Collectors.counting()
                        )
                );

        List<SkillCount> skillCounts = new ArrayList<>();

        skillCountMap.forEach((skill, count) -> {
            skillCounts.add(new SkillCount(skill, count.intValue()));
        });

        return skillCounts;
    }
}
