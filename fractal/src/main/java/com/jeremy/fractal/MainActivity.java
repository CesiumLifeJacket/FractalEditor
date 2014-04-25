package com.jeremy.fractal;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.RelativeLayout;

import java.util.ArrayList;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        private FractalView fractalView;
        private NodesView nodesView;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            initFractal(rootView);
            return rootView;
        }

        private void initFractal(View v) {
            // get nodes' transformations, define the fractal with them
            fractalView = (FractalView) v.findViewById(R.id.main_fractal_view);
            nodesView = (NodesView) v.findViewById(R.id.node_container);
            nodesView.fractalView = fractalView;

            ArrayList<Node> nodes = new ArrayList<Node>();
            nodes.add(new Node(Color.RED  , .3f, 50,  50, .5f, .5f, 0f, 0f, 0f));
            nodes.add(new Node(Color.GREEN, .3f, 400, 50, .5f, .5f, 0f, 0f, 0f));
            nodes.add(new Node(Color.BLUE , .3f, 50, 400, .5f, .5f, 0f, 0f, 0f));
            nodesView.setNodes(nodes);

            fractalView.setFractal(nodesView.getFractal());
        }

        private void updateMenu() {
            // update the menu I guess
        }
    }

}
