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
package gplx.xowa.files.wiki_orig; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*;
public class Xof_wiki_orig_itm {
	public int Id() {return id;} public Xof_wiki_orig_itm Id_(int v) {id = v; return this;} private int id;
	public byte[] Ttl() {return ttl;} public Xof_wiki_orig_itm Ttl_(byte[] v) {ttl = v; return this;} private byte[] ttl;
	public byte Status() {return status;} public Xof_wiki_orig_itm Status_(byte v) {status = v; return this;} private byte status;
	public byte Orig_repo() {return orig_repo;} public Xof_wiki_orig_itm Orig_repo_(byte v) {orig_repo = v; return this;} private byte orig_repo;
	public int Orig_w() {return orig_w;} public Xof_wiki_orig_itm Orig_w_(int v) {orig_w = v; return this;} private int orig_w;
	public int Orig_h() {return orig_h;} public Xof_wiki_orig_itm Orig_h_(int v) {orig_h = v; return this;} private int orig_h;
	public int Orig_ext() {return orig_ext;} public Xof_wiki_orig_itm Orig_ext_(int v) {orig_ext = v; return this;} private int orig_ext;
	public byte[] Orig_redirect() {return orig_redirect;} public Xof_wiki_orig_itm Orig_redirect_(byte[] v) {orig_redirect = v; return this;} private byte[] orig_redirect;
	public static Xof_wiki_orig_itm load_(DataRdr rdr) {
		Xof_wiki_orig_itm rv = new Xof_wiki_orig_itm();
		rv.ttl = rdr.ReadBryByStr(Xof_wiki_orig_tbl.Fld_orig_ttl);
		rv.status = rdr.ReadByte(Xof_wiki_orig_tbl.Fld_status);
		rv.orig_repo = rdr.ReadByte(Xof_wiki_orig_tbl.Fld_orig_repo);
		rv.orig_w = rdr.ReadInt(Xof_wiki_orig_tbl.Fld_orig_w);
		rv.orig_h = rdr.ReadInt(Xof_wiki_orig_tbl.Fld_orig_h);
		rv.orig_ext = rdr.ReadInt(Xof_wiki_orig_tbl.Fld_orig_ext);
		rv.orig_redirect = rdr.ReadBryByStr(Xof_wiki_orig_tbl.Fld_orig_redirect);
		return rv;
	}
	public static final Xof_wiki_orig_itm Null = null;
}
