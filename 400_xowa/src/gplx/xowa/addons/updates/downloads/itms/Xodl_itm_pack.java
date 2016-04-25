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
package gplx.xowa.addons.updates.downloads.itms; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.updates.*; import gplx.xowa.addons.updates.downloads.*;
import gplx.langs.jsons.*; import gplx.langs.mustaches.*;
import gplx.xowa.addons.updates.downloads.core.*;
public class Xodl_itm_pack implements Mustache_doc_itm, Gfo_download_itm {
	public int Id = -1;				// EX: 1
	public String Wiki = "";		// EX: 'simple.wikipedia.org'
	public String Date = "";		// EX: '2016-04-07'
	public String Type = "";		// EX: 'html' or 'file'
	public String Name = "";		// EX: 'html: complete'
	public long Size = 0;			// EX: 1234
	public String Url = "";			// EX: https://archive.org/download/Xowa_simplewiki_latest/Xowa_simplewiki_2016-04-06_html.7z
	public int Ns = -1;				// EX: 0
	public int Part = -1;			// EX: 1, 2
	public Xodl_itm_pack() {}
	public Xodl_itm_pack(int id, String wiki, String date, String type, int ns, int part, String name, long size, String url) {
		this.Id = id;
		this.Wiki = wiki;
		this.Date = date;
		this.Type = type;
		this.Ns = ns;
		this.Part = part;
		this.Name = name;
		this.Size = size;
		this.Url = url;
	}
	public String Download__src() {return Url;}
	public Io_url Download__trg() {return trg;} private Io_url trg; public void Download__trg_(Io_url v) {this.trg = v;}
	public void To_json(Json_wtr wtr) {
		wtr.Kv_int("id", Id);
		wtr.Kv_str("wiki", Wiki);
		wtr.Kv_str("date", Date);
		wtr.Kv_str("type", Type);
		wtr.Kv_str("name", Name);
		wtr.Kv_long("size", Size);
		wtr.Kv_str("url", Url);
		if (Ns != -1)	wtr.Kv_int("ns", Ns);
		if (Part != -1) wtr.Kv_int("part", Part);
	}
	public void By_json(Json_nde nde) {
		this.Id   = nde.Get_as_int("id");
		this.Wiki = nde.Get_as_str("wiki");
		this.Date = nde.Get_as_str("date");
		this.Type = nde.Get_as_str("type");
		this.Name = nde.Get_as_str("name");
		this.Size = nde.Get_as_long("size");
		this.Url  = nde.Get_as_str("url");
		this.Ns   = nde.Get_as_int_or("ns", -1);
		this.Part = nde.Get_as_int_or("part", -1);
	}
	public boolean Mustache__write(String key, Mustache_bfr bfr) {
		if		(String_.Eq(key, "id"))				bfr.Add_int(Id);
		else if	(String_.Eq(key, "wiki"))			bfr.Add_str_u8(Wiki);
		else if	(String_.Eq(key, "date"))			bfr.Add_str_u8(Date);
		else if	(String_.Eq(key, "type"))			bfr.Add_str_u8(Type);
		else if	(String_.Eq(key, "name"))			bfr.Add_str_u8(Name);
		else if	(String_.Eq(key, "size"))			bfr.Add_long(Size);
		else if	(String_.Eq(key, "url"))			bfr.Add_str_u8(Url);
		else if	(String_.Eq(key, "ns"))				bfr.Add_int(Ns);
		else if	(String_.Eq(key, "part"))			bfr.Add_int(Part);
		else										return false;
		return true;
	}
	public Mustache_doc_itm[] Mustache__subs(String key) {return Mustache_doc_itm_.Ary__empty;}
	public static final    Xodl_itm_pack[] Ary_empty = new Xodl_itm_pack[0];
}
