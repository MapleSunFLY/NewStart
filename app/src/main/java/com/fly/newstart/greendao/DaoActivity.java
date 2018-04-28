package com.fly.newstart.greendao;

import android.os.Bundle;
import android.util.Log;

import com.fly.newstart.R;
import com.fly.newstart.common.base.BaseActivity;
import com.fly.newstart.greendao.db.utils.GreenDaoUtils;
import com.fly.newstart.greendao.entity.User;
import com.fly.newstart.greendao.gen.UserDao;

import java.util.List;

public class DaoActivity extends BaseActivity {

    UserDao mUserDao = GreenDaoUtils.getDaoSession().getUserDao();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dao);
    }

    //增
    private void insert(){
        User mUser = new User((long)2,"anye3");
        mUserDao.insert(mUser);//添加一个
    }

    //删
    private void delete(){
        //mUserDao.deleteByKey(id);
    }

    //改
    private void update(){
        User mUser = new User((long)2,"anye0803");
        mUserDao.update(mUser);
    }

    //查
    private void load(){
        List<User> users = mUserDao.loadAll();
        String userName = "";
        for (int i = 0; i < users.size(); i++) {
            userName += users.get(i).getName()+",";
        }
        Log.d(TAG, "load: "+userName);

        List<User> list = mUserDao.queryBuilder()
                .offset(1)//偏移量，相当于 SQL 语句中的 skip
                .limit(3)//只获取结果集的前 3 个数据
                .orderAsc(UserDao.Properties.Name)//通过 StudentNum 这个属性进行正序排序
                .where(UserDao.Properties.Name.eq("zone"))//数据筛选，只获取 Name = "zone" 的数据。
                .build()
                .list();
       /* 需要注意的是 offset 是要和 limit 配合使用的。

        list() 所有实体会直接加载到内存中。
        listLazy() 当你需要使用时，才会加载，会自动缓存。使用完必须关闭。
        listLazyUncached() 如你所见，就是不会缓存的意思。使用完必须关闭。
        listIterator() 通过迭代器遍历结果集，不会缓存。使用完必须关闭。
        unique() 返回一个或者零个结果
        uniqueOrThrow() 返回非空的结果，否则抛出异常
        listLazy(), listLazyUncached(), listIterator() 这三个方法都使用了 LazyList.class 这个类。它持有了数据库游标的引用，这就是为什么要关闭的原因。当然，当你遍历完所有的结果集，它是会自动关闭的。如果没有遍历完，就得手动关闭了。*/
    }
}
