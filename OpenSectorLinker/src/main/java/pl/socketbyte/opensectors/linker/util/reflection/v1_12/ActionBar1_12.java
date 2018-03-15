package pl.socketbyte.opensectors.linker.util.reflection.v1_12;

import org.bukkit.entity.Player;
import pl.socketbyte.opensectors.linker.util.Util;
import pl.socketbyte.opensectors.linker.util.reflection.ActionBar;
import pl.socketbyte.opensectors.linker.util.reflection.PacketInjector;
import pl.socketbyte.opensectors.linker.util.reflection.Reflection;

import java.lang.reflect.InvocationTargetException;

public class ActionBar1_12 implements ActionBar {
    private static final Class<?> CHAT_MESSAGE_TYPE =
           Reflection.getCraftClass("ChatMessageType");
    private static final Class<?> ICHAT_BASE_COMPONENT =
            Reflection.getCraftClass("IChatBaseComponent");
    private static final Class<?> CHAT_COMPONENT_TEXT =
            Reflection.getCraftClass("ChatComponentText");
    private static final Class<?> PACKET_PLAY_OUT_CHAT =
            Reflection.getCraftClass("PacketPlayOutChat");

    public ActionBar1_12() {
    }


    @Override
    public void send(Player player, String content) {
        PacketInjector.sendPacket(player, getPacket(content));
    }

    @Override
    public void send(String content) {
        PacketInjector.sendPacket(getPacket(content));
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object getPacket(String content) {
        try {
            Object baseComponent = CHAT_COMPONENT_TEXT
                    .getConstructor(String.class)
                    .newInstance(Util.fixColors(content));
            return PACKET_PLAY_OUT_CHAT
                    .getConstructor(ICHAT_BASE_COMPONENT, CHAT_MESSAGE_TYPE)
                    .newInstance(baseComponent, Enum.valueOf((Class<Enum>)CHAT_MESSAGE_TYPE, "ACTION_BAR"));
        } catch (NoSuchMethodException
                | InstantiationException
                | InvocationTargetException
                | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
