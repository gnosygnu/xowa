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
import gplx.xowa.wikis.*;
public class Xob_dump_file {
	public Xow_wiki_domain Wiki_type()	{return wiki_type;} private Xow_wiki_domain wiki_type;
	public String Dump_date()			{return dump_date;} public void Dump_date_(String v) {dump_date = v;}  String dump_date;
	public String Dump_file_type()		{return dump_file_type;} private String dump_file_type;
	public String Server_url()			{return server_url;} private String server_url;
	public String File_url()			{return file_url;} private String file_url;
	public String File_name()			{return file_name;} private String file_name;
	public long File_len()				{return file_len;} long file_len;
	public DateAdp File_modified()		{return file_modified;} DateAdp file_modified;
	public byte[] Wiki_alias()			{return wiki_alias;} private byte[] wiki_alias;
	public Xob_dump_file Ctor(String wiki_domain, String dump_date, String dump_file_type) {
		this.dump_date = dump_date; this.dump_file_type = dump_file_type;
		this.wiki_type = Xow_wiki_domain_.parse_by_domain(Bry_.new_ascii_(wiki_domain));
		this.wiki_alias = Xow_wiki_alias.Build_alias(wiki_type);
		byte[] dump_file_bry = Bry_.new_utf8_(dump_file_type);
		byte dump_file_tid = Xow_wiki_alias.Parse__tid(dump_file_bry);
		byte[] ext = Xob_dump_file_.Ext_xml_bz2;
		switch (dump_file_tid) {
			case Xow_wiki_alias.Tid_page_props: case Xow_wiki_alias.Tid_categorylinks: case Xow_wiki_alias.Tid_image:
				ext = Xob_dump_file_.Ext_sql_gz;
				break;
		}
		this.file_name = String_.new_utf8_(Xob_dump_file_.Bld_dump_file_name(wiki_alias, Bry_.new_utf8_(dump_date), dump_file_bry, ext));
		return this;
	}
	public void Server_url_(String server_url) {
		this.server_url = server_url;
		String dump_dir_url = String_.new_utf8_(Xob_dump_file_.Bld_dump_dir_url(Bry_.new_utf8_(server_url), wiki_alias, Bry_.new_utf8_(dump_date)));
		this.file_url = dump_dir_url + file_name;
	}
	public boolean Connect() {
		gplx.ios.IoEngine_xrg_downloadFil args = Io_mgr._.DownloadFil_args("", Io_url_.Null);
		boolean rv = args.Src_last_modified_query_(true).Exec_meta(file_url);
		if (rv) {
			file_len = args.Src_content_length();
			file_modified = args.Src_last_modified();
			if (file_modified.Timestamp_unix() <= 0) return false;
		}
		return rv;
	}
	public static Xob_dump_file new_(String wiki_domain, String dump_date, String dump_type) {return new Xob_dump_file().Ctor(wiki_domain, dump_date, dump_type);}
}
