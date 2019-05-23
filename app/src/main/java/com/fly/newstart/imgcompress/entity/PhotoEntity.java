package com.fly.newstart.imgcompress.entity;

import java.io.Serializable;

/**
 * <pre>
 *           .----.
 *        _.'__    `.
 *    .--(Q)(OK)---/$\
 *  .' @          /$$$\
 *  :         ,   $$$$$
 *   `-..__.-' _.-\$/
 *         `;_:    `"'
 *       .'"""""`.
 *      /,  FLY  ,\
 *     //         \\
 *     `-._______.-'
 *     ___`. | .'___
 *    (______|______)
 * </pre>
 * 包    名 : com.fly.newstart.imgcompress.entity
 * 作    者 : FLY
 * 创建时间 : 2019/5/14
 * 描述: 图片压缩的实体
 */
public class PhotoEntity implements Serializable {

    /**
     * 图片原始路径
     */
    private String originalPath;

    /**
     * 是否压缩成功
     */
    private boolean compressed;

    /**
     * 压缩后路径
     */
    private String compressPath;

    /**
     * 上传后的网络Url
     */
    private String uploadUrl;

    private String failedReason;

    public PhotoEntity() {
    }

    public PhotoEntity(String originalPath) {
        this.originalPath = originalPath;
    }

    public String getOriginalPath() {
        return originalPath;
    }

    public void setOriginalPath(String originalPath) {
        this.originalPath = originalPath;
    }

    public boolean getCompressed() {
        return compressed;
    }

    public void setCompressed(boolean compressed) {
        this.compressed = compressed;
    }

    public String getCompressPath() {
        return compressPath;
    }

    public void setCompressPath(String compressPath) {
        this.compressPath = compressPath;
    }

    public String getUploadUrl() {
        return uploadUrl;
    }

    public void setUploadUrl(String uploadUrl) {
        this.uploadUrl = uploadUrl;
    }

    public String getFailedReason() {
        return failedReason;
    }

    public void setFailedReason(String failedReason) {
        this.failedReason = failedReason;
    }
}
