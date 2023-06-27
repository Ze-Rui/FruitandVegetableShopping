package com.example.shopping.app;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.example.shopping.R;
import com.example.shopping.sell.adapter.ImagePickerAdapter;
import com.example.shopping.sell.util.GlideImageLoader;
import com.example.shopping.sell.util.SelectDialog;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;
import com.lzy.imagepicker.view.CropImageView;

import java.util.ArrayList;
import java.util.List;

public class DynamicActivity extends AppCompatActivity {

    RecyclerView dynamic_add_rec;

    public static final int IMAGE_ITEM_ADD = -1;
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;
    private ImagePickerAdapter adapter;
    private ArrayList<ImageItem> selImageList; //当前选择的所有图片
    private int maxImgCount = 1;        //允许选择图片最大数

    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_activity);

        mContext = this;

        dynamic_add_rec = findViewById(R.id.dynamic_recyclerView);
        // 标题栏，返回功能
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setTitle("新增动态");
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        initImagePicker();
        initWidget();
    }
    /**
     * 返回功能
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish(); // back button
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());  //设置图片加载器
        imagePicker.setShowCamera(true);           //显示拍照按钮
        imagePicker.setCrop(true);              //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true);          //是否按矩形区域保存
        imagePicker.setSelectLimit(maxImgCount);       //选中数量限制
        imagePicker.setMultiMode(false);           //多选
        imagePicker.setStyle(CropImageView.Style.RECTANGLE); //裁剪框的形状
        imagePicker.setFocusWidth(800);            //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);           //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(5000);             //保存文件的宽度。单位像素
        imagePicker.setOutPutY(5000);             //保存文件的高度。单位像素
    }
    private void initWidget() {

        selImageList = new ArrayList<>();
        adapter = new ImagePickerAdapter(mContext, selImageList, maxImgCount);
        adapter.setOnItemClickListener(new ImagePickerAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                switch (position) {
                    case IMAGE_ITEM_ADD:
                        List<String> names = new ArrayList<>();
                        names.add("拍照");
                        names.add("相册");
                        showDialog(new SelectDialog.SelectDialogListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                switch (position) {
                                    case 0: // 直接调起相机
                                        //打开选择,本次允许选择的数量
                                        ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
                                        Intent intent = new Intent(mContext, ImageGridActivity.class);
                                        intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS,true); // 是否是直接打开相机
                                        startActivityForResult(intent, REQUEST_CODE_SELECT);
                                        break;
                                    case 1:
                                        //打开选择,本次允许选择的数量
                                        ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
                                        Intent intent1 = new Intent(mContext, ImageGridActivity.class);
                                        startActivityForResult(intent1, REQUEST_CODE_SELECT);
                                        break;
                                    default:
                                        break;
                                }
                            }
                        }, names);
                        break;
                    default:
                        //打开预览
                        Intent intentPreview = new Intent(mContext, ImagePreviewDelActivity.class);
                        intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, (ArrayList<ImageItem>) adapter.getImages());
                        intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);
                        intentPreview.putExtra(ImagePicker.EXTRA_FROM_ITEMS,true);
                        startActivityForResult(intentPreview, REQUEST_CODE_PREVIEW);
                        break;
                }
            }
        });

        dynamic_add_rec.setLayoutManager(new GridLayoutManager(mContext, 4));
        dynamic_add_rec.setHasFixedSize(true);
        dynamic_add_rec.setAdapter(adapter);
    }

    private SelectDialog showDialog(SelectDialog.SelectDialogListener listener, List<String> names) {
        SelectDialog dialog = new SelectDialog(this, R.style.transparentFrameWindowStyle, listener, names);
        if (!this.isFinishing()) {
            dialog.show();
        }
        return dialog;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            //添加图片返回
            if (data != null && requestCode == REQUEST_CODE_SELECT) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (images != null){
                    selImageList.addAll(images);
                    adapter.setImages(selImageList);
                }
            }
        } else if (resultCode == ImagePicker.RESULT_CODE_BACK) {
            //预览图片返回
            if (data != null && requestCode == REQUEST_CODE_PREVIEW) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
                if (images != null){
                    selImageList.clear();
                    selImageList.addAll(images);
                    adapter.setImages(selImageList);
                }
            }
        }
    }




}
