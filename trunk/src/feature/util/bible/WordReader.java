
package feature.util.bible;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StreamTokenizer;

public class WordReader extends FileReader {

    private StreamTokenizer st;

    public WordReader(String fileName) throws IOException {
        super(fileName);
        st = new StreamTokenizer(new BufferedReader(this));
        st.wordChars(0, 255);
        st.whitespaceChars(StreamTokenizer.TT_EOL, StreamTokenizer.TT_EOL);
    } // WordReader

    public String readWord() {
        try {
            int token = st.nextToken();
            while ((token != StreamTokenizer.TT_EOF)
                    && (token != StreamTokenizer.TT_WORD)) {
                token = st.nextToken();
            } // while
            if (token == StreamTokenizer.TT_EOF)
                return null;

            return st.sval;
        } catch (IOException e) {
            return null;
        } // try/catch
    } // readWord
}
