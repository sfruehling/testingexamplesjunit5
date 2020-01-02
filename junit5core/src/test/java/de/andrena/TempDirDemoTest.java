package de.andrena;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class TempDirDemoTest {

    @SuppressWarnings("WeakerAccess")
    @TempDir
    Path tempFile;

    @Test
    void useTempFileAsAttribute() throws IOException {
        Path tempFilePath = tempFile.resolve("file.txt");
        Files.write(tempFilePath, Arrays.asList("1","2"));

        assertThat(Files.exists(tempFilePath), is(true));
    }

    @Test
    void useTempFileAsParam(@TempDir Path tempDirParam) throws IOException {
        Path tempFilePath = tempDirParam.resolve("file.txt");
        Files.write(tempFilePath, Arrays.asList("1","2"));

        assertThat(Files.exists(tempFilePath), is(true));
    }
}
