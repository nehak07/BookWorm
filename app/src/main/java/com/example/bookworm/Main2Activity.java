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


    /* Arraylist is static so that it binds with instance of class
     * and we dont have to initialize again in else under onCreate*/
    public static ArrayList<book> bookList = null;
    private static final int BOOKS_LOADER_ID = 1;
    private EditText searchBox;
    private ProgressBar books_progressBar;
    private TextView empty_state;

    public static final String TAG = "MyActivity";

    @Override
    public Loader<List<book>> onCreateLoader(int id, Bundle args) {
        searchBox = (EditText) findViewById(R.id.searchBox);
        ///BUT How to convert spaces
        String query = searchBox.getText().toString();
        if(query.isEmpty() || query.length() == 0){
            searchBox.setError("Please Enter Any Book");
            return new booksLoader(this, null);
        }

        //WITH URI
        Uri baseUri = Uri.parse(bookFetchUrl);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("q", query);

        //when we click om searchButton keyboard will hide
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
            Log.i(TAG, "onLoadFinished: ");
        }
        else{
            empty_state.setText("NO DATA");
            empty_state.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<book>> loader) {
        Log.i(QueryUtils.TAG, "onLoaderReset: ");
        if(adapter == null){
            return;
        }
        bookList.clear();
        adapter.notifyDataSetChanged();
        Log.i(TAG, "onLoaderReset: " + bookList);
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
        getSupportActionBar().setTitle("Book Details");


        books_progressBar = (ProgressBar) findViewById(R.id.books_progressBar);
        books_progressBar.setIndeterminate(true);
        books_progressBar.setVisibility(View.GONE);

        empty_state = (TextView) findViewById(R.id.empty_state);

        //Checking the Network State
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

            //log Statement
            Log.i(TAG, "onCreate: " + bookList);
        }else {
            bookList.addAll(savedInstanceState.<book>getParcelableArrayList("booksList"));

            //log statement
            Log.i(TAG, "onCreate: under else" + bookList );
            adapter = new BooksAdapter(this, bookList);
            //this will reLoad the adapter
            adapter.notifyDataSetChanged();

        }


        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        //getLoaderManager().initLoader(BOOKS_LOADER_ID, null, this);


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
        Log.i(TAG, "searchButton: "  + bookList);
    }

    /**
     * Initializing collapsing toolbar
     * Will show and hide the toolbar title on scroll
     */
//    private void initCollapsingToolbar() {
//        final CollapsingToolbarLayout collapsingToolbar =
//                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
//        collapsingToolbar.setTitle(" ");
//        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
//        appBarLayout.setExpanded(true);
//
//        //ERROR:  This Activity already has an action bar supplied by the window decor. Do not request Window.FEATURE_SUPPORT_ACTION_BAR and
//        // set windowActionBar to false in your theme to use a Toolbar instead.
//
//
//        // hiding & showing the title when toolbar expanded & collapsed
//        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
//            boolean isShow = false;
//            int scrollRange = -1;
//
//            @Override
//            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
//                if (scrollRange == -1) {
//                    scrollRange = appBarLayout.getTotalScrollRange();
//                }
//                if (scrollRange + verticalOffset == 0) {
//                    //collapsingToolbar.setTitle(getString(R.string.app_name));
//                    isShow = true;
//                } else if (isShow) {
//                    collapsingToolbar.setTitle(" ");
//                    isShow = false;
//                }
//            }
//        });
//    }


    private void prepareBooks(List<book> booksList) {

        bookList.addAll(booksList);
        Log.i(TAG, "prepareBooks: " + bookList);

        //notifiying the recycleradapter that data has been changed
        adapter.notifyDataSetChanged();
    }

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
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
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}