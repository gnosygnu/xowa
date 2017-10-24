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
public class XmlSplitWtr {
	public Io_url Url() {return url;} Io_url url;
	public XmlSplitWtr Init_(Io_url partDir, byte[] hdr, XmlFileSplitterOpts opts) {
		this.partDir = partDir; this.hdr = hdr; this.opts = opts;
		return this;
	}
	public void Bgn(int partIdx) {
		String partStr = opts.Namer().GenStrIdxOnly(partIdx);
		url = Io_url_.mem_fil_(partStr);
		stream = Io_mgr.Instance.OpenStreamWrite(url);
		init = true;
	}	boolean init = true; byte[] hdr; XmlFileSplitterOpts opts; Io_url partDir; IoStream stream;
	public void Write(byte[] ary) {
		if (init) {
			stream.WriteAry(hdr);
			init = false;
		}
		stream.WriteAry(ary);
	}
	public void Rls() {stream.Rls();}
}
