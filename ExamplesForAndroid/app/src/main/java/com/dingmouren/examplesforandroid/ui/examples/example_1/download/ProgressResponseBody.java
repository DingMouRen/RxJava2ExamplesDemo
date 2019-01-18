package com.dingmouren.examplesforandroid.ui.examples.example_1.download;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * 自定义responsebody对返回进度进行处理
 * Created by miya95 on 2016/12/5.
 */
public class ProgressResponseBody extends ResponseBody {

    private ResponseBody responseBody;

    private BufferedSource bufferedSource;

    public ProgressResponseBody(ResponseBody body) {
        this.responseBody = body;
    }


    @Override
    public MediaType contentType() {
        return responseBody.contentType();
    }

    /**
     * 返回文件的总大小
     * @return
     */
    @Override
    public long contentLength() {
        return responseBody.contentLength();
    }

    @Override
    public BufferedSource source() {
        if (bufferedSource == null) {
            bufferedSource = Okio.buffer(source(responseBody.source()));
        }

        return bufferedSource;
    }

    private Source source(Source source) {
        return new ForwardingSource(source) {
            long bytesReaded = 0;//记录当前文件已经下载的数据大小
            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                long bytesRead = super.read(sink, byteCount);
                bytesReaded += bytesRead == -1 ? 0 : bytesRead;

                EventBus.getDefault().post(new FileDownloadEvent(contentLength(),bytesReaded));//实时发送当前已读取(上传/下载)的字节
                return bytesRead;
            }
        };
    }
}
