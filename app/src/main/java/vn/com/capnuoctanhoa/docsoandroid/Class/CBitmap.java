package vn.com.capnuoctanhoa.docsoandroid.Class;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class CBitmap {

    public static Bitmap imageOreintationValidator(Bitmap bitmap, String path) {
        ExifInterface ei;
        try {
            ei = new ExifInterface(path);
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    bitmap = rotateImage(bitmap, 90);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    bitmap = rotateImage(bitmap, 180);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    bitmap = rotateImage(bitmap, 270);
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    public static Bitmap rotateImage(Bitmap source, float angle) {

        Bitmap bitmap = null;
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        try {
            bitmap = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                    matrix, true);
        } catch (OutOfMemoryError err) {
            err.printStackTrace();
        }
        return bitmap;
    }

    public static String convertBitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        String str = Base64.encodeToString(stream.toByteArray(), Base64.NO_WRAP);
        return str;
    }

    public static byte[] convertBitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        return stream.toByteArray();
    }

    public static Bitmap convertByteArrayToBitmap(byte[] bytes) {
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    public static Bitmap scale(Bitmap bm, float ratio) {
        int width = Math.round(ratio * bm.getWidth());
        int height = Math.round(ratio * bm.getHeight());
        return Bitmap.createScaledBitmap(bm, width, height, true);
    }

    public static Bitmap scale(Bitmap bm, int maxImageSize) {
        float ratio = 1F * maxImageSize / Math.max(bm.getWidth(), bm.getHeight());
        return scale(bm, ratio);
    }

    public static Bitmap scale(byte[] bytes, float ratio) {
        Bitmap bm = byteArrayToBitmap(bytes);
        return scale(bm, ratio);
    }

    public static Bitmap scale(byte[] bytes, int maxImageSize) {
        Bitmap bm = byteArrayToBitmap(bytes);
        return scale(bm, maxImageSize);
    }

    public static Bitmap scaleDown(Bitmap bm, float ratio) {
        if (ratio > 1F)
            return bm;
        return scale(bm, ratio);
    }

    public static Bitmap rotate(Bitmap bm, int degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        return Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, false);
    }

    public static byte[] compress(Bitmap bmp, int quality) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            bmp.compress(Bitmap.CompressFormat.JPEG, quality, baos);
            return baos.toByteArray();
        }
        catch (Exception ignored) {
            return null;
        }
    }

    public static byte[] Transform(Bitmap bmp, int quality, int maxImageSize) {
        float ratio = 1F * maxImageSize / Math.max(bmp.getWidth(), bmp.getHeight());
//        float ratio = 1F;
        bmp = scaleDown(bmp, ratio);
        bmp = rotate(bmp, 90);
        return compress(bmp, quality);
    }

    public static byte[] defaultTransform(Bitmap bmp) {
        return Transform(bmp, 90, 1500);
    }

    public static Bitmap byteArrayToBitmap(byte[] bytes) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
    }

    public static String byteArrayToBase64(byte[] bytes) {
        if (bytes.length == 0)
            return "";
        return Base64.encodeToString(bytes, Base64.NO_WRAP);
    }

    public static String imageToBase64(Bitmap bmp) {
        if (bmp == null)
            return "";
        byte[] bytes = compress(bmp, 90);
        return byteArrayToBase64(bytes);
    }

    public static Bitmap base64ToImage(String base64) {
        if (base64 == null || base64.isEmpty())
            return null;
        byte[] decodedString = Base64.decode(base64, Base64.NO_WRAP);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }
}
