package com.example.user.poddarcanteen;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

public class AdmHome extends AppCompatActivity implements TabLayout.OnTabSelectedListener  {
    private TabLayout tabLayout;
    //This is our viewPager
    private ViewPager viewPager;

//    private DrawerLayout dl;
//    private ActionBarDrawerToggle t;
 //   private NavigationView nv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adm_home);

  /*      dl = (DrawerLayout) findViewById(R.id.activity_adm_home);
        t = new ActionBarDrawerToggle(this, dl, R.string.Open, R.string.Close);
        t.setDrawerIndicatorEnabled(true);
        dl.addDrawerListener(t);
        t.syncState();
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nv = (NavigationView) findViewById(R.id.nv);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.account:
                        Toast.makeText(AdmHome.this, "My Account", Toast.LENGTH_SHORT).show();
                    case R.id.settings:
                        Toast.makeText(AdmHome.this, "Settings", Toast.LENGTH_SHORT).show();
//                    case R.id.mycart:
//                        Toast.makeText(MainActivity.this, "My Cart",Toast.LENGTH_SHORT).show();
                    case R.id.add_Item:
                        Intent i = new Intent(AdmHome.this, Add_Item.class);
                        startActivity(i);
                    default:
                        return true;
                }
            }
        });
*/

        //Initializing the tablayout
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        //Adding the tabs using addTab() method
        tabLayout.addTab(tabLayout.newTab().setText("Received"));
        tabLayout.addTab(tabLayout.newTab().setText("Cooking"));
        tabLayout.addTab(tabLayout.newTab().setText("Delivered"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //Initializing viewPager
        viewPager = (ViewPager) findViewById(R.id.pager);

        //Creating our pager adapter
        Pager adapter = new Pager(getSupportFragmentManager(), tabLayout.getTabCount());

        //Adding adapter to pager
        viewPager.setAdapter(adapter);

        //Adding onTabSelectedListener to swipe views
        tabLayout.setOnTabSelectedListener(this);

    }
/*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return t.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }
*/


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_navigation,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_item:
                Intent i=new Intent(getApplicationContext(),Add_Item.class);
                startActivity(i);
                return true;
            case R.id.delete_item:
                Toast.makeText(getApplicationContext(),"Delete",Toast.LENGTH_LONG).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }





    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

}

//public class AdmHome extends AppCompatActivity {
//    SQLiteOpenHelper openHelper;
//    SQLiteDatabase db;
//    EditText fname,fprice,food_type;
//    Button enter,order_list;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_adm_home);
//        openHelper = new UserDbHelper1(this);
//
//        fname=findViewById(R.id.fname);
//        fprice=findViewById(R.id.fprice);
//        food_type=findViewById(R.id.food_type);
//        enter=findViewById(R.id.enter);
//        order_list=findViewById(R.id.order_list);
//        enter.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                db = openHelper.getWritableDatabase();
//                String fd_name = fname.getText().toString();
//                String fd_price = fprice.getText().toString();
//                String fd_type = food_type.getText().toString();
//
//                if(fd_name.length()>1 && fd_type.length()>1 && fd_price.length()>1){
//                    insertData(fd_name, fd_type, fd_price);
//                    Toast.makeText(getApplicationContext(),"Inserted",Toast.LENGTH_LONG).show();
//                    Intent i=new Intent(getApplicationContext(),Home.class);
//                    startActivity(i);
//                }else{
//                    Toast.makeText(getApplicationContext(),"You have to enter all the data",Toast.LENGTH_LONG).show();
//                }
//
//            }
//        });
//    }public void insertData(String f_name, String f_type, String f_price) {
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(UserDbHelper1.COL_1, f_name);
//        contentValues.put(UserDbHelper1.COL_2, f_type);
//        contentValues.put(UserDbHelper1.COL_3, f_price);
//        long id = db.insert(UserDbHelper1.TABLE_NAME, null, contentValues);
//    }
//}
