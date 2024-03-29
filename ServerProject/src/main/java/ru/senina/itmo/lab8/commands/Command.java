package ru.senina.itmo.lab8.commands;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;
import ru.senina.itmo.lab8.*;
import ru.senina.itmo.lab8.exceptions.UnLoginUserException;
import ru.senina.itmo.lab8.parser.LabWorkListParser;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Parent of all commands classes
 */

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "@class")
public abstract class Command {
    private String[] args;
    private final String name;
    private final String description;
    @Getter @Setter
    private String token;
    @JsonIgnore
    private Locale locale;
    @Getter @JsonIgnore
    private ResourceBundle resourceBundle;

    protected Command(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void setArgs(CommandArgs args) {
        this.args = args.getArgs();
        this.token = args.getToken();
        this.locale = Command.fromString(args.getLocale());
        this.resourceBundle = ResourceBundle.getBundle("text", locale);
    }

    public String[] getArgs() {
        return args;
    }

    public CommandResponse run() {
        validateArguments();
        checkIfLogin();
        return doRun();
    }

    /**
     * Command execute method
     *
     * @return value to print in output like the result of command execute
     */
    protected abstract CommandResponse doRun();

    /**
     * Arguments validation method
     */
    public abstract void validateArguments();

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setCollectionKeeper(CollectionKeeper collectionKeeper) {
    }

    public void setParser(LabWorkListParser parser) {
    }

    protected void checkIfLogin() throws UnLoginUserException {
        if (!DBManager.checkLogin(token)){
            throw new UnLoginUserException();
        }
    }

    public static Locale fromString(String locale) {
        String[] parts = locale.split("_", -1);
        if (parts.length == 1) return new Locale(parts[0]);
        else if (parts.length == 2
                || (parts.length == 3 && parts[2].startsWith("#")))
            return new Locale(parts[0], parts[1]);
        else return new Locale(parts[0], parts[1], parts[2]);
    }
}
