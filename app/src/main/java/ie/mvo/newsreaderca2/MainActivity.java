package ie.mvo.newsreaderca2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import ie.mvo.newsreaderca2.data.DataBase;
import ie.mvo.newsreaderca2.data.DbHelper;
import ie.mvo.newsreaderca2.databinding.ActivityMainBinding;
import ie.mvo.newsreaderca2.screens.FragmentGeneralNews;
import ie.mvo.newsreaderca2.screens.FragmentMain;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataBase dataBase = new DataBase(this);
        new DbHelper(dataBase.getWritableDatabase());
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        getSupportFragmentManager()
                .beginTransaction()
                        .add(binding.mainFragmentContainer.getId(), new FragmentMain())
                                .commit();
        setContentView(binding.getRoot());
    }
}