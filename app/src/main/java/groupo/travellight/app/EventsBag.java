package groupo.travellight.app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/*
    Bag of events listed to user
 */
//Added Back in Due to Tommy Deleting on Masters - Joe

public class EventsBag extends ActionBarActivity {
    private List<Events> myEvents = new ArrayList<Events>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_bag);

        //create a test events list
        populateEventsList();

        //populate the list
        populateListView();

        //ask if you want to remove item.
        //if yes remove, if no dismiss the long click
        checkForRemove();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.actionbar_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /*
        Create test list of Events class
     */
    private void populateEventsList()
    {
        myEvents.add((new Events("Bungee Jump")));
        myEvents.add((new Events("Skiing")));
        myEvents.add((new Events("Biking")));
        myEvents.add((new Events("Eating")));
        myEvents.add((new Events("Adventuring")));
    }

    /*
        Show the list of events to the user
     */
    private void populateListView()
    {
        ArrayAdapter<Events> adapter = new MyListAdapter();
        ListView list = (ListView) findViewById(R.id.eventsListView);
        list.setAdapter(adapter);
    }

    /*
        Check whether the user wants to remove the item or not.
     */
    private void checkForRemove()
    {
       ListView list = (ListView) findViewById(R.id.eventsListView);
       final ArrayAdapter<Events> adapter = new MyListAdapter();
       list.setAdapter(adapter);
       list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
       {
           /*
                Made int position final int position
            */
           @Override
           public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l)
           {
               AlertDialog.Builder adb = new AlertDialog.Builder(EventsBag.this);
               adb.setTitle("Are you sure you want to delete this item?");
               adb.setPositiveButton("Yes", new DialogInterface.OnClickListener()
               {
                   /*
                        remove item at the located position and refresh the list view
                    */
                   @Override
                   public void onClick(DialogInterface dialogInterface, int i)
                   {
                       myEvents.remove(position); //remove item at the current position
                       adapter.notifyDataSetChanged(); //refreshing the list view with the changes
                   }
               });

               adb.setNegativeButton("No", new DialogInterface.OnClickListener()
               {
                   @Override
                   public void onClick(DialogInterface dialogInterface, int i)
                   {
                       dialogInterface.dismiss();
                   }
               });

               adb.show();
               return false;
           }
       });
    }

    /*
        Be able to list events classes in the listView
     */
    private class MyListAdapter extends  ArrayAdapter<Events>
    {
        public MyListAdapter()
        {
            super(EventsBag.this, R.layout.event_view, myEvents);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            //Check that we have a view to work with, if not create one
            View itemView = convertView;
            if (itemView == null)
            {
                itemView = getLayoutInflater().inflate(R.layout.event_view, parent, false);
            }

            //Find which event to work with
            Events currentEvent = myEvents.get(position);

            //Name:
            //possible bug with itemView.findViewById(R.id.eventName) where it can throw a null pointer exception
            //bug fixed with assert itemView != null statement
            assert itemView != null;
            TextView eventNameText = (TextView) itemView.findViewById(R.id.event_Name);
            eventNameText.setText(currentEvent.getName());

            return itemView;
        }
    }

}
