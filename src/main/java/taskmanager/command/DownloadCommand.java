package taskmanager.command;

import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class DownloadCommand extends AbstractCommand<Void, String> {

    DownloadCommand(String[] command) {
        super(command);
    }

    @Override
    public String call() throws Exception {

        try (InputStream in = new URL(command[1]).openStream()) {
            Files.copy(in, Paths.get(command[2]), StandardCopyOption.REPLACE_EXISTING);
        }

        String res = command[2];

        return publish(res);
    }

}
