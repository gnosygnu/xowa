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
package gplx.xowa.wikis.data; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
public class Xow_db_layout {
	public Xow_db_layout(int tid) {this.tid = tid;}
	public int Tid()					{return tid;} private final    int tid;
	public boolean Tid_is_all()			{return tid == Tid__all;}
	public boolean Tid_is_all_or_few()		{return tid != Tid__lot;}
	public boolean Tid_is_lot()			{return tid == Tid__lot;}
	public String Key() {
		switch (tid) {
			case Xow_db_layout.Tid__all:		return Key__all;
			case Xow_db_layout.Tid__few:		return Key__few;
			case Xow_db_layout.Tid__lot:		return Key__lot;
			default: 							throw Err_.new_unimplemented();
		}
	}

	public static final int Tid__all = 1, Tid__few = 2, Tid__lot = 3;
	public static final String Key__all = "all", Key__few = "few", Key__lot = "lot";
	public static final    Xow_db_layout
	  Itm_all = new Xow_db_layout(Tid__all)
	, Itm_few = new Xow_db_layout(Tid__few)
	, Itm_lot = new Xow_db_layout(Tid__lot)
	;
	public static Xow_db_layout Get_by_name(String v) {
		if		(String_.Eq(v, Key__all))	return Itm_all;
		else if	(String_.Eq(v, Key__few))	return Itm_few;
		else if	(String_.Eq(v, Key__lot))	return Itm_lot;
		else								throw Err_.new_unimplemented();
	}
}
