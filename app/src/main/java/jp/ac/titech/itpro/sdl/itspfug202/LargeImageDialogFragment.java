package jp.ac.titech.itpro.sdl.itspfug202;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.ImageView;

public class LargeImageDialogFragment extends DialogFragment {

    private AlertDialog dialog;
    private AlertDialog.Builder dialogBuilder;
    public ImageView shop_image;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        dialogBuilder = new AlertDialog.Builder(getActivity());

        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_shop_image, null);

        ImageView detail_shop_image = view.findViewById(R.id.shop_image);
        detail_shop_image.setImageDrawable(shop_image.getDrawable());
        detail_shop_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        detail_shop_image.setScaleType(ImageView.ScaleType.FIT_XY);
        detail_shop_image.setAdjustViewBounds(true);

        dialogBuilder.setView(view);
        dialog = dialogBuilder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        return dialog;
    }

    public void setShop_image(ImageView image) {
        shop_image = image;
    }
}
