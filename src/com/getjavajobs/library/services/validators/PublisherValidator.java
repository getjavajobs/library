package com.getjavajobs.library.services.validators;

import com.getjavajobs.library.model.Publisher;

import java.util.regex.Pattern;

/**
 * Created by Roman on 20.08.14.
 */
public class PublisherValidator {

    private final String sDomen = "[a-z][a-z[0-9]\u005F\u002E\u002D]*[a-z||0-9]";
    private final String sDomen2 = "(net||org||ru||info||com||im||ch)";
    private final Pattern EMAIL_PATTERN = Pattern.compile(sDomen + "@" + sDomen + "\u002E" + sDomen2);
    private final Pattern TELEPHONE_PATTERN = Pattern.compile("^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$");


    public boolean validate(Publisher publisher) {
        return EMAIL_PATTERN.matcher(publisher.getEmail().toLowerCase()).matches() &&
                TELEPHONE_PATTERN.matcher(publisher.getPhoneNumber()).matches();
    }

}
