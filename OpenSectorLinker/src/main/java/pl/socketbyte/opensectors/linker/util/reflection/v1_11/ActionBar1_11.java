package pl.socketbyte.opensectors.linker.util.reflection.v1_11;

import org.bukkit.entity.Player;
import pl.socketbyte.opensectors.linker.util.Util;
import pl.socketbyte.opensectors.linker.util.reflection.ActionBar;
import pl.socketbyte.opensectors.linker.util.reflection.PacketInjector;
import pl.socketbyte.opensectors.linker.util.reflection.Reflection;

import java.lang.reflect.InvocationTargetException;

public class ActionBar1_11 implements ActionBar{

    private static final Class<?> ICHAT_BASE_COMPONENT =
            Reflection.getCraftClass("IChatBaseComponent");
    private static final Class<?> CHAT_COMPONENT_TEXT =
            Reflection.getCraftClass("ChatComponentText");
    private static final Class<?> PACKET_PLAY_OUT_CHAT =
            Reflection.getCraftClass("PacketPlayOutChat");

    public ActionBar1_11() {
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
    public Object getPacket(String content) {
        try {
            Object baseComponent = CHAT_COMPONENT_TEXT
                    .getConstructor(String.class)
                    .newInstance(Util.fixColors(content));
            return PACKET_PLAY_OUT_CHAT
                    .getConstructor(ICHAT_BASE_COMPONENT, Byte.TYPE)
                    .newInstance(baseComponent, (byte)2);
        } catch (NoSuchMethodException
                | InstantiationException
                | InvocationTargetException
                | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }


}
