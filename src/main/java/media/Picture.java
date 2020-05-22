package media;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Picture {

    public Picture() {
    }

    public List <String> getPictures(Path filepath) {
        try {
        return   Files.list(filepath)
                    .map(Path::toString)
                    .collect(Collectors.toCollection(ArrayList::new));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public List<String> getMiniGifs(){
        Path filePath  = Paths.get("./src/main/resources/icons/miniGifs");

        try {
        return    Files.list(filePath)
                    .map(Path::toString)
                    .sorted()
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }


        return Collections.emptyList();

    }


}
