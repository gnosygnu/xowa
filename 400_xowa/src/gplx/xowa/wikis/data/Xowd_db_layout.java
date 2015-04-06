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
package gplx.xowa.wikis.data; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
public class Xowd_db_layout {
	public Xowd_db_layout(int tid) {this.tid = tid;}
	public int Tid() {return tid;} private final int tid;
	public boolean Tid_is_all()			{return tid == Const_all;}
	public boolean Tid_is_all_or_few()		{return tid != Const_lot;}
	public boolean Tid_is_lot()			{return tid == Const_lot;}
	public String Name() {
		switch (tid) {
			case Const_all:		return Name_all;
			case Const_few:		return Name_few;
			case Const_lot:		return Name_lot;
			default: 			throw Err_.not_implemented_();
		}
	}
	public static final String Name_all = "all", Name_few = "few", Name_lot = "lot";
	public static final int Const_all = 1, Const_few = 2, Const_lot = 3;
	public static final Xowd_db_layout
	  Itm_all = new Xowd_db_layout(Const_all)
	, Itm_few = new Xowd_db_layout(Const_few)
	, Itm_lot = new Xowd_db_layout(Const_lot)
	;
	public static Xowd_db_layout get_(String v) {
		if		(String_.Eq(v, Name_all))	return Itm_all;
		else if	(String_.Eq(v, Name_few))	return Itm_few;
		else if	(String_.Eq(v, Name_lot))	return Itm_lot;
		else								throw Err_.not_implemented_();
	}
}
