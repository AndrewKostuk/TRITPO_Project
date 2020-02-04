package eugeen3.keepinfit.ui;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.appcompat.app.AppCompatActivity;

import eugeen3.keepinfit.R;
import eugeen3.keepinfit.adapters.DBAdapter;

public class SearchFoodItem extends AppCompatActivity {

    DBAdapter sqlHelper;
    SQLiteDatabase db;
    Cursor foodItemCursor;
    SimpleCursorAdapter foodItemAdapter;
    ListView foodItemList;
    EditText foodItemFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        foodItemList = findViewById(R.id.foundedFoodItems);
        foodItemFilter = findViewById(R.id.searchFood);

        sqlHelper = new DBAdapter(getApplicationContext());
        sqlHelper.create_db();
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            db = sqlHelper.open();
            foodItemCursor = db.rawQuery("select * from " + DBAdapter.getTable(), null);
            String[] headers = new String[]{DBAdapter.getColumnName(),
                    DBAdapter.getColumnProteins(), DBAdapter.getColumnFats(),
                    DBAdapter.getColumnCarbs(), DBAdapter.getColumnKcals(), DBAdapter.getColumnId()};
            foodItemAdapter = new SimpleCursorAdapter(this, R.layout.founded_food_items,
                    foodItemCursor, headers, new int[]{R.id.foundedFoodName,
                    R.id.foundedFoodProtValue, R.id.foundedFoodFatValue,
                    R.id.foundedFoodCarbValue, R.id.foundedFoodKcalValue}, 0);

            if(!foodItemFilter.getText().toString().isEmpty())
                foodItemAdapter.getFilter().filter(foodItemFilter.getText().toString());

            foodItemFilter.addTextChangedListener(new TextWatcher() {

                public void afterTextChanged(Editable s) { }

                public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    foodItemAdapter.getFilter().filter(s.toString());
                }
            });

            foodItemAdapter.setFilterQueryProvider(new FilterQueryProvider() {
                @Override
                public Cursor runQuery(CharSequence constraint) {

                    if (constraint == null || constraint.length() == 0) {
                        return db.rawQuery("select * from " + DBAdapter.getTable(), null);
                    }
                    else {
                        return db.rawQuery("select * from " + DBAdapter.getTable() + " where " +
                                DBAdapter.getColumnName() + " like ?", new String[]{"%" + constraint.toString() + "%"});
                    }
                }
            });

            foodItemList.setAdapter(foodItemAdapter);
        }
        catch (SQLException ex){}
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        db.close();
        foodItemCursor.close();
    }

    public void cancelSearch(View view) {
        Intent intent = new Intent(this, Eating.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, Eating.class);
        startActivity(intent);
        finish();
    }

    public void addFoodItemToDB() {
        
    }
}