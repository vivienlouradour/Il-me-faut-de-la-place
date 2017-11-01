package Core;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileFiltering {
    private Pattern pattern;
    private Matcher matcher;

    public FileFiltering(String pattern){
        this.pattern = Pattern.compile(pattern);
    }

    public boolean isValid(File file){
        this.matcher = pattern.matcher(file.getName());
        return matcher.matches();
    }
}
