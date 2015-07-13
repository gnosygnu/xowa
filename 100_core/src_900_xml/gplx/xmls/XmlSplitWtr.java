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
package gplx.xmls; import gplx.*;
import gplx.ios.*;
public class XmlSplitWtr {
	public Io_url Url() {return url;} Io_url url;
	public XmlSplitWtr Init_(Io_url partDir, byte[] hdr, XmlFileSplitterOpts opts) {
		this.partDir = partDir; this.hdr = hdr; this.opts = opts;
		return this;
	}
	public void Bgn(int partIdx) {
		String partStr = opts.Namer().GenStrIdxOnly(partIdx);
		url = Io_url_.mem_fil_(partStr);
		stream = Io_mgr.I.OpenStreamWrite(url);
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
