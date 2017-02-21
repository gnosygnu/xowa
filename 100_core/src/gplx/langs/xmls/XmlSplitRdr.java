/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.langs.xmls; import gplx.*; import gplx.langs.*;
import gplx.core.ios.streams.*;
public class XmlSplitRdr {
	public byte[] CurAry() {return curAry;} private byte[] curAry;
	public long CurSum() {return curSum;} long curSum;
	public int CurRead() {return curRead;} int curRead;
	public boolean Done() {return done;} private boolean done;		
	public XmlSplitRdr InitAll_(Io_url url) {
		stream = Io_mgr.Instance.OpenStreamRead(url);
		curLen = stream.Len();
		curAry = new byte[(int)curLen];
		curSum = 0;
		curRead = 0;
		done = false;
		return this;
	}
	public XmlSplitRdr Init_(Io_url url, int curArySize) {
		stream = Io_mgr.Instance.OpenStreamRead(url);
		curLen = Io_mgr.Instance.QueryFil(url).Size();
		curAry = new byte[curArySize];
		curSum = 0;
		curRead = 0;
		done = false;
		return this;
	}	IoStream stream; long curLen;
	public void Read() {
		curRead = stream.ReadAry(curAry);
		curSum += curRead;
		done = curSum == curLen;
		if (done && curRead != curAry.length) // on last pass, readAry may have garbage at end, remove
			curAry = Bry_.Resize(curAry, curRead);
	}
	public void Rls() {stream.Rls();}
}
