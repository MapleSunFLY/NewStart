package com.fly.newstart.imgcompress.config;

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
 * 包    名 : com.fly.newstart.imgcompress.config
 * 作    者 : FLY
 * 创建时间 : 2019/5/14
 * 描述: 压缩配置
 */
public class CompressConfig implements Serializable {

    /**
     * 最小像素不压缩
     */
    private int unCompressMinPixel = 1000;

    /**
     * 标准像素不压缩
     */
    private int unCompressNormalPixel = 2000;

    /**
     * 长或宽不超过的最大像素，单位px
     */
    private int maxPixel = 1200;

    /**
     * 压缩到的最大大小，单位b
     */
    private int maxSize = 200 * 1024;

    /**
     * 是否启动像素压缩
     */
    private boolean enablePixelCompress = true;

    /**
     * 是否启动质量压缩
     */
    private boolean enableQualityCompress = true;

    /**
     * 是否保存源文件
     */
    private boolean enableReserveRaw = true;

    /**
     * 压缩后缓存图片目录，非文件路径
     */
    private String cacheDir;

    /**
     * 是否显示压缩进度条
     */
    private boolean showCompressDialog;

    private CompressConfig() {
    }

    public static CompressConfig getDefaultConfig() {
        return new CompressConfig();
    }

    public int getUnCompressMinPixel() {
        return unCompressMinPixel;
    }

    public void setUnCompressMinPixel(int unCompressMinPixel) {
        this.unCompressMinPixel = unCompressMinPixel;
    }

    public int getUnCompressNormalPixel() {
        return unCompressNormalPixel;
    }

    public void setUnCompressNormalPixel(int unCompressNormalPixel) {
        this.unCompressNormalPixel = unCompressNormalPixel;
    }

    public int getMaxPixel() {
        return maxPixel;
    }

    public void setMaxPixel(int maxPixel) {
        this.maxPixel = maxPixel;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public boolean getEnablePixelCompress() {
        return enablePixelCompress;
    }

    public void setEnablePixelCompress(boolean enablePixelCompress) {
        this.enablePixelCompress = enablePixelCompress;
    }

    public boolean getEnableQualityCompress() {
        return enableQualityCompress;
    }

    public void setEnableQualityCompress(boolean enableQualityCompress) {
        this.enableQualityCompress = enableQualityCompress;
    }

    public boolean getEnableReserveRaw() {
        return enableReserveRaw;
    }

    public void setEnableReserveRaw(boolean enableReserveRaw) {
        this.enableReserveRaw = enableReserveRaw;
    }

    public String getCacheDir() {
        return cacheDir;
    }

    public void setCacheDir(String cacheDir) {
        this.cacheDir = cacheDir;
    }

    public boolean getShowCompressDialog() {
        return showCompressDialog;
    }

    public void setShowCompressDialog(boolean showCompressDialog) {
        this.showCompressDialog = showCompressDialog;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private CompressConfig config;

        public Builder() {
            config = new CompressConfig();
        }


        public Builder setUnCompressMinPixel(int unCompressMinPixel) {
            config.setUnCompressMinPixel(unCompressMinPixel);
            return this;
        }

        public Builder setUnCompressNormalPixel(int unCompressNormalPixel) {
            config.setUnCompressNormalPixel(unCompressNormalPixel);
            return this;
        }

        public Builder setMaxPixel(int maxPixel) {
            config.setMaxPixel(maxPixel);
            return this;
        }

        public Builder setMaxSize(int maxSize) {
            config.setMaxSize(maxSize);
            return this;
        }

        public Builder enablePixelCompress(boolean enablePixelCompress) {
            config.setEnablePixelCompress(enablePixelCompress);
            return this;
        }

        public Builder enableQualityCompress(boolean enableQualityCompress) {
            config.setEnableQualityCompress(enableQualityCompress);
            return this;
        }

        public Builder enableReserveRaw(boolean enableReserveRaw) {
            config.setEnableReserveRaw(enableReserveRaw);
            return this;
        }

        public Builder setCacheDir(String cacheDir) {
            config.setCacheDir(cacheDir);
            return this;
        }

        public Builder setShowCompressDialog(boolean showCompressDialog) {
            config.setShowCompressDialog(showCompressDialog);
            return this;
        }

        public CompressConfig create() {
            return config;
        }
    }
}
