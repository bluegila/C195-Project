package utilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeValidation
{
    private Pattern pattern;
    private Matcher matcher;

    private static final String TIME_PATTERN = "(1[012]|[1-9]):[0-5][0-9](\\s)?(?i)(am|pm)";

    public TimeValidation()
    {
        pattern = Pattern.compile(TIME_PATTERN);
    }

    public boolean timeValidate(final String time)
    {
        matcher = pattern.matcher(time);
        return matcher.matches();
    }
}
