package eugeen3.keepinfit.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import eugeen3.keepinfit.R;
import eugeen3.keepinfit.entities.FoodItem;

import static java.lang.String.valueOf;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<FoodItem> foodItems;

    public MealAdapter(Context context, List<FoodItem> foodItems) {
        this.foodItems = foodItems;
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public MealAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.food_items_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MealAdapter.ViewHolder holder, int position) {
        FoodItem foodItem = foodItems.get(position);
        holder.nameView.setText(foodItem.getName());
        holder.massView.setText((foodItem.getMass()) + " гр.");
        holder.proteinsView.setText(valueOf(foodItem.getProteins()));
        holder.carbohydratesView.setText(valueOf(foodItem.getCarbohydrates()));
        holder.fatsView.setText(valueOf(foodItem.getFats()));
        holder.kcalsView.setText(valueOf(foodItem.getKcals()));
    }

    @Override
    public int getItemCount() {
        return foodItems == null ? 0 : foodItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView nameView, massView, proteinsView, carbohydratesView, fatsView, kcalsView;
        ViewHolder(View view){
            super(view);
            nameView = view.findViewById(R.id.foodName);
            massView = view.findViewById(R.id.foodMass);
            proteinsView = view.findViewById(R.id.foodProtValue);
            carbohydratesView = view.findViewById(R.id.foodCarbValue);
            fatsView = view.findViewById(R.id.foodFatValue);
            kcalsView = view.findViewById(R.id.foodKcalValue);
        }
    }
}