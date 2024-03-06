package `fun`.xjbcode.assetmanager

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.net.URL
import java.nio.file.Files
import kotlin.io.path.Path
import kotlin.coroutines.*;
import kotlin.io.*;

data class Asset(var url: String, var fileName: String, var fileSize: Long) {}

enum class AssetState {
    Valid,
    Corrupt,
    Downloading,
    NotDownloaded
}

var baseDir = "/sdcard"

class AssetManager() {
    val downloadDoneChan = Channel<Asset>() // url download done
    var assets = arrayListOf<Asset>()

    private var downloadQueue = arrayListOf<Asset>()

    private fun downloadFile(url: URL, dir: String, fileName: String) {
        url.openStream().use { inp ->
            BufferedInputStream(inp).use { bis ->
                FileOutputStream(dir + "/" + fileName).use { fos ->
                    val data = ByteArray(1024)
                    var count: Int
                    while (bis.read(data, 0, 1024).also { count = it } != -1) {
                        fos.write(data, 0, count)
                    }
                }
            }
        }
    }

    private fun download(url:String, dir:String, fileName:String) {
        downloadFile(URL(url), dir, fileName)
    }

    fun getAssetState(asset: Asset) : AssetState {
        if(downloadQueue.contains(asset)) {
            return AssetState.Downloading
        }
        val path = Path(baseDir + "/" + asset.fileName)
        if(Files.exists(path) && Files.isReadable(path) && Files.isRegularFile(path)) {
            if(path.toFile().length() == asset.fileSize) {
                return AssetState.Valid
            } else {
                return AssetState.Corrupt
            }
        }
        return AssetState.NotDownloaded;
    }

    fun scheduleDownload(index:Int) : Boolean {
        if(index<0 || index>=assets.size) {
            return false;
        }
        val asset = assets.get(index)

        GlobalScope.launch {
            asyncDownload(asset)
        }
        return true;
    }

    private suspend fun asyncDownload(asset:Asset) {
        downloadQueue.add(asset)
        download(asset.url, baseDir, asset.fileName)
        downloadQueue.remove(asset)
        downloadDoneChan.send(asset)

    }


}