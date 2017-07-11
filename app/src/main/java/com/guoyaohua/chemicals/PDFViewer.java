package com.guoyaohua.chemicals;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.epapyrus.plugpdf.SimpleDocumentReader;
import com.epapyrus.plugpdf.SimpleDocumentReaderListener;
import com.epapyrus.plugpdf.SimpleReaderFactory;
import com.epapyrus.plugpdf.core.viewer.DocumentState;

import java.io.File;

public class PDFViewer extends AppCompatActivity {
    String filePath = "";
    String title = "";
    private SimpleDocumentReader mReader;
    /**
     * listener implemented to receive event notifications on completion of PDF document loading on a {@link SimpleDocumentReader}
     */
    private SimpleDocumentReaderListener listener = new SimpleDocumentReaderListener() {

        @Override
        public void onLoadFinish(DocumentState.OPEN state) {
            Log.i("PlugPDF", "[INFO] Open " + state);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfviewer);
        filePath = this.getIntent().getExtras().getString("filePath");//获得Intent传过来的化学品名称。
        title = this.getIntent().getExtras().getString("title");
        setTitle(title);
        if (fileIsExists(filePath)) {//文件存在
            // launch the PDF viewer
            mReader = SimpleReaderFactory.createSimpleViewer(this, listener);
            mReader.openFile(filePath, "");
            mReader.enableAnnotationMenu(false);
            // mReader.setTitle(title);
        } else {
            Toast.makeText(this, "文件缺失，请重新安装程序", Toast.LENGTH_SHORT).show();
            this.finish();
        }
    }

    @Override
    protected void onDestroy() {
        if (mReader != null && mReader.getDocument() != null) {
            mReader.save();
            mReader.clear();
        }
        super.onDestroy();
    }

    public boolean fileIsExists(String path) {
        try {
            File f = new File(path);
            if (!f.exists()) {
                return false;
            }

        } catch (Exception e) {
            // TODO: handle exception
            return false;
        }
        return true;
    }
}
