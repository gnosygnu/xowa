/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx.langs.xmls; import gplx.*; import gplx.langs.*;
import gplx.core.ios.*;
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
