package com.example.bookworm;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.app.LoaderManager;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.res.Resources;
import android.graphics.Rect;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity  implements LoaderManager.LoaderCallbacks<List<book>> {

    private static final String bookFetchUrl = "https://www.googleapis.com/books/v1/volumes";
    private RecyclerView recyclerView;
    public BooksAdapter adapter;
    private Toolbar mToolbar;

    private FirebaseAuth firebaseAuth;

    public static ArrayList<book> bookList = null;
    private static final int BOOKS_LOADER_ID = 1;
    private EditText searchBox;
    private ProgressBar books_progressBar;
    private TextView empty_state;


    @Override
    //Check to see if the user has entered text in the search box
    public Loader<List<book>> onCreateLoader(int id, Bundle args) {
        searchBox = (EditText) findViewById(R.id.searchBox);
        String query = searchBox.getText().toString();
        if(query.isEmpty() || query.length() == 0){
            searchBox.setError("Please enter a books name");
            return new booksLoader(this, null);
        }

        //find the books matching the users requirements
        Uri baseUri = Uri.parse(bookFetchUrl);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("q", query);

        //once a users clicks on the search button the below code hides the keyboard
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        searchBox.setText("");

        //Returning a new Loader Object
        return new booksLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<book>> loader, List<book> list) {
        books_progressBar.setVisibility(View.GONE);
        if(list !=null && !list.isEmpty()){
            prepareBooks(list);

        }
        else{
            empty_state.setText("NO DATA");
            empty_state.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<book>> loader) {
        if(adapter == null){
            return;
        }
        bookList.clear();
        adapter.notifyDataSetChanged();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        firebaseAuth = FirebaseAuth.getInstance();

        mToolbar = (Toolbar) findViewById(R.id.ContentBooks_Toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Book Search");


        books_progressBar = (ProgressBar) findViewById(R.id.books_progressBar);
        books_progressBar.setIndeterminate(true);
        books_progressBar.setVisibility(View.GONE);

        empty_state = (TextView) findViewById(R.id.empty_state);

        //The Google API requires internet connection else a error message will appear
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo == null){
            empty_state.setText("NO INTERNET");
            empty_state.setVisibility(View.VISIBLE);
            ((ImageButton) findViewById(R.id.searchButton)).setEnabled(false);
        }

       // initCollapsingToolbar();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        if(savedInstanceState == null || !savedInstanceState.containsKey("booksList")){
            bookList = new ArrayList<>();
            adapter = new BooksAdapter(this, bookList);



        }else {
            bookList.addAll(savedInstanceState.<book>getParcelableArrayList("booksList"));

            adapter = new BooksAdapter(this, bookList);
            //this will reLoad the adapter
            adapter.notifyDataSetChanged();

        }

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        try {
            Glide.with(this).load(R.drawable.search).into((ImageView) findViewById(R.id.backdrop));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if(id == android.R.id.home)
        {
            SendUserToHome();
        }
        return super.onOptionsItemSelected(item);
    }

    private void SendUserToHome()
    {
        Intent mainintent = new Intent(Main2Activity.this, BlankActivity.class);
        startActivity(mainintent);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("booksList", bookList);
        super.onSaveInstanceState(outState);
    }

    public void searchButton(View view){
        books_progressBar.setVisibility(View.VISIBLE);
        bookList.clear();
        adapter.notifyDataSetChanged();
        getLoaderManager().restartLoader(BOOKS_LOADER_ID, null, this);
        getLoaderManager().initLoader(BOOKS_LOADER_ID, null, this);

    }

    private void prepareBooks(List<book> booksList) {

        bookList.addAll(booksList);

        //The recycler adapter needs to be notified that the data has been changed
        adapter.notifyDataSetChanged();
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting the picture into to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}