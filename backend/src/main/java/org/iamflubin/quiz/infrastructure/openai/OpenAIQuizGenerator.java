package org.iamflubin.quiz.infrastructure.openai;

import lombok.extern.slf4j.Slf4j;
import org.iamflubin.quiz.application.spi.QuizGenerator;
import org.iamflubin.quiz.domain.Answer;
import org.iamflubin.quiz.domain.Category;
import org.iamflubin.quiz.domain.DifficultyLevel;
import org.iamflubin.quiz.domain.Question;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@Slf4j
class OpenAIQuizGenerator implements QuizGenerator {
    private final ChatClient client;

    OpenAIQuizGenerator(ChatClient.Builder builder) {
        var systemText = """
                You are a quiz generator for a trivia application. Your role is to create engaging, accurate, and well-structured quizzes based on the provided inputs. Follow these guidelines:
                
                1. Question Types:
                - Questions can have a single correct answer, multiple correct answers, or no correct answer, depending on the context.
                - Some questions may include special answer choices such as:
                  - "None of the above" when none of the listed options are correct.
                  - "All of the above" when all listed options are correct.
                
                2. Question Structure:
                - Provide clear and concise questions.
                - Include multiple-choice options when possible.
                - Ensure that distractor options (incorrect answers) are plausible but clearly distinguishable from correct answers.
                
                3. Content Criteria:
                - Tailor questions to the specified category, difficulty level, and number of questions.
                - Ensure questions are accurate, engaging, and appropriate for the selected category and difficulty.
                
                4. Diversity:
                - Strive for a mix of factual, logical, and thought-provoking questions to keep the quiz interesting.
                - Include a variety of topics within the selected category to create a well-rounded quiz.
                
                5. Adaptability:
                - Adjust the complexity of the questions based on the difficulty level (e.g., 'Easy' should be straightforward; 'Hard' should be more challenging).
                
                Always provide high-quality, creative, and diverse quiz content that aligns with the inputs.
                
                Example inputs:
                - Number of questions: 5
                - Category: Science
                - Difficulty: Medium
                
                Your task is to generate the best possible quiz based on these parameters.
                """;


        this.client = builder
                .defaultSystem(systemText)
                .build();
    }

    @Override
    public Set<Question> generate(Category category, DifficultyLevel difficultyLevel,
                                  int questionCount) {
        var prompt = generatePrompt(questionCount, category, difficultyLevel);
        log.debug("Prompt generated. [prompt={}]", prompt);

        var response = client
                .prompt()
                .advisors(new SimpleLoggerAdvisor())
                .user(prompt)
                .call()
                .entity(QuizAIResponse.class);

        log.debug("Response received. [response={}]", response);

        if (response == null) {
            log.error("No response received from OpenAI");
            throw new IllegalStateException("No response received from OpenAI");
        }

        return parseResponse(response);
    }

    private String generatePrompt(int questionCount, Category category, DifficultyLevel difficulty) {

        return "Generate a quiz with the following criteria:\n" +
                "- Number of questions: " + questionCount + "\n" +
                "- Category: " + category + "\n" +
                "- Difficulty level: " + difficulty + "\n\n" +
                "Each question can have:\n" +
                "1. Multiple correct answers, a single correct answer, " +
                "or no correct answer.\n" +
                "2. Questions should be engaging and relevant to the specified " +
                "category and difficulty level.\n" +
                "3. Ensure the questions are accurate and thought-provoking.\n\n" +
                "4. If applicable, the answer choices may include options such as:\n" +
                "   - 'None of the above'\n" +
                "   - 'All of the above'\n" +
                "\nGenerate the quiz.";
    }

    private Set<Question> parseResponse(QuizAIResponse response) {
        return response.questions().stream()
                .map(q -> Question.create(
                        q.text(),
                        answersFrom(q.answers()),
                        answersFrom(q.correctAnswers())
                ))
                .collect(Collectors.toSet());
    }

    private Set<Answer> answersFrom(Set<String> strings) {
        return strings.stream().map(Answer::new).collect(Collectors.toSet());
    }
}
