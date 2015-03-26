package jumpers.com.pipervoice.Control;

import android.content.Context;
import android.widget.Toast;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import jumpers.com.pipervoice.Model.Command;

/**
 * Created by Carla on 3/12/2015.
 */
public class ControlCommand
{
    private Context context;
    private Queue<Command> commandQueue;

    public ControlCommand(Context context)
    {
        commandQueue = new LinkedBlockingQueue<Command>();
        this.context = context;
    }

    public void processCommand()
    {
        try
        {
           // Requisition.send(callbackInstance, "http://10.1.254.30/index.php", "post", new Command(1, Command.CMD_SPEECH.APAGAR_LUZ), context);
            while (!commandQueue.isEmpty())
            {

                Command c = commandQueue.remove();
                Requisition.send(callbackInstance, "http://10.1.254.30/index.php", "post", new Command(1, c.getCommand()), context);
                /*switch (c.getCommand())
                {
                    case ACENDER_LUZ:
                        Toast.makeText(context, "Solicitação enviada para acender a luz.", Toast.LENGTH_SHORT).show();
                        break;
                    case LIGAR_AR:
                        Toast.makeText(context, "Solicitação enviada para ligar o ar.", Toast.LENGTH_SHORT).show();
                        break;
                    case APAGAR_LUZ:
                        Toast.makeText(context, "Solicitação enviada para apagar a luz.", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(context, "Desculpe, pode repetir?", Toast.LENGTH_SHORT).show();
                }*/
            }
        }
        catch (Exception e)
        {
            Toast.makeText(context, "Deu erro no processCommand", Toast.LENGTH_SHORT).show();
        }
    }

    public void addToQueue(Command command)
    {
        try
        {
            commandQueue.add(command);
        }
        catch (Exception e)
        {
            Toast.makeText(context, "Erro ao adicionar à fila", Toast.LENGTH_SHORT).show();
        }
    }

    Requisition.Callback callbackInstance = new Requisition.Callback()
    {
        @Override
        public void requisitionCompleted(int status, String answer, Exception e)
        {
            //Gson g = new Gson();
            //Command command = g.fromJson(answer, Command.class);
            Toast.makeText(context, answer, Toast.LENGTH_SHORT).show();

        }
    };
}
