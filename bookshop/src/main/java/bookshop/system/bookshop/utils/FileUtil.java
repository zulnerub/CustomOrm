package bookshop.system.bookshop.utils;

import java.io.IOException;

public interface FileUtil {
    String[] readFileContent(String filePath) throws IOException;
}
