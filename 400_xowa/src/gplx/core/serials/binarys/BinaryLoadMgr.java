package gplx.core.serials.binarys;

import gplx.Bry_;
import gplx.core.serials.core.SerialLoadMgr;
import gplx.core.serials.core.SerialLoadWkr;
import gplx.core.texts.Base64Converter;

public class BinaryLoadMgr implements SerialLoadMgr {
    public static final int CORE_VERSION = 0;
    private byte[] data;
    private int headerEnd;
    private int dataVersion;

    @Override
    public int CoreVersion() {
        return CORE_VERSION;
    }

    @Override
    public int DataVersion() {return dataVersion;}

    @Override
    public void ReadHeader(byte[] data) {
        this.data = data;
        this.dataVersion = Base64Converter.GetIndexInt((char)data[1]);
        this.headerEnd = 3; // 1=coreVersion; 2=dataVersion; 3=\n
    }

    @Override
    public byte[] Data() {
        return data;
    }

    @Override
    public int HeaderEnd() {
        return headerEnd;
    }

    @Override
    public SerialLoadWkr NewLoadWkr() {
        return new BinaryLoadWkr().Ctor(this);
    }

    public byte[] FldDlm() {return fldDlm;} private final byte[] fldDlm = Bry_.new_a7("|");
    public byte[] RowDlm() {return rowDlm;} private final byte[] rowDlm = Bry_.new_a7("\n");
}
