package taskmanager.command;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static taskmanager.command.ExecutionReport.WORDS_COUNTED;
import static taskmanager.command.ExecutionReport.WORDS_COUNT_FAILED;

public class CountWordsCommand extends AbstractCommand {

    CountWordsCommand(String[] command) {
        super(command);
    }

    @Override
    public ExecutionReport call() {
        try {
            String inputFile = command[1];
            String outputFile = command[2];
            List<String> allWords = getAllWords(inputFile);
            saveWords(allWords, outputFile);
        } catch (IOException e) {
            return WORDS_COUNT_FAILED;
        }
        return WORDS_COUNTED;
    }


    private List<String> getAllWords(String inputFile) throws IOException {
        return Files.lines(Paths.get(inputFile), Charset.forName("UTF-8")).map(l -> l.split("\\W+"))
                .flatMap(Stream::of).map(String::toLowerCase).distinct()
                .collect(Collectors.toList());
    }

    private void saveWords(List<String> words, String outputFile) throws IOException {
        Files.write(
                Paths.get(outputFile),
                String.format("Words count is %d%s%s",
                        words.size(),
                        System.getProperty("line.separator"),
                        String.join(", ", words))
                        .getBytes());
    }
}
