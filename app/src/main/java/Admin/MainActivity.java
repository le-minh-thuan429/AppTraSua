package Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.apptrasua.R;
import com.google.android.material.navigation.NavigationView;

import Admin.fragmentAdmin.DanhMucFragment;
import Admin.fragmentAdmin.DonHangFragment;
import Admin.fragmentAdmin.SanPhamFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private DrawerLayout mDrawerLayout;
    private static final int FRAGMENT_HOME=0;
    private static final int FRAGMENT_FAVORITE=0;
    private static final int FRAGMENT_HISTORY=0;
    private int mCurrentFragment=1;
    FragmentTransaction transaction;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

         toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar); //thêm toolbar

        mDrawerLayout=findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,mDrawerLayout,toolbar,
                R.string.Open,R.string.Close);//Chuyển đổi ngăn thanh tác vụ
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //bắt sự kiện
        NavigationView navigationView=findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        replaceFragment(new DanhMucFragment());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.DanhMuc) {
            replaceFragment(new DanhMucFragment());
            toolbar.setTitle("Danh mục sản phẩm");
        } else if (id == R.id.SanPham){
            replaceFragment(new SanPhamFragment());
            toolbar.setTitle("Danh sách sản phẩm");
        } else if (id == R.id.DonHang){
            replaceFragment(new DonHangFragment());
            toolbar.setTitle("Tài khoản và Đơn hàng");
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void replaceFragment(Fragment fragment){
        transaction= getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame,fragment);
        transaction.commit();

    }

    @Override
    public void onBackPressed() {
        if(mDrawerLayout.isDrawerOpen(GravityCompat.START)){
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }
}