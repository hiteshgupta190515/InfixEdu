package com.infix.edu.Interface;

import android.os.Handler;
import android.os.Looper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.LogRecord;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okio.BufferedSink;

public class ProgressRequestBody extends RequestBody {

    private File file;
    private UploadCallBack listener;
    private static final int DEFAULT_BUFFER_SIZE = 4096;

    public ProgressRequestBody(File file, UploadCallBack listener) {
        this.file = file;
        this.listener = listener;
    }

    @Override
    public MediaType contentType() {
        return MediaType.parse("*/*");
    }

    @Override
    public long contentLength() throws IOException {
        return file.length();
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {

        long file_lenght = file.length();
        byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
        FileInputStream in = new FileInputStream(file);
        long uploaded = 0;

        try{

            int read;

            Handler handler = new Handler(Looper.getMainLooper());

            while ((read = in.read(buffer)) != -1){

                handler.post(new ProgressUpdater(uploaded,file_lenght));
                uploaded += read;
                sink.write(buffer,0,read);

            }


        }catch (Exception e){
            e.printStackTrace();
        }finally {
            in.close();
        }

    }

    private class ProgressUpdater implements Runnable{

        private long uploaded;
        private long fileLenght;

        public ProgressUpdater(long uploaded, long fileLenght) {
            this.uploaded = uploaded;
            this.fileLenght = fileLenght;
        }

        @Override
        public void run() {
            listener.onProgressUpdate((int)(100*uploaded/fileLenght));
        }
    }

}
