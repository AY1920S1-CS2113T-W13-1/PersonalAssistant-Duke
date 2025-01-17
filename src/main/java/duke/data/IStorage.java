package duke.data;

import duke.models.ToDo;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Interface for loading classes.
 */
public interface IStorage {
    /**
     * Method will load saved files.
     * @return array list
     */
    ArrayList<ToDo> load(String date) throws IOException;

    /**
     * Method will save file to location.
     */
    void save(ToDo toDo, String date) throws IOException;
}
