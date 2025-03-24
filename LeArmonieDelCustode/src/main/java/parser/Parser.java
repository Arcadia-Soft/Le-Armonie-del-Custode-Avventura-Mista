package parser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.Map;
import java.util.HashMap;

import grafica.InterfacciaGioco;
import other.Utils;
import tipi.Command;
import tipi.CommandType;
import tipi.Item;

/**
 *
 * @author Alessandro Pellegrino
 * @author Kevin Saracino
 */
public class Parser implements Serializable {
    private final Set<String> stopwords;
    private final List<Command> commands;
    private final Command nullCommand = new Command(CommandType.NULL_COMMAND, null);

    public Parser(Set<String> stopwords, List<Command> commands) {
        this.stopwords = stopwords;
        this.commands = commands;
    }

    private Command findCommand(final String token) {
        return commands.stream()
                .filter(cmd -> cmd.getName().equals(token) || cmd.getAlias().contains(token))
                .findFirst()
                .orElse(null);
    }

    private String[] findItems(final String[] tokens, final Set<Item> items) {
        List<String> itemsFound = new ArrayList<>();
        Map<Pattern, Item> patternMap = new HashMap<>();

        for (Item item : items) {
            Set<String> aliases = item.getAlias();
            aliases.add(item.getName().toLowerCase().trim());
            Pattern pattern = createPrefixRegexItem(new ArrayList<>(aliases));
            patternMap.put(pattern, item);
        }

        StringBuilder currentInput = new StringBuilder();
        StringBuilder lastItemFound = new StringBuilder();
        Pattern lastMatchedPattern = null;

        for (String token : tokens) {
            boolean found = false;
            currentInput.append(token).append(" ");
            String string = currentInput.toString().trim();
            
            // verifica se parole successive possono essere aggiunte l'ultimo a quelle precedenti per identificare lo stesso item
            if (lastMatchedPattern != null) {
                lastItemFound.append(token).append(" ");
                if (lastMatchedPattern.matcher(lastItemFound.toString()).find()) {
                    currentInput.setLength(0);
                    found = true;
                }
            }

            // Se non è possibile identificare lo stesso item, cerca di trovare un altro item
            if (!found) {
                found = false;
                for (Pattern pattern : patternMap.keySet()) {
                    if (pattern.matcher(string).find()) {
                        lastMatchedPattern = pattern;
                        found = true;
                        break;
                    }
                }

                // Se è stato trovato un item, aggiungilo alla lista degli item trovati
                if (found) {
                    itemsFound.add(currentInput.toString().trim());
                    lastItemFound = new StringBuilder(currentInput.toString());
                    currentInput.setLength(0);
                }
            }
        }

        if (currentInput.length() > 0) {
            itemsFound.add(currentInput.toString().trim());
        }

        return itemsFound.toArray(new String[0]);
    }

    private Pattern createPrefixRegexItem(List<String> prefixes) {
        if (prefixes == null || prefixes.isEmpty()) {
            return Pattern.compile("^$");
        }

        prefixes.sort((a, b) -> Integer.compare(b.length(), a.length()));
        return Pattern.compile("^(?:" + String.join("|", prefixes) + ")");
    }

    public ParserOutput parse(String input, Set<Item> inventory, Set<Item> roomItems,
            InterfacciaGioco interfaccia) {
        List<String> tokens = Utils.parseString(input, stopwords);

        if (tokens.isEmpty()) {
            return new ParserOutput(nullCommand, null, interfaccia);
        }

        // Supponendo che i comandi siano formati da una sola parola
        Command command = findCommand(tokens.get(0));
        if (command == null) {
            return new ParserOutput(nullCommand, input, interfaccia);
        }

        if (tokens.size() == 1) {
            return new ParserOutput(command, null, interfaccia);
        }

        if (command.getType().equals(CommandType.PICK_UP) || command.getType().equals(CommandType.USE)) {
            Set<Item> items = new java.util.HashSet<>();
            if (command.getType().equals(CommandType.USE)) {
                items.addAll(inventory);
            }
            items.addAll(roomItems);
            String[] itemsFound = findItems(tokens.subList(1, tokens.size()).toArray(new String[0]), items);
            return new ParserOutput(command, itemsFound, interfaccia);
        }

        return new ParserOutput(command, String.join(" ", tokens.subList(1, tokens.size())), interfaccia);
    }
}