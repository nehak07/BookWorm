package com.example.bookworm;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.StyleSpan;
import android.text.style.URLSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import static com.example.bookworm.AllClubs2Fragment.EXTRA_CLUBDESC;
import static com.example.bookworm.AllClubs2Fragment.EXTRA_CLUBNAME;
import static com.example.bookworm.BooksFragment.EXTRA_AUTHOR;
import static com.example.bookworm.BooksFragment.EXTRA_BOOKURL;
import static com.example.bookworm.BooksFragment.EXTRA_GENRE;
import static com.example.bookworm.BooksFragment.EXTRA_NAME;
import static com.example.bookworm.BooksFragment.EXTRA_PRICE;
import static com.example.bookworm.BooksFragment.EXTRA_URL;

public class BookDetails2Activity extends AppCompatActivity implements View.OnClickListener{

    private Button AddToReadList;
    private TextView BookName, BookPrice, Category, Genre, Author;
    private ImageView Image;
    String imageURL;
    private Button NextHome;

    private FirebaseFirestore mFirestore;
    private StorageReference mStorageRef;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Toolbar mToolbar;
    private Button Link;
    private String NAME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details2);

        mToolbar = (Toolbar) findViewById(R.id.BookDetails_two_Toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Book Details");

        mFirestore = FirebaseFirestore.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.btn_add).setOnClickListener(this);

        BookName = findViewById(R.id.txt_BookName_detail);
        BookPrice = findViewById(R.id.txt_price_detail);
        Genre = findViewById(R.id.txt_Book_Genre);
        Image = findViewById(R.id.image_outfit_detail);
        NextHome = findViewById(R.id.btn_nextHome);
        Author = findViewById(R.id.txt_view_Author);
        Link = findViewById(R.id.WebURL);

        findViewById(R.id.btn_nextHome).setOnClickListener(this);


        NextHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                final String CLUBNAME = intent.getStringExtra(EXTRA_CLUBNAME);
                Intent mainintent = new Intent(BookDetails2Activity.this, AdminSettingsActivity.class);
                mainintent.putExtra(AllClubsActivity.EXTRA_CLUBNAME,CLUBNAME);
                startActivity(mainintent);
            }
        });


        Intent intent = getIntent();
        imageURL = intent.getStringExtra(EXTRA_URL);
        String GENRE = intent.getStringExtra(EXTRA_GENRE);
        String NAME = intent.getStringExtra(EXTRA_NAME);
        String AUTHOR = intent.getStringExtra(EXTRA_AUTHOR);
        final String BOOKURL = intent.getStringExtra(EXTRA_BOOKURL);
        int PRICE = intent.getIntExtra(EXTRA_PRICE, 0);
        final String CLUBNAME = intent.getStringExtra(EXTRA_CLUBNAME);
        NAME = intent.getStringExtra(BlankActivity.EXTRA_NAME);

        //Toast.makeText(BookDetails2Activity.this,CLUBNAME,Toast.LENGTH_SHORT).show();

        ImageView imageView = findViewById(R.id.image_outfit_detail);
        TextView textViewGenre = findViewById(R.id.txt_Book_Genre);
        TextView textViewName = findViewById(R.id.txt_BookName_detail);
        TextView textViewPrice = findViewById(R.id.txt_price_detail);
        TextView textViewAuthor = findViewById(R.id.txt_view_Author);

        Picasso.get().load(imageURL).fit().centerInside().into(imageView);
        textViewGenre.setText(GENRE);
        textViewAuthor.setText(AUTHOR);
        textViewName.setText(NAME);
        textViewPrice.setText(""+PRICE);

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
        Intent intent = getIntent();
        final String CLUBNAME = intent.getStringExtra(EXTRA_CLUBNAME);
        Intent mainintent = new Intent(BookDetails2Activity.this, BookClubAdminActivity.class);
        mainintent.putExtra(AllClubsActivity.EXTRA_CLUBNAME,CLUBNAME);
        mainintent.putExtra(EXTRA_NAME,NAME);
        startActivity(mainintent);

    }


    @Override
    public void onClick(View v) {

        FirebaseUser user = mAuth.getCurrentUser();
        String UserID = user.getUid();

        String NAME = BookName.getText().toString();
        String GENRE = Genre.getText().toString();
        String AUTHOR = Author.getText().toString();
        String PRICE = BookPrice.getText().toString();
        String Image = com.example.bookworm.BooksFragment.EXTRA_URL;

//Coding With Mitch, 2018. Inserting Data Android Firestore [ONLINE] Available at: https://www.youtube.com/watch?v=xnFnwbiDFuE&t=14s [Accessed on the 28th Feb 2019]
//Simplified Coding, 2018 [ONLINE] Available at: https://www.youtube.com/watch?v=fGiIUi3FqfQ [Accessed on the 13th March 2019]
        Note2 note2 = new Note2(
                AUTHOR,
                GENRE,
                NAME,
                UserID,
                imageURL,
                Integer.parseInt(PRICE)

                //The order must match how you want it to be saved into the database and to Note"

        );

              Intent intent = getIntent();
      final String CLUBNAME = intent.getStringExtra(EXTRA_CLUBNAME);


        mFirestore.collection("Club").document(CLUBNAME).collection("ClubReading").document(NAME)
                .set(note2).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(BookDetails2Activity.this, "Added to wishlist database", Toast.LENGTH_SHORT).show();
            }
        });


    }

}


