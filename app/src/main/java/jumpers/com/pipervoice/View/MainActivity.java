package jumpers.com.pipervoice.View;

import android.app.Activity;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

import jumpers.com.pipervoice.Control.ControlCommand;
import jumpers.com.pipervoice.Control.ControlSpeech;
import jumpers.com.pipervoice.Control.Requisition;
import jumpers.com.pipervoice.Model.Command;
import jumpers.com.pipervoice.Model.Pessoa;
import jumpers.com.pipervoice.NavTest;
import jumpers.com.pipervoice.NavigationDrawerFragment;
import jumpers.com.pipervoice.R;


public class MainActivity extends ActionBarActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks
{

    private Button buttonSpeak;
    private Button buttonDownload;
    private ControlSpeech controlSpeech;
    public final int REQ_CODE_SPEECH_INPUT = 500;
    private Activity thisActivity;
    private TextView textViewResults;
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private CharSequence mTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewResults = (TextView)findViewById(R.id.textViewResults);
        buttonSpeak = (Button) findViewById(R.id.buttonSpeak);
        buttonDownload = (Button) findViewById(R.id.buttonDownload);
        thisActivity = this;
        buttonSpeak.setOnClickListener(buttonSpeak_onClick);
        buttonDownload.setOnClickListener(buttonDownload_onClick);


        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position)
    {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, NavTest.PlaceholderFragment.newInstance(position + 1))
                .commit();
    }



    public void onSectionAttached(int number)
    {
        switch (number)
        {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar()
    {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }








    View.OnClickListener buttonSpeak_onClick = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            controlSpeech = new ControlSpeech(thisActivity);
            controlSpeech.setRequestCode(REQ_CODE_SPEECH_INPUT);
            controlSpeech.promptSpeechInput();


        }
    };

    View.OnClickListener buttonDownload_onClick = new View.OnClickListener()
    {
        @Override
        //192.168.56.1
        public void onClick(View v)
        {
            Requisition.send(callbackInstance, "http://10.1.254.30/index.php", "post", new Command(1, Command.CMD_SPEECH.APAGAR_LUZ), thisActivity);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        //return true;
        if (!mNavigationDrawerFragment.isDrawerOpen())
        {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.global, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        try
        {
            super.onActivityResult(requestCode, resultCode, data);

            switch (requestCode)
            {
                case REQ_CODE_SPEECH_INPUT:
                {
                    if (resultCode == RESULT_OK && data != null)
                    {
                        ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                        textViewResults.setText(results.get(0));

                        controlSpeech.findInSpeech(results.get(0));
                    }
                }
            }
        }
        catch (Exception e)
        {
            Toast.makeText(thisActivity, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    Requisition.Callback callbackInstance = new Requisition.Callback()
    {
        @Override
        public void requisitionCompleted(int status, String answer, Exception e)
        {
            //Gson g = new Gson();
            //Command command = g.fromJson(answer, Command.class);
            Toast.makeText(getApplicationContext(), answer, Toast.LENGTH_SHORT).show();

        }
    };

}
