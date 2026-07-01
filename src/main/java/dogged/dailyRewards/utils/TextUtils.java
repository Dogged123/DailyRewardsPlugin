package dogged.dailyRewards.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public class TextUtils {
    private static final String NORMAL_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final String MINI_CHARS   = "ᴀʙᴄᴅᴇꜰɢʜɪᴊᴋʟᴍɴᴏᴘQʀꜱᴛᴜᴠᴡxʏᴢᴀʙᴄᴅᴇꜰɢʜɪᴊᴋʟᴍɴᴏᴘǫʀꜱᴛᴜᴠᴡxʏᴢ";

    public static Component miniText(String text, String colour) {
        String format = "<!italic><" + colour.toLowerCase() + ">" + toSmallCaps(text);
        return MiniMessage.miniMessage().deserialize(format);
    }

    public static Component miniText(String text, String colour, boolean bold) {
        String format = "<!italic><" + colour.toLowerCase() + ">" + toSmallCaps(text);
        if (bold) format = "<bold>" + format;

        return MiniMessage.miniMessage().deserialize(format);
    }

    public static String toSmallCaps(String input) {
        if (input == null) return "";
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < input.length(); i++) {
            int index = NORMAL_CHARS.indexOf(input.charAt(i));

            if (index != -1 && (i == 0 || input.charAt(i - 1) != '§')) builder.append(MINI_CHARS.charAt(index));
            else builder.append(input.charAt(i));
        }
        return builder.toString();
    }

    public static String toNormalChars(String input) {
        if (input == null) return "";
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < input.length(); i++) {
            int index = MINI_CHARS.indexOf(input.charAt(i));

            if (index != -1 && (i == 0 || input.charAt(i - 1) != '§')) {
                builder.append(NORMAL_CHARS.charAt(index));
            } else {
                builder.append(input.charAt(i));
            }
        }
        return builder.toString();
    }

    public static String capitalizeWords(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        String[] words = input.split("\\s+");
        StringBuilder result = new StringBuilder();

        for (String word : words) {
            if (!word.isEmpty()) {
                result.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1))
                        .append(" ");
            }
        }

        // Trim the extra trailing space
        return result.toString().trim();
    }

    public static String serializeLegacy(Component comp) {
        if (comp == null) return "null";
        return LegacyComponentSerializer.legacySection().serialize(comp);
    }

    public static Component deserializeAmpersand(String s) {
        return LegacyComponentSerializer.legacyAmpersand().deserialize(s).decoration(TextDecoration.ITALIC, false);
    }

    public static Component deserializeAmpersand(String s, boolean italics) {
        return LegacyComponentSerializer.legacyAmpersand().deserialize(s).decoration(TextDecoration.ITALIC, italics);
    }
}
