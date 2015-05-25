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
package gplx.xowa.apis.xowa.usrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.apis.*; import gplx.xowa.apis.xowa.*;
import gplx.ios.*; import gplx.xowa.files.caches.*;
public class Xoapi_cache implements GfoInvkAble {
	private Xou_cache_mgr cache_mgr;
	public void Init_by_app(Xoa_app app) {this.cache_mgr = app.User().File__cache_mgr();}
	private String Info() {
		cache_mgr.Page_bgn();
		Bry_bfr bfr = Bry_bfr.new_(255);
		KeyVal[] ary = cache_mgr.Info();
		int len = ary.length;
		for (int i = 0; i < len; ++i) {
			KeyVal kv = ary[i];
			bfr.Add_str_a7(kv.Key()).Add_str_a7(": ").Add_str_u8(kv.Val_to_str_or_empty()).Add_byte_nl();
		}
		return bfr.Xto_str_and_clear();
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_fsys_size_min)) 						return cache_mgr.Fsys_size_min() / Io_mgr.Len_mb;
		else if	(ctx.Match(k, Invk_fsys_size_min_)) 					cache_mgr.Fsys_size_min_(Io_size_.To_long_by_msg_mb(m, cache_mgr.Fsys_size_min()));
		else if	(ctx.Match(k, Invk_fsys_size_max)) 						return cache_mgr.Fsys_size_max() / Io_mgr.Len_mb;
		else if	(ctx.Match(k, Invk_fsys_size_max_)) 					cache_mgr.Fsys_size_max_(Io_size_.To_long_by_msg_mb(m, cache_mgr.Fsys_size_max()));
		else if	(ctx.Match(k, Invk_info)) 								return Info();
		else if	(ctx.Match(k, Invk_reduce_to_min)) 						cache_mgr.Reduce(cache_mgr.Fsys_size_min());
		else if	(ctx.Match(k, Invk_reduce_to_zero)) 					cache_mgr.Reduce(0);
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}
	private static final String 
	  Invk_fsys_size_min		= "fsys_size_min"		, Invk_fsys_size_min_		= "fsys_size_min_"
	, Invk_fsys_size_max		= "fsys_size_max"		, Invk_fsys_size_max_		= "fsys_size_max_"
	, Invk_info					= "info"
	, Invk_reduce_to_min		= "reduce_to_min"		, Invk_reduce_to_zero		= "reduce_to_zero"
	;
}
