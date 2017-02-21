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
package gplx.xowa.addons.apps.helps.logs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*; import gplx.xowa.addons.apps.helps.*;
import gplx.langs.mustaches.*;
class Xolog_doc implements Mustache_doc_itm {		
	public Xolog_doc(String log_file, String log_name, byte[] log_data, Xolog_doc_file[] log_files) {
		this.log_file = log_file; this.log_name = log_name;
		this.log_data = log_data; this.log_files = log_files;
	}
	public String Log_file() {return log_file;} private final    String log_file;	// EX: 20160613_165025
	public String Log_name() {return log_name;} private final    String log_name;	// EX: 2016-06-13 16:50:25
	public byte[] Log_data() {return log_data;} private final    byte[] log_data;
	public Xolog_doc_file[] Log_files() {return log_files;} private final    Xolog_doc_file[] log_files;
	public boolean Mustache__write(String key, Mustache_bfr bfr) {
		if		(String_.Eq(key, "log_name"))		bfr.Add_str_u8(log_name);
		else if	(String_.Eq(key, "log_file"))		bfr.Add_str_u8(log_file);
		else if	(String_.Eq(key, "log_data"))		bfr.Add_bry(log_data);
		return false;
	}
	public Mustache_doc_itm[] Mustache__subs(String key) {
		if		(String_.Eq(key, "log_files"))		return log_files;
		return Mustache_doc_itm_.Ary__empty;
	}
}
class Xolog_doc_ {
	public static Xolog_doc New(Io_url log_dir, String log_file) {
		// read fsys
		Io_url[] fils = Io_mgr.Instance.QueryDir_fils(log_dir);
		int len = fils.length;

		// get log_files
		Ordered_hash hash = Ordered_hash_.New();
		for (int i = 0; i < len; ++i) {
			Io_url fil = fils[i];
			String file = fil.NameOnly();
			String name = Xolog_file_utl.To_name(file);
			hash.Add(file, new Xolog_doc_file(file, name));
		}

		// determine cur_file
		String cur_file = log_file;
		if (	cur_file == null		// cur_file is null; happens for plain "Special:XowaLog"
			||	!hash.Has(cur_file)		// cur_file deleted (accessed from history)
			) {
			cur_file = hash.Len() == 0
				? null
				: ((Xolog_doc_file)hash.Get_at(len - 1)).File();
		}
		if (cur_file != null)
			hash.Del(cur_file);			// don't bother showing current item in list
		Xolog_doc_file[] log_files = (Xolog_doc_file[])hash.To_ary_and_clear(Xolog_doc_file.class);

		// get body
		byte[] cur_body = Io_mgr.Instance.LoadFilBry(Xolog_file_utl.To_url_by_file(log_dir, cur_file));
		String cur_name = cur_file == null ? null : Xolog_file_utl.To_name(cur_file);
		return new Xolog_doc(cur_file, cur_name, cur_body, log_files);
	}
}
class Xolog_doc_file implements Mustache_doc_itm {		
	public Xolog_doc_file(String file, String name) {
		this.file = file; this.name = name;
	}
	public String File() {return file;} private final    String file;
	public String Name() {return name;} private final    String name;
	public boolean Mustache__write(String key, Mustache_bfr bfr) {
		if		(String_.Eq(key, "file"))			bfr.Add_str_u8(file);
		else if	(String_.Eq(key, "name"))			bfr.Add_str_u8(name);
		else										return false;
		return true;
	}
	public Mustache_doc_itm[] Mustache__subs(String key) {return Mustache_doc_itm_.Ary__empty;}
}
