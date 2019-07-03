package com.fly.newstart.load.page;

import com.fly.myview.loading.page.Page;
import com.fly.newstart.R;

/**
 * 包    名 : com.fly.newstart.load.page
 * 作    者 : FLY
 * 创建时间 : 2019/7/2
 * 描述: 没有数据界面
 */
public class EmptyPage extends Page {
    @Override
    protected int onCreateView() {
        return R.layout.loading_page_empty_layout;
    }
}
