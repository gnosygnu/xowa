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
package gplx.xowa; import gplx.*;
public class Xoh_href {
	public byte[] Raw() {return raw;} public Xoh_href Raw_(byte[] v) {raw = v; return this;} private byte[] raw;
	public byte[] Wiki() {return wiki;} public Xoh_href Wiki_(byte[] v) {wiki = v; return this;} private byte[] wiki;
	public byte[] Page() {return page;} public Xoh_href Page_(byte[] v) {page = v; return this;} private byte[] page;
	public byte[] Anchor() {return anchor;} public Xoh_href Anchor_(byte[] v) {anchor = v; return this;} private byte[] anchor;
	public byte Tid() {return tid;} public Xoh_href Tid_(byte v) {tid = v; return this;} private byte tid;
	public byte Protocol_tid() {return protocol_tid;} private byte protocol_tid;
	public void Init(byte[] raw, byte protocol_tid) {
		this.raw = raw; this.protocol_tid = protocol_tid;
		wiki = page = anchor = null;
		tid = Tid_null;
	}
	public void Print_to_bfr(Bry_bfr bfr, boolean full) {		// currently used for status bar (not embedded in any html)
		switch (tid) {
			case Xoh_href.Tid_http: case Xoh_href.Tid_file:		// full protocol; embed all; EX: "http://en.wikipedia.org/wiki/A"; "file:///C/dir/file.txt"
				bfr.Add(raw);
				break;
			case Xoh_href.Tid_xowa:
				bfr.Add(page);
				break;
			default: 
				if (full) {																//  "full" can be copied and pasted into firefox url bar
					switch (tid) {
						case Xoh_href.Tid_wiki: case Xoh_href.Tid_site: case Xoh_href.Tid_anchor:
							bfr.Add(wiki);												// add wiki_key;	EX: "en.wikipedia.org"
							bfr.Add(Xoh_href_parser.Href_wiki_bry);						// add wiki_str;	EX: "/wiki/"
							bfr.Add(page);												// add page;		EX: "A"
							if (anchor != null)
								bfr.Add_byte(Byte_ascii.Hash).Add(anchor);				// add anchor;		EX: "#B"
							break;
					}
				}
				else {
					switch (tid) {
						case Xoh_href.Tid_site:
							bfr.Add(wiki).Add_byte(Byte_ascii.Slash);					// add wiki_key;	EX: "en.wikipedia.org/"
							bfr.Add(page);												// add page;		EX: "A"
							break;
						case Xoh_href.Tid_wiki:
							bfr.Add(page);												// add page;		EX: "A"
							break;
						case Xoh_href.Tid_anchor:										// anchor to be added below
							break;
					}
					if (anchor != null)
						bfr.Add_byte(Byte_ascii.Hash).Add(anchor);						// add anchor;		EX: "#B"
				}
				break;
		}
	}
	public static final byte Tid_null = 0, Tid_http = 1, Tid_file = 2, Tid_wiki = 3, Tid_site = 4, Tid_xcmd = 5, Tid_anchor = 6, Tid_xowa = 7;
}
