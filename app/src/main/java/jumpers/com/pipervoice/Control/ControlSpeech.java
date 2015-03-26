package jumpers.com.pipervoice.Control;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.widget.Toast;

import java.util.Locale;
import java.util.Queue;

import jumpers.com.pipervoice.Model.Command;
import jumpers.com.pipervoice.R;

/**
 * Created by Carla on 3/12/2015.
 */
public class ControlSpeech
{
    private Context context;
    private int requestCode;




    public ControlSpeech(Context context)
    {
        this.context = context;
    }

    public void promptSpeechInput()
    {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        //putExtra parâmetros = referência, valor.
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, context.getString(R.string.speech_message));
        try
        {
            ((Activity)context).startActivityForResult(intent, requestCode);
        }
        catch (ActivityNotFoundException e)
        {
            Toast.makeText(context, context.getString(R.string.speech_message_not_supported), Toast.LENGTH_LONG).show();
        }
    }

    public void setRequestCode(int requestCode)
    {
        this.requestCode = requestCode;
    }

    public void findInSpeech(String result)
    {

        ControlCommand controlCommand = new ControlCommand(context);


        if(result.contains("acender") && result.contains("luz"))
        {
            controlCommand.addToQueue(new Command(result.indexOf("acender a luz"), Command.CMD_SPEECH.ACENDER_LUZ));
        }
        if(result.contains("ligar") && result.contains("ar condicionado"))
        {
            controlCommand.addToQueue(new Command(result.indexOf("ligar ar condicionado"), Command.CMD_SPEECH.LIGAR_AR));
        }
        if(result.contains("apagar") && result.contains("luz"))
        {
            controlCommand.addToQueue(new Command(result.indexOf("apagar a luz"), Command.CMD_SPEECH.APAGAR_LUZ));
        }

        controlCommand.processCommand();
    }

}
