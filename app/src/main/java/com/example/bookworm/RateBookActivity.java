package com.example.bookworm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
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

import static com.example.bookworm.BooksFragment.EXTRA_AUTHOR;
import static com.example.bookworm.BooksFragment.EXTRA_BOOKURL;
import static com.example.bookworm.BooksFragment.EXTRA_GENRE;
import static com.example.bookworm.BooksFragment.EXTRA_NAME;
import static com.example.bookworm.BooksFragment.EXTRA_PRICE;
import static com.example.bookworm.BooksFragment.EXTRA_URL;

public class RateBookActivity extends AppCompatActivity  implements View.OnClickListener {

    private Button AddToReadList;
    private TextView BookName, BookPrice, Category, Genre, Author, Rating;
    private ImageView Image;
    String imageURL;
    private Button NextHome, SaveRating, Save;
    private EditText WrittenReview;

    private FirebaseFirestore mFirestore;
    private StorageReference mStorageRef;
    private FirebaseAuth mAuth;

    private Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_book);

        mToolbar = (Toolbar) findViewById(R.id.RateBooks_Toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Book Rating");


        final RatingBar ratingBar = (RatingBar) findViewById(R.id.rating_Bar);
        //Button SaveRating = (Button) findViewById(R.id.btn_add);
        final TextView txt_Rate = (TextView) findViewById(R.id.txt_Rate);


        mFirestore = FirebaseFirestore.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.btn_add).setOnClickListener(this);

        BookName = findViewById(R.id.txt_BookName_detail);
        BookPrice = findViewById(R.id.txt_price_detail);
        Genre = findViewById(R.id.txt_Book_Genre);
        Image = findViewById(R.id.image_outfit_detail);
        Author = findViewById(R.id.txt_view_Author);
        SaveRating = findViewById(R.id.btn_add);
        Rating = findViewById(R.id.txt_Rate);
        Save = findViewById(R.id.btn_save);
        WrittenReview = findViewById(R.id.edWriteReview);



        findViewById(R.id.btn_save).setOnClickListener(this);


        SaveRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_Rate.setText("You rated the book: " + ratingBar.getRating());
            }
        });


        Intent intent = getIntent();
        imageURL = intent.getStringExtra(EXTRA_URL);
        String NAME = intent.getStringExtra(EXTRA_NAME);
        String AUTHOR = intent.getStringExtra(EXTRA_AUTHOR);


        // String GENRE = intent.getStringExtra(EXTRA_GENRE);
        //String CAT = intent.getStringExtra(EXTRA_CAT);
        //String BOOKURL = intent.getStringExtra(EXTRA_BOOKURL);
        // int PRICE = intent.getIntExtra(EXTRA_PRICE, 0);

        ImageView imageView = findViewById(R.id.image_outfit_detail);
        TextView textViewName = findViewById(R.id.txt_BookName_detail);
        TextView textViewAuthor = findViewById(R.id.txt_view_Author);


        // TextView textViewGenre = findViewById(R.id.txt_Book_Genre);
        //TextView textViewPrice = findViewById(R.id.txt_price_detail);
        //TextView textViewBrandURL = findViewById(R.id.txt_Bookbrand_url);

        Picasso.get().load(imageURL).fit().centerInside().into(imageView);
        textViewAuthor.setText(AUTHOR);
        textViewName.setText(NAME);

        // textViewPrice.setText(""+PRICE);
        // textViewBrandURL.setText(BOOKURL);
        // textViewGenre.setText(GENRE);
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
        Intent mainintent = new Intent(RateBookActivity.this, BlankActivity.class);
        startActivity(mainintent);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_save:

                FirebaseUser user = mAuth.getCurrentUser();
                String UserID = user.getUid();
                String NAME = BookName.getText().toString();
                String AUTHOR = Author.getText().toString();
                String Image = com.example.bookworm.BooksFragment.EXTRA_URL;
                String RATE = Rating.getText().toString();
                String WRITE = WrittenReview.getText().toString();

                // String GENRE = Genre.getText().toString();
                // String PRICE = BookPrice.getText().toString();
                //String CAT = Category.getText().toString();


//Coding With Mitch, 2018. Inserting Data Android Firestore [ONLINE] Available at: https://www.youtube.com/watch?v=xnFnwbiDFuE&t=14s [Accessed on the 28th Feb 2019]
//Simplified Coding, 2018 [ONLINE] Available at: https://www.youtube.com/watch?v=fGiIUi3FqfQ [Accessed on the 13th March 2019]

                Note6 note6 = new Note6(
                        AUTHOR,
                        NAME,
                        RATE,
                        WRITE,
                        imageURL,
                        UserID
                );

                mFirestore.collection("Rating").add(note6)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(RateBookActivity.this, "Book Review Saved!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RateBookActivity.this, BlankActivity.class));
                            }
                        });

        }
    }
}