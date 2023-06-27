package com.example.shopping.sell.fragment;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopping.R;
import com.example.shopping.app.MyApplication;
import com.example.shopping.base.BaseFragment;
import com.example.shopping.sell.adapter.ImagePickerAdapter;
import com.example.shopping.sell.util.GlideImageLoader;
import com.example.shopping.sell.util.HttpUtil;
import com.example.shopping.sell.util.MyStringCallBack;
import com.example.shopping.sell.util.SelectDialog;
import com.example.shopping.utils.Constants;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;
import com.lzy.imagepicker.view.CropImageView;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class SellerFragment extends BaseFragment {

    public static final int IMAGE_ITEM_ADD = -1;
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;
    private ImagePickerAdapter adapter;
    private ArrayList<ImageItem> selImageList; //当前选择的所有图片
    private int maxImgCount = 1;        //允许选择图片最大数
    private HttpUtil httpUtil;

    private Context context;

    private EditText sellGood_name;
    private EditText sellGood_description;
    private EditText sellGood_price;
    private RecyclerView recyclerView;

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_sell, null);

        sellGood_name = view.findViewById(R.id.sellGood_name);
        sellGood_description = view.findViewById(R.id.sellGood_description);
        sellGood_price = view.findViewById(R.id.sellGood_price);
        recyclerView = view.findViewById(R.id.recyclerView);

        httpUtil = new HttpUtil();
        context = view.getContext();
        view.findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage(selImageList);
            }
        });
        //最好放到 Application oncreate执行
        initImagePicker();
        initWidget();


        return view;
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
        adapter = new ImagePickerAdapter(context, selImageList, maxImgCount);
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
                        Intent intentPreview = new Intent(context, ImagePreviewDelActivity.class);
                        intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, (ArrayList<ImageItem>) adapter.getImages());
                        intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);
                        intentPreview.putExtra(ImagePicker.EXTRA_FROM_ITEMS,true);
                        startActivityForResult(intentPreview, REQUEST_CODE_PREVIEW);
                        break;
                }
            }
        });

        recyclerView.setLayoutManager(new GridLayoutManager(context, 4));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    private SelectDialog showDialog(SelectDialog.SelectDialogListener listener, List<String> names) {
        SelectDialog dialog = new SelectDialog(getActivity(), R.style.transparentFrameWindowStyle, listener, names);
        if (!getActivity().isFinishing()) {
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

    // 上传商品
    private void uploadImage(ArrayList<ImageItem> pathList) {

        String name = sellGood_name.getText().toString();
        String description = sellGood_description.getText().toString();
        String price = sellGood_price.getText().toString();
        int seller_id = MyApplication.getMe().getId();

        httpUtil.postFileRequest(Constants.UploadGood, null, pathList, new MyStringCallBack() {

            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
            }
            @Override
            public void onResponse(String response, int id) {
                super.onResponse(response, id);
                //返回图片的地址
            }
        });
    }
}
