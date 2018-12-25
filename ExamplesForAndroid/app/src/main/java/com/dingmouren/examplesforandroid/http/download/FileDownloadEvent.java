package com.dingmouren.examplesforandroid.http.download;

/**
 * @author dingmouren
 */
public class FileDownloadEvent {
    public long total;
    public long progress;
    public FileDownloadEvent(long total,long progress){
        this.total = total;
        this.progress = progress;
    }
}
