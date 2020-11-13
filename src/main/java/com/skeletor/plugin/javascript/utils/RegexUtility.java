package com.skeletor.plugin.javascript.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtility {

    public static String getYouTubeId (String youTubeUrl) {
        String pattern = "(?<=youtu.be/|watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*";
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(youTubeUrl);
        if(matcher.find()){
            return matcher.group();
        } else {
            return "";
        }
    }

    public static String getUserMentionedFromChat(String chat) {
        String pattern = "@(\\w+)";
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(chat);
        if(matcher.find() && matcher.groupCount() > 0) {
            return matcher.group(1);
        } else {
            return "";
        }
    }
}
