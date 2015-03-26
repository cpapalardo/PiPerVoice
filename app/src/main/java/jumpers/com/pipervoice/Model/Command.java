package jumpers.com.pipervoice.Model;

import android.app.Activity;
import android.widget.Toast;

import java.util.Queue;

/**
 * Created by Carla on 3/12/2015.
 */
public class Command
{
    private int index;
    private CMD_SPEECH command;

    public enum CMD_SPEECH
    {
        ACENDER_LUZ,
        LIGAR_AR,
        APAGAR_LUZ
    }

    public Command(int index, CMD_SPEECH command)
    {
        this.index = index;
        this.command = command;
    }

    public CMD_SPEECH getCommand()
    {
        return command;
    }

    public int getIndex()
    {
        return index;
    }

    @Override
    public String toString()
    {
        return "comando executado";
    }
}
