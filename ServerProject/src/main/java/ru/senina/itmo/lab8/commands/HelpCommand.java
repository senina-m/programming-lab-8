package ru.senina.itmo.lab8.commands;

import ru.senina.itmo.lab8.CommandResponse;
import ru.senina.itmo.lab8.Status;

import java.util.HashMap;
import java.util.Map;

/**
 * Command displays help for available ru.senina.itmo.lab6.commands
 */

@CommandAnnotation(name = "help")
public class HelpCommand extends CommandWithoutArgs {
    private Map<String, Command> commands;

    public HelpCommand(Map<String, Command> commands) {
        super("help", "displays help for available commands");
        this.commands = commands;
    }

    //fixme can't build result string - find and fix problem
    @Override
    public CommandResponse doRun() {
        StringBuilder string = new StringBuilder();
        string.append(getResourceBundle().getString("fullListOfCommands")).append(": \n");
        for(Command command : createCommandMapForClient().values()){
            string.append(getResourceBundle().getString(command.getName()+ ".Command")).append(" : ").
                    append(getResourceBundle().getString(command.getName() + ".Description")).append("\n");
        }
        return new CommandResponse(Status.OK, getName(), string.toString());
    }

    public Map<String, Command> getCommands() {
        return commands;
    }

    public void setCommands(Map<String, Command> commands) {
        this.commands = commands;
    }

    private Map<String, Command> createCommandMapForClient(){
        Map<String, Command> commandMapForClient = new HashMap<>();
        for(Command command : commands.values()){
            if (command.getClass().isAnnotationPresent(CommandAnnotation.class)) {
                CommandAnnotation annotation = command.getClass().getAnnotation(CommandAnnotation.class);
                if (annotation.isVisibleInHelp()) {
                    commandMapForClient.put(command.getName(), command);
                }
            }
        }
        return commandMapForClient;
    }
}