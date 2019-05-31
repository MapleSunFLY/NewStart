package com.fly.newstart.imgcompress.inputstream;

import java.io.IOException;
import java.io.InputStream;

/**
 * 打开新的inputstream时自动关闭上一个inputstream，最后需要手动调用@link close（）释放资源
 * Automatically close the previous InputStream when opening a new InputStream,
 * and finally need to manually call {@link #close()} to release the resource.
 */
public abstract class InputStreamAdapter implements InputStreamProvider {

  private InputStream inputStream;

  @Override
  public InputStream open() throws IOException {
    close();
    inputStream = openInternal();
    return inputStream;
  }

  public abstract InputStream openInternal() throws IOException;

  @Override
  public void close() {
    if (inputStream != null) {
      try {
        inputStream.close();
      } catch (IOException ignore) {
      }finally {
        inputStream = null;
      }
    }
  }
}