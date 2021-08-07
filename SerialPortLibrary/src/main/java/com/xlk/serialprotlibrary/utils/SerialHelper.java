package com.xlk.serialprotlibrary.utils;

import android.util.Log;

import com.xlk.serialprotlibrary.bean.ComBean;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidParameterException;

import android_serialport_api.SerialPort;

/**
 * @author Created by xlk on 2021/8/3.
 * @desc https://github.com/HuRuWo/SerialPortHelper
 */
public abstract class SerialHelper {
    private final String TAG = "SerialHelper-->";
    private SerialPort mSerialPort;
    /**
     * 输出流，Android将需要传输的数据发送到单片机或者传感器（使用的时候，执行Write方法）
     */
    private OutputStream mOutputStream;
    /**
     * 输入流，也就是获取从单片机或者传感器，通过串口传入到Android主板的IO数据（使用的时候，执行Read方法）
     */
    private InputStream mInputStream;
    private ReadThread mReadThread;
    private SendThread mSendThread;
    /**
     * 串口的物理地址，一般硬件工程师都会告诉你的例如ttyS0、ttyS1等，或者通过SerialPortFinder类去寻找得到可用的串口地址。
     */
    private String sPort;
    /**
     * 波特率，与外接设备一致
     */
    private int iBaudRate;
    /**
     * 当前串口是否开启
     */
    private boolean _isOpen;
    private byte[] _bLoopData;
    private int iDelay;

    public SerialHelper(String sPort, int iBaudRate) {
        this.sPort = "/dev/ttyS0";
        this.iBaudRate = 9600;
        this._isOpen = false;
        this._bLoopData = new byte[]{48};
        this.iDelay = 500;
        this.sPort = sPort;
        this.iBaudRate = iBaudRate;
    }

    private SerialHelper() {
    }

    public SerialHelper(String sPort) {
        this(sPort, 9600);
    }

    public SerialHelper(String sPort, String sBaudRate) {
        this(sPort, Integer.parseInt(sBaudRate));
    }

    public void open() throws SecurityException, IOException, InvalidParameterException {
        this.mSerialPort = new SerialPort(new File(this.sPort), this.iBaudRate, 0);
        this.mOutputStream = this.mSerialPort.getOutputStream();
        this.mInputStream = this.mSerialPort.getInputStream();
        this.mReadThread = new ReadThread();
        this.mReadThread.start();
        this.mSendThread = new SendThread();
        this.mSendThread.setSuspendFlag();
        this.mSendThread.start();
        this._isOpen = true;
    }

    public void close() {
        if (this.mReadThread != null) {
            this.mReadThread.interrupt();
        }

        if (this.mSerialPort != null) {
            this.mSerialPort.close();
            this.mSerialPort = null;
        }

        this._isOpen = false;
    }

    public void send(byte[] bOutArray) {
        try {
            this.mOutputStream.write(bOutArray);
            int available = SerialHelper.this.mInputStream.available();
            Log.e(TAG, "send available=" + available);
        } catch (IOException var3) {
            var3.printStackTrace();
        }
    }

    public void sendHex(String sHex) {
        byte[] bOutArray = DataUtils.HexToByteArr(sHex);
        this.send(bOutArray);
    }

    public void sendTxt(String sTxt) {
        byte[] bOutArray = sTxt.getBytes();
        this.send(bOutArray);
    }

    public int getBaudRate() {
        return this.iBaudRate;
    }

    public boolean setBaudRate(int iBaud) {
        if (this._isOpen) {
            return false;
        } else {
            this.iBaudRate = iBaud;
            return true;
        }
    }

    public boolean setBaudRate(String sBaud) {
        int iBaud = Integer.parseInt(sBaud);
        return this.setBaudRate(iBaud);
    }

    public String getPort() {
        return this.sPort;
    }

    public boolean setPort(String sPort) {
        if (this._isOpen) {
            return false;
        } else {
            this.sPort = sPort;
            return true;
        }
    }

    public boolean isOpen() {
        return this._isOpen;
    }

    public byte[] getbLoopData() {
        return this._bLoopData;
    }

    public void setbLoopData(byte[] bLoopData) {
        this._bLoopData = bLoopData;
    }

    public void setTxtLoopData(String sTxt) {
        this._bLoopData = sTxt.getBytes();
    }

    public void setHexLoopData(String sHex) {
        this._bLoopData = DataUtils.HexToByteArr(sHex);
    }

    public int getiDelay() {
        return this.iDelay;
    }

    public void setiDelay(int iDelay) {
        this.iDelay = iDelay;
    }

    public void startSend() {
        if (this.mSendThread != null) {
            this.mSendThread.setResume();
        }

    }

    public void stopSend() {
        if (this.mSendThread != null) {
            this.mSendThread.setSuspendFlag();
        }

    }

    protected abstract void onDataReceived(ComBean var1);

    private class SendThread extends Thread {
        /**
         * 暂停标志
         */
        public boolean suspendFlag;

        private SendThread() {
            this.suspendFlag = true;
        }

        public void run() {
            super.run();

            Log.i(TAG, "SendThread this.isInterrupted()=" + this.isInterrupted());
            while (!this.isInterrupted()) {
                synchronized (this) {
                    while (this.suspendFlag) {
                        try {
                            this.wait();
                        } catch (InterruptedException var5) {
                            var5.printStackTrace();
                        }
                    }
                }
                Log.i(TAG, "SendThread 发送数据");
                SerialHelper.this.send(SerialHelper.this.getbLoopData());

                try {
                    Thread.sleep((long) SerialHelper.this.iDelay);
                } catch (InterruptedException var4) {
                    var4.printStackTrace();
                }
            }

        }

        public void setSuspendFlag() {
            this.suspendFlag = true;
        }

        public synchronized void setResume() {
            this.suspendFlag = false;
            this.notify();
        }
    }

    private class ReadThread extends Thread {
        private ReadThread() {
        }

        public void run() {
            super.run();

            Log.i(TAG, "ReadThread this.isInterrupted()=" + this.isInterrupted());
            while (!this.isInterrupted()) {
                try {
                    if (SerialHelper.this.mInputStream == null) {
                        Log.e(TAG, "串口还未开启,退出ReadThread");
                        return;
                    }
                    byte[] buffer = new byte[1024];
                    //需要先判断mInputStream中是否有数据
                    int available = SerialHelper.this.mInputStream.available();
                    if (available > 0) {
                        int size = SerialHelper.this.mInputStream.read(buffer);
                        Log.e(TAG, "ReadThread size=" + size);
                        if (size > 0) {
                            ComBean ComRecData = new ComBean(SerialHelper.this.sPort, buffer, size);
                            SerialHelper.this.onDataReceived(ComRecData);
                        }
                    }
                } catch (Throwable var4) {
                    Log.e("error", var4.getMessage());
                    return;
                }
            }

        }
    }
}
