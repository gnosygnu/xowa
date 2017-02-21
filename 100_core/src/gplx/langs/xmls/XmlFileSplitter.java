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
import gplx.core.consoles.*;
import gplx.core.ios.*;
import gplx.core.texts.*;
public class XmlFileSplitter {
	public XmlFileSplitterOpts Opts() {return opts;} XmlFileSplitterOpts opts = new XmlFileSplitterOpts();
	public byte[] Hdr() {return hdr;} private byte[] hdr;
	public void Clear() {hdr = null;}
	public void Split(Io_url xmlUrl) {
		Io_url partDir = opts.PartDir();
		byte[] xmlEndTagAry = Bry_.new_u8(opts.XmlEnd());
		byte[][] nameAry = XtoByteAry(opts.XmlNames());
		int partIdx = 0;

		// bgn reading file
		XmlSplitRdr rdr = new XmlSplitRdr().Init_(xmlUrl, opts.FileSizeMax());

		// split hdr: includes <root>, xmlNamespaces, and any DTD headers; will be prepended to each partFile
		rdr.Read();
		int findPos = FindMatchPos(rdr.CurAry(), nameAry); if (findPos == String_.Find_none) throw Err_.new_wo_type("could not find any names in first segment");
		byte[] dataAry = SplitHdr(rdr.CurAry(), findPos);
		if (opts.XmlBgn() != null)
			hdr = Bry_.new_u8(opts.XmlBgn());
		byte[] tempAry = new byte[0];
		int newFindPos = FindMatchPosRev(dataAry, nameAry);
		findPos = (newFindPos <= findPos) ? String_.Find_none : newFindPos;
		boolean first = true;

		// split files
		XmlSplitWtr partWtr = new XmlSplitWtr().Init_(partDir, hdr, opts);
		while (true) {
			partWtr.Bgn(partIdx++);
			if (opts.StatusFmt() != null) Console_adp__sys.Instance.Write_str_w_nl(String_.Format(opts.StatusFmt(), partWtr.Url().NameOnly()));
			partWtr.Write(tempAry);
			if (!first) {
				rdr.Read();
				dataAry = rdr.CurAry();
				findPos = FindMatchPosRev(dataAry, nameAry);
			}
			else
				first = false;

			// find last closing node
			while (findPos == String_.Find_none) {
				if (rdr.Done())  {
					findPos = rdr.CurRead();
					break;
				}
				else {
					partWtr.Write(dataAry);
					rdr.Read();
					dataAry = rdr.CurAry();
					findPos = FindMatchPosRev(dataAry, nameAry);
				}
			}
			
			byte[][] rv = SplitRest(dataAry, findPos);
			partWtr.Write(rv[0]);
			tempAry = rv[1];
			boolean done = rdr.Done() && tempAry.length == 0;
			if (!done)
				partWtr.Write(xmlEndTagAry);
			partWtr.Rls();
			if (done) break;
		}
		rdr.Rls();
	}
	public byte[] SplitHdr(byte[] src, int findPos) {
		hdr = new byte[findPos];
		Array_.Copy_to(src, 0, hdr, 0, findPos);
		byte[] rv = new byte[src.length - findPos];
		Array_.Copy_to(src, findPos, rv, 0, rv.length);
		return rv;
	}
	public byte[][] SplitRest(byte[] src, int findPos) {
		byte[][] rv = new byte[2][];
		rv[0] = new byte[findPos];
		Array_.Copy_to(src, 0, rv[0], 0, findPos);
		rv[1] = new byte[src.length - findPos];
		Array_.Copy_to(src, findPos, rv[1], 0, rv[1].length);
		return rv;
	}
	public int FindMatchPos(byte[] src, byte[][] wordAry) {return FindMatchPos(src, wordAry, true);}
	public int FindMatchPosRev(byte[] src, byte[][] wordAry) {return FindMatchPos(src, wordAry, false);}
	int FindMatchPos(byte[] src, byte[][] wordAry, boolean fwd) {
		int[] findAry = new int[wordAry.length];
		for (int i = 0; i < findAry.length; i++)
			findAry[i] = fwd ? -1 : Int_.Max_value;
		for (int i = 0; i < wordAry.length; i++) {							// look at each word in wordAry
			int srcLen = src.length, srcPos, srcEnd, srcDif;
			if (fwd)	{srcPos = 0; srcEnd = srcLen; srcDif = 1;}
			else		{srcPos = srcLen - 1; srcEnd = -1; srcDif = -1;}
			while (srcPos != srcEnd) {										// look at each byte in src
				byte[] ary = wordAry[i];
				int aryLen = ary.length, aryPos, aryEnd, aryDif;
				if (fwd)	{aryPos = 0; aryEnd = aryLen; aryDif = 1;}
				else		{aryPos = aryLen - 1; aryEnd = -1; aryDif = -1;}
				boolean found = true;
				while (aryPos != aryEnd) {									// look at each byte in word
					int lkpPos = srcPos + aryPos;
					if (lkpPos >= srcLen) {found = false; break;}			// outside bounds; exit
					if (ary[aryPos] != src[lkpPos]) {found = false; break;} // srcByte doesn't match wordByte; exit
					aryPos += aryDif;
				}
				if (found) {findAry[i] = srcPos; break;}					// result found; stop now and keep "best" result
				srcPos += srcDif;
			}
		}
		int best = fwd ? -1 : Int_.Max_value;
		for (int find : findAry) {
			if		((fwd && find > best)
				||	(!fwd && find < best)) 
				best = find;
		}
		if (best == Int_.Max_value) best = -1;
		return best;
	}
	byte[][] XtoByteAry(String[] names) {
		byte[][] rv = new byte[names.length][];
		for (int i = 0; i < names.length; i++)
			rv[i] = Bry_.new_u8(names[i]);
		return rv;
	}
}
