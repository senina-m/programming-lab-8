package ru.senina.itmo.lab8.commands;

import ru.senina.itmo.lab8.*;
import ru.senina.itmo.lab8.parser.LabWorkListParser;

/**
 * Command sorts collection
 */
@CommandAnnotation(name = "sort", collectionKeeper = true, parser = true)
public class SortCommand extends CommandWithoutArgs {
    private CollectionKeeper collectionKeeper;
    private LabWorkListParser parser;

    public SortCommand() {
        super("sort", "sort the collection in natural order and return it");
    }

    public void setCollectionKeeper(CollectionKeeper collectionKeeper) {
        this.collectionKeeper = collectionKeeper;
    }
    public void setParser(LabWorkListParser parser) {
        this.parser = parser;
    }

    @Override
    protected CommandResponse doRun() {
        return new CommandResponse(Status.OK, getName(), parser.fromObjectToString(new LabWorkList(collectionKeeper.getSortedList())));
    }
}
